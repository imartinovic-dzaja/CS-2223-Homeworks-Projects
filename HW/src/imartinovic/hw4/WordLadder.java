package imartinovic.hw4;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.BreadthFirstPaths;

/**
 * Modify this class for problem 1 on HW5.
 */
public class WordLadder {

	/**
	 * Represent the mapping of (uniqueID, 4-letter word)
	 */
	static SeparateChainingHashST<String,Integer> table = new SeparateChainingHashST<String,Integer>();
	static SeparateChainingHashST<Integer,String> reverse = new SeparateChainingHashST<Integer,String>();

	/**
	 * Determine if the two same-sized words are off by just a single character.
	 */
	public static boolean offByOne(String w1, String w2) {
		int different = 0;											//count the number of letters which are different
		if (w1.length() != w2.length()) {return false;}				//make sure the words are equal length
		else {
				for(int i = 0; i<w1.length(); ++i) {				//step through each letter of both words
				if(w1.charAt(i) != w2.charAt(i) ) {++different;}	//if words differ by a character increment the number of different letters
				if (different >= 2) {return false;}					//if they differ by 2 or more characters they are not off by one
			}
		}
		return (different == 1);									//if everything checks out, they are off by one
	}


	/**
	 * Main method to execute.
	 * 
	 * From console, enter the start and end of the word ladder.
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// Use this to contain all four-letter words that you find in dictionary
		AVL<String> avl = new AVL<String>();

		// create a graph where each node represents a four-letter word.
		// Also construct avl tree of all four-letter words.
		// Note: you will have to copy this file into your project to access it, unless you
		// are already writing your code within the SedgewickAlgorithms4ed project.
		Scanner sc = new Scanner(new File ("words.english.txt"));
		int i = 0;
		while (sc.hasNext()) {
			String s = sc.next();
			if (s.length() == 4) {
				avl.insert(s);
				table.put(s,i);
				reverse.put(i, s);
				++i;
			}
		}
		sc.close();

		// now construct graph, where each node represents a word, and an edge exists between 
		// two nodes if their respective words are off by a single letter. Hint: use the
		// keys() method provided by the AVL tree to iterate over all keys in the graph
		
		Graph graph = new Graph(table.size());
		for(String w1 : avl.keys()) {
			for(String w2 : avl.keys()) {
				if (offByOne(w1,w2)) {
					graph.addEdge(table.get(w1), table.get(w2));
				}
			}
		}

		StdOut.println("Enter word to start from (all in lower case):");
		String start = StdIn.readString().toLowerCase();
		StdOut.println("Enter word to end at (all in lower case):");
		String end = StdIn.readString().toLowerCase();

		// need to validate that these are both actual four-letter words in the dictionary
		if (!avl.contains(start)) {
			StdOut.println (start + " is not a valid word in the dictionary.");
			System.exit(-1);
		}
		if (!avl.contains(end)) {
			StdOut.println (end + " is not a valid word in the dictionary.");
			System.exit(-1);
		}

		// Once both words are known to exist in the dictionary, then create a search
		// that finds shortest distance (should it exist) between start and end.
		// be sure to output the words in the word ladder, IN ORDER, from the start to end.

		BreadthFirstPaths shortest = new BreadthFirstPaths(graph, table.get(start));
		Iterable<Integer> shortestPath = shortest.pathTo(table.get(end));
		if(shortestPath == null) {
			System.out.println("No such wordladder exists");
		}
		else {
			boolean first = true;
			for(Integer id : shortest.pathTo(table.get(end)))
			{
				if (first) 
					{System.out.print(String.format("%s", reverse.get(id))); first = false;}	
				else 
					System.out.print(String.format("->%s", reverse.get(id)));
			}
		}
	}
}
