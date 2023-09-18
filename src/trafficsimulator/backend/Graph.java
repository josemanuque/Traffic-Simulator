package trafficsimulator.backend;

import static java.lang.Thread.sleep;
import java.util.*;

public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Vehicle> vehicles;

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

    public void createCar() {
        Object lock = new Object();
        int randomIndex = 0;

        if (!nodes.get(randomIndex).isFilled()){
            Node startNode = nodes.get(randomIndex);

            Random rand2 = new Random();
            int randomIndex2 = rand2.nextInt(nodes.size());

            while (randomIndex2 == randomIndex){
                randomIndex2 = rand2.nextInt(nodes.size());
            }
            Node finishNode = nodes.get(randomIndex2);

            Vehicle vehicle = new Vehicle(this, startNode, finishNode, lock);
            vehicles.add(vehicle);
        }

    }

    public void startSimulation(){
        int randomIndex;
        Object lock = new Object();
        for (int i = 0; i < 10; i++){
            Node startNode = nodes.get(0);
            
            randomIndex = new Random().nextInt(2);

            System.out.println(randomIndex);
            Node finishNode = nodes.get(2);
            
            Vehicle vehicle = new Vehicle(this, startNode, finishNode, lock);
            vehicles.add(vehicle);
        }
        System.out.println("Se inician los threads");
        Thread c = new Thread(() -> {
            for (Vehicle vehicle : vehicles){
                Thread t = new Thread(vehicle);
                t.start();
                try {
                    // Agregar una pausa de 1 segundo (1000 milisegundos)
                    sleep(1000);
                } catch (InterruptedException e) {
                    // Manejar la excepción si es necesario
                    e.printStackTrace();
                }
            }
        });
        c.start();
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
