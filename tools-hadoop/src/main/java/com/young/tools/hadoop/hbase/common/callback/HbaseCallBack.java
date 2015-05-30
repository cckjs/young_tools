package com.young.tools.hadoop.hbase.common.callback;

import org.apache.hadoop.hbase.client.HTableInterface;

public interface HbaseCallBack<T> {

	T doInTable(HTableInterface table) throws Exception;
}
