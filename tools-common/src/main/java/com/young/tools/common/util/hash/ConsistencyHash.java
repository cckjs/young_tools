package com.young.tools.common.util.hash;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistencyHash<Shard> {

	private TreeMap<Long, Shard> virtual_nodes;

	private List<Shard> real_nodes;

	private int num_virtual = 100;

	private Hash hash = new MD5Hash();

	public ConsistencyHash(List<Shard> real_nodes) {
		this.real_nodes = real_nodes;
		init();
	}

	public int getNum_virtual() {
		return num_virtual;
	}

	public void setNum_virtual(int num_virtual) {
		this.num_virtual = num_virtual;
	}

	public ConsistencyHash(List<Shard> real_nodes, Hash hash) {
		this.hash = hash;
		this.real_nodes = real_nodes;
		init();
	}

	private void init() {
		virtual_nodes = new TreeMap<Long, Shard>();
		// 构造虚拟节点的环形hash
		for (Shard shard : real_nodes) {
			addNode(shard);
		}
	}

	public void addNode(Shard realShard) {
		for (int i = 0; i < num_virtual; i++) {
			virtual_nodes.put(hash.hash(realShard.toString() + "_" + i), realShard);
		}
	}
	
	public void deleteNode(Shard realShard){
		for (int i = 0; i < num_virtual; i++) {
			virtual_nodes.remove(hash.hash(realShard.toString() + "_" + i));
		}
	}

	public Shard getShard(String key) {
		SortedMap<Long, Shard> tail = virtual_nodes.tailMap(hash.hash(key));
		if (tail.size() == 0) {
			// 返回第一个虚拟节点的真实节点信息
			return virtual_nodes.get(virtual_nodes.firstKey());
		} else {
			// 返回距离hash值最近的节点的真实节点信息
			return tail.get(tail.firstKey());
		}

	}
}
