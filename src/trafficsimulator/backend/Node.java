package trafficsimulator.backend;

import java.util.ArrayList;

public class Node {
    private boolean isFilled;
    private float alfa; //creation of cars

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

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public float getAlfa() {
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

    public void addEdge(float distance, Node destinyNode){
        edges.add(new Edge(distance, this, destinyNode));
    }
}
