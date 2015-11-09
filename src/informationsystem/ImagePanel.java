/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Алексей
 */
public class ImagePanel extends JPanel {
    private Image image;
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    
    public void setImage(String str){
        try {
            this.image = ImageIO.read(new File(str));
        } catch (IOException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(image != null){            
            int w = image.getWidth(null);
            int h = image.getHeight(null);
            double k = 1.0;
            if(w >= h){
                k = (double)this.getWidth() / w;
            }
            else{
                k = (double)this.getHeight()/h;
            }
            w*=k;
            h*=k;        
            g.drawImage(image,(this.getWidth() - w)/2,(this.getHeight() - h)/2 , w, h, null);
        }
    }
}
