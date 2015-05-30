package com.young.tools.miner.influence.weibo.bean;

import java.util.Date;

public class UserFeature {

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private static final long dayInterval = 1000*60*60*24;
	
	private String uid;

	private int publishCount;

	private int commentCount;

	private Date startTime;
	
	private Date endTime;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getPublishCount() {
		return publishCount;
	}

	public void setPublishCount(int publishCount) {
		this.publishCount = publishCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public double getDayInterval() {
		return (endTime.getTime()-startTime.getTime())/dayInterval;
	}
	
	public double userFeatureInfluence(){
		return (publishCount+commentCount)*1.0/getDayInterval();
	}

}
