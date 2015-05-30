package com.young.tools.common.util.mysql.id;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.young.tools.common.util.mysql.id.datasource.IDDataSource;

/**
 * sequence_1表的stub字段必须建立为唯一索引,否则不能实现,而且这两个sql必须顺序执行(因为select
 * last_insert_id()是一个全局的函数) replace into sequence_1(stub) values('a'); select
 * last_insert_id(); 所以代码中还是要加锁,使获取ID的服务在同一个库中顺序执行
 * 
 * @author yangy
 * 
 */

public class IDFactory extends BaseIdGenerator {

	
	public Long getId(String tableName) throws SQLException {
		IDDataSource dataSource = dsPool.getRDBMSDataSource();
		Connection con = dataSource.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		Long id = null;
		synchronized (dataSource) {
			sql.append("replace into sequence_" + tableName
					+ "(stub) values('a')");
			stmt = con.createStatement();
			stmt.execute(sql.toString());
			sql.setLength(0);
			sql.append("select last_insert_id() as id");
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				id = rs.getLong("id");
				rs.close();
				stmt.close();
				con.close();
			} else {
				rs.close();
				stmt.close();
				con.close();
				throw new SQLException(
						"generate id error,no query result from ["
								+ "select last_insert_id() as id" + "]");
			}
			return id;
		}
	}

}
