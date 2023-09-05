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
}
