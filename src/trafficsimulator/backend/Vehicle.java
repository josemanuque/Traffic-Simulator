package trafficsimulator.backend;

public class Vehicle implements Runnable{
    private Node start;
    private Node finish;
    private Graph graph;

    public Vehicle(Graph graph, Node start, Node finish) {
        this.graph = graph;
        this.start = start;
        this.finish = finish;
    }


    @Override
    public void run() {
        // Implementa la lógica de movimiento del automóvil aquí
        // Puedes utilizar un bucle while para que el automóvil se mueva continuamente
        // dentro del grafo siguiendo las aristas disponibles
        while (!Thread.currentThread().isInterrupted()) {
            // Mueve el automóvil al siguiente nodo a lo largo de una arista
            // Puedes implementar esta lógica utilizando la información de las aristas
            // y el nodo actual del automóvil
            // Actualiza el nodo actual del automóvil

            // Agrega alguna lógica de espera (Thread.sleep) para simular el tiempo que
            // lleva el viaje entre nodos
        }
    }
}
