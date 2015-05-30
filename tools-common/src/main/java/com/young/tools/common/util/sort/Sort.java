package com.young.tools.common.util.sort;


public interface Sort<T extends Comparable<T>> {

	public enum SortType{
		asc,desc;
	}
	
	public void sort(T[] array);
	
	public void sort(T[] array,SortType type);
	
}
