package com.young.tools.common.util.sort.impl;

import com.young.tools.common.util.sort.AbstractSort;
import com.young.tools.common.util.sort.Sort;

/**
 * 插入排序,原理 把数据分成两部分,前部分有序,然后从分割线开始把后面的数据插入到有序的部分当中去
 * 
 * @author yangy
 * 
 * @param <T>
 */
public class InsertSort<T extends Comparable<T>> extends AbstractSort<T>
		implements Sort<T> {

	public void sort(T[] array) {
		sort(array, SortType.asc);
	}

	public void sort(T[] array, SortType type) {
		boolean flag = SortType.asc == type;
		for (int i = 1; i < array.length; i++) {
			for (int j = 0; j < i; j++) {
				if (flag) {
					if (array[j].compareTo(array[i]) > 0) {
						trasfer(array, i, j);
					}
				} else {
					if (array[j].compareTo(array[i]) < 0) {
						trasfer(array, i, j);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		InsertSort<Integer> sort = new InsertSort<Integer>();
		Integer[] array = new Integer[] { 29, 69, 58, 37, 90, 19, 89, 20 };
		System.out.println(sort.toString(array));
		sort.sort(array,SortType.desc);
		System.out.println(sort.toString(array));
	}

}
