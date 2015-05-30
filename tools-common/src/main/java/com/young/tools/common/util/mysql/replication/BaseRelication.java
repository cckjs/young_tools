package com.young.tools.common.util.mysql.replication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.young.tools.common.util.mysql.replication.bean.DBOperate;
import com.young.tools.common.util.mysql.replication.bean.ShardDBInfo;
import com.young.tools.common.util.mysql.replication.bean.ShardFragment;
import com.young.tools.common.util.mysql.replication.bean.ShardGroup;
import com.young.tools.common.util.mysql.replication.bean.ShardRoute;
import com.young.tools.common.util.mysql.replication.exception.ReplicationException;

public class BaseRelication {

	protected DataSource routeDataSource;

	private static final Object group_lock = new Object();

	private static final Object shard_lock = new Object();

	protected static final Log log = LogFactory.getLog(BaseRelication.class);

	private Map<String, List<ShardGroup>> partition_shardGroup_mapping = new HashMap<String, List<ShardGroup>>();

	private Map<String, List<ShardRoute>> shard_route_mapping = new HashMap<String, List<ShardRoute>>();

	public DataSource getRouteDataSource() {
		return routeDataSource;
	}

	public void setRouteDataSource(DataSource routeDataSource) {
		this.routeDataSource = routeDataSource;
	}

	protected boolean isInScope(String scope, long value) throws Exception {
		Pattern pattern = Pattern.compile("^(\\(|\\[)\\d+,\\s*\\d+(\\)|\\])$");
		Matcher matcher = pattern.matcher(scope);
		if (!matcher.find()) {
			throw new Exception("scope is error--" + scope + "");
		}
		String[] scopes = scope.split(",");
		Double valueF = Double.valueOf(value);
		Double min = Double.valueOf(scopes[0].substring(1));
		if ("(".equals(String.valueOf(scopes[0].charAt(0)))) {
			if (valueF <= min)
				return false;
		} else if ("[".equals(String.valueOf(scopes[0].charAt(0)))) {
			if (valueF < min)
				return false;
		}
		Double max = Double.valueOf(scopes[1].substring(0,
				scopes[1].length() - 1));
		if (")".equals(String.valueOf(scopes[1].charAt(scopes[1].length() - 1)))) {
			if (valueF >= max)
				return false;
		} else if ("]".equals(String.valueOf(scopes[1].charAt(scopes[1]
				.length() - 1)))) {
			if (valueF > max)
				return false;
		}
		return true;
	}

	/**
	 * 根据业务获取某个业务所有的shardGroup select
	 * s_group.id,s_group.name,s_group.hash_mod,g_interval.start_id,g_interval
	 * .end_id from shard_group s_group,shard_group_interval
	 * g_interval,partition partition where partition.id=s_group.partition_id
	 * and s_group.id=g_interval.shard_group_id and g_interval.writable is true
	 * and partition.name='user_business'
	 * 
	 * @param business
	 * @return
	 * @throws SQLException
	 */
	private List<ShardGroup> findShardGroup(String business)
			throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select s_group.id,s_group.name,s_group.hash_mod,g_interval.start_id,g_interval.end_id from shard_group s_group,shard_group_interval g_interval,partition partition  where partition.id=s_group.partition_id and s_group.id=g_interval.shard_group_id and g_interval.writable is true and partition.name=?");
		Connection con = this.getRouteDataSource().getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, business);
		log.info("--sql--[" + sql.toString() + "],params is -name=[" + business
				+ "]");
		ResultSet rs = ps.executeQuery();
		List<ShardGroup> groups = new ArrayList<ShardGroup>();
		ShardGroup group = null;
		while (rs.next()) {
			group = new ShardGroup();
			group.setId(rs.getInt("id"));
			group.setGroupName(rs.getString("name"));
			group.setHashMod(rs.getInt("hash_mod"));
			group.setStartId(rs.getLong("start_id"));
			group.setEndId(rs.getLong("end_id"));
			groups.add(group);
		}
		rs.close();
		ps.close();
		con.close();
		return groups;
	}

	/**
	 * 根据ID的scope获取ShardGroup
	 * 
	 * @param groups
	 * @param bussinessId
	 * @return
	 * @throws Exception
	 */
	protected ShardGroup chooseTargetGroup(List<ShardGroup> groups,
			long bussinessId) throws Exception {
		for (ShardGroup group : groups) {
			if (isInScope("[" + group.getStartId() + "," + group.getEndId()
					+ ")", bussinessId)) {
				return group;
			}
		}
		throw new ReplicationException(
				"can not find ShardGroup ,bussinessId = [" + bussinessId
						+ "] is not in id scope");
	}

	protected List<ShardRoute> findShard(ShardGroup targetGroup, long mod,
			DBOperate operate) throws SQLException {
		String key = targetGroup.getId() + ":" + targetGroup.getStartId() + ":"
				+ targetGroup.getEndId() + ":" + mod + ":" + operate.toString();
		return getShardRoute(targetGroup, key, mod, operate);
	}

	/**
	 * 根据ID的scope获取shard的详细信息 select
	 * fragment.table_index,f_interval.start_id,f_interval.end_id,info.
	 * shard_url,info.shard_user,info.shard_pass from shard shard,shard_fragment
	 * fragment,shard_fragment_interval f_interval,shard_info info where
	 * shard.id = fragment.shard_id and fragment.id = f_interval.fragment_id and
	 * shard.id = info.shard_id and shard_group_id =1 and hash_value like '%0%'
	 * and info.shard_role like '%r%' and f_interval.start_id = 10000 and
	 * f_interval.end_id = 20000
	 * 
	 * @param targetGroup
	 * @param key
	 * @param mod
	 * @param operate
	 * @return
	 * @throws SQLException
	 */
	private List<ShardRoute> getShardRoute(ShardGroup targetGroup, String key,
			long mod, DBOperate operate) throws SQLException {
		synchronized (shard_lock) {
			List<ShardRoute> routes = shard_route_mapping.get(key);
			ShardRoute route = null;
			if (routes == null) {
				routes = new ArrayList<ShardRoute>();
				StringBuilder sql = new StringBuilder();
				sql.append(" select fragment.table_index,f_interval.start_id,f_interval.end_id,info.shard_url,info.shard_user,info.shard_pass,info.shard_role from shard shard,shard_fragment fragment,shard_fragment_interval f_interval,shard_info info where ");
				sql.append(" shard.id = fragment.shard_id and fragment.id = f_interval.fragment_id and shard.id = info.shard_id and shard_group_id =? and hash_value like '%"
						+ mod
						+ "%' and info.shard_role = '"
						+ operate.toString() + "' ");
				sql.append(" and f_interval.start_id = ? and f_interval.end_id = ?");
				Connection con = this.getRouteDataSource().getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString());
				ps.setInt(1, targetGroup.getId());
				ps.setLong(2, targetGroup.getStartId());
				ps.setLong(3, targetGroup.getEndId());
				log.info("--sql--[" + sql.toString()
						+ "],params is -start_id=[" + targetGroup.getStartId()
						+ "] end_id=[" + targetGroup.getEndId()
						+ "],shard_group_id = [" + targetGroup.getId() + "]");
				ResultSet rs = ps.executeQuery();
				ShardFragment fragment = null;
				ShardDBInfo db = null;
				while (rs.next()) {
					fragment = new ShardFragment();
					fragment.setTableIndex(rs.getString("table_index"));
					fragment.setStartId(rs.getLong("start_id"));
					fragment.setEndId(rs.getLong("end_id"));
					db = new ShardDBInfo();
					db.setShardPass(rs.getString("shard_pass"));
					db.setShardUrl(rs.getString("shard_url"));
					db.setShardUser(rs.getString("shard_user"));
					db.setRole(DBOperate.valueOf(rs.getString("shard_role")));
					route = new ShardRoute(fragment, db);
					routes.add(route);
				}
				rs.close();
				ps.close();
				con.close();
				if (CollectionUtils.isEmpty(routes)) {
					throw new ReplicationException(
							"can not find shard,shard is not exist!");
				}
				shard_route_mapping.put(key, routes);
			}
			return routes;
		}

	}

	protected List<ShardGroup> getShardGroups(String business)
			throws SQLException {
		synchronized (group_lock) {
			// 先从缓存中获取数据库集群信息
			List<ShardGroup> groups = partition_shardGroup_mapping
					.get(business);
			// 如果未找到就从数据库路由表中获取,这样做的好处是可以动态的扩展,而不用重启服务,同时只能有一个线程从数据库中获取
			if (groups == null) {
				groups = findShardGroup(business);
				// 判断是否包含business业务的数据库集群
				if (CollectionUtils.isEmpty(groups)) {
					throw new ReplicationException(
							"can not find shard, bussiness = [" + business
									+ "] is not exist");
				}
				partition_shardGroup_mapping.put(business, groups);
			}
			return groups;
		}
	}

}
