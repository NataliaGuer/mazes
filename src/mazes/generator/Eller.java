package mazes.generator;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;
import ds.util.DisjointSet;

public class Eller extends MazeGenerator {

    public Grid generate(int rows, int columns) {
        Grid g = new Grid(rows,columns);
        g.init();

        DisjointSet ds = new DisjointSet();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GridCell v = g.getCellByCoordinates(i, j);
                ds.makeSet(v);
            }
        }

        for (int i = 0; i < rows-1; i++) {

            for (int j = 0; j < columns-1; j++) {
                GridCell v = g.getCellByCoordinates(i, j);
                GridCell left = g.getCellByCoordinates(i, j+1);
                
                if( !ds.findSet(v).equals(ds.findSet(left)) && rnd.nextBoolean()) {
                    //colleagamento a destra
                    ds.union(v,  left);
                    g.addEdge(v, left);
                } 
            }

            GridCell ref = g.getCellByCoordinates(i, 0);
            int linkedDown = 0;
            for (int j = 0; j < columns; j++) {
                
                GridCell v = g.getCellByCoordinates(i,j);

                if (!ds.findSet(v).equals(ds.findSet(ref))) {
                    ref = v;
                    linkedDown = 0;
                }

                //se ancora non ci sono collegamenti a sinistra per il set
                if (linkedDown == 0 || rnd.nextBoolean()) {
                    GridCell south = g.getSouth(v);
                    g.addEdge(v, south);
                    ds.union(v, south);
                    linkedDown++;
                }
            }
        }

        //riga finale
        for (int j = 0; j < columns-1; j++) {
            GridCell v = g.getCellByCoordinates(rows-1, j);
            GridCell east = g.getEast(v);
            if(!ds.findSet(v).equals(ds.findSet(east))){
                g.addEdge(v, east);
                ds.union(v, east);
            }
        }

        g.clean();
        return g;
    }
    
}
