import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Generic Class Binary Tree
 * @author AYDIN CALIKOGLU
 * Fonksiyonlari Kitaptan ornek alarak yazdim.
 **/
public class BinaryTree<E extends Comparable<E>> implements Iterable<E> {


    /**
     * PreOrderTravers Iterator
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new myIterator<>();
    }


    private class myIterator<E> implements Iterator<E> {

        Stack<Node> stack = new Stack<>();

        /**
         * Constructor
         */
        public myIterator() {
            stack.push(root);
        }



        /**
         * Override Method HasNext if there is a value
         *
         * @return boolean value
         */
        @Override
        public boolean hasNext() {
            if (stack.size() > 0)
                return true;
            return false;
        }

        /**
         * Give Next item
         */
        @Override
        public E next() {
            if (stack.size() < 1) {
                throw new NoSuchElementException();
            } else {
                Node<E> temp=stack.pop();
                if (temp.right!=null)
                    stack.push(temp.right);
                if (temp.left!=null)
                    stack.push(temp.left);
                return temp.data;
            }
        }




    }




    /** The root of the binary tree */
    protected Node<E> root;




    /** Constructor Empty BinaryTree */
    public BinaryTree() {
        root = null;
    }




    /**
     * Construct a BinaryTree with a other BinaryTree Root
     * @param root root of tree
     */
    protected BinaryTree(Node<E> root) {
        this.root = root;
    }




    /**
     * Constructs a new binary tree with data in its root,leftTree
     * as its left subtree and rightTree as its right subtree.

     * @param data value of root
     * @param leftTree left tree of root
     * @param rightTree right Tree of root
     */
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        root = new Node<E>(data);
        if (leftTree != null)
        {
            root.left = leftTree.root;
        } else
        {
            root.left = null;
        }
        if (rightTree != null) {
            root.right = rightTree.root;
        } else {
            root.right = null;
        }
    }





    /**
     * Binary Node has left and right Node and E value

     */
    protected static class Node<E> implements Serializable {
        // Data Fields

        /** The information stored in this node. */
        public E data;
        /** Reference to the left child. */
        public Node<E> left;
        /** Reference to the right child. */
        public Node<E> right;

        // Constructors
        /**
         * Construct a node with given data and no children.
         * @param data The data to store in this node
         */
        public Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }

        // Methods
        /**
         * Returns a string representation of the node.
         * @return A string representation of the data fields
         */
        @Override
        public String toString() {
            return data.toString();
        }
    }



    /**
     * Return the data of the root
     * @return get Data of root
     */
    public E getData() {
        if (root != null) {
            return root.data;
        } else {
            return null;
        }
    }






    /**
     * Return the left subtree.
     * @return The left subtree or null
     */
    public BinaryTree<E> getLeftSubtree() {
        if (root != null) {
            if ( root.left != null)
                return new BinaryTree<E>(root.left);
        } else {
            return null;
        }
        return null;
    }





    /**
     * Return the Rigth subtree.
     * @return The Rigth subtree or null
     */
    public BinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null) {
            return new BinaryTree<E>(root.right);
        } else {
            return null;
        }
    }





    /**
     * true if root has not a leaf
     * @return true if the root has no children
     */
    public boolean isLeaf() {
        return (root == null || (root.left == null && root.right == null));
    }


    /**
     * toString Method Pre Order Using preOrderTraverse Method
     * @return all values of tree
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root, 1, sb);
        return sb.toString();
    }






    /**
     * Preorder travers and fill String Builder
     * @param node The local root
     * @param depth The depth for order list
     * @param sb The string buffer to save the output
     */
    private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append("  ");
        }
        if (node == null) {
            sb.append("null\n");
        } else {
            sb.append(node.toString());
            sb.append("\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth + 1, sb);
        }
    }

    public static BinaryTree<String> readNumbersFile(Scanner sc)
            throws IOException {
            BinaryTree<String> makeTree =new BinaryTree<String>();
            while (sc.hasNextInt())
                makeTree.add(Integer.toString(sc.nextInt()));
            return makeTree;
        }

    private boolean add(E item) {
        root = add(root, item);
        return addReturn;
    }
    private boolean addReturn;
    private Node<E> add(Node<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            return new Node<E>(item);
        } else if (item.compareTo(localRoot.data) == 0) {
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            localRoot.left = add(localRoot.left, item);
            return localRoot;
        } else {
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }




    /**
     * Method to read a binary tree.
     * The input consists of a preorder traversal
     * of the binary tree. The line "null" indicates a null tree.
     * @param bR The input file
     * @return The binary tree
     * @throws IOException If there is an input error
     */
    public static BinaryTree<String> readBinaryTree(BufferedReader bR) throws IOException
    {

        String data = bR.readLine().trim();
        if (data.equals("null")) {
            return null;
        } else {
            BinaryTree<String> leftTree = readBinaryTree(bR);
            BinaryTree<String> rightTree = readBinaryTree(bR);
            return new BinaryTree<String>(data, leftTree, rightTree);
        }
    }
}
