package com.young.tools.hadoop.zookeeper.operator;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperTool {

	private ThreadLocal<ZooKeeper> connectionPool;

	public ZookeeperTool() {
		connectionPool = new ThreadLocal<ZooKeeper>();
	}

	public ZooKeeper getConnection(String zookeeper, int sessionTimeOut)
			throws IOException {
		ZooKeeper zk = connectionPool.get();
		if (zk == null) {
			zk = new ZooKeeper(zookeeper, sessionTimeOut, new Watcher() {
				// 监控所有被触发的事件
				public void process(WatchedEvent event) {
					System.out.println(event.getType() + "," + event.getPath()
							+ "," + event.getState());
				}
			});
			while (!zk.getState().isConnected()) {
				System.out.println(zk.getState().isConnected());
			}
			connectionPool.set(zk);
		}
		return zk;
	}
	
}
