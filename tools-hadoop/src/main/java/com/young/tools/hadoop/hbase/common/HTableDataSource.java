package com.young.tools.hadoop.hbase.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;

public class HTableDataSource {

	private HTablePool tablePool;

	public HTableDataSource() {
		this("127.0.0.1", "2181", 10);
	}

	public HTableDataSource(String zookeeper, String zkPort, int maxSize) {
		Configuration configuration = new Configuration();
		configuration.set("hbase.zookeeper.quorum", zookeeper);
		configuration.set("hbase.zookeeper.property.clientPort", zkPort);
		tablePool = new HTablePool(configuration, maxSize);
	}

	public HTableInterface getConnection(String tableName) {
		return tablePool.getTable(tableName);
	}

	public HTablePool getTablePool() {
		return tablePool;
	}
}
