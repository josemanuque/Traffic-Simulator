package trafficsimulator.backend;

import java.util.ArrayList;

public class Node implements Comparable<Node> {

    private boolean isFilled;
    private double alfa; //creation of cars
    private int accumulatedDistance;

    private Node prev;
    private Node next;
    private float x;
    private float y;
    private ArrayList<Edge> edges;

    public Node(double alfa) {
        this.isFilled = false;
        this.alfa = alfa;
        this.edges = new ArrayList<Edge>();
    }
    public Node() {
        this.isFilled = false;
        this.edges = new ArrayList<Edge>();
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(float alfa) {
        this.alfa = alfa;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public int getAccumulatedDistance() {
        return accumulatedDistance;
    }

    public void setAccumulatedDistance(int accumulatedDistance) {
        this.accumulatedDistance = accumulatedDistance;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        edge.getDestiny().edges.add(new Edge(edge.getDistance(), edge.getDestiny(), this));
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.getAccumulatedDistance(), o.getAccumulatedDistance());
    }

//    public void printEdges() {
//        System.out.println("Aristas de Node: " + this);
//        for (Edge edge : edges) {
//            System.out.println("  -> Arista: " + edge.getOrigin() + " -> " + edge.getDestiny() + " (Distancia: " + edge.getDistance() + ")");
//        }
//    }
}
