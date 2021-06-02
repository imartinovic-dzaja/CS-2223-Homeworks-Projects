package imartinovic.hw3;

import java.io.IOException;

import imartinovic.hw2.TaleOfTwoCitiesExtractor;

/** 
 * Copy this class into your USERID.hw3 package and complete
 */
public class Question3 {
	
	public static void main(String[] args) throws java.io.IOException {
		
		// First Construct the Binary Search Tree from these Strings where
		// the associated value is the total number of times the key appeared
		// in "The Tale Of Two Cities".
		BST bt = new BST();
/*		
		bt.put("d", 2003);
		bt.put("g", 40);
		bt.put("e", 20);
		bt.put("h", 1);
		bt.put("f", 1);
		bt.put("x", 1);
		bt.put("y", 1);
		bt.put("z", 1);


*/
		for (int i = 1; i<=45; ++i) {
			try {
				for (String s : new TaleOfTwoCitiesExtractor(i)) {
					if (bt.get(s) == null) {							//if 1 data Structure contains it, then all of them contain the element
						bt.put(s,1);
					}
					else {
						int freq = bt.get(s) + 1;					//all data structures should have the same number of frequencies for the same key
						bt.put(s,freq);
					}
					
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
/**/	
		System.out.println("Top ten most frequent words\n");
		for(int i = 0; i<10; ++i) {
			String mostFrequentWord = bt.mostFrequent(bt.root).key;
			int mostFrequentCount = bt.mostFrequent(bt.root).count;

			System.out.println(String.format("%s\t%d", mostFrequentWord, mostFrequentCount));
			bt.delete(mostFrequentWord);
		}
	
		// TODO
		System.out.println("\nWords that appear only once\n");
		int n = bt.printUnique();
		System.out.println("\n" + n + " unique words.");
		 
	}
	
}
