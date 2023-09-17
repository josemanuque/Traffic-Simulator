package trafficsimulator.backend;

import static java.lang.Thread.sleep;
import java.util.ArrayList;

public class Vehicle implements Runnable {

    private Node start;
    private Node finish;
    private Graph graph;
    private final Object lock;



    public Vehicle(Graph graph, Node start, Node finish, Object lock) {
        this.graph = graph;
        this.start = start;
        this.finish = finish;
        this.lock = lock;
    }


    @Override
    public void run() {
        System.out.println("Hilo " + Thread.currentThread().getId());
        ArrayList<Node> route = this.graph.dijkstra(this.start, this.finish);
        for (int i = 0; i< route.size(); i++){
            Node currentNode = route.get(i);
            Node nextNode = null;
            if (currentNode != this.finish){
                nextNode = route.get(i+1);
            }
            try {

                currentNode.setFilled(true);
                System.out.println("Current node filled");
                sleep(2000);
                currentNode.setFilled(false);
                if (!currentNode.getGeneralQ().isEmpty()){
                    System.out.println(currentNode.getGeneralQ().element());
                    Vehicle firstInQ = currentNode.getGeneralQ().poll();
                    if (firstInQ != null) {
                        synchronized (firstInQ) {
                            firstInQ.notify();
                        }
                        System.out.println("Dejando pasar el siguiente carro notify()...");
                    } else {
                        System.out.println("fistInQ es null, no se llama a notify()");
                    }
                }
                

            } catch (InterruptedException e) {
                // Manejo de la excepción, si es necesario
            }

            if (nextNode != null && nextNode.isFilled()){
                // Encuentra la arista que conecta currentNode y el siguiente nodo en la ruta
                System.out.println("Hilo " + Thread.currentThread().getId() + "con nodo lleno");
                Edge currentEdge = null;
                for (Edge edge : currentNode.getEdges()) {
                    if (edge.getDestiny() == nextNode) {
                        currentEdge = edge;
                        break;
                    }
                }
                
                synchronized (lock) {
                    currentEdge.getVehicleQueues().get(nextNode).add(this);
                    nextNode.addVehicleQ(this);

                    try {
                        System.out.println("En espera");
                        lock.wait();
                        System.out.println("Ya continue...");
                    } catch (InterruptedException e) {
                        // Manejo de la excepción, si es necesario
                    }
                }
            }
        }
        System.out.println("Llegue");
    }
}