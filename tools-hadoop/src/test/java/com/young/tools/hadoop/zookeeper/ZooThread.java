package com.young.tools.hadoop.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

import com.young.tools.hadoop.zookeeper.operator.ZookeeperTool;

public class ZooThread implements Runnable{

	private ZookeeperTool tool;
	
	private String zkString;
	
	private int sessionTimeOut;
	
	public ZooThread(ZookeeperTool tool,String zkString,int sessionTimeOut){
		this.tool = tool;
		this.zkString = zkString;
		this.sessionTimeOut = sessionTimeOut;
	}
	
	public void run() {
		try {
			ZooKeeper zk1 = tool.getConnection(zkString, sessionTimeOut);
			
			System.out.println(Thread.currentThread().getName()+",zk1="+zk1.getSessionId());
			
			ZooKeeper zk2 = tool.getConnection(zkString, sessionTimeOut);
			System.out.println(Thread.currentThread().getName()+",zk2="+zk2.getSessionId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
