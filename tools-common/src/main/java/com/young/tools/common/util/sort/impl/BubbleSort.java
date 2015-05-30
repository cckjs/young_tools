package com.young.tools.common.util.sort.impl;

import com.young.tools.common.util.sort.AbstractSort;
import com.young.tools.common.util.sort.Sort;
/**
 * 冒泡排序,一轮排序把大的数据放在最后,以此类推,大数据都一个一个往后冒
 * @author yangy
 *
 * @param <T>
 */
public class BubbleSort<T extends Comparable<T>> extends AbstractSort<T>
		implements Sort<T> {

	public void sort(T[] array) {
         sort(array,SortType.asc);
	}

	public void sort(T[] array, SortType type) {
		boolean flag = SortType.asc == type;
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (flag) {
					if (array[j].compareTo(array[j + 1]) > 0) {
						trasfer(array, j, j + 1);
					}
				} else {
					if (array[j].compareTo(array[j + 1]) < 0) {
						trasfer(array, j, j + 1);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		BubbleSort<Integer> sort = new BubbleSort<Integer>();
		Integer[] array = new Integer[] { 29, 69, 58, 37, 90, 19, 89, 20 };
		System.out.println(sort.toString(array));
		sort.sort(array, SortType.asc);
		System.out.println(sort.toString(array));
	}

}
