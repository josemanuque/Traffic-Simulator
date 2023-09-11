package trafficsimulator.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {

    ArrayList<Node> nodes;

    public Graph() {
        this.nodes = new ArrayList<Node>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
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
}
