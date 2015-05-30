package com.young.tools.common.util.bloom;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.BitSet;

/**
 * BloomFilter
 * 
 * @author yangy
 * 
 */
public class BloomFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6341636359591748657L;
	private int bit_size = Integer.MAX_VALUE;// 布隆过滤器的比特长度
	private final Integer[] seeds = new Integer[]{3,5,7,11,13,31,37,61};// 这里要�?取质数，能很好的降低错误�?
	private BitSet bits = new BitSet(bit_size);
	private SimpleHash[] func = new SimpleHash[seeds.length];

	public BloomFilter() {
		for (int i = 0; i < seeds.length; i++) {
			func[i] = new SimpleHash(bit_size, seeds[i]);
		}
	}
	
	public int getBit_size() {
		return bit_size;
	}

	public void setBit_size(int bit_size) {
		this.bit_size = bit_size;
	}

	public void addValue(String value) {
		if (value != null)
			for (SimpleHash f : func)
				// 将字符串value哈希�?个或多个整数，然后在这些整数的bit上变�?
				bits.set(f.hash(value), true);
	}

	public boolean contains(String value) {
		if (value == null)
			return false;
		boolean ret = true;
		for (SimpleHash f : func) {
			ret = ret && bits.get(f.hash(value));
			if (!ret) {
				return ret;
			}
		}
		return ret;
	}
}
