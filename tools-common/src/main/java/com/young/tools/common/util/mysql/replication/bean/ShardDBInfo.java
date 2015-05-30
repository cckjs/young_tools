package com.young.tools.common.util.mysql.replication.bean;

import java.io.Serializable;

public class ShardDBInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3446402833949241336L;

	private String shardUrl;
	
	private String shardUser;
	
	private String shardPass;
	
	private DBOperate role;

	public DBOperate getRole() {
		return role;
	}

	public void setRole(DBOperate role) {
		this.role = role;
	}

	public String getShardUrl() {
		return shardUrl;
	}

	public void setShardUrl(String shardUrl) {
		this.shardUrl = shardUrl;
	}

	public String getShardUser() {
		return shardUser;
	}

	public void setShardUser(String shardUser) {
		this.shardUser = shardUser;
	}

	public String getShardPass() {
		return shardPass;
	}

	public void setShardPass(String shardPass) {
		this.shardPass = shardPass;
	}
	
	
}
