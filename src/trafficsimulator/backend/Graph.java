package trafficsimulator.backend;

import java.util.ArrayList;

public class Graph {
    ArrayList<Node> nodes;

    public Graph(){
        this.nodes = new ArrayList<Node>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public Node getNode(int x, int y){
        for (Node node : nodes){
            if (node.getX() == x && node.getY() == y)
                return node;
        }
        return null;
    }
}
