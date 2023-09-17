/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trafficsimulator;

import java.util.ArrayList;
import java.util.Scanner;

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

        Graph graph = new Graph();

        Node nodeA = new Node(1, 60, 20);
        Node nodeB = new Node(2, 80, 90);
        Node nodeC = new Node(3, 49, 120);
        Node nodeD = new Node(4, 39, 10);

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);

        Edge edge1 = new Edge(2, nodeA, nodeB);
        Edge edge2 = new Edge(1, nodeB, nodeC);
        Edge edge3 = new Edge(3, nodeC, nodeD);
        Edge edge4 = new Edge(2, nodeD, nodeA);

        nodeA.addEdge(edge1);
        nodeB.addEdge(edge2);
        nodeC.addEdge(edge3);
        nodeD.addEdge(edge4);

        int numVehicles = 5; // Número de vehículos que deseas crear

        // Crear y ejecutar múltiples vehículos
        for (int i = 0; i < numVehicles; i++) {
            Node startNode = nodeA; // Nodo de inicio
            Node finishNode = nodeC; // Nodo de destino (puedes cambiarlo si lo deseas)
            Vehicle vehicle = new Vehicle(graph, startNode, finishNode);
            //vehicle.getThread().setName("Vehicle " + (i + 1)); // Asigna un nombre único a cada vehículo
            vehicle.run();
        }
        for (Node node : graph.getNodes()) {
            for (Edge edge : node.getEdges()) {
                edge.printVehicleQueue();
            }
        }
    }
}
