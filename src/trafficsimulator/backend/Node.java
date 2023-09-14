package trafficsimulator.backend;

import java.util.ArrayList;

public class Node implements Comparable<Node> {

    private boolean isFilled;
    private double alfa; //creation of cars
    private int accumulatedDistance;

    private Node prev;
    private Node next;
    private int x;
    private int y;
    private ArrayList<Edge> edges;

    public Node(float alfa, int x, int y){
        this.isFilled = false;
        this.alfa = alfa;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<Edge>();
        System.out.println("Node Created");
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
    public void addEdge(int distance, Node destinyNode){
        edges.add(new Edge(distance, this, destinyNode));
    }
}
