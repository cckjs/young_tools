package com.young.tools.hadoop.zookeeper.configmanager;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
/**
 * 配置中心client端
 * @author yangy
 *
 */
public class ConfigWatcher implements Watcher, Runnable {
    
	private ZooKeeper zooKeeper;
	private String znode;

	public void connect(String zkString, String znode) throws IOException,
			KeeperException, InterruptedException {
		zooKeeper = new ZooKeeper(zkString, 10 * 6000, this);
		this.znode = znode;
		this.zooKeeper.exists(znode, true);
	}
    //监控到配置服务端的数据变化后进行处理，这个主要是修改client端缓存的配置信息
	public void process(WatchedEvent event) {
		System.out.println(Thread.currentThread().getName()+","+event.toString());
		try {
			Stat stat = this.zooKeeper.exists(znode, true);
			System.out.println(Thread.currentThread().getName()+","+new String(zooKeeper.getData(event.getPath(), this, stat)));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    /**
     * 这个线程主要是为了不让主线程退出,因为退出的话session就断掉了,就无法监控了
     */
	public void run() {
		try {
			synchronized (this) {
				while (true) {
					System.out.println(Thread.currentThread().getName()+","+"---wait---");
					wait();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
