package com.young.tools.hadoop.hbase.common.mapper;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;

public interface HbaseMapper<T> {

	public T mapApi(Result result) throws Exception;

	public Put mapPut(T t) throws Exception;

	public Delete mapDelete(T t) throws Exception;

	public Get mapGet(T t) throws Exception;

	public Scan mapScan(T t) throws Exception;
	
	public String getRowKeyString(Result result);
	
	public String generateRowKeyString(T t);
	
	public String printRowKeyByte(byte[] rowKey);

}
