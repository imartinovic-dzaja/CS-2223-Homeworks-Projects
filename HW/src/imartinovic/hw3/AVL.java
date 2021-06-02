package imartinovic.hw3;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;

/**
 * Minimum implementation of AVL balanced binary tree used as a Symbol Table<String, Integer>
 *
 * You need to copy this class into your USERID.hw3 and add methods to the end of this class.
 */
public class AVL {

	Node root;               // root of the tree
	
	class Node {
		String    key;          
		Integer   count;    
		Node      left, right;  // left and right subtrees
		int       height;       // need to know how TALL this subtree is (from leaf, not root).

		public Node(String key) {
			this.key = key;
		}

		public String toString() {
			String leftS = "";
			if (left != null) leftS = "left:" + left.key;
			String rightS = "";
			if (right != null) rightS = "right:" + right.key;
			
			return "[" + key + " "+ leftS + " " + rightS + "]";
		}
	}

	/**
	 * Determine height difference for a given node by subtracting leftH - rightH
	 */
	int heightDifference(Node node) {
		int leftTarget = 0;
		int rightTarget = 0;
		if (node.left != null) {
			leftTarget = 1 + node.left.height;
		}
		if (node.right != null) {
			rightTarget = 1 + node.right.height;
		}

		return leftTarget - rightTarget;
	}

	/** Update height for node. */
	void computeHeight(Node node) {
		int height = -1;
		if (node.left != null) {
			height = Math.max(height, node.left.height);
		}
		if (node.right != null) {
			height = Math.max(height, node.right.height);
		}

		node.height = height + 1;
	}

	/** Retrieve integer associated with key. */ 
	public Integer get(String key) { return get(root, key); }
	
	/** Helper method to retrieve count associated with key. */
	private Integer get(Node parent, String key) {
		if (parent == null) return null;

		int cmp = key.compareTo(parent.key);

		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return parent.count;
	}

	/** Invoke put on parent, should it exist. */
	public void put(String key, int count) {
		root = put(root, key, count);
	}

	/** Helper method to put (key, count) value into symbol table. */
	private Node put(Node parent, String key, int count) {
		if (parent == null) return new Node(key);

		int cmp = key.compareTo(parent.key);
		if (cmp == 0) {
			parent.count = count;
			return parent;
		}
		
		if (cmp < 0) {
			parent.left  = put(parent.left,  key, count);
			if (heightDifference(parent) == 2) {
				if (key.compareTo(parent.left.key) <= 0) {
					parent = rotateRight(parent);
				} else {
					parent = rotateLeftRight(parent);
				}
			}
		} else {
			parent.right = put(parent.right, key, count);
			if (heightDifference(parent) == -2) {
				if (key.compareTo(parent.right.key) > 0) {
					parent = rotateLeft(parent);
				} else {
					parent = rotateRightLeft(parent);
				}
			}
		}

		computeHeight(parent);
		return parent;
	}

	/** Perform right rotation around given node. */
	private Node rotateRight(Node parent) {
		Node newRoot = parent.left;
		Node grandson = newRoot.right;
		parent.left = grandson;
		newRoot.right = parent;

		computeHeight(parent);
		return newRoot;
	}

	/** Perform left rotation around given node. */
	private Node rotateLeft(Node parent) {
		Node newRoot = parent.right;
		Node grandson = newRoot.left;
		parent.right = grandson;
		newRoot.left = parent;

		computeHeight(parent);
		return newRoot;
	}

	/** Perform left, then right rotation around given node. */
	private Node rotateLeftRight(Node parent) {
		Node child = parent.left;
		Node newRoot = child.right;
		Node grand1  = newRoot.left;
		Node grand2  = newRoot.right;
		child.right = grand1;
		parent.left = grand2;

		newRoot.left = child;
		newRoot.right = parent;

		computeHeight(child);
		computeHeight(parent);
		return newRoot;
	}

	/** Perform right, then left rotation around given node. */
	private Node rotateRightLeft(Node parent) {
		Node child = parent.right;
		Node newRoot = child.left;
		Node grand1  = newRoot.left;
		Node grand2  = newRoot.right;
		child.left = grand2;
		parent.right = grand1;

		newRoot.left = parent;
		newRoot.right = child;

		computeHeight(child);
		computeHeight(parent);
		return newRoot;
	}
	
	/**
	 * Returns all keys in the symbol table as an <tt>Iterable</tt>.
	 * To iterate over all of the keys in the symbol table named <tt>st</tt>,
	 * use the foreach notation: <tt>for (Key key : st.keys())</tt>.
	 *
	 * @return all keys in the symbol table
	 */
	public Iterable<String> keys() {
		if (root == null) { return new Queue<String>(); }
		Queue<String> queue = new Queue<String>();
		keys(root, queue);
		
		return queue;
	}

	private void keys(Node node, Queue<String> queue) { 
		if (node == null) return; 
		
		// much like a traversal; builds up state in the queue.
		keys(node.left, queue); 
		queue.enqueue(node.key); 
		keys(node.right, queue);
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
    	
    	collect(n.left, table, depth + 1);
    	collect(n.right, table, depth + 1);
    }
    	
    
}
