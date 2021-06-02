package imartinovic.hw3;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;
import imartinovic.hw3.AVL.Node;

/**
 * Minimum implementation of Binary Search Tree (BST) as a Symbol Table<String, Integer>
 *
 * You need to copy this class into your USERID.hw3 and add methods to the end of this class.
 */
public class BST {

	Node root;               // root of the tree
	
	class Node {
		String    key;          
		Integer   count;         
		Node      left, right;  // left and right subtrees
		int       N;            // number of nodes in subtree

		public Node(String key, int ct, int N) {
			this.key = key;
			this.count = ct;
			this.N = N;
		}
		
		public String toString() { return "[" + key + "]"; }
	}

	public boolean isEmpty() { return size() == 0; }
	
	/** Return number of key-value pairs in ST. */
	public int size()                { return size(root); }

	// Helper method that deals with "empty nodes"
	private int size(Node node) {
        if (node == null) return 0;
        
        return node.N;
    }
	
	/** Search for key in BST. */
	public Integer get(String key)      { return get(root, key); }
	
	/** Helper method to search for key in BST rooted at parent. */
	private Integer get(Node parent, String key) {
		if (parent == null) return null;
		
		int cmp = key.compareTo(parent.key);
		
		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return parent.count;
	}

	/** Invoke put on parent, should it exist. */
	public void put(String key, Integer val) {
		root = put(root, key, val);
	}
	
	/** Helper method to put (key, ct) pair into BST rooted at parent. */
	private Node put(Node parent, String key, Integer ct) {
		if (parent == null) return new Node(key, ct, 1);
		
		int cmp = key.compareTo(parent.key);
		if      (cmp < 0) parent.left  = put(parent.left,  key, ct);
		else if (cmp > 0) parent.right = put(parent.right, key, ct);
		else              parent.count = ct;
		
		parent.N = 1 + size(parent.left) + size(parent.right);
		return parent;
	}
	
	// traversal ideas
    // invoke an inorder traversal of the tree
    public void inorder() { inorder(root); }
    private void inorder(Node n) {
    	if (n == null) { return; }
    	
		inorder (n.left);
		StdOut.println (n.key);
		inorder (n.right);
    }
   
    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
    }

    /** Taken from Sedgewick algo. */
    private Node delete(Node x, String key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else { 
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        } 
        return x;
    } 
    
    private Node min(Node x) { 
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }
    
	// ------------------------------------------------------------------------------------------------
	// YOU WILL ADD METHODS BELOW. THERE IS NO NEED TO MODIFY CODE ABOVE.
	// ------------------------------------------------------------------------------------------------

    /** 
     * Returns the count of nodes at a given depth. Key is depth, Value is count.
     * 
     * For the following tree
     * 
     *          G           -- depth is 0
     *         / \
     *        D   H         -- depth is 1
     *         \
     *          F           -- depth is 2
     *  
     * The returned hash table should be { (0, 1), (1, 2), (2, 1) } where each (key, value) is (depth, count).
     * 
     * Note: you will need a helper method, much like you have seen in other recursive methods.
     * 
     * Returns a edu.princeton.cs.algs4.SeparateChainingHashST object representing the symbol table.
     */
    public SeparateChainingHashST<Integer,Integer> collect() {
    	SeparateChainingHashST<Integer,Integer> result = new SeparateChainingHashST<Integer,Integer> ();	
    	collect(root, result, 0);
    	return result;
    }
    
    public void collect(Node n, SeparateChainingHashST<Integer,Integer> table, int depth) {
    	if (n == null) {return;}
    	
    	if (table.contains(depth)) {								//if the depth we are trying to add is already in the ST
			int freq = table.get(depth) + 1;						//increment its frequency by 1
			table.put(depth,freq);									//update its frequency
		}
		else {														//if it was not in the ST
			table.put(depth, 1);									//add it to the ST
		}
    	
    	collect(n.left, table, depth+1);
    	collect(n.right, table, depth+1);
    }
    
    /**
     * Returns the height of this binary tree.
     */
	public int height() {
		int height = -1;
		if (root.left != null) {
			height = Math.max(height, height(root.left));
		}
		if (root.right != null) {
			height = Math.max(height, height(root.right));
		}

		height = height + 1;
		return height;
	}
	
    /** 
     * Returns the height of a given node.
     * 
     * For the following tree
     * 
     *          G           -- height of G is 2
     *         / \
     *        D   H         -- height of D is 1, height of H is 0
     *         \
     *          F           -- height of F is 0
     */
    public int height(Node n) {
    	int height = -1;
		if (n.left != null) {
			height = Math.max(height, height(n.left));
		}
		if (n.right != null) {
			height = Math.max(height, height(n.right));
		}

		return height + 1;
    }
    
    /**
     * Return the key whose count is the greatest (that is, has the most occurrences in the BST).
     * 
     */
    
    public String mostFrequent() {
    	Node most = mostFrequent(this.root);
    	return most.key;
    }
    
    public Node mostFrequent(Node n) {
    	Node most = n;
    	if (n.left != null) {
    		most = moreFrequent(most,mostFrequent(n.left));
    	}
    	if (n.right != null) {
    		most = moreFrequent(most,mostFrequent(n.right));
    	}
    	return most;
    }
    
    public Node moreFrequent(Node n1, Node n2) {
    	if(n1.count>n2.count) {
    		return n1;
    	}
    	else {
    		return n2;
    	}
    }
    
    /** Print in ascending order the keys whose count is 1 (that is, only occur once) and return total. */
    public int printUnique() {
    	return printUnique(this.root, 0);
    }
    
    public int printUnique(Node n, int countSoFar) {
    	int returnCount = 0;
    	if(n.left != null) {
    		returnCount += printUnique(n.left, returnCount);
    	}
    	if (n.count == 1) {
    		System.out.println(n.key);
    		returnCount++;
    	}
    	if (n.right != null) {
    		returnCount += printUnique(n.right, returnCount);
    	}
    	return returnCount;
    }

}
