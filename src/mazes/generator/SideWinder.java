package mazes.generator;

import java.util.ArrayList;
import java.util.Random;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public class SideWinder extends MazeGenerator{

    public Grid generate(int rows, int columns) {

        Grid g = new Grid(rows, columns);

        g.init();

        Random rnd = new Random();
        for (int i = 0; i < rows; i++) {
            ArrayList<GridCell> run = new ArrayList<GridCell>();
            for (int j = 0; j < columns; j++) {
                GridCell v = g.getCellByCoordinates(i, j);

                run.add(v);

                boolean rand = rnd.nextBoolean();

                GridCell ve = g.getEast(v);
                boolean chooseFromRun = (g.getNorth(v) != null && rand) || ve == null;

                if (chooseFromRun) {
                    int index = (int) Math.round(Math.random() * (run.size() - 1));
                    GridCell v2 = run.get(index);
                    GridCell v2n = g.getNorth(v2);
                    if (v2n != null) {
                        g.addEdge(v2, v2n);
                    }
                    run.clear();
                } else {
                    g.addEdge(v, ve);
                    run.add(ve);
                }
            }
        }

        return g;
    }
    
}
