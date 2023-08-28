package mazes.generator;

import java.util.ArrayList;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public class Prim extends MazeGenerator {

    public Grid generate(int rows, int columns) {
        Grid g = new Grid(rows,columns);
        g.init();

        ArrayList<GridCell> active = new ArrayList<>();

        active.add(g.addVertex(0, 0));
        
        while (active.size() > 0) {

            GridCell c = active.get(rnd.nextInt(active.size()));

            ArrayList<GridCell> neig = g.getCellNearTo(c);
            neig.removeIf(n -> n.countLinks() > 0);
            
            if (neig.size() > 0) {
                GridCell n = neig.get(rnd.nextInt(neig.size()));
                g.addEdge(c, n);
                active.add(n);
            } else {
                active.remove(c);
            }
        }

        return g;
    }
}
