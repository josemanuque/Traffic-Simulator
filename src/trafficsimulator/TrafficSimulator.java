/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trafficsimulator;

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
    }
    
}
