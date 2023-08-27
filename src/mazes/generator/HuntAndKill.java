package mazes.generator;

import java.util.ArrayList;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public class HuntAndKill extends MazeGenerator{

    public Grid generate(int rows, int columns) {
        Grid g = new Grid(rows, columns);
        g.init();

        // inizia scegliendo una cella casuale
        GridCell v = g.getVertexByCoordinates(rows - 1, columns - 1);

        while (v != null) {
            v.visited = true;
            // ottenimento delle celle adiacenti a v non visitate
            ArrayList<GridCell> unvisitedAdjacent = g.getVerticesNearTo(v);
            unvisitedAdjacent.removeIf(b -> b.visited == true);
            if (unvisitedAdjacent.size() > 0) {
                // ci sono ancora vicini da vistare
                GridCell vn = unvisitedAdjacent.get(this.rnd.nextInt(unvisitedAdjacent.size()));

                g.addEdge(vn, v);
                v = vn;
            } else {
                v = findNextStart(g, rows, columns);
            }
        }

        return g;
    }

    protected GridCell findNextStart(Grid g, int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GridCell a = g.getVertexByCoordinates(i, j);
                ArrayList<GridCell> visitedAdjacent = g.getVerticesNearTo(a);
                visitedAdjacent.removeIf(b -> b.visited == false);
                // se a ha almeno un vicino visitato e lui non è visitato
                int s = visitedAdjacent.size();
                if (s > 0 && !a.visited) {
                    // selezione di un vertice random tra quelli viistati vicini ad a
                    GridCell an = visitedAdjacent.get(rnd.nextInt(s));
                    g.addEdge(a, an);
                    return a;
                }
            }
        }
        return null;
    }
}
