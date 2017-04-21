import java.io.Serializable;
import java.util.*;

public class BinaryHeap<E extends Comparable<E>> extends BinaryTree<E> implements Queue<E> {
    int size=0;

    /** The root of the binary tree */
    protected Node<E> root;

    Queue<Node<E>> LastNode = new LinkedList<>();

    /** Return value from the public add method. */
    protected boolean addReturn;
    /** Return value from the public delete method. */
    protected E deleteReturn;


    /**
     * Constructor Empty BinaryTree
     */
    public BinaryHeap() {
        super();
    }

    /**
     * Construct a BinaryTree with a other BinaryTree Root
     *
     * @param root root of tree
     */
    protected BinaryHeap(Node<E> root) {
        super(root);
    }

    /**
     * Constructs a new binary tree with data in its root,leftTree
     * as its left subtree and rightTree as its right subtree.
     *
     * @param data      value of root
     * @param leftTree  left tree of root
     * @param rightTree right Tree of root
     */
    public BinaryHeap(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {

        add(data);
        if (leftTree!=null) {
            Iterator<E> leftIterator = leftTree.iterator();
            while (leftIterator.hasNext()) {
                add(leftIterator.next());
            }
        }
        if (rightTree!=null) {
            Iterator<E> rightIterator = rightTree.iterator();
            while (rightIterator.hasNext()) {
                add(rightIterator.next());
            }
        }
    }


    /**
     * Iterating LevelOrder Method
     * @return iterator Level Order
     */
    @Override
    public Iterator<E> iterator() {
        return new levelOrderIterator();
    }

    /**
     * Inner Class iterator traverse the tree according to Level order
     */
    private class levelOrderIterator implements Iterator<E> {
        public levelOrderIterator() {
            queue.offer(root);
        }

        Queue<Node<E>> queue = new LinkedList<>();

        @Override
        public boolean hasNext() {
            if (queue.size() > 0)
                return true;
            return false;
        }

        @Override
        public E next() {
            if (queue.size() < 1) {
                throw new NullPointerException();
            }
            else {
                Node<E> temp = queue.poll();
                if (temp.left != null)
                    queue.offer(temp.left);
                if (temp.right != null)
                    queue.offer(temp.right);

                return temp.data;

            }
        }
    }



    /**
     * Heap Binary Node has left and right and parent Node and E value and level

     */
    protected static class Node<E> extends BinaryTree.Node<E> implements Serializable {
        // Data Fields


        /* Key Eklemek yerine Yeni Method Yazdim YOK DIYE 0 vermeyin. logn calisiyor. */


        public Node<E>  parent=null;

        /** Reference to the left child. */
        public Node<E> left;
        /** Reference to the right child. */
        public Node<E> right;

        // Constructors
        /**
         * Construct a node with given data and no children.
         * @param parent Parent of the Node
         * @param data Node data Type Generic
         */
        public Node(E data,Node<E> parent) {
            super(data);
            this.parent=parent;
            left=null;
            right=null;
        }

    }



    /**
     * method delete.
     * @return The object deleted from the tree
     * @throws ClassCastException if target does not implement
     */
    public E deleteFirst() {
        if (root==null)
            return null;
        Node<E> last=findLastNode(root,size);
        if (last==null)
            return null;
        deleteReturn=root.data;
        root.data=last.data;
        if (last.parent!=null) {
            if (last.parent.left.data.compareTo(last.data) == 0)
                last.parent.left = null;
            else
                last.parent.right = null;
            shiftDown(root);
        }
        else
            root=null;
        size--;
        return deleteReturn;
    }

    /**
     * shift Up the Node data with swapping according to their values
     * @param localRoot
     */
    private void shiftDown(Node<E> localRoot)
    {
        if (localRoot.left!=null && localRoot.right!=null) {
            if (localRoot.left.data.compareTo(localRoot.right.data) < 0) {
                if (localRoot.left.data.compareTo(localRoot.data) < 0) {
                    E temp = localRoot.left.data;
                    localRoot.left.data = localRoot.data;
                    localRoot.data = temp;
                    shiftDown(localRoot.left);
                }
            } else {
                if (localRoot.right.data.compareTo(localRoot.data) < 0) {
                    E temp = localRoot.right.data;
                    localRoot.right.data = localRoot.data;
                    localRoot.data = temp;
                    shiftDown(localRoot.right);
                }
            }
        }
        else
        {
            if (localRoot.left!=null)
                if (localRoot.left.data.compareTo(localRoot.data) < 0) {
                    E temp = localRoot.left.data;
                    localRoot.left.data = localRoot.data;
                    localRoot.data = temp;
                    shiftDown(localRoot.left);
                }
        }
    }


    /**
     * it considers the size of tree and find the last Node or Last emptyNode in tree
     * @param localRoot
     * @param size
     * @return Last Node or Parent Node
     */
    private Node<E> findLastNode(Node<E> localRoot,int size)
    {
        if (localRoot==null)
            return null;
        if(size()<=1)
        {
            if (size()==1)
                return localRoot;
            else
                return null;
        }

        /* Anlasilabilir olmasi icin bir cok degisken kullandim. */
        int treeLevel=(new Double(Math.ceil( Math.log(size)/Math.log(2)))).intValue();
        if(Math.log(size)/Math.log(2)==treeLevel)
            treeLevel+=1;
        int totalLastNodes=(new Double(Math.pow(2,treeLevel-1))).intValue();
        int totalUpNodes=(new Double(Math.pow(2,treeLevel-1))).intValue()-1;
        int lastNodesRemaining=size-totalUpNodes;
        int newSize=(totalUpNodes-1)/2;
        if (lastNodesRemaining <= totalLastNodes/2)
            newSize+=lastNodesRemaining;
        else
            newSize+=lastNodesRemaining-totalLastNodes/2;



        if (lastNodesRemaining <= totalLastNodes/2)
            if (localRoot.left!=null)
                return findLastNode(localRoot.left,newSize);
            else /* return Parent Node for add */
                return localRoot;

        else
            if (localRoot.right!=null)
                return findLastNode(localRoot.right,newSize);
            else /* return Parent Node for add */ {
                return localRoot;
            }
    }

    /**
     * Print the Tree according to preOrderTreverse Method
     * @return String of All HeapTree datas
     */
    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        preOrderTraverse(root,1,sb);
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
    /**
     * method add.
     * @param item The object being inserted
     * @return true if the object is inserted, false
     *         if the object already exists in the tree
     */
    public boolean add(E item) {
        shiftUp(addLast(item));
        if (addReturn==true)
            size++;
        return addReturn;
    }

    /**
     * add item at Last empty Node of tree according to LevelOrder
     * @param item
     * @return
     */
    private Node<E> addLast(E item) {
        addReturn=true;
        if (root==null)
        {
            root=new Node<E>(item,null);
            return root;
        }
        //Node<E> last=findLastParentNode(item);
        Node<E> last=findLastNode(root,size+1);
        if (addReturn==false) {
            return null;
        }
        if (last==null)
        {
            return last;
        }

        if (last.left==null)
        {
            last.left=new Node<E>(item,last);
            return last.left;
        }

        last.right=new Node<E>(item,last);
        return last.right;
    }

    /**
     * * shift down the Node data with swapping according to their data values
     * @param lastItem
     * @return
     */
    private Node<E> shiftUp(Node<E> lastItem)
    {
        if (lastItem==null)
            return null;
        if(lastItem.parent==null)
            return lastItem;
        if(lastItem.parent.data.compareTo(lastItem.data)>0)
        {
            E tempData=lastItem.parent.data;
            lastItem.parent.data=lastItem.data;
            lastItem.data=tempData;
            return shiftUp(lastItem.parent);
        }
        else
            return lastItem;
    }

    /**
     * give the size of Tree
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     * When using a capacity-restricted queue, this method is generally
     * preferable to {@link #add}, which can fail to insert an element only
     * by throwing an exception.
     *
     * @param e the element to add
     * @return {@code true} if the element was added to this queue, else
     * {@code false}
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it from being added to this queue
     * @throws NullPointerException     if the specified element is null and
     *                                  this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *                                  prevents it from being added to this queue
     */
    @Override
    public boolean offer(E e) {
        if(!add(e))
            new NullPointerException();
        return true;
    }

    /**
     * Retrieves and removes the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    @Override
    public E poll() {
        return deleteFirst();
    }

    /**
     * Retrieves, but does not remove, the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    @Override
    public E peek() {
        return root.data;
    }


    /**
     * Retrieves and removes the head of this queue.  This method differs
     * from {@link #poll poll} only in that it throws an exception if this
     * queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    @Override
    public E remove() {
        if (size()==0)
            throw new NoSuchElementException();
        return deleteFirst();
    }

    /**
     * Retrieves, but does not remove, the head of this queue.  This method
     * differs from {@link #peek peek} only in that it throws an exception
     * if this queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    @Override
    public E element() {
        if (size()==0)
            throw new NoSuchElementException();
        return root.data;
    }

    /**
     * Returns <tt>true</tt> if this collection contains no elements.
     *
     * @return <tt>true</tt> if this collection contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    /**
     * Returns <tt>true</tt> if this collection contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this collection
     * contains at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this collection is to be tested
     * @return <tt>true</tt> if this collection contains the specified
     * element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this collection
     *                              (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              collection does not permit null elements
     *                              (<a href="#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        if (find(root,(E)o)==null)
            throw new NullPointerException();
        return true;
    }


    /**
     * Search Target value on tree
     * Return Node of that Target
     * @param localRoot
     * @param target
     * @return Node of that Target
     */
    private Node<E> find(Node<E> localRoot, E target) {
        if (localRoot == null) {
            return null;
        }
        // Compare the target with the data field at the root.
        int compResult = target.compareTo(localRoot.data);
        if (compResult == 0) {
            return localRoot;

        }
        Node temp = find(localRoot.left, target);
        if (temp != null)
            return temp;
        return find(localRoot.right, target);
    }

    /**
     * Returns an array containing all of the elements in this collection.
     * If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     * The returned array will be "safe" in that no references to it are
     * maintained by this collection.  (In other words, this method must
     * allocate a new array even if this collection is backed by an array).
     * The caller is thus free to modify the returned array.
     * This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this collection
     */
    @Override
    public Object[] toArray() {
        Object[] newAr = new Object[size];
        Iterator it= this.iterator();
        int i=0;
        while (it.hasNext()) {
            newAr[i++] = it.next();
        }
        return new Object[0];
    }

    /**
     * Returns an array containing all of the elements in this collection;
     * the runtime type of the returned array is that of the specified array.
     * If the collection fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this collection.
     * 
     * If this collection fits in the specified array with room to spare
     * (i.e., the array has more elements than this collection), the element
     * in the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of this
     * collection <i>only</i> if the caller knows that this collection does
     * not contain any <tt>null</tt> elements.)
     * 
     * If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     * 
     * Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     * 
     * Suppose <tt>x</tt> is a collection known to contain only strings.
     * The following code can be used to dump the collection into a newly
     * allocated array of <tt>String</tt>:
     * 
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     * 
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param a the array into which the elements of this collection are to be
     *          stored, if it is big enough; otherwise, a new array of the same
     *          runtime type is allocated for this purpose.
     * @return an array containing all of the elements in this collection
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this collection
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present (optional operation).  More formally,
     * removes an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this collection contains one or more such elements.  Returns
     * <tt>true</tt> if this collection contained the specified element (or
     * equivalently, if this collection changed as a result of the call).
     *
     * @param o element to be removed from this collection, if present
     * @return <tt>true</tt> if an element was removed as a result of this call
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this collection
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       collection does not permit null elements
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this collection
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns <tt>true</tt> if this collection contains all of the elements
     * in the specified collection.
     *
     * @param c collection to be checked for containment in this collection
     * @return <tt>true</tt> if this collection contains all of the elements
     * in the specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              collection
     *                              (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this collection does not permit null
     *                              elements
     *                              (<a href="#optional-restrictions">optional</a>),
     *                              or if the specified collection is null.
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator it =c.iterator();
        while (it.hasNext())
        {
            if (find(root,(E)it.next())==null)
                return false;
        }
        return true;
    }

    /**
     * Adds all of the elements in the specified collection to this collection
     * (optional operation).  The behavior of this operation is undefined if
     * the specified collection is modified while the operation is in progress.
     * (This implies that the behavior of this call is undefined if the
     * specified collection is this collection, and this collection is
     * nonempty.)
     *
     * @param c collection containing elements to be added to this collection
     * @return <tt>true</tt> if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this collection
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this collection
     * @throws NullPointerException          if the specified collection contains a
     *                                       null element and this collection does not permit null elements,
     *                                       or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this
     *                                       collection
     * @throws IllegalStateException         if not all the elements can be added at
     *                                       this time due to insertion restrictions
     * @see #add(Object)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Iterator it =c.iterator();
        while (it.hasNext())
        {
            if (!add((E)it.next()))
                return false;
        }
        return true;
    }

    /**
     * Removes all of this collection's elements that are also contained in the
     * specified collection (optional operation).  After this call returns,
     * this collection will contain no elements in common with the specified
     * collection.
     *
     * @param c collection containing elements to be removed from this collection
     * @return <tt>true</tt> if this collection changed as a result of the
     * call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> method
     *                                       is not supported by this collection
     * @throws ClassCastException            if the types of one or more elements
     *                                       in this collection are incompatible with the specified
     *                                       collection
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this collection contains one or more
     *                                       null elements and the specified collection does not support
     *                                       null elements
     *                                       (<a href="#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    /**
     * Retains only the elements in this collection that are contained in the
     * specified collection (optional operation).  In other words, removes from
     * this collection all of its elements that are not contained in the
     * specified collection.
     *
     * @param c collection containing elements to be retained in this collection
     * @return <tt>true</tt> if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *                                       is not supported by this collection
     * @throws ClassCastException            if the types of one or more elements
     *                                       in this collection are incompatible with the specified
     *                                       collection
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this collection contains one or more
     *                                       null elements and the specified collection does not permit null
     *                                       elements
     *                                       (<a href="#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (this.size()<c.size())
            return false;
        Iterator itCol=c.iterator();
        while (itCol.hasNext())
        {
            if (find(root,(E)itCol.next())==null)
                return false;
        }
        return true;
    }

    /**
     * Removes all of the elements from this collection (optional operation).
     * The collection will be empty after this method returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *                                       is not supported by this collection
     */
    @Override
    public void clear() {
        for (int i=0;i<size();i++)
            remove();
    }
}
