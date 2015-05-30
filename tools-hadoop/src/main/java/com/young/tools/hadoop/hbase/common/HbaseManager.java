package com.young.tools.hadoop.hbase.common;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HbaseManager {

	private HBaseAdmin admin;

	public HbaseManager() {
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum", "master");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		try {
			admin = new HBaseAdmin(conf);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}

	public HbaseManager(Configuration conf) {
		try {
			admin = new HBaseAdmin(conf);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}

	private void checkAdmin() throws IOException {
		if (admin == null) {
			throw new IOException(
					"no init HbaseManager or manager is closed,please renew HbaseManger instance");
		}
	}

	public void createTable(String tableName, String[] column_familys)
			throws IOException {
		checkAdmin();
		HTableDescriptor descriptor = new HTableDescriptor(tableName);
		for (String familyName : column_familys) {
			descriptor.addFamily(new HColumnDescriptor(familyName));
		}
		admin.createTable(descriptor);
	}

	public void disableTable(String tableName) throws IOException {
		checkAdmin();
		if (admin.tableExists(tableName)) {
			admin.disableTable(tableName);
		}
	}

	public void enableTable(String tableName) throws IOException {
		checkAdmin();
		if (admin.tableExists(tableName)) {
			admin.enableTable(tableName);
		}
	}

	public void dropTable(String tableName) throws IOException {
		disableTable(tableName);
		if (admin.tableExists(tableName)) {
			admin.deleteTable(tableName);
		}
	}

	public void deleteColumn(String tableName, String[] columns)
			throws IOException {
		disableTable(tableName);
		for (String columnName : columns) {
			admin.deleteColumn(tableName, columnName);
		}
		enableTable(tableName);
	}

	public void addColumn(String tableName, String[] columns)
			throws IOException {
		disableTable(tableName);
		for (String column : columns) {
			admin.addColumn(tableName, new HColumnDescriptor(column));
		}
		enableTable(tableName);
	}

	public HTableDescriptor[] listTable() throws IOException {
		return admin.listTables();
	}

	public void close() throws IOException {
		if (admin != null) {
			admin.close();
		}
		admin = null;
	}

}
