package trafficsimulator.backend;

import java.util.ArrayList;

public class Node {
    private boolean isFilled;
    private float alfa; //creation of cars

    private Node prev;
    private Node next;
    private float x;
    private float y;
    private ArrayList<Edge> edges;

    public void Node(float alfa){
        this.isFilled = false;
        this.alfa = alfa;
        this.edges = new ArrayList<Edge>();
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
}
