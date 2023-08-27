package ds.util.priorityQueue;

public interface PriorityQueue<E> {
    //insieme di elementi con chiave
    //ordinamento definito sulle chiavi degli elementi

    //operazioni da implemetare:
    //-insert
    //-minimum
    //-extrract-min
    //-decrease-key
    //-delete
    boolean insert(E e);
    E getMin();
    E extractMin();
    void decreaseKey(E e, Object k);
    void delete(E e);
    boolean isEmpty();
    boolean contains(E e);

}
