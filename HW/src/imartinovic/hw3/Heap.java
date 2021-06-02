package imartinovic.hw3;

import edu.princeton.cs.algs4.StdOut;

/**
 *  The {@code Heap} class provides a static method to constructHeap.
 *  
 *  This is the first step in HeapSort, and all that is being considered for 
 *  Question 1.
 *  
 *  You will need to modify this class to count # of exchanges, 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @modified George Heineman
 */
public class Heap {
	
	static class Stats{			//class containing number of exchanges and comparisons
		int numOfExchanges=0;
		int numOfComparisons=0;
		
		Stats() {
			numOfExchanges = 0;
			numOfComparisons = 0;
		}
	}
	/**
	  * Rearranges the array in ascending order, using the natural order.
	  * @param a the array to be sorted
	  */
	 public static int[] constructHeap(Comparable[] a) {	// return an array of 2 ints, first containing number of comparisons, 
		 													// second number of exchanges while contructing the heap
		 int n = a.length;
		 Stats statistics = new Stats();
		 
		 // construct heap from the raw array of which we know nothing.
		 // This is the focus of Question 1 on the homework.
		 for (int k = n/2; k >= 1; k--) {
			 sink(a, k, n, statistics);
		 }
		 
		 return new int[] {statistics.numOfComparisons, statistics.numOfExchanges};

	 }

	 /***************************************************************************
	  * Helper functions to restore the heap invariant.
	  ***************************************************************************/

	 private static void sink(Comparable[] pq, int k, int n, Stats s) {
		 while (2*k <= n) {
			 int j = 2*k;
			 if (j < n && less(pq, j, j+1, s)) j++;
			 if (!less(pq, k, j, s)) break;
			 exch(pq, k, j, s);
			 k = j;
		 }
	 }

	 /***************************************************************************
	  * Helper functions for comparisons and swaps.
	  * Indices are "off-by-one" to support 1-based indexing.
	  ***************************************************************************/
	 private static boolean less(Comparable[] pq, int i, int j, Stats s) {
		 s.numOfComparisons++;
		 return pq[i-1].compareTo(pq[j-1]) < 0;
	 }

	 private static void exch(Object[] pq, int i, int j, Stats s) {
		 Object swap = pq[i-1];
		 pq[i-1] = pq[j-1];
		 pq[j-1] = swap;
		 s.numOfExchanges++;
	 }

	 // print array to standard output
	 private static void show(Comparable[] a) {
		 for (int i = 0; i < a.length; i++) {
			 StdOut.println(a[i]);
		 }
	 }

 }
