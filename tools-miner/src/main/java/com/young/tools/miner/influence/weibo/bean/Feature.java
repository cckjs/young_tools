package com.young.tools.miner.influence.weibo.bean;

import java.util.List;

public class Feature {

	private List<TimeLineFeature> timelineFeatures;
	
	private UserFeature userFeature;

	public List<TimeLineFeature> getTimelineFeatures() {
		return timelineFeatures;
	}

	public void setTimelineFeatures(List<TimeLineFeature> timelineFeatures) {
		this.timelineFeatures = timelineFeatures;
	}

	public UserFeature getUserFeature() {
		return userFeature;
	}

	public void setUserFeature(UserFeature userFeature) {
		this.userFeature = userFeature;
	}
	
	
}
