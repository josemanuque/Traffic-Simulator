package trafficsimulator.backend;

import java.util.ArrayList;

public class Edge {
    private float distance;
    private Node origin;
    private Node destiny;

    public Edge(float distance, Node origin, Node destiny){
        this.distance = distance;
        this.origin = origin;
        this.destiny = destiny;
        System.out.println("Edge created");
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public Node getDestiny() {
        return destiny;
    }

    public void setDestiny(Node destiny) {
        this.destiny = destiny;
    }
}
