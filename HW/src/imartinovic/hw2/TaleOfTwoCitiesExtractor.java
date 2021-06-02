package imartinovic.hw2;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Given a file, retrieve words one at a time via Iterator interface.
 * 
 * A word is defined as being separated by any number of spaces.
 * 
 * You should COPY this file into your USERID.hw2 and it should work WITHOUT any additional
 * changes. If this is not the case, post on piazza and I will show how to adjust the 
 * value of actualDir to work on your local machine.
 */
public class TaleOfTwoCitiesExtractor implements Iterable<String>, Iterator<String> {

	/**
	 * If this class doesn't work RIGHT OUT OF THE BOX for you, then you will need to
	 * change this value to refer to the actual File location where
	 * 
	 * What works on my PC computer is the following (on PC you need to have \\ for directory dividers)
	 * On Macs and Linux, these become "/users/someone/git/another" but you might not need these.
	 * 
	 * new File("C:\\Users\\Laptop\\git\\cs2223d20\\Algorithms D2020\\tale");
	 */
	File actualDir = null;
	
	/** Processes the input from the designated chapter. */ 
	Scanner sc;
	
	/**
	 * There are forty-five chapters, from 1 to 45.
	 *     
	 * @throws IOException
	 */
	public TaleOfTwoCitiesExtractor(int id) throws IOException {
		String chapterID = String.format("%02d", id);
		File taleDir = null;
		// let user override if possible
		if (actualDir != null) {
			taleDir = actualDir;
		} else {
			String homeDir = System.getProperty("user.home");  // %HOMEDIR%/git is where git places files
			String gitDir = "git";
			String gitProject = "cs2223d20";                   // my git repo is here
			String dir = "Algorithms D2020";                   // this is the name of my project
			String tale = "tale";                              // folder contains all chapters
			taleDir = new File (new File (new File(new File(new File(homeDir), gitDir), gitProject), dir), tale);
		}
		
		sc = new Scanner(new File(taleDir, chapterID));
	}
	
	@Override
	public Iterator<String> iterator() {
		return this;
	}
	
	@Override
	public boolean hasNext() {
		return sc.hasNext();
	}

	@Override
	public String next() {
		return sc.next();
	}
	
	/** Run this to validate that it works. If not, see a TA/SA/Professor immediately. */
	public static void main(String[] args) throws IOException {
		TaleOfTwoCitiesExtractor te = new TaleOfTwoCitiesExtractor(1);
		
		String firstWord = te.next();
		String secondWord = te.next();
		
		if (!firstWord.equals("it")) {
			throw new RuntimeException ("first word MUST be it in the book!");
		}
		if (!secondWord.equals("was")) {
			throw new RuntimeException ("second word MUST be it in the book!");
		}
		
		System.out.println("Looks like this is working.");
	}
}
