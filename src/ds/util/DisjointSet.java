package ds.util;

import ds.graph.tree.Node;

public class DisjointSet {

    public DisjointSet() {}

    public void makeSet(Node v) {
        //il fatto che v.parent punti a v identifica la radice dell'albero e il rappresentante
        //dell'insieme
        v.setParent(v);
    }

    /**
     * restituisce il rappresentante dell'insieme a cui appartiene v
     * @param v
     * @return
     */
    public Node findSet(Node v) {
        
        //compressione del cammino
        if (!v.getParent().equals(v)) {
            v.setParent(findSet(v.getParent()));
            return v.getParent();
        } else {
            return v;
        }
    }

    /**
     * unisce i due insiemi in un unico insieme e restituisce il rappresentante del nuovo insieme
     * @param x
     * @param y
     * @return
     */
    public Node union(Node x, Node y) {
        Node rootx = findSet(x);
        Node rooty = findSet(y);

        if (rootx.rank == rooty.rank) {
            //se le due radici hanno lo stesso rango 
            //una delle qualsiasi delle due punta all'altra
            //viene incrementato il rango della radice finale
            rootx.setParent(rooty);
            rooty.rank++;
            return rooty;
        }

        //se i due ranghi non coincidono
        //la radice con rango minore viene fatta puntare all'altra
        if(rootx.rank < rooty.rank) {
            rootx.setParent(rooty);
            return rooty;
        } else {
            rooty.setParent(rootx);
            return rootx;
        }

    }
}
