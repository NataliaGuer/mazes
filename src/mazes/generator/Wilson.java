package mazes.generator;

import java.util.ArrayList;
import java.util.Random;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

public class Wilson extends MazeGenerator{
        
    public Grid generate(int rows, int columns) {
        Grid g = new Grid(rows, columns);

        // init marca tutti i vertici come non visitati
        g.init();

        ArrayList<GridCell> unvisited = new ArrayList<GridCell>();
        GridCell[][] tot = g.getVertices();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                unvisited.add(tot[i][j]);
            }
        }

        // vertice iniziale, il primo ciclo termina quando si arriva a esso
        GridCell start = tot[0][0];
        start.visited = true;
        unvisited.remove(start);

        ArrayList<GridCell> path = new ArrayList<GridCell>();

        Random rnd = new Random();
        // finché ci sono vertici non visitati
        while (unvisited.size() > 0) {
            // ottenimento di un vertice casuale tra quelli ancora non visitati
            GridCell current = unvisited.get(rnd.nextInt(unvisited.size()));
            path.clear();
            path.add(current);

            while (unvisited.contains(current)) {
                ArrayList<GridCell> neighbors = g.getVerticesNearTo(current);
                // viene scelto casualmente un vicino di current
                current = neighbors.get(rnd.nextInt(neighbors.size()));
                int position = path.indexOf(current);
                if (position >= 0) {
                    // se abbiamo individuato un loop lo eliminiamo togliendo dal percorso
                    // tutti i nodi successivi a quello corrente
                    for (int j = path.size() - 1; j > position; j--) {
                        path.remove(j);
                    }
                } else {
                    path.add(current);
                }
            }

            // nel momento in cui si ritorna alla cella da cui si è partiti si collegano
            // i vertici nel percorso
            for (int j = 0; j < path.size() - 1; j++) {
                GridCell v = path.get(j);
                g.addEdge(v, path.get(j + 1));
                unvisited.remove(v);
            }
        }

        return g;
    }
}
