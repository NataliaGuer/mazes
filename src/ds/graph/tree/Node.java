package ds.graph.tree;

import ds.graph.Vertex;

public class Node extends Vertex{

    public int rank;
    
    protected Node left;
    protected Node right;
    protected Node parent;
    protected Object index;

    public Node(Object key) {
        super(key);
    }

    public void setParent(Node p) {
        this.parent = p;
    }

    public void setLeft(Node n) {
        this.left = n;
    }

    public void setRight(Node n) {
        this.right = n;
    }

    public void setIndex(Object i) {
        this.index = i;
    }

    public Node getParent() {
        return this.parent;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public Object getIndex() {
        return this.index;
    }
}
