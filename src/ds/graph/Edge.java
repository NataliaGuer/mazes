package ds.graph;

public class Edge {

    public Vertex start;
    public Vertex end;
    public Integer weight;

    public Edge(Vertex start, Vertex end, Integer weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}
