package ds.graph.tree;

import ds.graph.Graph;

public class BinaryTree extends Graph {

    protected Node root;

    public BinaryTree() {
        super(false, false);
        this.root = null;
    }

    public void empty() {
        this.root = null;
    }

    public Node addRoot(Object key) {
        Node n = new Node(key);
        this.root = n;
        return n;
    }

    public Node search(Object key) {
        return search_ric(root, key);
    }

    public boolean contains(Node n) {
        return search_ric(root, n);
    }

    public Node addLeftChild(Node n, Object key) {
        Node c = new Node(key);
        n.setLeft(c);
        c.setParent(n);
        return c;
    }

    public Node addRightChild(Node n, Object key) {
        Node c = new Node(key);
        n.setRight(c);
        c.setParent(n);
        return c;
    }

    public void addLeftSubTree(BinaryTree t, Node n) {
        t.getRoot().setParent(n);
        n.setLeft(t.getRoot());
    }

    public void addRightSubTree(BinaryTree t, Node n) {
        t.getRoot().setParent(n);
        n.setRight(t.getRoot());
    }

    public Node getRoot() {
        return this.root;
    }

    public Node getLeft(Node n) {
        return n.getLeft();
    }

    public Node getRight(Node n) {
        return n.getRight();
    }

    public Node getParent(Node n) {
        return n.getParent();
    }

    protected Node search_ric(Node n, Object key) {
        if (n == null || n.getData().equals(key)) {
            return n;
        }

        Node left_search = search_ric(n.getLeft(), key);
        if (left_search.getData().equals(key)) {
            return left_search;
        }

        return search_ric(n.getRight(), key);
    }

    protected boolean search_ric(Node start, Node toFind) {
        if (start == null) {
            return false;
        }
        if (start.equals(toFind)) {
            return true;
        }

        return search_ric(start.getLeft(), toFind) || search_ric(start.getRight(), toFind);
    }
}
