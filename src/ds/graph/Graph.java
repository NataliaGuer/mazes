package ds.graph;

import java.util.ArrayList;

public class Graph {

    protected ArrayList<Vertex> vertices;
    protected boolean weighted;
    protected boolean directed;

    public Graph(boolean directed, boolean weighted) {
        this.weighted = weighted;
        this.directed = directed;
        this.vertices =  new ArrayList<Vertex>();
    }

    public Vertex addVertex(Object data) {
        Vertex v = new Vertex(data);
        this.vertices.add(v);;
        return v;
    }

    public void addEdge(Vertex v1, Vertex v2, Integer weight) {
        if (!weighted) {
            weight = null;
        }
        v1.addEdge(v2, weight);
        if (!directed) {
            v2.addEdge(v1, weight);
        }
    }

    public void removeEdge(Vertex v1, Vertex v2) {
        v1.removeEdge(v2);
        if (!directed) { 
            v2.removeEdge(v1);
        }
    }

    public void removeVertex(Vertex v) {
        this.vertices.remove(v);
    }

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> res = new ArrayList<Edge>();
        for (Vertex vertex : vertices) {
            for (Edge edge : vertex.getEdges()) {
                res.add(edge);
            }
        }
        return res;
    }
}
