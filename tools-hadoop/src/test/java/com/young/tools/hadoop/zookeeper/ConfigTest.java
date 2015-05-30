package com.young.tools.hadoop.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.young.tools.hadoop.zookeeper.operator.ZookeeperTool;

public class ConfigTest {

	public static void main(String[] args) throws IOException, KeeperException,
			InterruptedException {
		ZookeeperTool tool = new ZookeeperTool();
		String zkString = "192.168.1.201:2181";
		int sessionTimeOut = 60 * 1000;
		ZooKeeper zk = new ZooKeeper(zkString, sessionTimeOut, new ConfigWatch());
		new Thread(new Runnable() {
			
			public void run() {
				while(true){
					try {
						Thread.sleep(10*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
			
	}
}
