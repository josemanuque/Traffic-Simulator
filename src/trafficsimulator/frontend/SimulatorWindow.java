/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package trafficsimulator.frontend;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author josemanuque
 */
public class SimulatorWindow extends javax.swing.JFrame {
    private int x1 = -1;
    private int y1 = -1;
    private int x2 = -1;
    private int y2 = -1;
    private NodeComponent nodeUI1;
    private NodeComponent nodeUI2;
    private int mode = 0;
    /**
     * Creates new form GUI
     */
    public SimulatorWindow() {
        setTitle("Traffic Simulation");
        
        initComponents();
        
        buttonRadioEdges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Changes mode to 1 when Edges button is pressed.
                mode = 1;
                x1 = -1; // Cleans coordinate variables for later use (Maybe not needed)
                y1 = -1;
                x2 = -1;
                y2 = -1;
            }
        });
        buttonRadioNodes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Changes mode to 0 when Nodes button is pressed
                mode = 0;
            }
        });
        
        
        /* 
            Método que escucha clicks de mouse y obtiene su posición x, y
        
        Cosas por arreglar: 
            Se debería manejar en una clase controller aparte. Para pruebas se maneja acá
            Una vez esté en controller debería también crear los objetos de la parte lógica.
            Creo que ya no es necesario manejar x2, y2 porque ya hay algo que detecta si un nodo fue seleccionado.
            
        Problemas:
            Da un exception cuando se selecciona node, luego se vuelve a edge y se selecciona un nodo
        ¨   
        */
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mode == 0){
                    x1 = e.getX();
                    y1 = e.getY();
                    panel.addNode(x1, y1);
                    repaint();
                    //float alpha = Float.parseFloat(JOptionPane.showInputDialog("Enter an alpha value", "0.5"));
                    if(panel.getNodesUISize() >= 2){
                        buttonRadioEdges.setEnabled(true);
                    }
                } else {
                    if (x1 == -1 && y1 == -1) {
                        // Si es la primera vez que se hace clic en modo 1, establece las coordenadas iniciales
                        x1 = e.getX();
                        y1 = e.getY();
                        nodeUI1 = panel.isNodeSelected(x1, y1);
                        if(nodeUI1 != null){
                            repaint();
                        } else {
                            x1 = -1;
                            y1 = -1;
                        }
                    } else {
                        x2 = e.getX();
                        y2 = e.getY();
                        nodeUI2 = panel.isNodeSelected(x2, y2);
                        if(nodeUI2 != null){
                            
                            double angle = Math.atan2(nodeUI2.getY() - nodeUI1.getY(), nodeUI2.getX() - nodeUI1.getX());

                            int x1 = (int) (nodeUI1.getX() + nodeUI1.getRadius() * Math.cos(angle));
                            int y1 = (int) (nodeUI1.getY() + nodeUI1.getRadius() * Math.sin(angle));

                            int x2 = (int) (nodeUI2.getX() - nodeUI2.getRadius() * Math.cos(angle));
                            int y2 = (int) (nodeUI2.getY() - nodeUI2.getRadius() * Math.sin(angle));
                            panel.addEdge(x1, y1, x2, y2);
                            panel.setSelectedNodeUI(null);
                            repaint();

                            nodeUI1 = null;
                            nodeUI2 = null;
                        }
                        //float distance = Float.parseFloat(JOptionPane.showInputDialog("Enter a distance value", "4"));
                        //repaint();
                        x1 = -1;
                        y1 = -1;
                        x2 = -1;
                        y2 = -1;
                     }
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                              }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    private void initComponents() {

        buttonRadioGroup = new javax.swing.ButtonGroup();
        panel = new GraphPanel();
        
        panel.setBackground(new java.awt.Color(204, 204, 204));
        panel.setPreferredSize(new java.awt.Dimension(600, 400));

        buttonRadioNodes = new javax.swing.JRadioButton();
        buttonRadioEdges = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        
        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 641, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );

        buttonRadioGroup.add(buttonRadioNodes);
        buttonRadioNodes.setSelected(true);
        buttonRadioNodes.setText("Nodes");

        buttonRadioGroup.add(buttonRadioEdges);
        buttonRadioEdges.setText("Edges");
        buttonRadioEdges.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(110, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonRadioNodes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonRadioEdges)
                        .addGap(113, 113, 113))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonRadioNodes)
                    .addComponent(buttonRadioEdges))
                .addGap(43, 43, 43))
        );

        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimulatorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimulatorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimulatorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimulatorWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimulatorWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton buttonRadioEdges;
    private javax.swing.ButtonGroup buttonRadioGroup;
    private javax.swing.JRadioButton buttonRadioNodes;
    private GraphPanel  panel;
    // End of variables declaration//GEN-END:variables
}
