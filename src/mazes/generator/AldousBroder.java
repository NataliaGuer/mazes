package mazes.generator;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public class AldousBroder extends MazeGenerator{
    
    public Grid generate(int rows, int columns) {
        Grid g = new Grid(rows, columns);
        g.init();

        GridCell current = g.getVertexByCoordinates(0, 0);
        current.visited = true;
        int visited = 1;
        int tot = rows * columns;

        while (visited != tot) {
            GridCell next = g.getRandomCellNearTo(current);
            if (!next.visited) {
                g.addEdge(current, next);
                next.visited = true;
                visited++;
            }
            current = next;
        }

        return g;
    }
}
