package ds.util.priorityQueue;

import ds.graph.grid.GridCell;
import ds.graph.tree.heap.BinaryMinHeap;

public class BinaryHeapPriorityQueue implements PriorityQueue<GridCell>{

    protected BinaryMinHeap heap;  

    public BinaryHeapPriorityQueue() {
        this.heap = new BinaryMinHeap();
    }

    @Override
    public boolean insert(GridCell e) {
        this.heap.insert(e);
        return true;
    }

    @Override
    public GridCell getMin() {
        return (GridCell)this.heap.getMin();
    }

    @Override
    public GridCell extractMin() {
        return (GridCell)this.heap.extract();
    }

    @Override
    public void decreaseKey(GridCell e, Object k) {
        this.heap.decreaseKey(e, k);
    }

    @Override
    public void delete(GridCell e) {
        this.heap.delete(e);
    }

    @Override
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    @Override
    public boolean contains(GridCell e) {
        return this.heap.contains(e);
    }
    
}
