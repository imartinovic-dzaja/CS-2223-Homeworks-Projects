package imartinovic.hw2;


/**
 * Once you copy this file into your USERID.hw2 package, you must complete this implementation.
 * 
 * This class will be used by Question Q1 on Homework2.
 */
public class WordList {
	
	private
		Node node;
	
	public WordList() {
		this.node = new Node("");		//use empty string to denote end of wordlist
	}
	
	/** 
	 * Leave this Node class as is. While you don't need to make changes to this class,
	 * it is acceptable if you would like to add methods to this class.
	 */
	class Node {
		String     word;
		Node       next;

		Node(String w) {
			this.word = w;
		}
		
		
	}

	/**
	 * If the given element doesn't exist in the set then update 
	 * the set and return true; otherwise return false. This means that
	 * adding a duplicate element to a set must return false.
	 * 
	 * @param elt      element to be added.
	 */
	public boolean add(String elt) {
		Node temp = this.node;
		while (!temp.word.equals("")) {					//search through the set
			
			if(temp.word.equals(elt)) {return false;}	//if duplicate is found return false
			else {
				temp = temp.next;						//otherwise continue stepping through the set
			}
		}
		temp.word = elt;								//once we reach the end add the element
		temp.next = new Node("");						//create a new end of set
		return true;									//return true
	}

	/** Returns the number of elements in the set. */ 
	public int size() {
		Node temp = this.node;
		int count = 0;
		while (!temp.word.equals("")) {		//search through the set
				++count;					//increment the size counter
				temp = temp.next;			//continue stepping through the set
			}
		return count;						//return the count
	}
	
	

	/**
	 * Returns true if the given element was in the set (and was removed) or 
	 * false if the given element did not belong to the set.
	 * @param elt      element to be removed.
	 */
	public boolean remove (String elt) {
		if (this.node.word.equals(elt)) {			//if we are removing the first element in the set
			this.node = this.node.next;				//skip the first element 
			return true;							//return true
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
	 * Returns true if the element exists within the collection.
	 * @param elt      target element to be searched.
	 */
	public boolean contains(String elt)  {
		Node temp = this.node;
		while (!temp.word.equals("")) {					//search through the set
			
			if(temp.word.equals(elt)) {return true;}	//if the element is found return true
			else {
				temp = temp.next;						//otherwise continue stepping through the set
			}
		}
		return false;									//once the end has been reached return false
	}
	
	/** For debugging, return comma-separated string of elements. */
	public String elements()  {
		Node temp = this.node;
		String result = "";
		while (!temp.word.equals("")) {								//search through the set
			if (result.equals("")) {result += temp.word;}	//append only the string if its the first element
			else {result += ", " + temp.word;}				//append a comma and the string otherwise
			temp = temp.next;								//go to the next element
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

		WordList wl = new WordList();
		validate(0, wl.size());
		validate("", wl.elements());           // empty word list must return ""
		validate(false, wl.contains("this"));
		validate(true, wl.add("test"));
		validate("test", wl.elements());       // no trailing or pre comma.
		validate(false, wl.contains("this"));
		validate(true, wl.contains("test"));

		validate(false, wl.add("test"));       // can't add twice
		validate(true, wl.remove("test"));     // can remove first element
		validate(false, wl.remove("test"));    // can't remove first empty
		validate(true, wl.add("test"));
		validate(true, wl.add("that"));
		validate(false, wl.remove("not"));
		validate(true, wl.remove("test"));
		validate("that", wl.elements());       // no trailing or pre comma.
		validate(true, wl.remove("that"));
	} 
}
