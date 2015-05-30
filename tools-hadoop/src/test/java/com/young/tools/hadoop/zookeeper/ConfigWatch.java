package com.young.tools.hadoop.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ConfigWatch implements Watcher{

	private ZooKeeper zk;
	
	public ConfigWatch() throws IOException{
		this.zk = new ZooKeeper("192.168.1.201:2181", 10*1000, this);
	}
	
	public void process(WatchedEvent event) {
	
		System.out.println(event.toString());
		try {
			Stat stat = zk.exists("/config", true);
			System.out.println(new String(zk.getData("/config", true, stat)));
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
