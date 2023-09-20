package trafficsimulator.backend;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Edge {

    private int distance;
    private Node origin;
    private Node destiny;
    private Point point1;
    private Point point2;

    private Map<Node, Queue<Vehicle>> vehicleQueues;

    public Edge(int distance, Node origin, Node destiny, Point point1, Point point2) {
        this.distance = distance;
        this.origin = origin;
        this.destiny = destiny;
        this.point1 = point1;
        this.point2 = point2;
        this.vehicleQueues = new HashMap<>();
        Queue<Vehicle> originQ = new LinkedList<>();
        Queue<Vehicle> destinyQ = new LinkedList<>();
        
        this.vehicleQueues = new HashMap<>();
        vehicleQueues.put(this.origin, originQ);
        vehicleQueues.put(this.destiny, destinyQ);
    }

    public Point[] getPointsByProximity(Point nodePoint){
        if(nodePoint.distance(point1) < nodePoint.distance(point2)){
            return new Point[] {point1, point2};
        }
        return new Point[] {point2, point1};
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

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    //    public void printVehicleQueue() {
//        System.out.println("Queue at Edge from Node (" + origin.getX() + ", " + origin.getY() + ") to Node (" + destiny.getX() + ", " + destiny.getY() + "):");
//        for (Vehicle vehicle : vehicleQueues.get(destiny)) {
//            System.out.println("    Vehicle: ");
//        }
//    }
}
