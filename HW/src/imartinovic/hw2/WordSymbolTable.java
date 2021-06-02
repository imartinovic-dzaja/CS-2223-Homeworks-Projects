package imartinovic.hw2;

import imartinovic.hw2.WordList.Node;

/**
 * Once you copy this file into your USERID.hw2 package, you must complete this implementation.
 * 
 * This class will be used by Question Q2 on Homework2.
 */
public class WordSymbolTable {
	
	public Node node;
	
	public WordSymbolTable() {
		this.node = new Node("", -1);		//use empty string with word count -1 to denote end of WorsSymbolTable
	}
	
	/** 
	 * Leave this Node class as is. While you don't need to make changes to this class,
	 * it is acceptable if you would like to add methods to this class.
	 */
	class Node {
		String     word;
		int        count;
		Node       next;

		Node(String w, int count) {
			this.word = w;
			this.count = count;
		}
	}

	/**
	 * Increase the count for given word.
	 * 
	 * Note: this might need to add the word in the first place. 
	 * 
	 * Returns TRUE if the word was newly added, otherwise FALSE
	 * 
	 * @param elt      element whose count has increased by 1.
	 */
	public boolean increment(String elt) {
		Node temp = this.node;
		while (!temp.word.equals("")) {					//search through the set
			
			if(temp.word.equals(elt)) {
				++temp.count;							//increment the word count
				return false;							//if duplicate is found return false
				}							
			else {
				temp = temp.next;						//otherwise continue stepping through the set
			}
		}
		temp.word = elt;								//once we reach the end add the element
		temp.count = 1;									//set its count to 1
		temp.next = new Node("", -1);					//create a new end of set
		return true;									//return true
	}

	/**
	 * Decrease the count for given word.
	 * 
	 * Note: this might need to remove the word once the count reaches zero.
	 * Returns TRUE if the word was removed, otherwise FALSE
	 * 
	 * @param elt      element whose count is to decrease by 1.
	 */
	public boolean decrement(String elt) {
		Node temp = this.node;
		while (!temp.word.equals("")) {					//search through the set
			
			if(temp.word.equals(elt)) {
				if(this.node.count == 1) {				//if the count is only one
					remove(elt);						//remove the element
					return true;						//return true
				}
				else {--this.node.count;}				//otherwise decrement the word count
				return false;
			}
			
			else {
				temp = temp.next;						//otherwise continue stepping through the set
			}
		}
		return false;									//once the end is reached and element was not found return false
	}

	/** Return number of words in the symbol table. */
	public int size()  {
		Node temp = this.node;
		int count = 0;
		while (!temp.word.equals("")) {		//search through the set
				++count;					//increment the size counter
				temp = temp.next;			//continue stepping through the set
			}
		return count;						//return the count
	}

	/** Return the accumulated counts of all words in the word table. */
	public int totalCounts()  {
		
		Node temp = this.node;
		int count = 0;
		while (!temp.word.equals("")) {		//search through the set
				count += temp.count;		//add the count to the counter
				temp = temp.next;			//continue stepping through the set
			}
		return count;						//return the count
	}

	/** Remove entire word from the word table. */
	public boolean remove (String elt)  {
		if (this.node.word.equals(elt)) {				//if we are removing the first element in the set
			this.node = this.node.next;					//skip the first element 
			return true;								//return true
			}				
		
		Node temp = this.node;
		Node prevTemp = null;
		while (!temp.word.equals("")) {					//search through the set
			
			if(temp.word.equals(elt)) {					//if the word has been found
				prevTemp.next = temp.next;				//skip the element we are removing
				return true;							//return true
				}										
			else {
				prevTemp = temp;						//set the previous temp to the current temp
				temp = temp.next;						//continue stepping through the set
			}
		}

		return false;									//return false
	}
	/**
	 * Returns true if word exists in the WordSymbolTable; false otherwise.
	 * 
	 * @param elt      target element to seek.
	 */
	public boolean contains(String elt) {
		Node temp = this.node;
		while (!temp.word.equals("")) {					//search through the set
			
			if(temp.word.equals(elt)) {return true;}	//if the element is found return true
			else {
				temp = temp.next;						//otherwise continue stepping through the set
			}
		}
		return false;									//once the end has been reached return false
	}

	/**
	 * Returns the count for the word (or 0 if the word doesn't exist in the symbol table).
	 * 
	 * @param elt      target element to seek.
	 */
	public int count(String elt)  {
		Node temp = this.node;
		while (!temp.word.equals("")) {						//search through the set
			
			if(temp.word.equals(elt)) {return temp.count;}	//if the element is found return its count
			else {temp = temp.next;}						//otherwise continue stepping through the set
		}
		return 0;											//if the element has not been found return 0
	}

	/** 
	 * Return a word whose repetition count is equal to the maximum in the Symbol table.
	 * 
	 * Note that there may be multiple words that have the maximum count, so this method 
	 * only needs to return one of them.
	 */
	public String mostFrequent() {
		Node temp = this.node;
		String frequent = "";
		int maxCount = 0;
		while (!temp.word.equals("")) {		//search through the set
				if(temp.count>maxCount) {	//if a new most frequent word is found
					frequent = temp.word;	//update the most frequent word
					maxCount = temp.count;	//update the counter
				}

				temp = temp.next;			//continue stepping through the set
			}
		return frequent;					//return the count
	}


	/** For debugging, return semicolon-separated string of (word,count) pairs. */
	public String elements(){
		Node temp = this.node;
		String result = "";
		while (!temp.word.equals("")) {												//search through the set
			if (result.equals("")) {result += "("+temp.word+","+temp.count+")";}	//append only the string if its the first element
			else {result += ", " + "("+temp.word+","+temp.count+")";}				//append a semicolon and the string otherwise
			temp = temp.next;														//go to the next element
		}
		return result;									//once the end has been reached return false
	}


	// you should not have to modify anything below. These are testing routines for you to check your work.
	// ----------------------------------------------------------------------------------------------------
	static void validate(Object o1, Object o2) {
		if (o1.equals(o2)) { return; }
		throw new RuntimeException(o1 + " doesn't equal " + o2);
	}

	// Once you have completed the implementation, you should be able to run this method and have
	// it complete without any runtime exceptions. While not an exhaustive test, this should be 
	// sufficient to help you uncover many of the boundary cases.
	public static void main(String[] args) {

		WordSymbolTable wl = new WordSymbolTable();
		validate(0, wl.size());
		validate("", wl.elements());           // empty word list must return ""
		validate(0, wl.count("nothing"));
		validate(false, wl.contains("this"));
		validate(true, wl.increment("test"));
		validate("(test,1)", wl.elements());       // no trailing or pre comma.
		validate(1, wl.count("test"));
		validate(false, wl.contains("this"));
		validate(true, wl.contains("test"));

		validate(1, wl.count("test"));
		validate(false, wl.increment("test"));       // when add TWICE, false is returned
		validate(2, wl.count("test"));

		validate(true, wl.remove("test"));     // can remove first element
		validate(false, wl.remove("test"));    // can't remove first empty
		validate(true, wl.increment("test"));
		validate(true, wl.increment("that"));
		validate(1, wl.count("test"));
		validate(false, wl.increment("that"));
		validate(2, wl.count("that"));
		validate(3, wl.totalCounts());
		validate(2, wl.size());
		validate(false, wl.remove("not"));
		validate(true, wl.remove("test"));
		validate("(that,2)", wl.elements());       // no trailing or pre comma.
		validate(true, wl.remove("that"));
	} 
}
