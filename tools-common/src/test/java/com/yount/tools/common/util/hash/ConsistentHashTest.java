package com.yount.tools.common.util.hash;

import java.util.ArrayList;
import java.util.List;

import com.young.tools.common.util.hash.ConsistencyHash;

public class ConsistentHashTest {

	public static void main(String[] args) {
		List<Shard> real = new ArrayList<Shard>();
		for(int i=1;i<=5;i++){
			real.add(new Shard("192.168.1."+i,80));
		}
		ConsistencyHash<Shard> cHash = new ConsistencyHash<Shard>(real);
		for(int i=0;i<100;i++){
			System.out.println(cHash.getShard(i+""));
		}
	}
}
