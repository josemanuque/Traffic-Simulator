package trafficsimulator.backend;

import trafficsimulator.controllers.SimulatorController;

import static java.lang.Thread.sleep;
import java.util.*;

public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Vehicle> vehicles;
    private boolean flag = false;
    private SimulatorController simulatorController;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void addSimulatorController(SimulatorController simulatorController){
        this.simulatorController = simulatorController;
    }

    public int getIndexOfNode(Node node){
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).equals(node)) {
                return i; // Returns index of found node
            }
        }
        return -1;
    }

    public Vehicle createCar(Node startNode) {
        Object lock = new Object();
        int randomIndex = 0;

        if (!nodes.get(randomIndex).isFilled()){
            //Node startNode = nodes.get(randomIndex);

            Random rand2 = new Random();
            int randomIndex2 = rand2.nextInt(nodes.size());

            while (randomIndex2 == randomIndex){
                randomIndex2 = rand2.nextInt(nodes.size());
            }
            Node finishNode = nodes.get(randomIndex2);

            Vehicle vehicle = new Vehicle(this.simulatorController, startNode, finishNode, lock);
            vehicles.add(vehicle);
            return vehicle;
        }
        return null;
    }

    public void startSimulation(){
        int randomIndex = 0;
        Node startNode = nodes.get(randomIndex);
        
        this.flag = true;
        while(flag){
            double alfa = (startNode.getAlfa()) * 1000;
            try{
                sleep((int) alfa);
            }
            catch (InterruptedException e) {
                System.out.println("Exception: " + e);
            }
            
            Vehicle vehicle = this.createCar(startNode);
            if(vehicle != null){
                Thread thread = new Thread(vehicle);
                thread.start();
            }
        }

    }
    
    public void stopSimulation(){
        this.flag = false;
    }


    public ArrayList<Node> dijkstra(Node originNode, Node destinyNode) {
        PriorityQueue<Node> noVisitedNodes = new PriorityQueue<>();
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();

        for (Node node : nodes) {
            if (node == originNode) {
                distance.put(node, 0);
                noVisitedNodes.offer(node);
            } else {
                distance.put(node, Integer.MAX_VALUE);
            }
        }

        while (!noVisitedNodes.isEmpty()) {
            Node currentNode = noVisitedNodes.poll();
            if (currentNode == destinyNode) {
                ArrayList<Node> path = new ArrayList<>();
                while (previous.containsKey(currentNode)) {
                    path.add(currentNode);
                    currentNode = previous.get(currentNode);
                }
                path.add(originNode);
                Collections.reverse(path);
                return path;
            }

            for (Edge edge : currentNode.getEdges()) {
                Node neighbor = edge.getDestiny();
                int alt = distance.get(currentNode) + edge.getDistance();
                if (alt < distance.get(neighbor)) {
                    distance.put(neighbor, alt);
                    previous.put(neighbor, currentNode);
                    neighbor.setAccumulatedDistance(alt);
                    noVisitedNodes.offer(neighbor);
                }
            }
        }
        
        return null;
    }
    
    public Node getNode(int x, int y){
        for (Node node : nodes){
            if (node.getX() == x && node.getY() == y)
                return node;
        }
        return null;
    }
}
