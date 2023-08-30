package mazes.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import ds.graph.Vertex;
import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public class AStarSolver extends MazeSolver{

    protected GridCell end;
    protected HashMap<GridCell, Integer> map;

    public Grid solve(Grid g, GridCell start, GridCell end) {

        this.end = end;

        GridCell[][] vertices = g.getVertices();
        
        //q contiene i vertici ordinati in base alla loro euristica
        PriorityQueue<GridCell> q = new PriorityQueue<>();

        //map contiene le distanze calcolate di ogni cella
        this.map = new HashMap<>();

        int rows = vertices.length;
        int columns = vertices[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns ; j++) {
                map.put(vertices[i][j], Integer.MAX_VALUE);
            }
        }

        map.put(start,0);

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

        for (GridCell gridCell : q) {
            gridCell.setDistance(map.get(gridCell));
        }
        
        return null;
    }

    protected int calcEuristic(GridCell c) {
        return Math.abs(c.x - end.x) + Math.abs(c.y - end.y);
    }

    protected boolean relax(GridCell u, GridCell v) {
        boolean res = false;
        int d = map.get(v) + 1;
        if (map.get(u) > d) {
            map.put(u, d);
            u.setDistance(d + calcEuristic(u));
            u.pathParent = v;
            res = true;
        }
        return res;
    }
}
