package com.young.tools.common.util.sort.impl;

import com.young.tools.common.util.sort.AbstractSort;
import com.young.tools.common.util.sort.Sort;

/**
 * 快速排序,原理,初始化一个middle,然后从两边开始遍历,找到一个大于middle的元素和一个小于middle的元素,然后交换
 * 
 * @author yangy
 * 
 * @param <T>
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSort<T>
		implements Sort<T> {

	public void sort(T[] array) {
		sort(array, SortType.asc);
	}

	public void sort(T[] array, SortType type) {
		middle(array, type, 0, array.length - 1);
	}

	private void middle(T[] array, SortType type, int low, int high) {
		T middle = array[low];
		int l = low;
		int h = high;
		while (l < h) {
			System.out.println("l=" + l + ",h=" + h + ",low=" + low + ",high=" + high);
			if (SortType.asc == type) {
				while (l < h && array[h].compareTo(middle) > 0) {
					h--;
				}
				while (l < h && array[l].compareTo(middle) < 0) {
					l++;
				}
			} else {
				while (l < h && array[h].compareTo(middle) < 0) {
					h--;
				}
				while (l < h && array[l].compareTo(middle) > 0) {
					l++;
				}
			}
			T temp = array[h];
			array[h] = array[l];
			array[l] = temp;
			System.out.println(toString(array));
		}
		//System.out.println(toString(array));
		if (l > low) {
			middle(array, type, low, l - 1);
		}
		if (high > h) {
			middle(array, type, h + 1, high);
		}
	}

	public static void main(String[] args) {
		QuickSort<Integer> sort = new QuickSort<Integer>();
		Integer[] array = new Integer[] { 39, 69,69, 58, 29, 90, 19, 89, 20 };
		System.out.println(sort.toString(array));
		sort.sort(array, SortType.asc);
		System.out.println(sort.toString(array));
	}
}
