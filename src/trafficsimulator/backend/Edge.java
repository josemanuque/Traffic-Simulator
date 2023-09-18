package trafficsimulator.backend;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Edge {

    private int distance;
    private Node origin;
    private Node destiny;

    private Map<Node, Queue<Vehicle>> vehicleQueues;

    public Edge(int distance, Node origin, Node destiny) {
        this.distance = distance;
        this.origin = origin;
        this.destiny = destiny;
        this.vehicleQueues = new HashMap<>();
        Queue<Vehicle> originQ = new LinkedList<>();
        Queue<Vehicle> destinyQ = new LinkedList<>();
        
        this.vehicleQueues = new HashMap<>();
        vehicleQueues.put(this.origin, originQ);
        vehicleQueues.put(this.destiny, destinyQ);
    }

    public int getDistance() {
        return distance;
    }

    public Map<Node, Queue<Vehicle>> getVehicleQueues() {
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

//    public void printVehicleQueue() {
//        System.out.println("Queue at Edge from Node (" + origin.getX() + ", " + origin.getY() + ") to Node (" + destiny.getX() + ", " + destiny.getY() + "):");
//        for (Vehicle vehicle : vehicleQueues.get(destiny)) {
//            System.out.println("    Vehicle: ");
//        }
//    }
}
