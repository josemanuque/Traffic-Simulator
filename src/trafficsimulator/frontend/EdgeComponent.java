/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trafficsimulator.frontend;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author josemanuque
 */
public class EdgeComponent {
    private int x1, y1, x2, y2;

    public EdgeComponent(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }
}
