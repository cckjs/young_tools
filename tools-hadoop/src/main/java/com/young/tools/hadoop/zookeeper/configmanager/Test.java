package com.young.tools.hadoop.zookeeper.configmanager;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class Test {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ConfigWatcher zw1 = new ConfigWatcher();
		zw1.connect("192.168.1.201:2181", "/config");
		ConfigWatcher zw2 = new ConfigWatcher();
		zw2.connect("192.168.1.201:2181", "/config");
		new Thread(zw1).start();
		new Thread(zw2).start();
		ConfigCenter cc = new ConfigCenter("192.168.1.201:2181", "/config");
		cc.updateConfig("a");
		cc.updateConfig("b");
		cc.updateConfig("c");
		cc.updateConfig("d");
	}
}
