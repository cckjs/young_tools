package com.young.tools.common.util.random;

import java.util.Random;

public class RandomUtil {

	private static final Random rand = new Random();
	
	public static int randomN(int n){
		return rand.nextInt(n)+1;
	}
	
	public static int random(int n){
		return rand.nextInt(n);
	}
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			System.out.println(randomN(2));
		}
	}
}
