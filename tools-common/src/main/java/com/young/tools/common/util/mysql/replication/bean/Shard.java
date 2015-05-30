package com.young.tools.common.util.mysql.replication.bean;

import java.io.Serializable;
import java.util.List;

public class Shard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8354412722707209786L;

	private Integer id;

	private String shardName;

	private String hashValue;

	private Integer shardGroupId;

	private List<ShardFragment> fragments;
	
	private ShardDBInfo dbConfig;
	
	public List<ShardFragment> getFragments() {
		return fragments;
	}

	public void setFragments(List<ShardFragment> fragments) {
		this.fragments = fragments;
	}

	public ShardDBInfo getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(ShardDBInfo dbConfig) {
		this.dbConfig = dbConfig;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShardName() {
		return shardName;
	}

	public void setShardName(String shardName) {
		this.shardName = shardName;
	}

	public String getHashValue() {
		return hashValue;
	}

	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}

	public Integer getShardGroupId() {
		return shardGroupId;
	}

	public void setShardGroupId(Integer shardGroupId) {
		this.shardGroupId = shardGroupId;
	}
}
