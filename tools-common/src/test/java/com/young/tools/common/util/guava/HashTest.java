package com.young.tools.common.util.guava;

import java.nio.charset.Charset;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HashTest {

	public static void main(String[] args) {
		HashFunction hash = Hashing.md5();
		HashCode hashCode = hash.hashString("中文", Charset.defaultCharset());
		System.out.println(hashCode.asLong());
	}
}
