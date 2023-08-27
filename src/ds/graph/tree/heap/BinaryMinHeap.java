package ds.graph.tree.heap;

import ds.graph.tree.BinaryTree;
import ds.graph.tree.Node;

public class BinaryMinHeap extends BinaryTree implements BinaryHeap {

    protected String lastIndex;

    public BinaryMinHeap() {
        this.root = null;
    }

    @Override
    public void heapify() {
        fixDown(root);
    }

    @Override
    public Node insert(Node n) {
        if (root == null) {
            root = n;
            this.lastIndex = "1";
        } else {
            String nextIndex = getNextIndex();
            insertByIndex(n, nextIndex);
            this.lastIndex = nextIndex;
            fixUp(n);
        }
        return n;
    }

    //***********************************
    //              TEST
    //***********************************
    public Node insert(int p) {
        Node n = new Node(null);
        n.setPriority(p);
        this.insert(n);
        return n;
    }

    @Override
    public Node extract() {
        if (root != null) {
            Node lastNode = this.getByIndex(lastIndex);
            Node toReturn = this.root;
            
            //caso in cui è presente solo un elemento nell'heap
            if (!lastNode.equals(root)) {
                swapNodes(root, lastNode);
                removeLeaf(toReturn);
                fixDown(root);
                this.lastIndex = getPreviousIndex();
            } else {
                removeLeaf(root);
                this.root = null;
                this.lastIndex = null;
            }
        
            return toReturn;
        }
        return null;
    }

    @Override
    public void delete(Node n) {
        Node lastNode = this.getByIndex(lastIndex);
        swapNodes(n, lastNode);
        removeLeaf(n);
        fixDown(lastNode);
    }

    @Override
    public void delete(Object key) {
        delete(search(key));
    }

    @Override
    public void decreaseKey(Node n, Object key) {
        n.setData(key);
        fixUp(n);
    }

    @Override
    public void increaseKey(Node n, Object key) {
        n.setData(key);
        fixDown(n);
    }

    @Override
    public boolean isEmpty(){
        return root == null;
    }
    
    public Node getMin() {
        return this.root;
    }

    public String toString() {
        return toString_ric(root, 0);
    }

    public String toString_ric(Node n, int i) {
        String s = null;
        if (n != null) {
            String tab = "\t";
            s = "Node: "+n.getPriority()+"\n";
            s += tab.repeat(i)+"left: "+toString_ric(n.getLeft(), i+1)+"\n";
            s += tab.repeat(i)+"right: "+toString_ric(n.getRight(), i+1)+"\n";
        }
        return s;
    }

    protected String getNextIndex() {
        int n = Integer.parseInt(lastIndex, 2);
        return Integer.toBinaryString(n + 1);
    }

    protected String getPreviousIndex() {
        int n = Integer.parseInt(lastIndex, 2);
        return Integer.toBinaryString(n - 1);
    }

    protected Node getByIndex(String index) {
        Node n = root;
        if(index.length() > 1) {
            for (int i = 1; i < index.length() - 1; i++) {
                if (index.charAt(i) == '0') {
                    n = n.getLeft();
                } else {
                    n = n.getRight();
                }
            }
    
            if (index.charAt(index.length()-1) == '0') {
                n = n.getLeft();
            } else {
                n = n.getRight();
            }
        }
        return n;
    }

    protected void insertByIndex(Node n, String index) {
        String parentIndex = index.substring(0, index.length()-1);
        Node parent = getByIndex(parentIndex);

        n.setParent(parent);

        if (index.charAt(index.length() - 1) == '0') {
            parent.setLeft(n);
        } else {
            parent.setRight(n);
        }
    }

    protected void fixUp(Node n) {
        while (n != null && n.getParent() != null && n.getPriority() < n.getParent().getPriority()) {
            // scambia i due nodi
            swapNodes(n, n.getParent());
        }
        //se il fix è arrivato fino alla radice
        // if(n.getParent() == null && !this.root.equals(n)) {
        //     this.root = n;
        // }
    }

    protected void fixDown(Node n) {
        // se la chiave nel nodo corrente è maggiore di quella di uno o di entrambi i
        // figli
        // si scambia il nodo corrente con il figlio che ha chiave minore
        if (n != null) {
            Node min = n;
            if (n.getLeft() != null && n.getLeft().getPriority() < min.getPriority()) {
                min = n.getLeft();
            }
            if (n.getRight() != null && n.getRight().getPriority() < min.getPriority()) {
                min = n.getRight();
            }

            if (!min.equals(n)) {
                swapNodes(n, min);
                fixDown(n);
            }
        }
    }

    public void swapNodes(Node n1, Node n2) {
        Node n1p = n1.getParent();
        boolean n1IsLeftChidl =  n1p != null ? n1p.getLeft() != null && n1p.getLeft().equals(n1) : false;
        Node n1l = n1.getLeft();
        Node n1r = n1.getRight();
        
        Node n2p = n2.getParent();
        boolean n2IsLeftChidl = n2p != null ? n2p.getLeft() != null && n2p.getLeft().equals(n2) : false;
        Node n2l = n2.getLeft();
        Node n2r = n2.getRight();
        
        //n1 diventa orfano
        if(n1p != null) {
            if (n1IsLeftChidl) {
                n1p.setLeft(null);
            } else {
                n1p.setRight(null);
            }
        }
        
        n1.setLeft(null);
        n1.setRight(null);
        
        //n2 diventa orfano
        if(n2p != null) {
            if (n2IsLeftChidl) {
                n2p.setLeft(null);
            } else {
                n2p.setRight(null);
            }
        }

        n2.setLeft(null);
        n2.setRight(null);

        n1.setLeft(n2l);
        if (n2l != null) {
            n2l.setParent(n1);
        }
        n1.setRight(n2r);
        if (n2r != null) {
            n2r.setParent(n1);
        }

        if (!n2.equals(n1l)) {
            n2.setLeft(n1l);
            if (n1l != null) {
                n1l.setParent(n2);
            }
        } else {
            n2.setLeft(null);
        }

        if (!n2.equals(n1r)) {
            n2.setRight(n1r);
            if (n1r != null) {
                n1r.setParent(n2);
            }
        } else {
            n2.setRight(null);
        }

        if (n1p != null && !n1p.equals(n2)) {
            if (n1IsLeftChidl) {
                n1p.setLeft(n2);
            } else {
                n1p.setRight(n2);
            }
        }

        if (n2p != null && !n2p.equals(n1)) {
            if (n2IsLeftChidl) {
                n2p.setLeft(n1);
            } else {
                n2p.setRight(n1);
            }
        }

        if (!n1.equals(n2p)) {
            n1.setParent(n2p);
        } else {
            n1.setParent(n2);
            if (n2IsLeftChidl) {
                n2.setLeft(n1);
            } else {
                n2.setRight(n1);
            }
        }
        if (!n2.equals(n1p)) {
            n2.setParent(n1p);
        } else {
            //siamo nel caso in cui n2 è padre di n1
            //n1.l o n1.r = n2
            n2.setParent(n1);
            if (n1IsLeftChidl) {
                n1.setLeft(n2);
            } else {
                n1.setRight(n2);
            }
        }

        //controllo per eventuale aggiornamento della root
        if (n1.getParent() == null) {
            this.root = n1;
        } else if (n2.getParent() == null) {
            this.root = n2;
        }
    }

    protected void removeLeaf(Node n) {
        Node parent = n.getParent();
        if (parent != null) {
            if (parent.getLeft() != null && parent.getLeft().equals(n)) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }

        n.setParent(null);
    }
}
