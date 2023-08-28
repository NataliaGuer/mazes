package mazes.generator;

import java.util.ArrayList;
import java.util.Collections;

import ds.graph.grid.Grid;
import ds.graph.grid.GridCell;
import ds.graph.grid.GridEdge;
import ds.graph.tree.Node;
import ds.util.DisjointSet;

public class Kruskal extends MazeGenerator {

    public Grid generate(int rows, int columns) {
        Grid g = new Grid(rows, columns);
        g.init();

        DisjointSet ds = new DisjointSet();

        //lista dei possibili archi
        ArrayList<GridEdge> virtualEdges = new ArrayList<GridEdge>();
        
        //per ogni vertice crea un insieme disgiunto
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GridCell v = g.getCellByCoordinates(i, j);
                ds.makeSet(v);
                
                GridCell north = g.getNorth(v);
                GridCell west = g.getWest(v);
                if(north != null) {
                    virtualEdges.add(new GridEdge(v, north));
                }
                if(west != null) {
                    virtualEdges.add(new GridEdge(v, west));
                }

            }
        }

        Collections.shuffle(virtualEdges);

        while (virtualEdges.size() > 0) {
            
            //seleziona uno dei vicini di u
            GridEdge e = virtualEdges.remove(0);

            //se trova che i nodi su cui insiste l'arco sono in due insiemi disgiunti li unisce
            if (!ds.findSet((Node)e.start).equals(ds.findSet((Node)e.end))) {
                ds.union((Node)e.start, (Node)e.end);
                g.addEdge(e.start, e.end);
            }
        }

        g.clean();
        return g;
    }
}
