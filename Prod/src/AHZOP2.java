import java.util.Scanner;
// BST class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
/**
 * Exception class for access in empty containers such as stacks, queues, and
 * priority queues.
 *
 * @author Mark Allen Weiss
 * @author Albert J. Wong
 *
 *         Modified by Albert J. Wong October 2003 - Made this into a runtime
 *         exception. It shouldn't be a non-runtime exception because...well,
 *         it's a condition that is detectable before triggering the exception,
 *         so there should be no reason to ever have this exception thrown
 *         except in the case of a program error. Thus, it shouldn't be checked.
 *         - Added a construction with a "string" argument, cause often, it's
 *         nice to be able to put cute messages in with your exceptions :)
 */
class UnderflowException extends RuntimeException {
	public UnderflowException() {
	}

	public UnderflowException(String message) {
		super(message);
	}
}

class BST<AnyType extends Comparable<? super AnyType>> {

	int pCnt = 0;// ProbeCounter

	/**
	 * Construct the tree.
	 */
	public BST() {
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		root = insert(x, root);
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * 
	 * @param x
	 *            the item to remove.
	 */
	public void remove(AnyType x) {
		root = remove(x, root);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return findMin(root).element;
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax() {
		if (isEmpty())
			throw new UnderflowException();
		return findMax(root).element;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return true if not found.
	 */
	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}
	
	private void splay(AnyType x , TreeNode<AnyType> t) {
		if (t == null) 
		{};
		
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private TreeNode<AnyType> insert(AnyType x, TreeNode<AnyType> t) {
		if (t == null)
			return new TreeNode<>(x, null, null);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left);
		else if (compareResult > 0)
			t.right = insert(x, t.right);
		else
			; // Duplicate; do nothing
		return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x
	 *            the item to remove.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private TreeNode<AnyType> remove(AnyType x, TreeNode<AnyType> t) {
		if (t == null)
			return t; // Item not found; do nothing

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = remove(x, t.left);
		else if (compareResult > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private TreeNode<AnyType> findMin(TreeNode<AnyType> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t;
		return findMin(t.left);
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private TreeNode<AnyType> findMax(TreeNode<AnyType> t) {
		if (t != null)
			while (t.right != null)
				t = t.right;

		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x
	 *            is item to search for.
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the matched item.
	 */
	private boolean contains(AnyType x, TreeNode<AnyType> t) {

		if (t == null)
			return false;

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0) {
			// increase the number of the probes
			pCnt++;
			return contains(x, t.left);
		} else if (compareResult > 0) {
			// increase the number of the probes
			pCnt++;
			return contains(x, t.right);
		} else
			return true; // Match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 */
	private void printTree(TreeNode<AnyType> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}

	/**
	 * Internal method to compute height of a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 */
	private int height(TreeNode<AnyType> t) {
		if (t == null)
			return -1;
		else
			return 1 + Math.max(height(t.left), height(t.right));
	}

	// Basic node stored in unbalanced binary search trees
	private static class TreeNode<AnyType> {
		// Constructors
		TreeNode(AnyType theElement) {
			this(theElement, null, null);
		}

		TreeNode(AnyType theElement, TreeNode<AnyType> lt, TreeNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
		}

		AnyType element; // The data in the node
		TreeNode<AnyType> left; // Left child
		TreeNode<AnyType> right; // Right child
	}

	/** The tree root. */
	private TreeNode<AnyType> root;

	// Test program

}

public class AHZOP2 {

	public static void main(String[] args) {

		BST<Integer> T = new BST<>(); // Create a new BST object T.
		Scanner reader = new Scanner(System.in);
		String inReq;
		char inReqChr;
		Integer inKey;
		int iCnt = 0; // Insert Counter
		int dCnt = 0; // Delete Counter;
		int fCnt = 0; // Find Counter;
		int tProbCnt = 0;// Total Prob Counter;
		float aProbes = 0; // Average number of probes.
		int sCnt = 0; // Total number of splays

		while (reader.hasNextLine()) {

			inReq = reader.next();
			inReqChr = inReq.charAt(0);
			switch (inReqChr) {
			case 'N':
				System.out.println("Ahmed Zouari");
				break;
			case 'A':
				inKey = reader.nextInt();
				if (T.contains(inKey)) {
					System.out.println("Key " + inKey + " already exists");
					break;
				}
				T.insert(inKey);
				iCnt++;
				System.out.println("Key " + inKey + " inserted");

				break;
			case 'D':
				inKey = reader.nextInt();
				if (!T.contains(inKey)) {
					System.out.println("Key " + inKey + " not found");
					break;
				}
				T.remove(inKey);
				dCnt++;
				System.out.println("Key " + inKey + " deleted");

				break;
			case 'F':
				inKey = reader.nextInt();
				T.pCnt = 1; // Reset the probe counter
				if (!T.contains(inKey)) {
					System.out.println("Key " + inKey + " not found");
					tProbCnt += T.pCnt;
					fCnt++;
					break;
				}

				tProbCnt += T.pCnt;
				fCnt++;
				System.out.println("Key " + inKey + " found");

			default:
				break;
			}
			System.out.println("The toltal number of inserts = " + iCnt);
			System.out.println("The toltal number of deletes = " + dCnt);
			System.out.println("The toltal number of splays = " + sCnt);
			System.out
					.println("The toltal number of nodes probed during searches = "
							+ tProbCnt);
			aProbes = (float) tProbCnt / fCnt;
			System.out
					.print("The average number of nodes probed during searches = ");
			System.out.printf("%.2f", aProbes);
			System.out.println();

			reader.nextLine();// Go to next line of the input
		}
		reader.close();
		

	}

}
