package com.young.tools.common.util.string;

public class KMPSearch {

	public static int searchKMP(char[] source, char[] pattern) {
		// validation
		if (source == null || source.length == 0 || pattern == null
				|| pattern.length == 0) {
			return -1;
		}

		// get the rollback array.
		int[] rollback = getRollbackArray(pattern);

		// incremental index of pattern. pointing the char to compare with.
		int currMatch = 0;
		int len = pattern.length;
		// i point the char to compare with
		for (int i = 0; i < source.length;) {
			// if current char match
			if ((currMatch == -1) || (source[i] == pattern[currMatch])) {
				/*
				 * then each of the indexes adding by one, moving to the next
				 * char for comparation. notice that if currMatch is -1, it
				 * means the first char in pattern can not be matched. so i add
				 * by one to move on. and currMatch add by one so its value is
				 * 0.
				 */
				i++;
				currMatch++;
				/*
				 * if reaches the end of pattern, then match success, return the
				 * index of first matched char.
				 */
				if (currMatch == len) {
					return i - len;
				}
			} else {
				/*
				 * if current char mismatch, then rollback the next char to
				 * compare in pattern.
				 */
				currMatch = rollback[currMatch];
			}
		}
		return -1;
	}

	private static int[] getRollbackArray(char[] pattern) {
		int[] rollback = new int[pattern.length];
		for (int i = 0; i < pattern.length; i++) {
			rollback[i] = 0;
		}
		rollback[0] = -1;
		for (int i = 1; i < rollback.length; i++) {
			char prevChar = pattern[i - 1];
			int prevRollback = i - 1;
			while (prevRollback >= 0) {
				int previousRollBackIdx = rollback[prevRollback];
				if ((previousRollBackIdx == -1)
						|| (prevChar == pattern[previousRollBackIdx])) {
					rollback[i] = previousRollBackIdx + 1;
					break;
				} else {
					prevRollback = rollback[prevRollback];
				}
			}
		}
		return rollback;
	}
}
