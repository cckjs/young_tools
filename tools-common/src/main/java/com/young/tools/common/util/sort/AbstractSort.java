package com.young.tools.common.util.sort;

public abstract class AbstractSort<T extends Comparable<T>> implements Sort<T>{

	protected void trasfer(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	protected String toString(T[] array){
		StringBuilder sb = new StringBuilder();
		sb.append("array : [ ");
		for(T t:array){
			sb.append(t.toString()+" ");
		}
		sb.append("]");
		return sb.toString();
	}
}
