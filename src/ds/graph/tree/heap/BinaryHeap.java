package ds.graph.tree.heap;

import ds.graph.tree.Node;

public interface BinaryHeap {
    
    public void heapify();
    public Node insert(Node n);
    public Node extract();
    public Node search(Object key);
    public void delete(Node n);
    public void delete(Object key);
    public void decreaseKey(Node n, Object key);
    public void increaseKey(Node n, Object key);
    public boolean isEmpty();
}
