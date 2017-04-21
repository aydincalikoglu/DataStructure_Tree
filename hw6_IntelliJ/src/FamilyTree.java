import java.util.*;

/**
 * Generic Class Family Tree
 * Take an parameter as a root Person Name
 */
public class FamilyTree extends BinaryTree<String> {

    /**
     * Construct an empty BinaryTree
     * @param person name that root of family tree
     */
    public FamilyTree(String person) {
        root=new Node<>(person);
    }


    /**
     * Give an iterator traverse the tree according to Level order
     * @return levelOrderIterator
     */
    public Iterator levelOrderIterator() {
        return new mylevelOrderIterator();
    }


    /**
     * Inner Class iterator traverse the tree according to Level order
     */
    private class mylevelOrderIterator implements Iterator<String> {
        public mylevelOrderIterator() {
            queue.offer(root);
        }

        Queue<Node> queue = new LinkedList<>();

        @Override
        public boolean hasNext() {
            if (queue.size() > 0)
                return true;
            return false;
        }

        @Override
        public String next() {
            if (queue.size() < 1) {
                throw new NullPointerException();
            }
            else {
                Node temp = queue.poll();
                if (temp.left != null)
                    queue.offer(temp.left);
                if (temp.right != null)
                    queue.offer(temp.right);

                return temp.data.toString();

            }
        }


    }
    /**
     * Iterator Class PreOrder
     * @param <E>
     */
    private class myIterator<E> implements Iterator<E> {
        /**
         * Constructor
         */
        public myIterator() {
            stack.push(root);
        }

        Node<E> curr = (Node<E>) root;

        Stack<Node> stack = new Stack<>();


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


    /**
     * binary tree add Node with data,
     * if it's child,add on the left,
     * if it's brother,add on the right,
     *
     * @param personName person name which is will added
     * @param parentName person of mother or father's name
     * @param nickNameParent nickName of parent
     */
    public void add(String personName, String parentName, String nickNameParent)
    {
        String[] nicks = nickNameParent.split("-");
        if (nicks[0].equals("ebu"))
        {
            Node father =find(root,parentName);
            if (father==null)
                throw new NoSuchElementException();
            if (father.left==null)
                father.left=new Node(personName);
            else
            {
                Node bro= find(father,nicks[1]);
                if (bro==null)
                    throw new NoSuchElementException();
                else
                {
                    while (bro.right!=null)
                        bro=bro.right;
                    bro.right=new Node(personName);
                }
            }
        }
        else if(nicks[0].equals("ibn")) {

            Node father = find(root, nicks[1]);
            if (father == null)
            {
                throw new NoSuchElementException();
            }
            Node parent=find(father,parentName);
            if (parent==null)
                throw new NoSuchElementException();
            if (parent.left==null)
                parent.left=new Node(personName);
            else
            {
                parent=parent.left;
                while (parent.right!=null)
                    parent=parent.right;
                parent.right=new Node(personName);
            }


        }
        else throw new NullPointerException();
    }

    /**
     * Search Target value on tree
     * Return Node of that Target
     * @param localRoot
     * @param target
     * @return Node of that Target
     */
    private Node<String> find(Node<String> localRoot, String target) {
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







}
