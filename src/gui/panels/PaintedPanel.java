package gui.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PaintedPanel extends JPanel{
    private Image backgroundImage;

    public PaintedPanel(String imagePath, JFrame frame){
        try{
            backgroundImage = ImageIO.read(new File(imagePath));
            backgroundImage=backgroundImage.getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //parte adibita alla "pittura" della foto sullo sfondo
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim= this.getSize();
        int width= dim.width;
        int height= dim.height;
        g.drawImage(backgroundImage, 0, 0,width,height, this);
    }

}
