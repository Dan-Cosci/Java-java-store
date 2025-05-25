package Objects;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JImageLabel extends JLabel{
    
    public JImageLabel() {
    }
    
    public void setImage(String img, Dimension size){
        
        this.setPreferredSize(size);
        this.setSize(size);
        
        if(size.getHeight() > size.getWidth()){
            ImageResizeV(img);
        } else{
            ImageResizeH(img);
        }
        
        this.revalidate();
        this.repaint();
    }
    
    public void ImageResizeV(String imgLoc){
        ImageIcon icon = new ImageIcon(imgLoc);
        Image img = icon.getImage();

        // Cast to float or double to avoid integer division
        float ratio = (float) this.getHeight() / img.getHeight(null);

        // Scale the width based on the height ratio
        int newWidth = (int) (img.getWidth(null) * ratio);
        int newHeight = this.getHeight();

        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(scaledImg));
    }
    
    public void ImageResizeH(String imgLoc){
        ImageIcon icon = new ImageIcon(imgLoc);
        Image img = icon.getImage();

        // Cast to float or double to avoid integer division
        float ratio = (float) this.getWidth()/ img.getWidth(null);

        // Scale the width based on the height ratio
        int newHeight = (int) (img.getHeight(null) * ratio);
        int newWidth = this.getWidth();

        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(scaledImg));
    }

    public void ImageResizeV(Image img) {
        // Cast to float to avoid integer division
        float ratio = (float) this.getHeight() / img.getHeight(null);

        int newWidth = (int) (img.getWidth(null) * ratio);
        int newHeight = this.getHeight();

        EventQueue.invokeLater(()->{
            Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(scaledImg));
        });
    }

    public void ImageResizeH(Image img) {
        // Cast to float to avoid integer division
        float ratio = (float) this.getWidth() / img.getWidth(null);

        int newHeight = (int) (img.getHeight(null) * ratio);
        int newWidth = this.getWidth();

        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(scaledImg));
    }

}
