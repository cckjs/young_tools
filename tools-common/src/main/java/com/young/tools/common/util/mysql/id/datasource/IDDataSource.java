package com.young.tools.common.util.mysql.id.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class IDDataSource {

	private String dsName;

	private DataSource dataSource;

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
