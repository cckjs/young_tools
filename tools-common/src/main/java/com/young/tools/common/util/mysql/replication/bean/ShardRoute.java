package com.young.tools.common.util.mysql.replication.bean;

import java.io.Serializable;

public class ShardRoute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6048233561249580946L;

	private ShardFragment fragment;

	private ShardDBInfo dbConfig;

	public ShardRoute(ShardFragment fragment,ShardDBInfo dbConfig){
		this.fragment = fragment;
		this.dbConfig = dbConfig;
	}
	
	public ShardFragment getFragment() {
		return fragment;
	}

	public void setFragment(ShardFragment fragment) {
		this.fragment = fragment;
	}

	public ShardDBInfo getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(ShardDBInfo dbConfig) {
		this.dbConfig = dbConfig;
	}

}
