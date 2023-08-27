package ds.graph;

import java.util.ArrayList;

import ds.util.PriorityElement;

public class Vertex extends PriorityElement{
    
    protected Object data;
    protected ArrayList<Edge> edges;

    public Vertex(Object data) {
        this.data = data;
        this.edges = new ArrayList<Edge>();
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void addEdge(Vertex vertex, Integer weight) {
        this.edges.add(new Edge(this, vertex, weight));
    }

    public void removeEdge(Vertex vertex) { 
        this.edges.removeIf(edge -> edge.end.equals(vertex));
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public ArrayList<Vertex> getNeighbors() {
        ArrayList<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            neighbors.add(edge.end);
        }
        return neighbors;
    }

    public boolean linkedTo(Vertex v) {
        for (Edge edge : edges) {
            if (edge.end.equals(v)) {
                return true;
            }
        }
        return false;
    }

    public int countLinks() {
        return this.edges.size();
    }

}
