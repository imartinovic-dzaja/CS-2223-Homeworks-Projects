package imartinovic.hw2;

import java.io.IOException;

/**
 * For this question, you are to process the book "The Tale of Two Cities" as included in the repository.   
 * 
 * There are 45 chapters in the book, which I have extracted into separate files. I will admit that the  
 * transcription is quite awkward. For example, everything has been converted to lower case, and all punctuation 
 * marks have been removed. Some words are subdivided improperly, but this is what we have to work with!
 * 
 * The questions you are to answer are:
 * 
 * 1. Which chapter contains the most # of words in total, as returned by the TaleOfTwoCitiesExtractor
 * 2. Which chapter (of the 45) contains the most # of unique words? and how many unique words occur in
 *    that chapter.
 * 3. Which two distinct chapters share the most words in common? And how many words is that?
 * 
 * The definition of a word is given to you by the TaleOfTwoCitiesExtractor class, which provides an
 * Iterable interface to a given chapter. This object will return the words in a chapter, one at a time,
 * in the order they appear in the chapter.
 * 
 * Your first responsibility is to ensure that TaleOfTwoCitiesExtractor works in your location.
 */
public class Q1 {
	
	/** Complete this implementation. */
	static void largestChapter() throws java.io.IOException {
		int chapter = -1;
		int max = -1;
			
			for (int i = 1; i<=45; ++i) {
				try {
					int count = 0;
					for (String s : new TaleOfTwoCitiesExtractor(i)) {
						++count;
					}
					if (count >= max ) {
						max = count;
						chapter=i;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	
		System.out.println(String.format("The chapter with the most number of words is %d with a total of %s", chapter, max));
	}
	
	/** Complete this implementation. */
	static void fewestUniqueWords() throws java.io.IOException {
		int chapter = -1;
		int minUnique = Integer.MAX_VALUE;
		
		for (int i = 1; i<=45; ++i) {
			WordList wl = new WordList();
			try {
				for (String s : new TaleOfTwoCitiesExtractor(i)) {
					wl.add(s);
				}
				int uniqueCount = wl.size();
				if (uniqueCount < minUnique ) {
					minUnique = uniqueCount;
					chapter=i;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(String.format("The chapter with the fewest number of unique words is %d with a total of %s", chapter, minUnique));
	}
	
	/** Complete this implementation. */
	static void totalUniqueWords() throws java.io.IOException {
		int totalUnique = 0;
		WordList wl = new WordList();

		for (int i = 1; i<=45; ++i) {
			try {
				for (String s : new TaleOfTwoCitiesExtractor(i)) {
					wl.add(s);
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		totalUnique = wl.size();	
		System.out.println(String.format("There are a total of %d unique words in the book.", totalUnique));
	}
	
	/** Complete this implementation. */
	static void twoChaptersShareMostInCommon() throws java.io.IOException {
		int chapter1 = -1;
		int chapter2 = -1;
		int maxShared = -1;
		
		for (int i = 1; i<45; ++i) {										//step through each chapter excluding last
			for (int j = i+1; j<=45; ++j) {									//then step through the remaining chapters
				WordList wl = new WordList();								//for current chapter construct a list of unique words
				try {
					for (String s : new TaleOfTwoCitiesExtractor(i)) {
						wl.add(s);												
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				int countCommon = 0;										//introduce a counter for common words
				try {
					for (String s : new TaleOfTwoCitiesExtractor(j)) {		//step through each word in other chapter
						if(wl.contains(s)) {								//if there are two words which match
							++countCommon;									//remove that word from the unique list
							wl.remove(s);									//increment the counter for common words
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (countCommon > maxShared) {								//if we found a new maximum number of shared words
					maxShared = countCommon;								//update the count
					chapter1 = i;											//update the chapters
					chapter2 = j;
				}
			}
			
		}
		
		
		System.out.println(String.format("The two chapters that share the most words in common are chapters %d and %d with a total of %s words", chapter1, chapter2, maxShared));
	}
	
	/** Leave this method alone. */
	public static void main(String[] args) throws java.io.IOException {
		largestChapter();
		fewestUniqueWords();
		totalUniqueWords();
		twoChaptersShareMostInCommon();
	}
}
