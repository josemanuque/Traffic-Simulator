package trafficsimulator.backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Node implements Comparable<Node> {

    private boolean isFilled;
    private double alfa; //creation of cars
    private int accumulatedDistance;

    private Node prev;
    private Node next;
    private int x;
    private int y;
    private ArrayList<Edge> edges;

    private Queue<Vehicle> generalQ;

    public Node(float alfa, int x, int y){
        this.isFilled = false;
        this.alfa = alfa;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<Edge>();
        this.generalQ = new LinkedList<Vehicle>();
        System.out.println("Node Created");
    }
    public Node() {
        this.isFilled = false;
        this.edges = new ArrayList<Edge>();
    }

    public Queue<Vehicle> getGeneralQ() {
        return generalQ;
    }

    public void setGeneralQ(Queue<Vehicle> generalQ) {
        this.generalQ = generalQ;
    }

    public void addVehicleQ(Vehicle vehicle){
        this.generalQ.add(vehicle);
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
