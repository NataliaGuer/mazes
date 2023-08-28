package ds.graph.grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import ds.graph.Edge;
import ds.graph.Graph;
import ds.graph.Vertex;

import java.awt.geom.Line2D.Double;

public class Grid extends Graph {

    protected GridCell[][] vertices;

    protected int rows;
    protected int columns;

    protected Random r;

    protected final static int CELL_HEIGHT = 10;
    protected final static int CELL_WIDTH  = 10;
    
    public Grid(int rows, int columns) {
        super(false, false);
        
        this.rows = rows;
        this.columns = columns;
        this.vertices = new GridCell[rows][columns];

        this.r = new Random();
    }

    public GridCell addVertex(int x, int y) {
        GridCell gv = new GridCell(x, y);
        this.vertices[x][y] = gv;
        return gv;
    }

    public void addEdge(GridCell v1, GridCell v2){
        super.addEdge(v1, v2, null);
    }

    public GridCell[][] getVertices() {
        return this.vertices;
    }

    public void removeVertex(GridCell v) {
        this.vertices[v.x][v.y] = null;
    }

    public void init() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.addVertex(i, j);
            }
        }
    }

    public void addEdge(Vertex v1, Vertex v2) {
        super.addEdge(v1, v2, 1);
    }

    public void clean() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; j++) {
                vertices[i][j].setParent(null);
            }
        }
    }

    public int countCells() {
        return rows*columns;
    }

    public GridCell getNorth(GridCell v) {
        return this.getCellByCoordinates(v.x - 1, v.y);
    }

    public GridCell getEast(GridCell v) {
        return this.getCellByCoordinates(v.x, v.y + 1);
    }

    public GridCell getSouth(GridCell v) {
        return this.getCellByCoordinates(v.x + 1, v.y);
    }

    public GridCell getWest(GridCell v) {
        return this.getCellByCoordinates(v.x, v.y - 1);
    }

    public GridCell getCellByCoordinates(int x, int y) {
        if (0 <= x && x < rows && 0 <= y && y < columns) {
            return this.vertices[x][y];
        }
        return null;
    }

    public GridCell getRandomCell() {
        return this.getCellByCoordinates(r.nextInt(rows), r.nextInt(columns));
    }

    /**
     * restituisce i Vertex che sono spazialmente vicini a v nella griglia
     * 
     * @param v
     * @return
     */
    public ArrayList<GridCell> getCellNearTo(GridCell v) {
        ArrayList<GridCell> res = new ArrayList<GridCell>();
        GridCell n = getNorth(v);
        if (n != null) {
            res.add(n);
        }

        n = getEast(v);
        if (n != null) {
            res.add(n);
        }

        n = getSouth(v);
        if (n != null) {
            res.add(n);
        }

        n = getWest(v);
        if (n != null) {
            res.add(n);
        }

        return res;
    }

    public GridCell getRandomCellNearTo(GridCell v) {
        ArrayList<GridCell> near = getCellNearTo(v);
        int i = r.nextInt((near.size()));
        return near.get(i);
    }

    @Override
    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> res = new ArrayList<Edge>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (Edge edge : vertices[i][j].getEdges()) {
                    res.add(edge);
                }
            }
        }
        return res;
    }

    public int countDeadEnds() {
        int res = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(vertices[i][j].isDeadEnd()){
                    res ++;
                }
            }
        }
        return res;
    }

    public void toImage(String filename) {

        BufferedImage img = new BufferedImage(CELL_WIDTH * rows + 1, CELL_HEIGHT * columns + 1,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        getGrid(g);
        try {
            ImageIO.write(img, "jpg", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
     * metodo per eliminazione dei vicoli ciechi
     * @param p
     */
    public void braid(double p){
        //iterazione attraverso i vicoli ciechi
        //il singolo vicolo cieco viene eliminato se il numero randomico estratto (tra 0 e 1) è minore di p
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                GridCell v = vertices[i][j];

                if(v.getEdges().size() == 1 && r.nextDouble() < p){
                    //eliminazione del vicolo cieco attraverso il collegamento del vertice a uno dei suo vicini
                    ArrayList<GridCell> nearV = getCellNearTo(v);
                    ArrayList<GridCell> preferredList = new ArrayList<>();
                
                    for (GridCell vertex : nearV) {
                        if (vertex.isDeadEnd()) {
                            preferredList.add(v);
                        }
                    }

                    int preferredListSize = preferredList.size();
                    GridCell toLink = null;
                    if(preferredListSize > 0){
                        //se ci sono vicoli ciechi tra i vertici vicini al vertice corrente si sceglie uno di essi
                        toLink = preferredList.get(r.nextInt(preferredListSize));
                    } else {
                        toLink = nearV.get(r.nextInt(nearV.size()));
                    }

                    addEdge(v, toLink, 1);
                }
            }
        }
    }

    public void printPath(String filename, GridCell start, GridCell end) {

        BufferedImage img = new BufferedImage(CELL_WIDTH * rows + 1, CELL_HEIGHT * columns + 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        int red = 11;
        int green = 114;
        int blue = 133;

        Color c = new Color(red, green, blue);
        
        GridCell v = end;

        ArrayList<GridCell> path = new ArrayList<>();

        while (v != null) {
            if (v.equals(start)) {
                g.setColor(Color.white);
            } else if (v.equals(end)) {
                g.setColor(Color.green);
            } else {
                g.setColor(c);
            }
            int x1 = CELL_WIDTH * v.y;
            int y1 = CELL_HEIGHT * v.x;

            g.fillRect(x1, y1, CELL_WIDTH, CELL_HEIGHT);
            path.add(v);
            v = v.pathParent;
        }

        getGrid(g);

        try {
            ImageIO.write(img, "jpg", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void getGrid(Graphics2D g) {
        
        g.setColor(Color.white);
        g.fillRect(0, 0, rows*CELL_WIDTH, columns*CELL_HEIGHT);
        
        g.setColor(Color.black);

        Double line = null;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {

                GridCell v = this.vertices[i][j];

                int x1 = CELL_WIDTH * j;
                int y1 = CELL_HEIGHT * i;
                int x2 = CELL_WIDTH * (j + 1);
                int y2 = y1;
                int x3 = x1;
                int y3 = CELL_HEIGHT * (i + 1);
                int x4 = x2;
                int y4 = y3;

                if (!v.linkedTo(getNorth(v))) {
                    line = new Double(x1, y1, x2, y2);
                    g.draw(line);
                }
                if (getWest(v) == null) {
                    line = new Double(x1, y1, x3, y3);
                    g.draw(line);
                }
                if (getSouth(v) == null) {
                    line = new Double(x3, y3, x4, y4);
                    g.draw(line);
                }
                Vertex e = getEast(v);
                if (e == null || !v.linkedTo(e)) {
                    line = new Double(x4, y4, x2, y2);
                    g.draw(line);
                }
            }
        }
    }
}