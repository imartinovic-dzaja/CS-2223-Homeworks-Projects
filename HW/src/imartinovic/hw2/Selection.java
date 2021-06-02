package imartinovic.hw2;

import algs.days.day05.FixedCapacityQueue;
import imartinovic.hw2.WordList.Node;

/**
 * N people go to a Casino and everyone wins a little bit of money. You suggest it would be 
 * better if just one person collected all the winnings and you propose the following strategy.
 * 
 * 1. You tell everyone to stand in a circle (yourself included)
 * 2. You ask someone to volunteer to be the start. That is person #1. In clockwise fashion,
 *    everyone in the circle is assigned a number from 1 to N.
 * 3. You pick a number 0 < k < N.
 * 
 * Starting with the starting person, count k people clockwise. The kth person leaves the circle
 * which shrinks by one in size; starting with the next person, again count k people clockwise
 * and that person leaves the circle.
 * 
 * The last one remaining collects all winnings.
 * 
 * Your program must produce a FixedCapacityQueue<Integer> reflecting the order in which people
 * are eliminated. The final item in the queue is the person who collects all winnings.
 * 
 * Your implementation MUST create a Linked List using the Node class.
 * 
 * For new Selection(5, 3).countOff(), the resulting queue should be [3 1 5 2 4] where last person to receive
 * winnings is person #4.
 * 
 * For new Selection(17, 7).countOff(), the resulting queue should be [7 14 4 12 3 13 6 17 11 9 8 10 16 5 15 1 2] where
 * last person to receive winnings is person #2.
 */
public class Selection {
	final int N;      /* Number of people. */
	final int k;      /* Delta to counting. */
	Node node;
	
	/** Construct an instance of the problem with N people choosing by k. */
	public Selection(int N, int k) {
		this.N = N;
		this.k = k;
		this.node = new Node (1);
		Node temp = this.node;
		for (int i = 1; i<= N; ++i) {
			if (i == N) {temp.next = null;}
			else {temp.next = new Node(i+1);
				  temp = temp.next;};
		}
	}
	
	/** Use this node to form the linked list. */
	class Node {
		int person;
		Node next;
		
		public Node(int person) {
			this.person = person;
		}
	}
	
	/**
	 * Method consumes the elements of the queue and outputs them with spaces between elements.
	 * 
	 * No need to worry about a trailing space.
	 * @param result
	 */
	static void output(FixedCapacityQueue<Integer> result) {
		System.out.print("[");
		while(!result.isEmpty()) {
			System.out.print(result.dequeue());
			System.out.print(" ");
		}
		System.out.println("]");
	}
	
	/**
	 * Key implementation for this assignment is to return a queue that contains the person identifiers (integers)
	 * in the order that they were removed. The very last value is the "last man standing" who claims the full lottery winnings.
	 */
	FixedCapacityQueue<Integer> countOff() {
		FixedCapacityQueue<Integer> result = new FixedCapacityQueue<Integer>(N);
		Node temp = null;
		Node prevTemp = null;
		
		for(int i = 0; i < N; i++) {
			for (int j = 0; j < k; j++) {						//this loop is for stepping through the correct number of elements
				if (temp == null) { temp = this.node;}			//temp is only null at the start of the program hence set it to the first element
				else {
					if (prevTemp == null) {						//prevTemp is only null at the start of the program
						if (temp.equals(this.node)) {}			//do nothing if temp points to the first element
						else {prevTemp = temp;}					//otherwise set it to temp
					}
					else {										//otherwise if prevTemp is not null
						if (prevTemp.next==null) {				//if prevTemp is at the end
							prevTemp = this.node;				//set it to the beginning			
						}			
						else {prevTemp = temp;}					//otherwise set it to temp
						
					}
					
					if(temp.next == null) {temp = this.node;}	//if temp is at the end then reset it to the beginning	
					else {temp = temp.next;}					//set temp to point to the next element
					
				}
			}

			result.enqueue(temp.person);
			if(temp.equals(this.node)) {					//if if temp is the first node
				this.node = temp.next;						//re-adjust the first node 
			}
			else {											//otherwise
				prevTemp.next = temp.next;					//remove the temp node
			}

		}
	return result;
	}	
	

	/** Launch the small examples to demonstrate success. */
	public static void main(String[] args) {
		FixedCapacityQueue<Integer> result = new Selection(5, 3).countOff();
		System.out.println("N=5, k=3 should be [3 1 5 2 4]");
		output(result);
		System.out.println();
		result = new Selection(17, 7).countOff();
		System.out.println("N=17, k=7 should be [7 14 4 12 3 13 6 17 11 9 8 10 16 5 15 1 2]");
		output(result);

	}
}
