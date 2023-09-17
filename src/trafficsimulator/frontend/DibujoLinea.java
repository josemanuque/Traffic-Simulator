import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DibujoLinea extends JFrame {
    private Timer timer;
    private int x1 = 50;
    private int y1 = 100;
    private int x2 = 300;
    private int y2 = 300;
    private double t = 0.0;
    private int circleX = 0;
    private int circleY = 0;
    private int radius = 10;
    
        
    
    public DibujoLinea() {
        setTitle("Dibujar Línea Paralela");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        double angle = Math.atan2(y2 - y1, x2 - x1);
        
        // Calcular las coordenadas de la línea paralela
        int distancia = radius; // Distancia de 4 unidades
        int x1Paralela = (int) (x1 + distancia * Math.sin(angle));
        int y1Paralela = (int) (y1 - distancia * Math.cos(angle));
        int x2Paralela = (int) (x2 + distancia * Math.sin(angle));
        int y2Paralela = (int) (y2 - distancia * Math.cos(angle));

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calcula la posición del círculo en función de t
                
                circleX = (int) (x1Paralela + (x2Paralela - x1Paralela) * t);
                circleY = (int) (y1Paralela + (y2Paralela - y1Paralela) * t ) ;
                
                
                
                //setCirclePosition(circleX, circleY);

                // Incrementa t para mover el círculo a lo largo de la línea
                t += 0.006;

                // Detén la animación cuando el círculo llegue al punto final
                if (t >= 1.0) {
                    timer.stop();
                }

                repaint();
            }
        });
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        // Coordenadas de inicio y fin de la línea original
        
        g.setColor(Color.BLACK);
        // Dibujar la línea original
        g.drawLine(x1, y1, x2, y2);
        
        // Calcular el ángulo de la línea original
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));
        // Dibujar la línea paralela
        //g.drawLine(x1Paralela, y1Paralela, x2Paralela, y2Paralela);
        int diameter = radius * 2;
        g2d.fillOval(circleX - radius, circleY - radius, diameter, diameter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DibujoLinea ventana = new DibujoLinea();
            ventana.setVisible(true);
        });
    }
}
