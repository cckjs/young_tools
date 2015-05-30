package com.young.tools.miner.influence.weibo.bean;

public class TimeLineFeature {
	
	private int repostNum;
	
	private int commentNum;
	
	private String timelineId;
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getRepostNum() {
		return repostNum;
	}

	public void setRepostNum(int repostNum) {
		this.repostNum = repostNum;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public String getTimelineId() {
		return timelineId;
	}

	public void setTimelineId(String timelineId) {
		this.timelineId = timelineId;
	}
	
	public double timeLineInfluence(){
		return Math.pow(repostNum, 1.0/3)+Math.pow(commentNum, 0.5);
	}
}
