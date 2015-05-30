package com.young.tools.hadoop.zookeeper.configmanager;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
/**
 * 配置中心代码
 * @author yangy
 *
 */
public class ConfigCenter implements Watcher {
    //zk client
	private ZooKeeper zk;
    //配置目录
	private String configNode;
    
	public ConfigCenter(String zkString, String configNode) throws IOException,
			KeeperException, InterruptedException {
		//自己就是watcher
		this.zk = new ZooKeeper(zkString, 10 * 6000, this);
		this.configNode = configNode;
		//主要是看配置节点是否存在,如果一定存在的话 这句可以不要
		Stat state = zk.exists(configNode, true);
		if (state == null) {
			zk.create(configNode, "initdata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
		}
	}

	//一旦监控到节点变化后,就会执行这个,可以在里面判断event的type来处理相应的事件
	public void process(WatchedEvent event) {
		System.out.println(Thread.currentThread().getName()+","+event.toString());
		try {
			//可以拿出来当前配置中心的数据来做一些逻辑
			Stat stat = this.zk.exists(configNode, true);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    //修改配置数据
	void updateConfig(String configData) {
		try {
			Stat s = this.zk.exists(this.configNode, true);
			this.zk.setData(this.configNode, configData.getBytes(),
					s.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
