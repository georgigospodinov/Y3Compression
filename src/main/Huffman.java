package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * The Huffman coding algorithm.
 * Wraps the elements from a given {@link InformationSource} inside {@link Node}s,
 * and then builds a tree of these Nodes. The leaf nodes contain symbols.
 * The code is constructed by traversing the tree bottom to top.
 *
 * @see CompressionTechnique
 * @see InformationSource
 * @see InformationSourceEntry
 * @see Node
 * @see Huffman#buildTree(int)
 * @see Huffman#getCoding(char)
 * @see Huffman#encode(String)
 * @author 150009974
 */
public class Huffman extends CompressionTechnique {

    /**
     * Represents a Node in the tree-like structure used by the huffman algorithm.
     * Nodes have a parent connection only and a parent knows its own weight.
     * Nodes may also contain an {@link InformationSourceEntry}.
     *
     * @see Node#parent
     * @see Node#weight
     * @see InformationSourceEntry
     * @version 2.2
     */
    private static class Node implements Comparable {

        /**
         * The parent Node of this one.
         * The root does not have a parent.
         */
        private Node parent;
        private InformationSourceEntry entry;
        private double weight = 0.0;
        private char code;
        private String fullCode;

        double getWeight() {
            if (this.entry != null) return this.entry.getProbability();
            else return this.weight;
        }
        
        void attachChild(Node n) {
            this.weight += n.getWeight();
            n.setParent(this);
        }

        private Node getParent() {
            return this.parent;
        }

        private void setParent(Node parent) {
            this.parent = parent;
        }

        void setCode(char code) {
            this.code = code;
        }

        private String getCode() {
            return this.code != 0 ? ""+this.code : "";
        }
        
        private String getParentCode() {
            return this.getParent() != null ? this.getParent().getFullCode() : "";
        }

        /**
         * Returns the full Huffman Code for this one.
         * That would be the sequence of codes for all parents
         * followed by this specific Node's code.
         * Codes are assigned when joining Nodes under a common parent.
         *
         * @return the full code of the node
         * @see Huffman#merge(int)
         */
        String getFullCode() {
            if (this.fullCode == null)
                this.fullCode = this.getParentCode() + this.getCode();
            return this.fullCode;
        }

        Node() {}

        Node(InformationSourceEntry entry) throws Exception {
            this.entry = new InformationSourceEntry(entry.getSymbol(), entry.getProbability());
        }
        
        @Override
        public int compareTo(Object other) {
            return Double.compare(this.getWeight(), ((Node) other).getWeight());
        }

    }

    /**
     * Contains individual {@link Node}s with {@link InformationSourceEntry}s,
     * ordered from lowest probability (weight) to highest.
     * This is the reverse of the {@link InformationSource#entries} {@link PriorityQueue}.
     * This allows for easier grouping later on.
     *
     * @see Huffman#buildTree(int)
     * @see Node
     */
    private LinkedList<Node> infoSourceAscending = new LinkedList<>();
    /**
     * Maps a symbol to its leaf {@link Node}, which is a Node within the final tree,
     * constructed by the Huffman algorithm. This mapping allows traversing trees bottom to top.
     *
     * @see Huffman#buildTree(int)
     * @see Node
     */
    private HashMap<Character, Node> charToCode = new HashMap<>();
    private HashMap<String, Character> codeToChar = new HashMap<>();
    /**
     * Contains {@link Node}s which may be parents and keeps them ordered by {@link Node#weight}.
     * This is used as a helper queue when grouping.
     *
     * @see Huffman#buildTree(int)
     * @see Node
     */
    private PriorityQueue<Node> trees = new PriorityQueue<>();

    public Huffman(LinkedList<InformationSourceEntry> source, InformationSourceEntry eod) throws Exception {
        for (InformationSourceEntry entry : source) {
            Node n = new Node(entry);
            this.infoSourceAscending.push(n);
            this.charToCode.put(entry.getSymbol(), n);
        }

        if (eod != null) {
            Node n = new Node(eod);
            this.infoSourceAscending.push(n);
            this.charToCode.put(eod.getSymbol(), n);
        }

    }
    
    private Node popLeast() {
        
        if (this.infoSourceAscending.isEmpty()) return this.trees.poll();
        else if (this.trees.isEmpty()) return this.infoSourceAscending.pollFirst();
        // If both are empty, the first if will return null.
        
        double fromSource = this.infoSourceAscending.peekFirst().getWeight();
        double fromTrees = this.trees.peek().getWeight();
        
        return fromSource < fromTrees ? this.infoSourceAscending.pollFirst() : this.trees.poll();
        
    }

    /**
     * Combines k {@link Node}s into one.
     * Creates a new empty Node and attaches the k least weighted Nodes to it, as children.
     * When each child is attached, its connection to the parent is given a single character code.
     * The sequence of codes from the root to a leaf is the encoding of the symbol at the leaf.
     *
     * @param k number of {@link Node}s to merge
     * @see Node
     * @see Node#attachChild(Node)
     */
    private void merge(int k) {
        if (k == 0 || k == 1) return;
        
        Node r = new Node();
        for (int i = 0; i < k ; i ++) {
            Node x = this.popLeast();
            if (x == null) break;  // Out of elements, nothing to do.
            x.setCode((char)('0'+i));
            r.attachChild(x);
        }

        this.trees.add(r);
    }

    /**
     * Combines {@link Node}s to build a tree, until exactly one Node is left.
     * That Node is the root, at each step after the first n Nodes are combined into 1,
     * resulting a (n-1) decrease of the total number of Nodes.
     * The first step combines such a number of Nodes so that
     * the remaining number of Nodes -1 (the root at the end) is divisible by (n-1).
     *
     * @param n the number of nodes to combine at each step after the first
     * @see Huffman#merge(int)
     * @see Node
     */
    public void buildTree(int n) {
        int excess = (this.infoSourceAscending.size()-1)%(n-1);
        if (excess != 0) merge(excess+1);
        while (!this.infoSourceAscending.isEmpty() || this.trees.size() > 1) merge(n);
        
        this.charToCode.forEach((c, node) -> this.codeToChar.put(node.getFullCode(), c));
    }

    @Override
    public String getCoding(char symbol) {
        return this.charToCode.get(symbol).getFullCode();
    }
    
    @Override
    public String encode(String text) {
        
        StringBuilder coded = new StringBuilder();
        int l = text.length();
        for (int i = 0; i < l; i ++)
            coded.append(this.charToCode.get(text.charAt(i)).getFullCode());

        return coded.toString();
        
    }
    
    @Override
    public String decode(String coded) {
        
        StringBuilder text = new StringBuilder();
        int l = coded.length();
        for (int i = 0; i < l; i++) {
            String buffer = "" + coded.charAt(i);
            Character c = this.codeToChar.get(buffer);
            while (c == null) {
                i++;
                buffer += coded.charAt(i);
                c = this.codeToChar.get(buffer);
            }
            text.append(c);
        }
        
        return text.toString();
        
    }

}
