package com.young.tools.common.util.bloom;

import java.io.Serializable;

public class SimpleHash implements Hash,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 424418600749314925L;
	private int cap;
	private int seed;

	public SimpleHash(int cap, int seed) {
		this.cap = cap;
		this.seed = seed;
	}

	public int hash(String value) {// 字符串哈希，选取好的哈希函数很重�?
		int result = 0;
		int len = value.length();
		for (int i = 0; i < len; i++) {
			result = seed * result + value.charAt(i);
		}
		return (cap - 1) & result;
	}
}
