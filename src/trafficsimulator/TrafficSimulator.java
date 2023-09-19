/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trafficsimulator;

import trafficsimulator.backend.Edge;
import trafficsimulator.backend.Graph;
import trafficsimulator.backend.Node;
import trafficsimulator.backend.Vehicle;
import trafficsimulator.controllers.SimulatorController;
import trafficsimulator.frontend.*;

/**
 *
 * @author josemanuque
 */
public class TrafficSimulator implements WelcomeScreenListener {

    private SimulatorController controller;

    public void onNewSimulationWindow(SimulatorWindow simulatorUI) {
        controller = new SimulatorController(simulatorUI);
        System.out.println("Se crea nuevo controller");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello World");
        WelcomeScreen screen = new WelcomeScreen();
        TrafficSimulator trafficSimulator = new TrafficSimulator();
        screen.setWelcomeScreenListener(trafficSimulator);
        screen.setVisible(true);

//        Graph graph = new Graph();
//
//        Node node1 = new Node(1, 0, 0);
//        Node node2 = new Node(1, 1, 1);
//        Node node3 = new Node(1, 2, 2);
//
//        // Agrega aristas al grafo
//        Edge edge1 = new Edge(10, node1, node2);
//        Edge edge2 = new Edge(20, node2, node3);
//
//        // Agrega nodos al grafo
//        graph.addNode(node1);
//        graph.addNode(node2);
//        graph.addNode(node3);
//
//        // Agrega aristas al grafo
//        node1.addEdge(edge1);
//        node2.addEdge(edge1);
//        node2.addEdge(edge2);
//        node3.addEdge(edge2);
//
//        graph.startSimulation();
    }
}
