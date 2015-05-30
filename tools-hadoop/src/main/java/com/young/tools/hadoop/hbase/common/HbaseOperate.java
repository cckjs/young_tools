package com.young.tools.hadoop.hbase.common;

import java.util.List;

import org.apache.hadoop.hbase.filter.Filter;

import com.young.tools.hadoop.hbase.common.callback.HbaseCallBack;
import com.young.tools.hadoop.hbase.common.mapper.HbaseMapper;

public interface HbaseOperate {

	public <V> void addObject(String tableName, V v, HbaseMapper<V> mapper)
			throws Exception;

	public <V> void addObjects(String tableName, List<V> objects,
			HbaseMapper<V> mapper) throws Exception;

	public <V> V getObject(String tableName, V v, HbaseMapper<V> mapper) throws Exception;

	public <V> List<V> getObjects(String tableName, V object,
			HbaseMapper<V> mapper)
			throws Exception;

	public <V> void deleteObject(String tableName, V object,
			HbaseMapper<V> mapper) throws Exception;

	public <V> void deleteObjects(String tableName, List<V> objects,
			HbaseMapper<V> mapper) throws Exception;

	public void increment(String tableName, byte[] rowKey,
			byte[] column_family, byte[] qualify, long amount) throws Exception;

	public <V> List<V> list(String tableName, byte[] startKey, byte[] stopKey,
			Filter filter, HbaseMapper<V> mapper) throws Exception;
	
	public boolean hasRowKey(String tableName,byte[] rowKey) throws Exception;
	
	public <V> V execute(String tableName,HbaseCallBack<V> callback) throws Exception;
	
}
