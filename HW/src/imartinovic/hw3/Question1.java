package imartinovic.hw3;
import java.util.Random;

import edu.princeton.cs.algs4.StdRandom;

/**
 * This is the template code for question 1.
 *
 * Be sure to Explain whether the empirical results support the proposition.
 *
 */
public class Question1 {
	public static void main(String[] args) {
		
		System.out.println("N\tMaxComp\tMaxExch");
		for (int N = 16; N<=512; N*=2) {												// for N in 16 .. 512
			int maxComparisons = 0;														//keep track of maximum # of comparisons
			int maxExchanges = 0;														//keep track of maximum # of exchanges
			for (int T = 1; T<=100; ++T) {												//for each N, do T=100 trials you want to keep track of 
				Double[] array = new Double[N];											//create our input array for our constructHeap
				for (int i = 0; i<N; ++i) {												//fill the array
					array[i] = StdRandom.uniform();										//using a random number generator
				}
				Heap heap = new Heap();													//create our heap
				int[] results = heap.constructHeap(array);								//store the # of comparisons and exchanges in results
				if (results[0] > maxComparisons) {maxComparisons = results[0];}			//update maxComparisons if necessary
				if (results[1] > maxExchanges) {maxExchanges = results[1];}				//update maxExchanges if necessary
			}
			System.out.printf("%d\t%d\t%d\n", N, maxComparisons, maxExchanges);
		}
		

		
		//   Make sure you output for each N the maximum values you saw
		//   in a table like...
		//
		//       N   MaxComp    MaxExch
		//       16  22         8
		//     .....
	}
}
