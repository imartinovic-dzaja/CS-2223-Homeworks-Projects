package imartinovic.hw1;

import algs.hw1.arraysearch.*;

/**
 * This is provided as an example to show how you will modify the necessary classes
 * for this assignment.
 * 
 * You do not need to copy or modify this method for this assignment. You may modify the 
 * constant used in 
 */
public class UnknownArraySolution extends UnknownArraySearch {

	/** Construct problem solution for given array. Do not modify this method. */
	public UnknownArraySolution(int[][] a) {
		super(a);
	}
	
	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		int[][] ar = UnknownArraySearch.create(13);
		new UnknownArraySolution(ar).trial();
	}
}
