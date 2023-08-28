package mazes.generator;

import java.util.ArrayList;
import java.util.Stack;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;

/**
 * versione base dell'algoritmo senza cancellazione dei loop
 */
public class WilsonTest extends MazeGenerator{

    @Override
    public Grid generate(int rows, int columns) {
        //tutte le celle della griglia vengono marcate come non visitate 
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

        //in questa pila vengono memorizzate tutte le celle visitate durante la random walk
        Stack<GridCell> path = new Stack<>();

        while (!unvisited.isEmpty()) {
            GridCell current = unvisited.get(rnd.nextInt(unvisited.size()));
            GridCell pathStart = current;

            //fin quando il vertice corrente non è visitato 
            while (!current.visited) {
                path.add(current);
                current = g.getRandomCellNearTo(current);
            }

            //nel momento in cui current è una cella già visitata si collegano le celle presenti in path tra di loro
            while (!path.isEmpty() && !current.equals(pathStart)) {
                GridCell top = path.pop();
                g.addEdge(current, top);
                //solo nel momento in cui le celle vengono aggiunte al labirinto esse vengono contrassegnate come visitate
                unvisited.remove(top);
                top.visited = true;
                current = top;
            }

            path.empty();
        }

        return g;
    }
    

}
