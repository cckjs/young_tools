package com.young.tools.common.util.hash;

import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * SimHash实现 采用了java的BitSet
 * 
 * @author yangy
 * 
 */
public class SimHash {

	private int bitsize;

	private HashFunction hashFunction;
    /**
     * 默认采用64的MD5进行hash
     */
	public SimHash() {
		this(64, Hashing.md5());
	}
    
	public SimHash(int bitsize, HashFunction hashFunction) {
		this.bitsize = bitsize;
		this.hashFunction = hashFunction;
	}

	/**
	 * 计算simHash值
	 * 
	 * @param features
	 * @return
	 */
	public long simHash(Map<String, Integer> features) {
		int[] simHashBit = new int[bitsize];
		long hash = 0;
		BitSet temp = null;
		for (Map.Entry<String, Integer> entry : features.entrySet()) {
			hash = hashFunction.hashString(entry.getKey(),
					Charset.defaultCharset()).asLong();
			temp = BitSet.valueOf(new long[] { hash });
			for (int i = 0; i < bitsize; i++) {
				if (temp.get(i)) {
					simHashBit[i] += entry.getValue();
				} else {
					simHashBit[i] -= entry.getValue();
				}
			}
		}
		BitSet simBitValue = BitSet.valueOf(new long[] { 0 });
		for (int i = 0; i < bitsize; i++) {
			if (simHashBit[i] > 0) {
				simBitValue.set(i, true);
			} else {
				simBitValue.set(i, false);
			}
		}
		return simBitValue.toLongArray()[0];
	}

	/**
	 * 计算海明距离
	 * 
	 * @param simhash1
	 * @param simhash2
	 * @return
	 */
	public int hanMingDistance(long simhash1, long simhash2) {
		BitSet bit1 = BitSet.valueOf(new long[] { simhash1 });
		BitSet bit2 = BitSet.valueOf(new long[] { simhash2 });
		bit1.xor(bit2);
		return bit1.cardinality();
	}

	public static void main(String[] args) {
		SimHash hash = new SimHash(64,Hashing.sha256());
		Map<String, Integer> features = new HashMap<String, Integer>();
		features.put("呵呵", 20);
		features.put("他们", 14);
		features.put("都说", 13);
		features.put("其实", 21);
		long sim1 = hash.simHash(features);

		Map<String, Integer> features1 = new HashMap<String, Integer>();
		features1.put("我们", 16);
		features1.put("都是", 14);
		features1.put("大好人", 13);
		features1.put("哈哈", 20);
		long sim2 = hash.simHash(features1);

		System.out.println(hash.hanMingDistance(sim1, sim2));
	}
}
