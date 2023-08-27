package mazes.solver;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public abstract class MazeSolver {

    public abstract Grid solve(Grid g, GridCell start, GridCell end);

    protected GridCell[][] initDistances(Grid g) {
        GridCell[][] vs = g.getVertices();
        for (int i = 0; i < vs.length; i++) {
            for (int j = 0; j < vs[0].length; j++) {
                // inizializzazione delle distanze
                vs[i][j].setDistance(Integer.MAX_VALUE);
                vs[i][j].visited = false;
            }
        }
        return vs;
    }

    protected boolean relax(GridCell u, GridCell v) {
        boolean res = false;
        if (u.getDistance() > v.getDistance() + 1) {
            u.setDistance(v.getDistance() + 1);
            u.pathParent = v;
            res = true;
        }
        return res;
    }

}
