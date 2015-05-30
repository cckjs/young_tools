package com.young.tools.common.util.mysql.replication.bean;

import java.io.Serializable;

public class ShardFragment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1433434411429291713L;

	private Integer id;

	private String tableIndex;

	private Long startId;

	private Long endId;

	private Integer shardId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}

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

	public Integer getShardId() {
		return shardId;
	}

	public void setShardId(Integer shardId) {
		this.shardId = shardId;
	}

}
