package imartinovic.hw1;

import algs.hw1.arraysearch.RowOrderedArraySearch;

/**
 * Copy this class into your package, which must have USERID has its root.
 */
public class RowOrderedArraySolution extends RowOrderedArraySearch {
	Integer Q1;
	Integer Q2;
	Integer Q3;								
	/** Construct problem solution for given array. Do not modify this method. */
	public RowOrderedArraySolution(int[][] a) {
		super(a);
		Q1 = null;
		Q2 = null;
		Q3 = null;
	}
	
	/** 
	 * For this homework assignment, you need to complete the implementation of this
	 * method.
	 */
	
	
	@Override
	public int[] locate(int target) {
		int N = this.length();
		int low, high;
		
		if (Q1 == null) {		//if it is the first time we are searching the array
			int index =  (N*(N + 1)/2 -1)/4;
			int indexRowAndCol[] = findRowAndColBasedOnIndex(index,N);
			Q1 = inspect(indexRowAndCol[0] , indexRowAndCol[1]);				//find Q1
			
			index =  (N*(N + 1)/2 -1)/2;
			indexRowAndCol = findRowAndColBasedOnIndex(index,N);
			Q2 = inspect(indexRowAndCol[0] , indexRowAndCol[1]);				//find Q2
			
			index =  (N*(N + 1)/2 -1)*3/4;
			indexRowAndCol = findRowAndColBasedOnIndex(index,N);
			Q3 = inspect(indexRowAndCol[0] , indexRowAndCol[1]);				//find Q3

		}
		if (target <= Q1) {			//target is among the 25% smallest values
			low = 0;
			high = (N*(N + 1)/2 -1)/4;
		}
		else if (target <= Q2) { 	//target is among the 25-50% values
			low = (N*(N + 1)/2 -1)/4 + 1;
			high = (N*(N + 1)/2 -1)/2;
		}
		else if (target <= Q3) {		//target is among the 50-75% values
			low = (N*(N + 1)/2 -1)/2 + 1;
			high = (N*(N + 1)/2 -1)*3/4;
		}	
		
		else {
			low = (N*(N + 1)/2 -1)*3/4 + 1;
			high =  N*(N + 1)/2 -1;	
		}
		
		while (low <= high ) {
			 int mid = (high + low)/2;
			 int midRowAndCol[] = findRowAndColBasedOnIndex(mid, N);
			 int row = midRowAndCol[0];
			 int col = midRowAndCol[1];			 

			 int value = inspect(row, col);
			 		 
			if (value > target) {	high = mid-1; }					//index was too big, decrease the high index
			else if (value < target) {	low = mid +1;	}		//index was too small, increase the low index
			else {
								return new int[] {row, col}; 
					}
		}
		return null;
	}

	/**
	 * Returns the row and column of an element in a lower triangular array given its index if the array elements are indexed as 
	 * a row ordered array
	 *  0 * * * * * * 
	 *  1 2 * * * * * 
	 *  3 4 5 * * * * 
	 *  6 7 8 9 * *
	 *  etc.
	 * @param index for which we wish to find the row and column number
	 * @param sizeOfNxN size of the nxn triangular array
	 * @return an ordered pair of ints {row, column}, if the index is out of bounds, returns {-1, -1}
	 */
	public int[] findRowAndColBasedOnIndex(int index, int sizeOfNxN) {
		int maxIndex = (sizeOfNxN*(sizeOfNxN+1)/2 - 1);
		int row = -1;
		int col = -1;
		
		if(index >= 0 ||  index <= maxIndex)  {
			for (int i = 0; i <= sizeOfNxN; ++i) {				
				if ( i * ( i +  1)  > (2 * index)) {
					row = i -1;
					col = index - (row * (row  + 1) / 2);
					break;
				}
			}
		}

		return new int[] {row, col};
	}
	
	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		int[][] ar = RowOrderedArraySearch.create(13);
		new RowOrderedArraySolution(ar).trial();
	}
}
