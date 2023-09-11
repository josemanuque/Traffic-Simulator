/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trafficsimulator;

import java.util.ArrayList;
import trafficsimulator.backend.Edge;
import trafficsimulator.backend.Graph;
import trafficsimulator.backend.Node;
import trafficsimulator.frontend.*;

/**
 *
 * @author josemanuque
 */
public class TrafficSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello World");
        WelcomeScreen screen = new WelcomeScreen();
        screen.setVisible(true);

// PRUEBA DIJKSTRA
        Node nodeA = new Node(1);
        Node nodeB = new Node(2);
        Node nodeC = new Node(3);
        Node nodeD = new Node(4);

        // Crear aristas bidireccionales
        nodeA.addEdge(new Edge(2, nodeA, nodeB));
        nodeB.addEdge(new Edge(1, nodeB, nodeC));
        nodeC.addEdge(new Edge(3, nodeC, nodeD));
        nodeD.addEdge(new Edge(2, nodeD, nodeA));

        // Crear grafo
        Graph graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);

        // Ejecutar algoritmo de Dijkstra
        ArrayList<Node> shortestPath = graph.dijkstra(nodeD, nodeA);

        // Imprimir el camino más corto
        if (shortestPath != null) {
            System.out.println("Camino más corto de A a E:");
            for (Node node : shortestPath) {
                System.out.println("Nodo: " + node + " Alfa: " + node.getAlfa());
            }
        } else {
            System.out.println("No se encontró un camino de A a E.");
        }
        
//        for (Node node : graph.getNodes()) {
//            node.printEdges();
//        }
    }
}
