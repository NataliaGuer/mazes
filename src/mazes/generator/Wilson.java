package mazes.generator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

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
        unvisited.remove(start);

        Stack<GridCell> path = new Stack<>();

        //finché ci sono vertici non visitati
        while (unvisited.size() > 0) {
            // ottenimento di un vertice casuale tra quelli ancora non visitati
            GridCell current = unvisited.get(rnd.nextInt(unvisited.size()));

            while (unvisited.contains(current)) {
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

                current = g.getRandomCellNearTo(current);
            }

            while (!path.isEmpty()) {
                GridCell top = path.pop();
                g.addEdge(current, top);
                //solo nel momento in cui le celle vengono aggiunte al labirinto esse vengono contrassegnate come visitate
                unvisited.remove(top);
                current = top;
            }
        }

        return g;
    }
}
