package trafficsimulator.backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Edge {
    private int distance;
    private Node origin;
    private Node destiny;

    private Map<Node, Queue<Vehicle>> vehicleQueues;

    public Edge(int distance, Node origin, Node destiny){
        this.distance = distance;
        this.origin = origin;
        Queue<Vehicle> originQ = new LinkedList<>();
        this.destiny = destiny;
        Queue<Vehicle> destinyQ = new LinkedList<>();
        //System.out.println("Edge created");
        vehicleQueues.put(this.origin, originQ);
        vehicleQueues.put(this.destiny, destinyQ);
    }

    public int getDistance() {
        return distance;
    }

    public Map<Node, Queue<Vehicle>> getVehicleQueues(){
        return this.vehicleQueues;
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
