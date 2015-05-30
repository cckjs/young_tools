package com.young.tools.common.util.bloom;

public class TestBloom {

	public static void main(String[] args) {
		BloomFilter filter = new BloomFilter();
		String value = "xkeyideal@gmail.com";

		filter.addValue(value);

		System.out.println(filter.contains(value));
		System.out.println(filter.contains("yangyong"));
		filter.addValue("yangyong");
		long start = System.currentTimeMillis();

		for (int i = 0; i < 1000; i++) {
			if (!filter.contains(i + ""))
				filter.addValue(i + "");
		}
		System.out.println(filter.getBit_size());
		SerializableBloom.write(filter, "E:\\bloomfilter.obj");
		filter = null;
		filter = SerializableBloom.read("E:\\bloomfilter.obj");
		System.out.println(filter.contains("yangyong"));
		long end_1 = System.currentTimeMillis();
		System.out.println("--time 1 -[" + (end_1 - start) + "]");
		for (int i = 0; i < 10000; i++) {
			filter.contains(i + "");
		}
		long end_2 = System.currentTimeMillis();
		System.out.println("--time 2 -[" + (end_2 - end_1) + "]");
		System.out.println(filter.getBit_size());
		
		
	}
}
