/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trafficsimulator.frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;
import trafficsimulator.backend.Node;
/**
 *
 * @author josemanuque
 */
public class GraphPanel extends JPanel {
    private List<NodeComponent> nodesUI;
    private List<EdgeComponent> edgesUI;
    private List<VehicleComponent> vehiclesUI;

    private NodeComponent selectedNodeUI;
    public GraphPanel(){
        nodesUI = new ArrayList<NodeComponent>();
        edgesUI = new ArrayList<EdgeComponent>();
        vehiclesUI = new ArrayList<VehicleComponent>();
    }
    
    public void addNode(int x, int y){
        nodesUI.add(new NodeComponent(x, y, 25));
    }
    
    public void addEdge(int x1, int y1, int x2, int y2){
        edgesUI.add(new EdgeComponent(x1, y1, x2, y2));
    }

    public VehicleComponent getNewVehicleUI(){
        VehicleComponent vehicleUI = new VehicleComponent(5);
        vehiclesUI.add(vehicleUI);
        return vehicleUI;
    }

    public void removeVehicleUI(VehicleComponent vehicleUI){
        vehiclesUI.remove(vehicleUI);
    }
    public List<NodeComponent> getNodesUI() {
        return nodesUI;
    }

    public List<EdgeComponent> getEdgesUI() {
        return edgesUI;
    }
    
    public int getNodesUISize(){
        return nodesUI.size();
    }

    public NodeComponent getSelectedNodeUI() {
        return selectedNodeUI;
    }

    public void setSelectedNodeUI(NodeComponent selectedNodeUI) {
        this.selectedNodeUI = selectedNodeUI;
    }

    public void clearAllVehicles(){
        vehiclesUI.clear();
    }
    public NodeComponent isNodeSelected(int x, int y){
        for(NodeComponent nodeUI : nodesUI){
            if(nodeUI.contains(x, y)){
                selectedNodeUI = nodeUI;
                return this.selectedNodeUI;
            }
        }
        selectedNodeUI = null;
        return null;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for(NodeComponent nodeUI : nodesUI) {
            if (selectedNodeUI != nodeUI)
                nodeUI.setColor(Color.BLACK);
            nodeUI.paint(g);
        }
        
        for(EdgeComponent edgeUI : edgesUI){
            edgeUI.paint(g);
        }
        
        for(int i = 0; i < vehiclesUI.size(); i++){
            vehiclesUI.get(i).paint(g);
        }
    }
}
