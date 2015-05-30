package com.young.tools.common.util.hash;

import java.nio.charset.Charset;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class MD5Hash implements Hash{

	private HashFunction hash = Hashing.md5();
	
	public long hash(String key) {
		return hash.hashString(key, Charset.defaultCharset()).asLong();
	}

}
