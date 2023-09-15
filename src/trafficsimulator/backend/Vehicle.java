package trafficsimulator.backend;

import java.util.ArrayList;

public class Vehicle implements Runnable{
    private Node start;
    private Node finish;
    private Graph graph;

    private Thread thread;

    public Vehicle(Graph graph, Node start, Node finish) {
        this.graph = graph;
        this.start = start;
        this.finish = finish;
        this.thread = new Thread();
        this.thread.start();
    }

    Thread getThread(){
        return this.thread;
    }


    @Override
    public void run() {

        ArrayList<Node> route = this.graph.dijkstra(this.start, this.finish);

        for (int i = 0; i< route.size(); i++){
            Node currentNode = route.get(i);
            Node nextNode = route.get(i+1);

            try {
                currentNode.setFilled(true);
                this.thread.sleep(2000);
                currentNode.setFilled(false);
                if (!currentNode.getGeneralQ().isEmpty()){
                    Vehicle firstInQ = currentNode.getGeneralQ().poll();
                    firstInQ.thread.notify();
                    System.out.println("Dejando pasar el siguiente carro...");
                }
            } catch (InterruptedException e) {
                // Manejo de la excepción, si es necesario
            }

            if (nextNode.isFilled()){
                // Encuentra la arista que conecta currentNode y el siguiente nodo en la ruta

                Edge currentEdge = null;
                for (Edge edge : currentNode.getEdges()) {
                    if (edge.getDestiny() == nextNode) {
                        currentEdge = edge;
                        break;
                    }
                }

                currentEdge.getVehicleQueues().get(nextNode).add(this);
                nextNode.addVehicleQ(this);

                try {
                    System.out.println("En espera");
                    this.thread.wait();
                    System.out.println("Ya continue...");
                } catch (InterruptedException e) {
                    // Manejo de la excepción, si es necesario
                }

            }

        }
        this.thread.interrupt();

    }
}
