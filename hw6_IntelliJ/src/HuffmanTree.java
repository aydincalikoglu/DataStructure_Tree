import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;

public class HuffmanTree implements Serializable {

    // Nested Classes
    /** A datum in the Huffman tree. */
    public static class HuffData implements Serializable, Comparable<HuffData> {
        // Data Fields

        /** The weight or probability assigned to this HuffData. */
        private double weight;
        /** The alphabet symbol if this is a leaf. */
        private Character symbol;

        public HuffData(double weight, Character symbol) {
            this.weight = weight;
            this.symbol = symbol;
        }


        public Character getSymbol() {return symbol;}

        @Override
        public int compareTo(HuffData o) {
            if (weight<o.weight)
                return -1;
            else if (weight>o.weight)
                return 1;
            return 0;
        }
    }
    // Data Fields
    /** A reference to the completed Huffman tree. */
    protected BinaryTree<HuffData> huffTree;

    /** A Comparator for Huffman trees; nested class. */
    private static class CompareHuffmanTrees
            implements Comparator<BinaryTree<HuffData>> {

        /**
         * Compare two objects.
         * @param treeLeft The left-hand object
         * @param treeRight The right-hand object
         * @return -1 if left less than right,
         * 0 if left equals right,
         * and +1 if left greater than right
         */
        @Override
        public int compare(BinaryTree<HuffData> treeLeft,
                BinaryTree<HuffData> treeRight) {
            double wLeft = treeLeft.getData().weight;
            double wRight = treeRight.getData().weight;
            return Double.compare(wLeft, wRight);
        }
    }

    /**
     * Builds the Huffman tree using the given alphabet and weights.
     *  huffTree contains a reference to the Huffman tree.
     * @param symbols An array of HuffData objects
     */
    public void buildTree(HuffData[] symbols) {
        Queue<BinaryTree<HuffData>> theQueue =
                new PriorityQueue<BinaryTree<HuffData>>(symbols.length,
                new CompareHuffmanTrees());
        // Load the queue with the leaves.
        for (HuffData nextSymbol : symbols) {
            BinaryTree<HuffData> aBinaryTree =
                    new BinaryTree<HuffData>(nextSymbol, null, null);
            theQueue.offer(aBinaryTree);
        }

        // Build the tree.
        while (theQueue.size() > 1) {
            BinaryTree<HuffData> left = theQueue.poll();
            BinaryTree<HuffData> right = theQueue.poll();
            double wl = left.getData().weight;
            double wr = right.getData().weight;
            HuffData sum = new HuffData(wl + wr, null);
            BinaryTree<HuffData> newTree =
                    new BinaryTree<HuffData>(sum, left, right);
            theQueue.offer(newTree);
        }

        // The queue should now contain only one item.
        huffTree = theQueue.poll();
    }

    /**
     * Outputs the resulting code.
     * @param out A PrintStream to write the output to
     * @param code The code up to this node
     * @param tree The current node in the tree
     */
    private void printCode(PrintStream out, String code,
            BinaryTree<HuffData> tree) {
        HuffData theData = tree.getData();
        if (theData.symbol != null) {
            if (theData.symbol.equals(' ')) {
                out.println("space " + code);
            } else {
                out.println(theData.symbol + " " + code);
            }
        } else {
            printCode(out, code + "0", tree.getLeftSubtree());
            printCode(out, code + "1", tree.getRightSubtree());
        }
    }

    /**
     * Outputs the resulting code.
     * @param out A PrintStream to write the output to
     */
    public void printCode(PrintStream out) {
        printCode(out, "", huffTree);
    }


    /**
     * Method to decode a message that is input as a string of
     * digit characters '0' and '1'.
     * @param codedMessage The input message as a String of
     *        zeros and ones.
     * @return The decoded message as a String
     */
    public String decode(String codedMessage) {
        StringBuilder result = new StringBuilder();
        BinaryTree<HuffData> currentTree = huffTree;
        for (int i = 0; i < codedMessage.length(); i++) {
            if (codedMessage.charAt(i) == '1') {
                currentTree = currentTree.getRightSubtree();
            } else {
                currentTree = currentTree.getLeftSubtree();
            }
            if (currentTree.isLeaf()) {
                HuffData theData = currentTree.getData();
                result.append(theData.symbol);
                currentTree = huffTree;
            }
        }
        return result.toString();
    }


    /**
     * Method to encode a message that is input as a string of
     * digit characters '0' and '1'.
     * @param ch a character of The input message
     * @return The decoded message as a String
     * @param code Binary Code String start with empty String
     * @param tree Start with Root of tree
     * @return
     */
    private String encode(char ch, String code,
                           BinaryTree<HuffData> tree) {
        HuffData theData = tree.getData();
        if (tree==null)
            return null;

        if (theData.symbol!=null)
            {
                if(theData.symbol==ch) {
                return code;
            }
        } else {

            String temp= encode(ch, code + "0", tree.getLeftSubtree());
            if (temp!=null)
                return temp;
            return encode(ch, code + "1", tree.getRightSubtree());
        }
        return null;
    }
    /**
     * Method to decode a message that is input as a string of
     * digit characters '0' and '1'.
     * @param value String of encode Value
     * @return The decoded message as a String
     */
    public String encode(String value)
    {
        StringBuilder sB = new StringBuilder();
        for (char tempChar: value.toCharArray()) {
            String code = encode(tempChar,"",huffTree);
            if (code!=null)
                sB.append(code);
        }
        return sB.toString();
    }

    /**
     * Build the tree with codeFile Created before
     * @param fileName Code File Name created before
     * @throws FileNotFoundException if the File not Found
     */

    public void readCodeFileBuildTree(String fileName) throws FileNotFoundException {
        ArrayList<HuffData> huffDataList = new ArrayList<>();
        Scanner s;
            s = new Scanner(new FileReader(fileName));
            while (s.hasNextLine())
            {
                String[] temp = s.nextLine().split(" ");
                try {
                    if (temp[0].contains("space"))
                        huffDataList.add(new HuffData(Integer.parseInt(temp[1]),(" ").charAt(0)));
                    else
                        huffDataList.add(new HuffData(Integer.parseInt(temp[1]),temp[0].charAt(0)));
                }
                catch (NumberFormatException e)
                {
                    System.out.println(fileName+" orderValue error number can not read");
                }
            }
            HuffData[] symbols=new HuffData[huffDataList.size()];
            huffDataList.toArray(symbols);
            buildTree(symbols);

    }


}
