package imartinovic.hw1;

import algs.hw1.arraysearch.NestedArraySearch;
import java.lang.Math;
/**
 * Copy this class into your package, which must have USERID has its root.
 */
public class NestedArraySolution extends NestedArraySearch {
	Integer Q1;
	Integer Q2;
	Integer Q3;								
	/** Construct problem solution for given array. Do not modify this method. */
	public NestedArraySolution(int[][] a) {
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
	 * a nested array:
	 *     0 			* 		*		*		*			*
	 *  (3n-4) 		1	 	*		*		*			*
	 *   	.	(3n-3)		2				*			*			
	 * 		.	 	.			.		3 		*			*			
	 *   	.							.		4			*			
	 *   	.		...					...				...				
	 * (2n-2).						...	   	(n)	 (n-1)		
	 *   
	 * @param index for which we wish to find the row and column number
	 * @param sizeOfNxN size of the nxn triangular array
	 * @return an ordered pair of ints {row, column}, if the index is out of bounds, returns {-1, -1}
	 */
	public int[] findRowAndColBasedOnIndex(int index, int sizeOfNxN) {
		int maxIndex = (sizeOfNxN*(sizeOfNxN+1)/2 - 1);

		int row = -1;
		int col = -1;
		int triangle = -1;						// triangles are zero-based indexed such that the outer most triangle has the lowest index
														// and the innermost triangle the highest index
		int sum = -1;
		int totalNumberOfTriangles = (int) Math.floor((sizeOfNxN -1) /3) + 1;

		
		if(index >= 0 ||  index <= maxIndex) {

			
			for (triangle =  0; triangle < totalNumberOfTriangles; ++triangle) {
				int value = 3*(sizeOfNxN-(1+3*triangle));
				if (value == 0) {				//if a triangle consists only of one element then the formula 3*(n - (1 + 3* triangle)) gives 0 instead of the correct answer 1 for number of elements in the triangle
					sum++;
				}
				else {
					sum += 3*(sizeOfNxN-(1+3*triangle));
				}
				
				if (index <= sum )  {
					if (value == 0) {

					}
					else {
						sum -= value - 1;		//useful for determining index in triangle
					}				
					int indexAlongTriangle = index - sum;		// we figured out the index along the triangle
					int triangleRow = 2 * triangle;
					int triangleColumn =  triangle;
					
					if (indexAlongTriangle < (sizeOfNxN - (1 + 3 * triangle) + 1))  			// index is in the diagonal of the triangle
					{
						int indexAlongDiagonal = indexAlongTriangle;
						row = triangleRow + indexAlongDiagonal;
						col = triangleColumn + indexAlongDiagonal;
					}
					
					else if (indexAlongTriangle < 2* (sizeOfNxN - (1 + 3 * triangle))) {		// index in the horizontal part of triangle

			
						int indexAlongHorizontal = indexAlongTriangle - (sizeOfNxN - (1 + 3 * triangle));
						row = triangleRow +  (sizeOfNxN - (1 + 3 * triangle));
						col = triangleColumn + (sizeOfNxN - (1 + 3 * triangle)) - indexAlongHorizontal;
					}
					
					else {																											// index in the vertical part of triangle
						int indexAlongVertical = indexAlongTriangle - 2* (sizeOfNxN - (1 + 3 * triangle));
						row = triangleRow +  (sizeOfNxN - (1 + 3 * triangle)) - indexAlongVertical;
						col = triangleColumn;

					}
					break;
				}
			}
		}

		return new int[] {row, col};
	}
	
	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		int[][] ar = NestedArraySearch.create(13);
		new NestedArraySolution(ar).trial();
	}
}
