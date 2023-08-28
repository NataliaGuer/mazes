package mazes.solver;

import java.util.ArrayList;
import java.util.PriorityQueue;

import ds.graph.Vertex;
import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public class DijkstraSolver extends MazeSolver{
    
    public Grid solve(Grid g, GridCell start, GridCell end) {
        //BinaryMinHeap q = new BinaryMinHeap();
        PriorityQueue<GridCell> q = new PriorityQueue<>();

        GridCell[][] vs = initDistances(g);

        start.setDistance(0);

        for (int i = 0; i < vs.length; i++) {
            for (int j = 0; j < vs[0].length; j++) {
                //inizializzazione delle distanze
                q.add(vs[i][j]);
            }
        }

        GridCell v = start;
        while (!q.isEmpty() && !v.equals(end)) {
            v = (GridCell)q.poll();
            ArrayList<Vertex> n = v.getNeighbors();
            for (int i = 0; i < n.size(); i++) {
                GridCell u = (GridCell)n.get(i);
                if (q.contains(u)) {
                    boolean relaxed = relax(u, v);
                    if (relaxed) {
                        q.remove(u);
                        q.add(u);
                    }
                }
            }
        }
        return g;
    }
    
}
