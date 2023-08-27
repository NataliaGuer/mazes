package mazes.solver;

import java.util.ArrayList;
import java.util.PriorityQueue;

import ds.graph.Vertex;
import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public class AStarSolver extends MazeSolver{

    protected GridCell end;

    public Grid solve(Grid g, GridCell start, GridCell end) {

        this.end = end;
        
        //coda contenente i vertici visitati
        //BinaryMinHeap q = new BinaryMinHeap();
        PriorityQueue<GridCell> q = new PriorityQueue<>();
        initDistances(g);

        start.setDistance(calcEuristic(start));

        //q.insert(start);
        q.add(start);

        while (!q.isEmpty()) {
            GridCell v = (GridCell)q.poll();
            if (v.equals(end)) {
                return g;
            }

            ArrayList<Vertex> n = v.getNeighbors();
            for (int i = 0; i < n.size(); i++) {
                GridCell u = (GridCell)n.get(i);
                boolean relaxed = relax(u, v);
                if (relaxed) {
                    if (q.contains(u)) {
                        q.remove(u);
                        q.add(u);
                        //q.decreaseKey(u, u.getDistance());
                    } else {
                        q.add(u);
                    }
                }
            }
        }
        
        return null;
    }

    protected int calcEuristic(GridCell c) {
        return Math.abs(c.x - end.x) + Math.abs(c.y - end.y);
    }
}
