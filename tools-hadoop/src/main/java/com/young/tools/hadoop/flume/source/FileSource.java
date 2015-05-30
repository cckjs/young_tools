package com.young.tools.hadoop.flume.source;

import org.apache.flume.Source;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.lifecycle.LifecycleState;

public class FileSource implements Source {

	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public LifecycleState getLifecycleState() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setChannelProcessor(ChannelProcessor channelProcessor) {
		// TODO Auto-generated method stub
		channelProcessor.getSelector();
	}

	public ChannelProcessor getChannelProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

}
