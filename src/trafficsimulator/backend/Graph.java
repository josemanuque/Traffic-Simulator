package trafficsimulator.backend;

import java.util.*;

public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Vehicle> vehicles;

    public Graph() {
        this.nodes = new ArrayList<Node>();
        this.vehicles = new ArrayList<Vehicle>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void createCar() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(nodes.size());

        if (!nodes.get(randomIndex).isFilled()){
            Node startNode = nodes.get(randomIndex);

            Random rand2 = new Random();
            int randomIndex2 = rand2.nextInt(nodes.size());

            while (randomIndex2 == randomIndex){
                randomIndex2 = rand2.nextInt(nodes.size());
            }
            Node finishNode = nodes.get(randomIndex2);

            Vehicle vehicle = new Vehicle(this, startNode, finishNode);
            vehicles.add(vehicle);
            Thread vehicleThread = new Thread(vehicle);
            vehicleThread.start();
        }

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
        
    public Node getNode(int x, int y){
        for (Node node : nodes){
            if (node.getX() == x && node.getY() == y)
                return node;
        }
        return null;
    }
}
