package imartinovic.hw2;

import java.io.IOException;

/**
 * Building from Question 1, there are different questions to answer.
 * 
 * 1. What is the most frequently used word in the entire book?
 * 2. What are the top-ten most frequently used words in the entire book?
 * 3. How many words occur exactly once in the book?
 */
public class Q2 {

	static void mostFrequent() throws java.io.IOException {
		WordSymbolTable wst = new WordSymbolTable();
		String topTen[] = new String[10];
		long topTenCount[] = new long[10];
		
		for (int i = 1; i<=45; ++i) {								//populate the Symbol Table with words and their counts
			try {
				for (String s : new TaleOfTwoCitiesExtractor(i)) {
					wst.increment(s);
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//obtain some general info about all the words in the table
		long totalWord = wst.totalCounts();
		long totalTopTen = 0;
		
		for (int i = 0; i<10; ++i) {						//step through the top 10 most frequent words
		String mostFrequent = wst.mostFrequent();			//find the most frequent word
		topTen[i] = mostFrequent;							//add it to the array of most frequent words
		topTenCount[i] = wst.count(mostFrequent);			//add its count to the array of counts of most frequent words
		totalTopTen += topTenCount[i];						//increase the number of counts for the top ten words 
		wst.remove(mostFrequent);							//remove the word from the Symbol Table
		}
		
		
		double percentage = (double)topTenCount[0] / totalWord * 100;
		System.out.println(
				String.format("\"%s\" is the most frequent word, used %d times out of %d total words (%.3f%%)",
						topTen[0], topTenCount[0], totalWord, percentage));

		System.out.println("The Top Ten words by frequency are:");
		for (int i = 0; i < 10; ++i) {
		System.out.println(String.format("%2d. %s (%d)", i + 1, topTen[i], topTenCount[i]));
		}
		
		percentage = (double)totalTopTen / totalWord * 100;
		System.out.println(String.format("These ten words represent %.3f%% of the total words in the book", percentage));

	}

	static void wordsUsedOnce() throws java.io.IOException {
		WordSymbolTable wst = new WordSymbolTable();
		for (int i = 1; i<=45; ++i) {								//populate the Symbol Table with words and their counts
			try {
				for (String s : new TaleOfTwoCitiesExtractor(i)) {
					wst.increment(s);
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		int numSingle = 0;
		int longest = 0;
		String longestWord = "";
		
		for (int i = 1; i<=45; ++i) {										//step through each word in the book
			try {
				for (String s : new TaleOfTwoCitiesExtractor(i)) {
					if (wst.count(s) == 1) {								//if a given word has count 1
						++numSingle;										//increase the count of single words
						if (s.length() > longest) {							//if its length is also longest
							longest = s.length();							//update the longest string length
							longestWord = s;								//update the longest word
						}
					}
					wst.remove(s);											//remove that string from the Symbol Table in order not to double count
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		System.out.println(String.format("%d words are used exactly once (longest is \"%s\")", numSingle, longestWord));
	}

	public static void main(String[] args) throws java.io.IOException {
		mostFrequent();
		wordsUsedOnce();
	}
}
