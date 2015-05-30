package com.young.tools.common.util.mysql.replication.bean;

import java.io.Serializable;

public class ShardGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4596792467064674967L;

	private Integer id;

	private String groupName;

	private Integer hashMod;
	
	private Long startId;
	
	private Long endId;

	public Long getStartId() {
		return startId;
	}

	public void setStartId(Long startId) {
		this.startId = startId;
	}

	public Long getEndId() {
		return endId;
	}

	public void setEndId(Long endId) {
		this.endId = endId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getHashMod() {
		return hashMod;
	}

	public void setHashMod(Integer hashMod) {
		this.hashMod = hashMod;
	}

}
