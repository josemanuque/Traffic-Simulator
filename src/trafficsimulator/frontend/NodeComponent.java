/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trafficsimulator.frontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author josemanuque
 */
public class NodeComponent {
    private int x = 0;
    private int y = 0;
    private int radius = 0;
    private Color c = Color.BLACK;
    
    public NodeComponent(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c);
        g2d.setStroke(new BasicStroke(3));
        int diameter = radius * 2;
        g2d.drawOval(x - radius, y - radius, diameter, diameter);
    }
    
    boolean contains(int px, int py) {
            int dx = px - x;
            int dy = py - y;
            return dx * dx + dy * dy <= radius * radius;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }
    
    
}
