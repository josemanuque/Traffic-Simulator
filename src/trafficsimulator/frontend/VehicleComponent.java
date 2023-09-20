/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trafficsimulator.frontend;

import java.awt.*;

/**
 *
 * @author josemanuque
 */
public class VehicleComponent {
    private int x = 0;
    private int y = 0;
    private int radius = 0;
    private Color c = Color.BLACK;
    
    public VehicleComponent(int radius) {
        this.radius = radius;
    }

    public Point[] getTangentPos(Point pointOrigin, Point pointDestiny){
        double angle = Math.atan2(pointDestiny.y - pointOrigin.y, pointDestiny.x - pointOrigin.x);
        Point[] points = new Point[2];

        int distance = 8;
        int x1Parallel = (int) (pointOrigin.x - distance * Math.sin(angle));
        int y1Parallel = (int) (pointOrigin.y + distance * Math.cos(angle));
        int x2Parallel = (int) (pointDestiny.x - distance * Math.sin(angle));
        int y2Parallel = (int) (pointDestiny.y + distance * Math.cos(angle));

        points[0] = new Point(x1Parallel, y1Parallel);
        points[1] = new Point(x2Parallel, y2Parallel);

        return points;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void moveVehicle(SimulatorWindow simulatorUI, Point originPoint, Point destinyPoint){
        Point[] points = getTangentPos(originPoint, destinyPoint);
        originPoint = points[0];
        destinyPoint = points[1];

        double distance = originPoint.distance(destinyPoint);
        //System.out.println("Distance: "+  distance);
        double speed = 10.0; // Establece tu velocidad deseada aquí
        double tIncrement = speed / distance; // Calcula el incremento de "t" basado en la velocidad
        //System.out.println("TIncrement: " + tIncrement);
        double t = 0;
        //Logic to move vehicle
        while (t < 1.0) {
            x = (int) (originPoint.x + (destinyPoint.x - originPoint.x) * t);
            y = (int) (originPoint.y + (destinyPoint.y - originPoint.y) * t);

            t += tIncrement; // Incrementa "t" en función de la velocidad
            try {
                Thread.sleep(100);
            } catch (Exception e){
                System.out.println("Exception: " + e);
            }
            simulatorUI.repaint();
        }
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c);
        g2d.setStroke(new BasicStroke(3));
        int diameter = radius * 2;
        g2d.drawOval(x - radius, y - radius, diameter, diameter);
    }
}
