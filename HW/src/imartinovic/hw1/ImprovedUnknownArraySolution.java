package imartinovic.hw1;

import algs.hw1.arraysearch.*;

/**
 * This is provided as an example to show how you will modify the necessary classes
 * for this assignment.
 * 
 * You do not need to copy or modify this method for this assignment.
 */
public class ImprovedUnknownArraySolution extends UnknownArraySearch {

	/** Construct problem solution for given array. Do not modify this method. */
	public ImprovedUnknownArraySolution(int[][] a) {
		super(a);
	}
	
	/** 
	 * Prepare for computation by assigning bad values. To be computed by 
	 * first invocation of locate().
	 * 
	 */
	public int min = Integer.MAX_VALUE;
	public int max = Integer.MIN_VALUE;
	
	/**
	 * First compute MIN and MAX and filter out immediately based on these values.
	 * This increases efficiency and only requires storing two additional values (min and max).
	 *
	 * If you rename this method to 'locate', then this implementation will override the 
	 * default implementation of locate found in ArraySearch.
	 */
	public int[] locate(int target) {
		int n = this.length();
		
		// On the very first time that locate() is called, compute the min
		// and max. Cost is a full inspection of every square in the nxn array.
		if (min == Integer.MAX_VALUE) {
			for (int r = 0; r < n; r++) {
				for (int c = 0; c <= r; c++) {
					int val = inspect(r,c);
					if (val < min) {
						min = val;
					}
					if (val > max) {
						max = val;
					}
				}
			}
		}
		
		if (target < min) { return null; }
		if (target > max) { return null; }
		
		for (int r = 0; r < n; r++) {
			for (int c = 0; c <= r; c++) {
				if (inspect(r,c) == target) {
					return new int[] { r, c };
				}
			}
		}
		
		return null;  // not found
	}
	
	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		int[][] ar = UnknownArraySearch.create(13);
		new ImprovedUnknownArraySolution(ar).trial();
	}
}
