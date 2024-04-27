package project4;

import java.util.*;


/**
 * This class represents a Binary Search Tree object. 
 * It declares its attributes and contains
 * methods to be used on binar seacrch trees 
 * such as adding/removing nodes. 
 * 
 * @author Tony Liu
 *
 */
public class BST < E extends Comparable <E> > implements Iterable<E>{

	private Node firstNode;
    private Node root;   //reference to the root node of the tree 
    private Comparator<E> comparator;   //comparator object to overwrite the natural ordering of the elements 	
	private boolean found;  //helper variable used by the remove methods
    private int size;
    private int height;
    /**
	 * Constructor that constructs a new, empty tree, 
	 * sorted according to the natural ordering of its elements.
	 */
    public BST () {
        root = null; 
        comparator = null; 
    }
    /**
   	 * Constructor that constructs a new tree containing 
   	 * the elements in the specified collection, sorted according
   	 * to the natural ordering of its elements.
   	 */
       public BST(E[] collection) {
           for(int i =0; i<collection.length; i++) {
               add(collection[i]);
           }
     }
    /**
	 * Constructor that constructs a new, empty tree, sorted
	 * according to the specified comparator.
	 */
    public BST(Comparator<E> comparator) {
        this.root = null; 
        this.comparator = comparator;
    }
	/**
	 * Adds the specified element to this tree if it is not already present. 
	 * If this tree already contains the element, the call leaves the 
     * tree unchanged and returns false.
	 * @param data element to be added to this tree 
     * @return true if this tree did not already contain the specified element 
     * @throws NullPointerException if the specified element is null  
	 */
    public boolean add ( E data ) { 
        if (data == null ) {
            throw new NullPointerException("no nulls allowed"); 
        }
        
        //adding first element to the tree 
        if (root == null ) {
            root = new Node(data); 
            size++;
            return true; 
        }

        return add(data, root) ; 

    }
    /**
	 * Actual recursive implementation of add. 
     * This function returns a reference to the subtree in which 
     * the new value was added. 
	 *
     * @param data element to be added to this tree 
     * @param node node at which the recursive call is made 
     * @return boolean representing whether or not node was added
	 */
    private boolean add(E data, Node node ) {
        
        if ( node.data.equals(data) ) {
            return false; 
        }
        else if (data.compareTo(node.data ) < 0 ) {
            if (node.left == null ) {
                node.left = new Node(data); 
                size++;
                return true; 
            }
            return add( data, node.left) ;
        }
        else if (data.compareTo(node.data ) == 0 ) {
        	return false; 
        }
        else {
            if (node.right == null ) {
                node.right = new Node(data);
                size++;
                return true; 
            }
            return add(data, node.right) ; 
        }

    }
    /**
	 * Removes the specified element from this tree if it is present. 
	 * Returns true if this tree contained the element (or equivalently, 
     * if this tree changed as a result of the call). 
     * (This tree will not contain the element once the call returns.)
	 * @param target object to be removed from this tree, if present
     * @return true if this set contained the specified element 
     * @throws NullPointerException if the specified element is null  
	 */
	public boolean remove(Object o){
		if(o==null) {
			throw new NullPointerException();
		}
		try {
			@SuppressWarnings("unchecked")
			E e = (E) o;
		}
		catch(ClassCastException e) {
			throw new ClassCastException();
		}
		@SuppressWarnings("unchecked")
		E target = (E) o;
        //replace root with a reference to the tree after target was removed 
		root = recRemove(target, root);
        if (found) {
        	size--; 
        }
		return found;
	}
	/**
	 * Actual recursive implementation of remove method: find the node to remove.
     *
	 * This function recursively finds and eventually removes the node with the target element 
     * and returns the reference to the modified tree to the caller. 
     * 
	 * @param target object to be removed from this tree, if present
     * @param node node at which the recursive call is made 
     * @return node representing the node removed
	 */
	private Node recRemove(E target, Node node)
	{
		if (node == null)  { //value not found 
			found = false;
            return node; 
        }
        
        //decide how comparisons should be done 
        int comp = 0 ;
        if (comparator == null ) //use natural ordering of the elements 
            comp = target.compareTo(node.data); 
        else                     //use the comparator 
            comp = comparator.compare(target, node.data ) ;

        
		if (comp < 0)       // target might be in a left subtree 
			node.left = recRemove(target, node.left);
		else if (comp > 0)  // target might be in a right subtree 
			node.right = recRemove(target, node.right );
		else {          // target found, now remove it 
			node = removeNode(node);
			found = true;
		}
		return node;
	}
	/**
	 * Actual recursive implementation of remove method: perform the removal.
	 *
	 * @param target the item to be removed from this tree
	 * @return a reference to the node itself, or to the modified subtree
	 */
	private Node removeNode(Node node)
	{
		E data;
		if (node.left == null)   //handle the leaf and one child node with right subtree 
			return node.right ; 
		else if (node.right  == null)  //handle one child node with left subtree 
			return node.left;
		else {                   //handle nodes with two children 
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}
   /**
    * Removes all of the elements from this set. The set will be empty after this call returns.
    */
    public void clear() {
    	root = null;
    	size = 0;
    }
    /**
     * Returns true if this set contains the specified element. 
     * More formally, returns true if and only if this set contains an element e 
     * such that Objects.equals(o, e).
     *
     * @param Object representing the object to be found 
     * @throwsClassCastException - if the specified object cannot be compared with the elements currently in the set
     * @throwsNullPointerException - if the specified element is null and this set uses natural ordering,
     *  or its comparator does not permit null elements
     */
    public boolean contains(Object o) {
    	if(o==null) {
			throw new NullPointerException();
		}
    	try {
			@SuppressWarnings("unchecked")
			E target = (E) o;
		}
		catch(ClassCastException e) {
			throw new ClassCastException();
		}
    	@SuppressWarnings("unchecked")
		E target = (E) o;
    	find(target, root);
    	return found;
    }
	/**
	 * Actual recursive implementation of contains method
	 *
	 * @param target object to be found from this tree, if present
     * @param node node at which the recursive call is made 
     * @return node representing the node being looked for
	 */
    private Node find(E target, Node node) {
    	found = false;
    	if (node == null)  { //value not found 
			found = false;
            return node; 
        }
        
        //decide how comparisons should be done 
        int comp = 0 ;
        if (comparator == null ) //use natural ordering of the elements 
            comp = target.compareTo(node.data); 
        else                     //use the comparator 
            comp = comparator.compare(target, node.data ) ;

        
		if (comp < 0)       // target might be in a left subtree 
			node.left = find(target, node.left);
		else if (comp > 0)  // target might be in a right subtree 
			node.right = find(target, node.right );
		else {          // target found, now remove it 
			found = true;
		}
		return node;
    }
    /**
     * Returns true if this set contains no elements. 
     * @return boolean representing if this set contains no elements.
     */
    public boolean isEmpty() {
    	return root == null;
    }
    /**
     * @return  an iterator instance that accesses the values in the tree according 
     * to the inorder traversal of the binary search tree
     */
    public Iterator<E> iterator(){
    	
    	return new BSTInOrderIterator(root);
    }
    /**
     * 
     * @return  an iterator instance that accesses the values in the tree 
     * according to the preorder traversal of the binary search tree
     */
    public Iterator<E> preorderIterator(){
    	
    	return new BSTPreorderIterator(root);
    }
    /**
     * 
     * @return  an iterator instance that accesses the values in the tree according 
     * to the postorder traversal of the binary search tree
     */
    public Iterator<E> postorderIterator(){
    	
    	return new BSTPostorderIterator(root);
    }
    /**
     * Returns the height of this tree. The height of a leaf is 1. 
     * The height of the tree is the height of its root node.
     * @return int representing the height of the tree
     */
    public int height() {
        return getNodeHeight(root);
    }
    /**
     * Actual recursive implementation of height()
     * @param node representing where we start to count the height from, always the root.
     * @return int representing the height
     */
    private int getNodeHeight(Node node) {
        if(node == null) {
            return 0;
        }
        return Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
    }   
    /**
     * Returns the element at the specified position in this tree. 
     * The order of the indexed elements is the same as provided by this tree's iterator. 
     * The indexing is zero based (i.e., the smallest element in this tree is at index 0 and the largest 
     * one is at index size()-1).
     * 
     * @param int representing the index 
     * @return E representing the object at the index
     * @throwsIndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())
     */
    public E get(int index) {
    	if(index<0 || index>=size) {
    		throw new IndexOutOfBoundsException();
    	}
    	Iterator<E> itr = iterator();
    	
    	for(Integer i = 0; itr.hasNext(); i++) {
    		if(i==index)return itr.next();
    		itr.next();
    	}
    	
    	return null;
    }
    /**
     * Returns the least element in this tree greater than or equal to the given element
     * or null if there is no such element. 
     * 
     * @param E representing the element being compared
     * @throwsClassCastException if the specified element cannot be compared with the elements currently in the set
     * @throwsNullPointerException - if the specified element is null
     * @return the least element in this tree greater than or equal to the given element
     */
    public E ceiling​(E e) {
    	if(e==null) {
    		throw new NullPointerException("specified element is null");
    	}    	
    	
    	return findCeil(root, e);
    }
    /**Actual recursive implementation of ceiling() method
     * 
     * @param node representing where we begin the comparison, the root
     * @param target representing the target being compared
     * @return E representing the correct object
     */
    private E findCeil(Node node, E target) {
    	if (node == null) {
            return null;
        }
        
  
    	try {
            if(target.compareTo(node.data) == 0 )return node.data; 
    	}catch(ClassCastException e) {
			throw new ClassCastException();
		}
        
        
        if (target.compareTo(node.data) > 0) {
            return findCeil(node.right, target);   
        }
        else { 
        	
        	E ceil = findCeil(node.left, target); 
        	return (ceil != null && ceil.compareTo(target) >= 0) ? ceil : node.data; 
        }	
    }
    /**
     * Returns the greatest element in this set less than or equal to the given element
     * or null if there is no such element. 
     * 
     * @param e representing the element being compared
     * @throwsClassCastException if the specified element cannot be compared with the elements currently in the set
     * @throwsNullPointerException - if the specified element is null
     * @return the greatest element in this set less than or equal to the given element
     */
    public E floor(E e) {
    	if(e==null) {
    		throw new NullPointerException("specified element is null");
    	}    	
    	
    	return findFloor(root, e);
    }
    /**Actual recursive implementation of floor() method
     * 
     * @param node representing where we begin the comparison, the root
     * @param target representing the target being compared
     * @return E representing the correct object
     */
    private E findFloor(Node node, E target) {
    	if (node == null) {
            return null;
        }
        
      
    	try {
    		if(target.compareTo(node.data) == 0 )return node.data;
    	}catch(ClassCastException e) {
			throw new ClassCastException();
		}
        
       
        if (target.compareTo(node.data) < 0) {
            return findFloor(node.left, target);  
        }
        else { 
        	E floor = findFloor(node.right, target);
        	return (floor != null && floor.compareTo(target) <= 0) ? floor : node.data; 
        }	
    } 
    /**
     * Returns the first (lowest) element currently in this tree. 
     * @throwsNoSuchElementException - if the set is empty 
     * @return the lowest element currently in this tree. 
     */
    public E first() {
    	if(isEmpty()) {
    		throw new NoSuchElementException("Set is empty");
    	}
    	return getMin(root);
    }
    /**
     * Actual recursive method for first()
     * @param node representing where we begin the comparison, the root
     * @return the lowest element currently in this tree. 
     */
    private E getMin(Node node) {
    	if(node.left!=null) {
    		return getMin(node.left);
    	}
    	firstNode = node;
    	return node.data;
    }
    /**
     * Returns the last (highest) element currently in this tree. 
     * @throwsNoSuchElementException - if the set is empty 
     * @return the highest element currently in this tree. 
     */
    public E last() {
    	if(isEmpty()) {
    		throw new NoSuchElementException("Set is empty");
    	}
    	return getMax(root);
    }
    /**
     * Actual recursive method for last()
     * @param node representing where we begin the comparison, the root
     * @return the highest element currently in this tree. 
     */
    private E getMax(Node node) {
    	if(node.right!=null) {
    		return getMax(node.right);
    	}
    	return node.data;
    }
    /**
     * Returns the greatest element in this set strictly less than the given element, 
     * or null if there is no such element.
     * 
     * @param the value to match
     * @thowsClassCastException - if the specified element cannot be compared with the elements currently in the set
     * @throwsNullPointerException - if the specified element is null
     * @return the greatest element less than e, or null if there is no such element
     */
    public E lower​(E e) {
    	if(e==null) {
    		throw new NullPointerException("specified element is null");
    	}    	
    	return findLower(root, e);
    }
    /**
     * Actual recursive implementation of lower() method
     * @param node representing where we begin the comparison, the root
     * @param target representing the value to match
     * @return
     */
    private E findLower(Node node, E target) {
    	if (node == null) {
            return null;
        }
        // If node's data is larger, lower must be in left subtree
    	try {
	        if (target.compareTo(node.data) <= 0) {
	            return findLower(node.left, target);
	            
	        }else { // Else, either right subtree or node has the lower value
	        	
	        	E lower = findLower(node.right, target);
	        
	        	return (lower != null && lower.compareTo(target) < 0) ? lower : node.data; 
	        }
    	}
		catch(ClassCastException e) {
			throw new ClassCastException();
		}
    } 
    /**
     *Returns the least element in this tree strictly greater than the given element, 
     *or null if there is no such element.
     * @param the value to match
     * @return the least element greater than e, or null if there is no such element
     * @thowsClassCastException - if the specified element cannot be compared with the elements currently in the set
     * @throwsNullPointerException - if the specified element is null
     */
    public E higher(E e)  {
    	if(e==null) {
    		throw new NullPointerException("specified element is null");
    	}    	
    	
    	return findHigher(root, e);
    }
    /**
     * Actual recursive implementation of higher() method
     * @param node representing where we begin the comparison, the root
     * @param target representing the value to match
     * @return the least element greater than e, or null if there is no such element
     */
    private E findHigher(Node node, E target) {
    	if (node == null) {
            return null;
        }
        
        // If root's data is smaller, higher must be in right subtree
        if (target.compareTo(node.data) >= 0) {
            return findHigher(node.right, target);
            
        }else { // Else, either left subtree or root has the higher value
        	
        	E higher = findHigher(node.left, target); 
        
        	return (higher != null && higher.compareTo(target) > 0) ? higher : node.data; 
        }	
    }
    /**
     * Compares the specified object with this tree for equality. 
     * Returns true if the given object is also a tree, the two trees have the same size, 
     * and every member of the given tree is contained in this tree. 
     * @param object to be compared for equality with this tree
     * @return boolean representing if the specified object is equal to this tree
     */
    public boolean equals(Object obj) {
    	try {
			@SuppressWarnings("unchecked")
			BST<E> target = (BST<E>) obj;
			return identicalTree(root, target.root);
		
		}
		catch(ClassCastException e) {
			return false;
		}
    }
    /**
     * Actual implementation of equals method
     * @param node representing where we begin the comparison, the root
     * @param target representing the value to match
     * @return boolean representing if the specified object is equal to this tree
     */
    private boolean identicalTree(Node thisNode, Node targetNode) {
		
		if(thisNode == null && targetNode == null) return true;
    	
		if((thisNode == null && targetNode != null)
    			|| (thisNode != null && targetNode == null)) return false;
		
		return (thisNode.data.compareTo(targetNode.data) == 0
                && identicalTree(thisNode.left, targetNode.left)
                && identicalTree(thisNode.right, targetNode.right));
    }   
	/**
	 * Returns the information held in the rightmost node of subtree
	 *
	 * @param subtree root of the subtree within which to search for the rightmost node
	 * @return returns data stored in the rightmost node of subtree
	 */
    private E getPredecessor(Node subtree)
	{
		if (subtree==null) //this should not happen 
            throw new NullPointerException("getPredecessor called with an empty subtree");
		Node temp = subtree;
		while (temp.right  != null)
			temp = temp.right ;
		return temp.data;
	}
    /**
	 * Returns the number of elements in this tree.
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}
	/**
	 * Produces tree like string representation of this tree. Returns a string representation 
	 * of this tree in a tree-like format. The string representation consists of a
	 * tree-like representation of this tree. Each node is shown in its own line 
	 * with the indentation showing the depth of the node in this tree. The root is 
	 * printed on the first line, followed by its left subtree,
	 * followed by its right subtree.
	 * @return String representing the BST
	 */
    public String toStringTreeFormat( ) {
    	   StringBuffer sb = new StringBuffer(); 
           toStringTree(sb, root, 0);
           return sb.toString();
    }
    /**Actual recursive implementation of toStringTreeFormat()
     * 
     * @param sb representing the StringBuffer
     * @param node representing the root
     * @param int representing the level
     */
    private void toStringTree( StringBuffer sb, Node node, int level ) {
    	 //display the node 
        if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append( "null\n"); 
            return;
        }
        else {
            sb.append( node.data + "\n"); 
        }

        //display the left subtree 
        toStringTree(sb, node.left, level+1); 
        //display the right subtree 
        toStringTree(sb, node.right, level+1); 
    }
    /**
     * Returns a string representation of this tree. The string representation
     * consists of a list of the tree's elements in the order they are returned 
     * by its iterator (inorder traversal), enclosed in square brackets ("[]"). 
     * Adjacent elements are separated by the characters ", " (comma and space).
     * 
     *  @return String representing the tree
     */
    public String toString() {
    	Iterator<E> itr = iterator();
    	StringBuffer sb = new StringBuffer();
    	sb.append("[");
    	boolean isFirst = true;
    	while(itr.hasNext()) {
    		if(isFirst) {
    			sb.append(itr.next());
    			isFirst = false; 
    		}
    		else { 
    			sb.append(", " + itr.next());
    			
    		}
    	}
    	sb.append("]");
    	return sb.toString();
    }
    /**
     * This Inner class is the BSTInOrderIterator class and implements Iterator interface. 
     * It represents an iterator object that holds all the values of a BST in inorder traversal
     * 
     * @author tonyliu
     */
	private class BSTInOrderIterator<E> implements Iterator<E>{

    	private Stack<Node> stack;
    	/**
    	 * Constructor for BSTInOrderIterator object
    	 * @param root representing the root of a BST
    	 */
    	public BSTInOrderIterator(Node root) {
            stack = new Stack<Node>();
            set(root);
        }
    	/**
    	 * Set method that sets the stack ready to be popped in inOrder 
    	 * traversal order
    	 * @param Node representing the starting node
    	 */
        private void set(Node current){
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }
        /**
         * Returns true if the stack is empty
         * @return boolean represent whether or not the stack is empty
         */
        public boolean hasNext()
        {
            return !stack.isEmpty();
        }
        /**
         * Method that tells you the next item in the stack
         * @return next item in stack
         */
        @SuppressWarnings("unchecked")
		public E next() {
     
            Node current = stack.pop();
     
            if (current.right != null)
                set(current.right);
     
            return (E) current.data;
        }
        /**
         * Remove() method does not work
         */
        public void remove() {
			throw new UnsupportedOperationException(); 
		}
    	
    }
    /**
     * This Inner class is the BSTPreorderIterator class and implements Iterator interface. 
     * It represents an iterator object that holds all the values of a BST in preOrder traversal
     * 
     * @author tonyliu
     */  
    private class BSTPreorderIterator implements Iterator<E> {

        private Stack<Node> stack;
    	/**
    	 * Constructor for BSTPreorderIterator object
    	 * @param root representing the root of a BST
    	 */
        public BSTPreorderIterator(Node root) {
            stack = new Stack<Node>();
            if (root != null)
                stack.push(root);
        }
        /**
         * Returns true if the stack is empty
         * @return boolean represent whether or not the stack is empty
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        /**
         * Method that tells you the next item in the stack
         * @return next item in stack
         */
        public E next() {
            Node node = stack.pop();
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
            return node.data;
        }
        /**
         * Remove() method does not work
         */
		public void remove() {
			throw new UnsupportedOperationException(); 
		}
    }
    /**
     * This Inner class is the BSTPostorderIterator class and implements Iterator interface. 
     * It represents an iterator object that holds all the values of a BST in postOrder traversal
     * 
     * @author tonyliu
     */      
    private class BSTPostorderIterator implements Iterator<E> {

        private Stack<Node> stack;
    	/**
    	 * Constructor for BSTPostorderIterator object
    	 * @param root representing the root of a BST
    	 */
        public BSTPostorderIterator(Node root) {
            stack = new Stack<>();
            set(root);
        }
    	/**
    	 * Set method that sets the stack ready to be popped in postOrder 
    	 * traversal order
    	 * @param Node representing the starting node
    	 */
        private void set(Node root) {
            while (root != null) {
                stack.push(root);
                if (root.left != null)
                    root = root.left;
                else
                    root = root.right;
            }
        }
        /**
         * Returns true if the stack is empty
         * @return boolean represent whether or not the stack is empty
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        /**
         * Method that tells you the next item in the stack
         * @return next item in stack
         */
        public E next() {
            Node node = stack.pop();
            if (!stack.isEmpty()) {
                if (node == stack.peek().left) {
                    // find next leaf in right sub-tree
                    set(stack.peek().right);
                }
            }
            return node.data;
        }
        /**
         * Remove() method does not work
         */
		public void remove() {
			throw new UnsupportedOperationException(); 
		}
    }

    /**
     * This inner class represents a node object. Nodes are what populate a BST
     * 
     * @author tonyliu
     *
     */
    private class Node implements Comparable < Node > {

        E data;
        Node  left;
        Node  right;
        
        /**
         * Constructor for Node object
         * @param data representing the data a Node holds
         */
        public Node ( E data ) {
            this.data = data;
        }
        /**
         * Second Constructor for Node object
         * @param data representing the data a Node holds
         * @param left representing what's left to it
         * @param right representing what's right to it
         */
        public Node (E data, Node left, Node right ) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
        /**
         * Method that compares nodes with one another. 
         * @param other node being compared
         * @return int representing result of compareTo() method 
         */
        public int compareTo ( Node other ) {
            if (BST.this.comparator == null )
                return this.data.compareTo ( other.data );
            else 
                return comparator.compare(this.data, other.data); 
        }
    }
    
    /**
     * Method that finds all valid paths from the root to the leaf of a tree
     */
    public void rootToLeafPathCheck() {
    	
    	List<E> nodeList = new ArrayList<E>();

    	rootToLeafPath(root, nodeList);
    }
    /**
     * Actual recursive implementation of rootToLeafPathCheck
     * @param node representing the start of a path
     * @param nodeList representing the list of nodes in a path
     */
    private void rootToLeafPath(Node node, List<E> nodeList){

        if (node == null) {
        	return;
        }
        
        nodeList.add(node.data);

        if(node.left == null && node.right == null) {
        	validatePath(nodeList);
        	nodeList.remove(node.data);
        }
        else {
        	rootToLeafPath(node.left, nodeList);	 
        	rootToLeafPath(node.right, nodeList);
        	nodeList.remove(node.data);
        }
    }
    /**
	 * Method that validates paths. In the actual program, the child's version of the method is used
	 * @param List representing a path a node
	 * @return true
	 */    
    public boolean validatePath(List<E> nodelist) {
    	return true;
    }


    
}

