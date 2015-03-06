/*
 * Name: Henry Kuo
 * Class: CS 241
 * Instructor: Dr. Fang Tang
 * Project: 1
 * Description: A binary search tree of integers with the following commands:
 * 				insert, delete, find predecessor, and find successor
 */

class BST {
	/*
	 * Node class
	 * fields: data, leftChild, rightChild, parent
	 * methods to set/get data/nodes and data printing of nodes
	 */
	class Node {
		private int data;
		private Node leftChild;
		private Node rightChild;
		private Node parent;
		
		public Node() {//default constructor
			data = 0;
			leftChild = null;
			rightChild = null;
			parent = null;
		}
		
		public Node(int element, Node left, Node right, Node p) {
			data = element;
			leftChild = left;
			rightChild = right;
			parent = p;
		}
		
		public void setData(int element) {
			data = element;
		}
		
		public void setLeft(Node left) {
			leftChild = left;
		}
		
		public void setRight(Node right) {
			rightChild = right;
		}
		
		public void setParent(Node p) {
			parent = p;
		}
		
		public int getData() {
			return data;
		}
		
		public Node getLeft() {
			return leftChild;
		}
		
		public Node getRight() {
			return rightChild;
		}
		
		public Node getParent() {
			return parent;
		}
		
		public int getLeftMostData() {
			if (leftChild == null)
				return data;
			else
				return leftChild.getLeftMostData();
		}
		
		public int getRightMostData() {
			if (rightChild == null)
				return data;
			else
				return rightChild.getRightMostData();
		}
		
		public Node removeLeftMost() {
			if (leftChild == null)
				return rightChild;
			else {
				leftChild = leftChild.removeLeftMost();
				return this;
			}
		}
		
		public Node removeRightMost() {
			if (rightChild == null)
				return leftChild;
			else {
				rightChild = rightChild.removeRightMost();
				return this;
			}
		}
		
		public void printPre() {//prints pre-order tree traversal
			System.out.print(data + " ");
			if (leftChild != null)
				leftChild.printPre();
			if (rightChild != null)
				rightChild.printPre();
		}
		
		public void printIn() {//prints in-order tree traversal
			if (leftChild != null)
				leftChild.printIn();
			System.out.print(data + " ");
			if (rightChild != null)
				rightChild.printIn();
		}
		
		public void printPost() {//prints post-order tree traversal
			if (leftChild != null)
				leftChild.printPost();
			if (rightChild != null)
				rightChild.printPost();
			System.out.print(data + " ");
		}
	}
	
	
	/*
	 * Binary Search Tree class
	 * holds the nodes created from the node class to form a bst tree
	 * fields: root, curr, currP, check
	 * methods: findElement, findPredecessor, findSuccessor, insert, remove,
	 * preOrder, inOrder, postOrder
	 */
	private Node root;
	private Node curr;//cursor to keep track of current node
	private Node currP;//cursor to keep track of parent node
	boolean check;//boolean value to keep track of successful/failed operations
	
	public BST() {//default constructor
		root = null;
		curr = null;
		currP = null;
		check = false;
	}
	
	//finds the node that contains the target element
	public Node findElement (int element) {
		Node traverse = root;
		while (true) {
			if (traverse == null)
				return null;
			if (element < traverse.getData())
				traverse = traverse.getLeft();
			else if (element > traverse.getData())
				traverse = traverse.getRight();
			else
				return traverse;
		}
	}
	
	public int findPredecessor (int element) {
		Node target = findElement(element);
		if (target == null) {//target node not found
			return -1;
		}
		
		if (target.getLeft() != null) {//target node has left sub-tree
			return target.getLeft().getLeftMostData();
		}
		
		currP = target.getParent();
		while (currP != null && target == currP.getLeft()) {
			target = currP;
			currP = currP.getParent();
		}
		return currP.getData();
	}
	
	public int findSuccessor (int element) {
		Node target = findElement(element);
		if (target == null) {
			return -1;
		}
		
		if (target.getRight() != null) {
			return target.getRight().getLeftMostData();
		}
		
		currP = target.getParent();
		while (currP != null && target == currP.getRight()) {
			target = currP;
			currP = currP.getParent();
		}
		return currP.getData();
	}
	
	public void insert(int element) {
		Node node = new Node(element, null, null, null);
		//inserts root if there is no root
		if (root == null) {
			root = node;
			curr = root;
			check = true;
			return;
		}
		//does not add same elements
		if (element == curr.getData()) {
			System.out.println(element + " already exists, ignore.");
			check = false;
			return;
		}
		//if element is smaller than the data in the current node go left
		else if (element < curr.getData()) {
			if (curr.getLeft() == null) {
				curr.setLeft(node);
				node.setParent(curr);
				curr = root;//resets current node to root
				check = true;
				return;
			}
			
			curr = curr.leftChild;
			insert(element);//recursive call to find the right spot
		}
		
		else {//go right if element is bigger than data in the current node
			if (curr.getRight() == null) {
				curr.setRight(node);
				node.setParent(curr);
				curr = root;
				check = true;
				return;
			}
			
			curr = curr.rightChild;
			insert(element);//recursive call
		}
		
		curr = root;
	}
	
	public void remove(int target) {
		if (curr == null) {//target element not in tree
			System.out.println(target + " doesn't exist!");
			check = false;
			return;
		}
		
		if (curr.getData() == target) {//target found
			if (curr.getLeft() == null) {
				if (curr == root) {
					root = root.getRight();//target at root and no left child
					check = true;
					return;
				}
				
				if (curr == curr.getParent().getLeft()) {//target found, no left child
					curr.getParent().setLeft(curr.getRight());
				}
				
				else {
					curr.getParent().setRight(curr.getRight());
				}
				check = true;
				return;
			}
			
			else {//there is a left child
				curr.setData(curr.getLeft().getRightMostData());
				curr.setLeft(curr.getLeft().removeRightMost());
				check = true;
				return;
			}
		}
		
		else if (target < curr.getData()) {
			curr = curr.getLeft();
			remove(target);//recursive call to traverse left tree
		}
		
		else {
			curr = curr.getRight();
			remove(target);//recursive call to traverse right tree
		}
		
		curr = root;//reset cursor to root
	}
	
	public void preOrder() {
		System.out.print("Pre-order: ");
		if (root == null)
			return;
		
		root.printPre();//calls pre-order print from node class
		System.out.println();
	}
	
	public void inOrder() {
		System.out.print("In-order: ");
		if (root == null)
			return;
		
		root.printIn();//calls in-order print from node class
		System.out.println();
	}
	
	public void postOrder() {
		System.out.print("Post-order: ");
		if (root == null)
			return;
		
		root.printPost();//calls post-order print from node class
		System.out.println();
	}
}
