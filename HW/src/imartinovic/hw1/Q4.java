package imartinovic.hw1;

import edu.princeton.cs.algs4.StopwatchCPU;
import algs.days.day04.FixedCapacityStack;

/**
 * Copy this class into your USERID.hw1 and modify accordingly.
 * 
 * You only have to modify the first two functions:
 * 
 *    1. fibonacci(FixedCapacityStack<Long> stack) 
 *    2. gcd(FixedCapacityStack<Long> stack) 
 */
public class Q4 {
	
	/** 
	 * This function takes in a stack that contains a single long value, n, which represents the
	 * desired Fibonacci value to return.
	 * 
	 * Note that F0 = 1 and F1=1. The next few Fibonacci numbers are 2, 3, 5, 8, 13, 21, ... 
	 * 
	 * When this function returns, the stack is empty and the value returned is equal to Fn where n
	 * is the value that was originally on the stack.
 	 * 
 	 * Complete this function, starting from the bare skeleton provided below. 
	 * 
	 * 1. You can't create any additional variables in this method (you can only use result, val and stack).
	 * 2. You cannot call any other function other than push and pop.
	 */
	static long fibonacci(FixedCapacityStack<Long> stack) {
		long result = 0;
		while (!stack.isEmpty()) { 
			long val = stack.pop();
			if (val ==1 || val ==0) { result = 1;}	//correctly handle cases for F(0) and F(1)
			else {
				for(int i = 2; i<val; ++i) {
					stack.push((long)(-1));	//fill the stack with -1's for all the calculations we are missing
				}
				stack.push((long)1);		//fill the top of the stack with 1 for F(1)
				stack.push((long)1);		//fill the top of the stack with 1 for F(0)
			}
			while(!stack.isEmpty()) {
				val = stack.pop();			//store F(n-1) into val
				result = stack.pop() + val;	//store F(n) into result
				if(stack.isEmpty()) {}	//if no -1's are remaining we have our result, notice this terminates both while loops
				else{
				stack.pop();			//otherwise remove a -1 from the stack
				stack.push(val);		//push  F(n-1) onto the stack
				stack.push(result);}	//push F(n) onto the stack
			}
		}
				
		return result;
	}
	
	/**
	 * Given two long values on the stack (call them A on the top, and B just below it), return the greatest common 
	 * divisor of A and B and make sure stack is empty when done.
	 * 
	 * Complete this function, starting from the bare skeleton provided below. 
	 * 
	 * 1. You can't create any additional variables in this method (you can only use a, b and stack).
	 * 2. You cannot call any other function other than push and pop.
	 */
	static long gcd(FixedCapacityStack<Long> stack) {
		
		while (!stack.isEmpty()) { 
			long a = stack.pop();
			long b = stack.pop();

			if (b != 0) {
				stack.push(a % b);
				stack.push(b);
			}
			else {
				return a;
			}
		}

		// should never get here
		throw new RuntimeException("Invalid input provided to function.");
	}
	
	// YOU DO NOT NEED TO MODIFY ANYTHING BELOW THIS LINE
	// ---------------------------------------------------------------------------------
	/** Number of trials to run. */
	static int NUM_TRIAL = 10;

	/** Standard recursive implementation. */
	static long fibonacci(long n) {
		// base case
		if (n == 0 || n == 1) { return 1; }
		
		return fibonacci(n-1) + fibonacci(n-2);
	}
	
	/** Standard recursive implementation of gcd. */
	static long gcd(long a, long b) {
		// base case
		if (b == 0) { return a; }
		
		return gcd(b, a % b);
	}
	
	/**
	 * Run trials to confirm Fibonacci implementation works.
	 */
	static void fibonacciTrial() {
		System.out.println(String.format(" N\t\t  Fn\tTime"));
		for (long n = 1; n < 45; n++) {
			double minTime = Integer.MAX_VALUE;
			long val = -1;
			
			// compute best time of NUM_TRIAL trials. If you are not certain
			// that the reported times follow a normal distribution, this is perhaps
			// a more accurate way to benchmark performance.
			for (int trial = 0; trial < NUM_TRIAL; trial++) {
				FixedCapacityStack<Long> stack = new FixedCapacityStack<>(60);
				stack.push(n);
				StopwatchCPU base = new StopwatchCPU();
				val = fibonacci(stack);
				double newTime = base.elapsedTime();
				if (newTime < minTime) { minTime = newTime; }
			}
			
			long referenceVal = fibonacci(n);
			if (val != referenceVal) {
				System.err.println(String.format("Your result of %d doesn't match %d", val, referenceVal));
			}
			
			// report results. Last column is time.
			System.out.println(String.format("%2d\t%12d\t%f", n, val, minTime));
		}
	}
	
	/**
	 * Run 1000 trials to confirm your gcd implementation matches reference implementation.
	 */
	static void gcdTrial() {
		for (long n = 1; n < 1000; n++) {
			long a = (long) (Math.pow(2, 32) * Math.random());
			long b = (long) (Math.pow(2, 32) * Math.random());
			
			// push in reverse order so the receiving function will first pop off A and then B
			FixedCapacityStack<Long> stack = new FixedCapacityStack<>(60);
			stack.push(b);
			stack.push(a);
			
			long g = gcd(stack);    // this is your implementation
			long g2 = gcd(a, b);    // this is reference implementation
			if (g != g2) {
				System.err.println(String.format("Your result of %d doesn't match %d", g, g2));
			} else {
				// Thanks to Aditya Kumar for patching this defect.
				System.out.println(String.format("%2d\t%12d\t%d", a, b, g));
			}
		}
	}
	
	/**
	 * Run sample trial of performance of code.
	 * 
	 * Run a number of trials and record the minimum performance for each n.
	 * 
	 * 1. What is your estimate for minTime(n+1)/minTime(n) as n increases? 
	 * 2. (Bonus) Much like the fibonacci numbers, this ratio
	 * @param args
	 */
	public static void main(String[] args) {
		fibonacciTrial();
		gcdTrial();
	}
}
