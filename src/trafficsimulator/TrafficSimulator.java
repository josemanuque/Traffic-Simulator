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
import trafficsimulator.controllers.SimulatorController;
import trafficsimulator.frontend.*;

/**
 *
 * @author josemanuque
 */
public class TrafficSimulator implements WelcomeScreenListener{
    private SimulatorController controller;
    public void onNewSimulationWindow(SimulatorWindow simulatorUI){
        controller = new SimulatorController(simulatorUI);
        System.out.println("Se crea nuevo controller");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello World");
        WelcomeScreen screen = new WelcomeScreen();
        TrafficSimulator trafficSimulator = new TrafficSimulator();
        screen.setWelcomeScreenListener(trafficSimulator);
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

        //Vehicles testing
        Scanner scanner = new Scanner(System.in);
        int alfa = 5000;
        while (true) {
            System.out.println("Presiona Enter para crear un carro...");
            scanner.nextLine();


            graph.createCar();
            System.out.println("Se ha creado un nuevo carro.");

            try {
                Thread.sleep(alfa);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
//        for (Node node : graph.getNodes()) {
//            node.printEdges();
//        }
    }
}
