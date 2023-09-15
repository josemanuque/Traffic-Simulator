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

            try {
                this.thread.sleep(2000);
            } catch (InterruptedException e) {
                // Manejo de la excepción, si es necesario
            }

            if (route.get(i).isFilled()){

            }

        }
        this.thread.interrupt();


    }
}
