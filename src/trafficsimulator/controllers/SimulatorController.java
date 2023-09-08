package trafficsimulator.controllers;

import trafficsimulator.backend.Graph;
import trafficsimulator.backend.Node;
import trafficsimulator.frontend.GraphPanel;
import trafficsimulator.frontend.NodeComponent;
import trafficsimulator.frontend.SimulatorWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class SimulatorController {
    private SimulatorWindow simulatorUI;
    private GraphPanel panel;
    private JRadioButton buttonRadioNodes;
    private JRadioButton buttonRadioEdges;
    private NodeComponent nodeUI1;
    private NodeComponent nodeUI2;
    private int mode = 0;
    private int x, y = 0;

    private Graph graph;

    public SimulatorController(SimulatorWindow simulatorUI) {
        this.simulatorUI = simulatorUI;
        this.panel = simulatorUI.getPanel();
        this.buttonRadioNodes = simulatorUI.getButtonRadioNodes();
        this.buttonRadioEdges = simulatorUI.getButtonRadioEdges();
        this.graph = new Graph();
        mouseListener();
        radioButtonListener();
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
                            if(nodeUI2 != null){
                                double angle = Math.atan2(nodeUI2.getY() - nodeUI1.getY(), nodeUI2.getX() - nodeUI1.getX());

                                int x1 = (int) (nodeUI1.getX() + nodeUI1.getRadius() * Math.cos(angle));
                                int y1 = (int) (nodeUI1.getY() + nodeUI1.getRadius() * Math.sin(angle));

                                int x2 = (int) (nodeUI2.getX() - nodeUI2.getRadius() * Math.cos(angle));
                                int y2 = (int) (nodeUI2.getY() - nodeUI2.getRadius() * Math.sin(angle));
                                panel.addEdge(x1, y1, x2, y2);
                                panel.setSelectedNodeUI(null);
                                simulatorUI.repaint();
                                float distance = Float.parseFloat(JOptionPane.showInputDialog("Enter a distance value", "4"));
                                Node node1 = graph.getNode(nodeUI1.getX(), nodeUI1.getY());
                                Node node2 = graph.getNode(nodeUI2.getX(), nodeUI2.getY());
                                if (node1 != null){
                                    node1.addEdge(distance, node2);
                                }
                                nodeUI1 = null;
                                nodeUI2 = null;
                            }
                            //repaint();

                        }
                    }
                }
            }
        });
    }


}
