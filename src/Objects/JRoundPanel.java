package Objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class JRoundPanel extends JPanel{
    
    private int curve = 0;
   
    // constructor
    public JRoundPanel() {
        setOpaque(false);
    }
    
    public void setCurve(int curve) {
        this.curve = curve;
        repaint();
    }

    
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        
        
        g2.fill(createShape());
        g2.dispose();
        
        super.paintComponent(g);
    }
    
    public Shape createShape(){
        int width = getWidth();
        int height = getHeight();

        // Adjust roundness as needed 
        int arcWidth = Math.min(width, curve);
        int arcHeight = Math.min(height, curve);

        return new RoundRectangle2D.Double(0, 0, width, height, arcWidth, arcHeight);
        
        
    }
    
}
