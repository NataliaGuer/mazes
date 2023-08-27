package ds.graph.grid;

import ds.graph.tree.Node;

public class GridCell extends Node implements Comparable<GridCell>{

    protected Integer distance;
    public boolean visited = false;
    public int x;
    public int y;

    public GridCell pathParent;

    public GridCell(int x, int y) {
        super(null);
        this.x = x;
        this.y = y;
        this.rank = 0;
    }
    
    public void setDistance(int d) {
        this.distance = this.priority = d;
    }

    public int getDistance() {
        return this.distance;
    }

    public boolean isDeadEnd() {
        return this.edges.size() == 1;
    }

    @Override
    public int compareTo(GridCell o) {
        //ordinamento in base alla distanza
        if(this.distance == o.distance) {
            return 0;
        } else if (this.distance > o.distance) {
            return 1;
        } else {
            return -1;
        }
    }
}
