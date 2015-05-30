package com.young.tools.hadoop.hbase.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.young.tools.hadoop.hbase.common.HTableDataSource;
import com.young.tools.hadoop.hbase.common.HbaseOperate;
import com.young.tools.hadoop.hbase.common.callback.HbaseCallBack;
import com.young.tools.hadoop.hbase.common.mapper.HbaseMapper;

public class HbaseTemplate implements HbaseOperate {

	private static final Logger log = Logger.getLogger(HbaseTemplate.class);

	private HTableDataSource dataSource;

	public HTableDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(HTableDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public <V> void addObject(String tableName, V v, HbaseMapper<V> mapper)
			throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		Put put = mapper.mapPut(v);
		log.info("put is -["+put+"]");
		htable.put(put);
		log.info("put one object to " + tableName+", put info -["+put+"]");
		htable.close();
	}

	public <V> void addObjects(String tableName, List<V> objects,
			HbaseMapper<V> mapper) throws Exception {
		if (CollectionUtils.isEmpty(objects)) {
			return;
		}
		HTableInterface htable = dataSource.getConnection(tableName);
		List<Put> puts = new ArrayList<Put>();
		for (V v : objects) {
			puts.add(mapper.mapPut(v));
		}
		htable.put(puts);
		log.info("put " + puts.size() + " objects to " + tableName);
		htable.close();
	}

	public <V> V getObject(String tableName, V v, HbaseMapper<V> mapper)
			throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		Get get = mapper.mapGet(v);
		log.info("get is -["+get+"]");
		Result result = htable.get(get);
		V r = null;
		if (!result.isEmpty()) {
			r = mapper.mapApi(result);
			log.info("get one objects from " + tableName + ",rowKeyString is-["
					+ mapper.getRowKeyString(result) + "]");
		}
		htable.close();
		return r;
	}

	public <V> List<V> getObjects(String tableName, V object,
			HbaseMapper<V> mapper) throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		Scan scan = mapper.mapScan(object);
		log.info("scan is -["+scan+"]");
		ResultScanner resultScanner = htable.getScanner(scan);
		List<V> result = trasfer(resultScanner, mapper);
		htable.close();
		log.info("get " + result.size() + " objects from " + tableName + "");
		return result;
	}

	private <V> List<V> trasfer(ResultScanner resultScanner,
			HbaseMapper<V> mapper) throws Exception {
		Iterator<Result> it = resultScanner.iterator();
		Result temp = null;
		List<V> result = new ArrayList<V>();
		while (it.hasNext()) {
			temp = it.next();
			result.add(mapper.mapApi(temp));
		}
		return result;
	}

	public <V> void deleteObject(String tableName, V object,
			HbaseMapper<V> mapper) throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		Delete delete = mapper.mapDelete(object);
		log.info("delete is -["+delete+"]");
		htable.delete(delete);
		log.info("delete one object from "+tableName+",rowKeyString is -["+mapper.generateRowKeyString(object)+"] ,delete info -["+delete+"]");
		htable.close();
	}

	public <V> void deleteObjects(String tableName, List<V> objects,
			HbaseMapper<V> mapper) throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		if (CollectionUtils.isEmpty(objects)) {
			return;
		}
		List<Delete> deletes = new ArrayList<Delete>();
		for (V v : objects) {
			deletes.add(mapper.mapDelete(v));
		}
		htable.delete(deletes);
		log.info("delete "+deletes.size()+" objects from "+tableName+"");
		htable.close();
	}

	public void increment(String tableName, byte[] rowKey,
			byte[] column_family, byte[] qualify, long amount) throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		htable.incrementColumnValue(rowKey, column_family, qualify, amount);
		log.info("increment "+amount+" to table="+tableName+",family="+column_family+",qualify="+qualify);
		htable.close();
	}

	public <V> List<V> list(String tableName, byte[] startKey, byte[] stopKey,
			Filter filter, HbaseMapper<V> mapper) throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		Scan scan = new Scan();
		scan.setStartRow(startKey);
		scan.setStopRow(stopKey);
		scan.setFilter(filter);
		log.info("scan is -["+scan+"]");
		ResultScanner resultScanner = htable.getScanner(scan);
		List<V> result = trasfer(resultScanner, mapper);
		htable.close();
		log.info("list "+result.size()+" objects from "+tableName);
		return result;
	}

	public boolean hasRowKey(String tableName, byte[] rowKey) throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		boolean flag = htable.exists(new Get(rowKey));
		htable.close();
		log.info(tableName+" contained key "+Bytes.toString(rowKey) +" flag is -["+flag+"");
		return flag;
	}

	public <V> V execute(String tableName, HbaseCallBack<V> callback)
			throws Exception {
		HTableInterface htable = dataSource.getConnection(tableName);
		log.info("execute call back tableName is -["+tableName+"]");
		V v = callback.doInTable(htable);
		htable.close();
		log.info("execute callback from "+tableName+",result is "+v);
		return v;
	}
}
