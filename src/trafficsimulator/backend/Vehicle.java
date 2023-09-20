package trafficsimulator.backend;

import trafficsimulator.controllers.SimulatorController;
import trafficsimulator.frontend.VehicleComponent;

import static java.lang.Thread.sleep;

import java.awt.*;
import java.util.ArrayList;

public class Vehicle implements Runnable {

    private Node start;
    private Node finish;
    private Graph graph;
    private SimulatorController simulatorController;
    private VehicleComponent vehicleUI;
    private final Object lock;
    private static int activeVehicle = 0;

    public Vehicle(SimulatorController simulatorController, Node start, Node finish, Object lock) {
        this.graph = simulatorController.getGraph();
        this.simulatorController = simulatorController;
        this.start = start;
        this.finish = finish;
        this.lock = lock;
        synchronized(Vehicle.class){
            activeVehicle++;
        }
    }

    public long getThreadID(){
        return Thread.currentThread().getId();
    }

    // Encuentra la arista que conecta currentNode y el siguiente nodo en la ruta
    public Edge findEdge(Node currentNode, Node nextNode) {
        for (Edge edge : currentNode.getEdges()) {
            if (edge.getDestiny() == nextNode) {
                return edge;

            }
        } return null;
    }


    @Override
    public void run() {
        System.out.println("Thread ID: " + this.getThreadID() + " started");
        ArrayList<Node> route = this.graph.dijkstra(this.start, this.finish);

        vehicleUI = simulatorController.getNewVehicleUI();
        simulatorController.drawVehicleInPos(vehicleUI, this.start.getX(), this.start.getY());

        for (int i = 0; i < route.size(); i++) {
            Node currentNode = route.get(i);
            if (currentNode == this.finish) {
                break;
            }
            Node nextNode = route.get(i + 1);
            Edge currentEdge = findEdge(currentNode, nextNode);

            Point[] points = currentEdge.getPointsByProximity(new Point(currentNode.getX(), currentNode.getY()));
            simulatorController.moveVehicle(vehicleUI, points[0], points[1]);

            currentEdge.getVehicleQueues().get(nextNode).add(this);
            nextNode.addVehicleQ(this);

            if (nextNode.isFilled()){
                synchronized (this) {

                    try {
                        System.out.println("Node filled, Thread" + this.getThreadID() + " waiting");
                        wait();
                        System.out.println("Node empty, Thread" + this.getThreadID() + " continues");
                    } catch (InterruptedException e) {
                        // Manejo de la excepción, si es necesario
                    }
                }
            }
            currentEdge.getVehicleQueues().get(nextNode).remove(this);
            nextNode.removeVehicleQ(this);

            try {
                simulatorController.drawVehicleInPos(vehicleUI, nextNode.getX(), nextNode.getY());
                nextNode.setFilled(true);
                System.out.println("Current node filled by thread " + this.getThreadID());
                sleep(2000);
                nextNode.setFilled(false);
                System.out.println("Thread " + this.getThreadID() + " leaves node");
                if (!nextNode.getGeneralQ().isEmpty()) {
                    Vehicle firstInQ = nextNode.getGeneralQ().poll();
                    if (firstInQ != null) {
                        synchronized (firstInQ) {
                            firstInQ.notify();
                        }
                        System.out.println("Notifying next vehicle in queue");
                    } else {
                        System.out.println("fistInQ es null, no se llama a notify()");
                    }
                }

            } catch (InterruptedException e) {
                // Manejo de la excepción, si es necesario
            }
        }
        simulatorController.deleteVehicleUI(vehicleUI);
        System.out.println("Thread " + this.getThreadID() + " arrived and will disappear");
        synchronized(Vehicle.class){
            activeVehicle--;
        }

    }
    
    public static int getActiveVehicle(){
        return activeVehicle;
    }
}
