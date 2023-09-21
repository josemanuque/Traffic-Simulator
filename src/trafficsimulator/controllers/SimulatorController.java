package trafficsimulator.controllers;

import java.awt.*;

import trafficsimulator.backend.Graph;
import trafficsimulator.backend.Node;
import trafficsimulator.backend.Vehicle;
import trafficsimulator.frontend.GraphPanel;
import trafficsimulator.frontend.NodeComponent;
import trafficsimulator.frontend.SimulatorWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Random;

import trafficsimulator.backend.Edge;
import trafficsimulator.frontend.VehicleComponent;

import static java.lang.Thread.sleep;
import java.util.ArrayList;

public class SimulatorController {
    private SimulatorWindow simulatorUI;
    private GraphPanel panel;
    private JRadioButton buttonRadioNodes;
    private JRadioButton buttonRadioEdges;
    private JButton buttonStart;
    private JButton buttonStop;
    private JLabel labelVehicleQuantity;
    private JLabel labelTime;
    private JLabel labelSpeed;
    private NodeComponent nodeUI1;
    private NodeComponent nodeUI2;
    private int mode = 0;
    private int x, y = 0;
    private Thread time;
    private long startTime;
    private Thread updateSpeed;
    private double averageSpeed;

    private Graph graph;

    private boolean isRunning = false;

    private List<Node> nodes;
    private List<Vehicle> vehicles;
    
    private List<Thread> nodesThreads;
    private List<Thread> vehiclesThreads;

    public SimulatorController(SimulatorWindow simulatorUI) {
        this.simulatorUI = simulatorUI;
        this.panel = simulatorUI.getPanel();
        this.buttonRadioNodes = simulatorUI.getButtonRadioNodes();
        this.buttonRadioEdges = simulatorUI.getButtonRadioEdges();
        this.buttonStart = simulatorUI.getButtonStart();
        this.buttonStop = simulatorUI.getButtonStop();
        this.labelVehicleQuantity = simulatorUI.getLabelVehicleQuantity();
        this.labelTime = simulatorUI.getLabelTime();
        this.labelSpeed = simulatorUI.getLabelSpeed();
        this.graph = new Graph();
        this.nodesThreads = new ArrayList<>();
        this.vehiclesThreads = new ArrayList<>();
        mouseListener();
        radioButtonListener();
        buttonStartListener();
        buttonStopListener();
        this.buttonStart.setEnabled(false);
        this.buttonStop.setEnabled(false);
    }

    private void radioButtonListener(){
        buttonRadioEdges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Changes mode to 1 when Edges button is pressed.
                mode = 1;

            }
        });
        buttonRadioNodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Changes mode to 0 when Nodes button is pressed
                mode = 0;
            }
        });
    }
    
    private void buttonStartListener(){
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //singleNodeVehicleSimulation();
                startSimulation();
                System.out.println("Se continua en controller");
            }
        });
    }
    
    private void buttonStopListener(){
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
                System.out.println("Se detuvo");
            }
        });
    }
    
    public void updateActiveVehicle(){
        int activeVehicles = Vehicle.getActiveVehicle();
        labelVehicleQuantity.setText("Cantidad de vehículos: " + activeVehicles);
    }
    
    public void averageSpeed(){
        int totalSpeed = 0;
        
        vehicles = graph.getVehicles();
        int vehiclesSize = vehicles.size();

        for(int i = 0; i < vehiclesSize; i++){
            totalSpeed += vehicles.get(i).getSpeed();
        }

        if (totalSpeed > 0) {
            averageSpeed = (totalSpeed / vehiclesSize); //km/s
        } else {
            averageSpeed = 0; 
        }
    }
    
    public void updateSpeed(){
        updateSpeed = new Thread(() ->{
            while (isRunning){
                try{
                    Thread.sleep(5000);
                    averageSpeed();
                    labelSpeed.setText("Velocidad promedio: " + averageSpeed + "km/s");
                } catch (InterruptedException ex) {
                    // Maneja la interrupción del hilo aquí si es necesario
                }
            }
        });
        updateSpeed.start();
    }
    
    public Graph getGraph() {
        return graph;
    }
    
   private void simulationTime() {
        time = new Thread(() -> {
            startTime = System.currentTimeMillis();
            while (isRunning) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long hours = (elapsedTime / 3600000) % 24;
                long minutes = (elapsedTime / 60000) % 60;
                long seconds = (elapsedTime / 1000) % 60;

                String timeFormat = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                labelTime.setText("Tiempo de simulación: " + timeFormat);

                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    // Maneja la interrupción del hilo aquí si es necesario
                }
            }
        });
        time.start();
   }

    public Vehicle createVehicle(Node startNode) {
        Object lock = new Object();

        if (!startNode.isFilled()){
            //Node startNode = nodes.get(randomIndex);
            int startIndex = graph.getIndexOfNode(startNode);

            int randomIndex = new Random().nextInt(nodes.size());

            while (startIndex == randomIndex){
                randomIndex = new Random().nextInt(nodes.size());
            }
            Node finishNode = nodes.get(randomIndex);

            Vehicle vehicle = new Vehicle(this, startNode, finishNode, lock);
            graph.addVehicle(vehicle);
            return vehicle;
        }
        return null;
    }

    public void vehicleGenerator(Node startNode){
        while(isRunning){
            // 1 / alfa to get seconds per vehicle
            double beta = (1 /(startNode.getAlfa())) * 1000;
            try{
                // Waits beta seconds to continue generating next vehicle
                sleep((int) beta);
            }
            catch (InterruptedException e) {
                System.out.println("Exception: " + e);
            }

            Vehicle vehicle = this.createVehicle(startNode);
            if(vehicle != null){
                Thread thread = new Thread(vehicle);
                thread.start();
                
                vehiclesThreads.add(thread);
            }
        }
    }
    
    public void startSimulation(){
        //graph.addSimulatorController(this); // Una vez se migre todo a controller se debe remover esto
        nodes = graph.getNodes();
        this.isRunning = true;
        this.buttonStart.setEnabled(false);
        this.buttonStop.setEnabled(true);
        // For each node it will create a thread to start generating vehicles
        for(Node node : nodes){
            Thread t = new Thread(() -> {
                vehicleGenerator(node);
            });
            t.start();
            nodesThreads.add(t);
        }
        simulationTime();
        updateSpeed();
    }
    
    public void stopSimulation(){
        //detener thread de cada nodo, detener carros. Puedo hacer lista con threads de carros y hacerles interrumpt
        this.isRunning = false;
        
        for(Thread vehicleThread : vehiclesThreads){
            vehicleThread.interrupt();
        }

        int nodesSize = nodes.size();
        for(int i = 0; i < nodesSize; i++){
            nodes.get(i).setFilled(false);
            nodesThreads.get(i).interrupt();
        }
        
        if (time != null) {
            time.interrupt();
        }
        
        if (updateSpeed != null) {
            updateSpeed.interrupt();
        }
        
        nodesThreads.clear();
        vehiclesThreads.clear();
        panel.clearAllVehicles();
        this.buttonStart.setEnabled(true);
        this.buttonStop.setEnabled(false);
    }

    public void singleNodeVehicleSimulation(){
        graph.addSimulatorController(this); // Una vez se migre todo a controller se debe remover esto
        nodes = graph.getNodes();
        Node node = nodes.get(0);
        Thread t = new Thread(() -> {
            vehicleGenerator(node);
        });
        t.start();
    }

    public void singleVehicleSimulation(){
        graph.addSimulatorController(this); // Una vez se migre todo a controller se debe remover esto
        nodes = graph.getNodes();
        Node node = nodes.get(0);
        Vehicle vehicle = this.createVehicle(node);
        if(vehicle != null){
            Thread thread = new Thread(vehicle);
            thread.start();
        }
    }

    public VehicleComponent getNewVehicleUI(){
        return panel.getNewVehicleUI();
    }

    public void deleteVehicleUI(VehicleComponent vehicleUI){
        panel.removeVehicleUI(vehicleUI);
        simulatorUI.repaint();
    }
    
    public void drawVehicleInPos(VehicleComponent vehicleUI, int x, int y){
        vehicleUI.setPos(x, y);
        simulatorUI.repaint();
    }
    public void moveVehicle(VehicleComponent vehicleUI, Point originPoint, Point destinyPoint){
        vehicleUI.moveVehicle(simulatorUI, originPoint, destinyPoint);
    }
    private void mouseListener(){
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isRunning){
                    return;
                }
                x = e.getX();
                y = e.getY();

                switch (mode) {
                    case 0 ->{
                        NodeComponent existingNode = panel.isNodeSelected(x, y);
                        System.out.println(existingNode);
                        if (existingNode == null) {
                            panel.addNode(x, y);
                            simulatorUI.repaint();
                            float alpha = Float.parseFloat(JOptionPane.showInputDialog("Enter an alpha value", "0.5"));
                            if (panel.getNodesUISize() >= 2) {
                                buttonRadioEdges.setEnabled(true);
                            }
                            graph.addNode(new Node(alpha, x, y));
                        }
                    }
                    case 1 -> {
                        if (panel.getSelectedNodeUI() == null) {
                            // Si es la primera vez que se hace clic en modo 1, establece las coordenadas iniciales
                            nodeUI1 = panel.isNodeSelected(x, y);
                            if(nodeUI1 != null){
                                nodeUI1.setColor(Color.BLUE);
                                simulatorUI.repaint();
                            }
                        } else {
                            nodeUI2 = panel.isNodeSelected(x, y);
                            if(nodeUI2 != null && nodeUI2 != nodeUI1){
                                nodeUI2.setColor(Color.BLUE);
                                double angle = Math.atan2(nodeUI2.getY() - nodeUI1.getY(), nodeUI2.getX() - nodeUI1.getX());

                                int x1 = (int) (nodeUI1.getX() + nodeUI1.getRadius() * Math.cos(angle));
                                int y1 = (int) (nodeUI1.getY() + nodeUI1.getRadius() * Math.sin(angle));

                                int x2 = (int) (nodeUI2.getX() - nodeUI2.getRadius() * Math.cos(angle));
                                int y2 = (int) (nodeUI2.getY() - nodeUI2.getRadius() * Math.sin(angle));
                                panel.addEdge(x1, y1, x2, y2);
                                panel.setSelectedNodeUI(null);
                                simulatorUI.repaint();
                                int distance = Integer.parseInt(JOptionPane.showInputDialog("Enter a distance value", "40"));
                                Node node1 = graph.getNode(nodeUI1.getX(), nodeUI1.getY());
                                Node node2 = graph.getNode(nodeUI2.getX(), nodeUI2.getY());
                                if (node1 != null){
                                    node1.addEdge(new Edge(distance, node1, node2, new Point(x1, y1), new Point(x2, y2)));
                                }
                                nodeUI1 = null;
                                nodeUI2 = null;
                                buttonStart.setEnabled(true);
                            }
                            else{
                                if (nodeUI1 != null){
                                    nodeUI1.setColor(Color.BLACK);
                                }
                                nodeUI1 = null;
                                nodeUI2 = null;
                                panel.setSelectedNodeUI(null);
                                simulatorUI.repaint();
                            }
                            //repaint();

                        }
                    }
                }
            }
        });
    } 
}
