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

public class SimulatorController {
    private SimulatorWindow simulatorUI;
    private GraphPanel panel;
    private JRadioButton buttonRadioNodes;
    private JRadioButton buttonRadioEdges;
    private JButton buttonStart;
    private NodeComponent nodeUI1;
    private NodeComponent nodeUI2;
    private int mode = 0;
    private int x, y = 0;

    private Graph graph;

    private boolean isRunning = false;

    private List<Node> nodes;

    public SimulatorController(SimulatorWindow simulatorUI) {
        this.simulatorUI = simulatorUI;
        this.panel = simulatorUI.getPanel();
        this.buttonRadioNodes = simulatorUI.getButtonRadioNodes();
        this.buttonRadioEdges = simulatorUI.getButtonRadioEdges();
        this.buttonStart = simulatorUI.getButtonStart();
        this.graph = new Graph();
        mouseListener();
        radioButtonListener();
        buttonStartListener();
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

    public Graph getGraph() {
        return graph;
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
        this.isRunning = true;
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
            }
        }
    }
    public void startSimulation(){
        //graph.addSimulatorController(this); // Una vez se migre todo a controller se debe remover esto
        nodes = graph.getNodes();

        // For each node it will create a thread to start generating vehicles
        for(Node node : nodes){
            Thread t = new Thread(() -> {
                vehicleGenerator(node);
            });
            t.start();
        }
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
                x = e.getX();
                y = e.getY();

                switch (mode) {
                    case 0 ->{
                        panel.addNode(x, y);
                        simulatorUI.repaint();
                        float alpha = Float.parseFloat(JOptionPane.showInputDialog("Enter an alpha value", "0.5"));
                        if(panel.getNodesUISize() >= 2){
                            buttonRadioEdges.setEnabled(true);
                        }
                        graph.addNode(new Node(alpha, x, y));
                    }
                    case 1 -> {
                        if (panel.getSelectedNodeUI() == null) {
                            // Si es la primera vez que se hace clic en modo 1, establece las coordenadas iniciales
                            nodeUI1 = panel.isNodeSelected(x, y);
                            if(nodeUI1 != null){
                                simulatorUI.repaint();
                            }
                        } else {
                            nodeUI2 = panel.isNodeSelected(x, y);
                            if(nodeUI2 != null && nodeUI2 != nodeUI1){
                                double angle = Math.atan2(nodeUI2.getY() - nodeUI1.getY(), nodeUI2.getX() - nodeUI1.getX());

                                int x1 = (int) (nodeUI1.getX() + nodeUI1.getRadius() * Math.cos(angle));
                                int y1 = (int) (nodeUI1.getY() + nodeUI1.getRadius() * Math.sin(angle));

                                int x2 = (int) (nodeUI2.getX() - nodeUI2.getRadius() * Math.cos(angle));
                                int y2 = (int) (nodeUI2.getY() - nodeUI2.getRadius() * Math.sin(angle));
                                panel.addEdge(x1, y1, x2, y2);
                                panel.setSelectedNodeUI(null);
                                simulatorUI.repaint();
                                int distance = Integer.parseInt(JOptionPane.showInputDialog("Enter a distance value", "4"));
                                Node node1 = graph.getNode(nodeUI1.getX(), nodeUI1.getY());
                                Node node2 = graph.getNode(nodeUI2.getX(), nodeUI2.getY());
                                if (node1 != null){
                                    node1.addEdge(new Edge(distance, node1, node2, new Point(x1, y1), new Point(x2, y2)));
                                }
                                nodeUI1 = null;
                                nodeUI2 = null;
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
