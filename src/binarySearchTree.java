/*
 * Name: Henry Kuo
 * Class: CS 241
 * Instructor: Dr. Fang Tang
 * Project: 1
 * Description: A binary search tree of integers with the following commands:
 * 				insert, delete, find predecessor, and find successor
 */

import java.util.Scanner;
import java.util.StringTokenizer;

public class binarySearchTree {
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		BST binaryTree = new BST();//created a new tree
		char op = ' ';
		int element = 0;
		
		//reads the initial input of integers and insert them into the tree
		System.out.println("Please enter the initial sequence of values:");
		String input = kb.nextLine();
		StringTokenizer token = new StringTokenizer(input);
		while (token.hasMoreTokens()) {
			binaryTree.insert(Integer.parseInt(token.nextToken()));
		}
		//prints out the three orders of tree traversal
		binaryTree.preOrder();
		binaryTree.inOrder();
		binaryTree.postOrder();
			
		while (op != 'E') {
			System.out.print("Command? ");
			op = kb.next().toUpperCase().charAt(0);
			
			switch (op) {
				case 'I':
					element = kb.nextInt();
					binaryTree.insert(element);
					if (binaryTree.check == true)
						binaryTree.inOrder();
					break;
					
				case 'D':
					element = kb.nextInt();
					binaryTree.remove(element);
					if (binaryTree.check == true)
						binaryTree.inOrder();
					break;
					
				case 'P':
					element = kb.nextInt();
					System.out.println(binaryTree.findPredecessor(element));
					break;
					
				case 'S':
					element = kb.nextInt();
					System.out.println(binaryTree.findSuccessor(element));
					break;
					
				case 'E':
					System.out.println("Thank you for using!");
					return;
					
				case 'H':
					System.out.println(" I Insert a value");
					System.out.println(" D Delete a value");
					System.out.println(" P Find predecessor");
					System.out.println(" S Find successor");
					System.out.println(" E Exit the program");
					System.out.println(" H Display this message");
					break;
					
				default:
					System.out.println("Please enter a valid option!\n\n");
			}
		}
	}
}