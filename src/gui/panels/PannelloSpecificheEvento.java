package gui.panels;

import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class PannelloSpecificheEvento extends JPanel {
    public PannelloSpecificheEvento(ArrayList<Locale> locali){
        // etichette specifiche
        JLabel compila= new JLabel("compila lista invitati");
        JLabel carica= new JLabel("carica lista invitati");

        //immagini
        final int WIDHT=20,HEIGHT=20;

        ImageIcon imgI= new ImageIcon("www.jpg");
        Image img= imgI.getImage();
        Image newImage= img.getScaledInstance(WIDHT,HEIGHT,1);
        ImageIcon newI=new ImageIcon(newImage);
        ImageIcon imgI2= new ImageIcon("WwW1.jpg");
        Image img2= imgI2.getImage();
        Image newImage2= img2.getScaledInstance(WIDHT,HEIGHT,1);
        ImageIcon newI2=new ImageIcon(newImage2);
        // bottoni
        JButton bCompila= new JButton(newI);
        bCompila.setPreferredSize(new Dimension(WIDHT,HEIGHT));
        JButton b2Compila= new JButton(newI2);
        bCompila.setPreferredSize(new Dimension(WIDHT,HEIGHT));

        // campi testo
        JLabel tdownloadExcel= new JLabel("download");
        JLabel tuploadExcel= new JLabel("upload");

        setLayout(new GridLayout(2,2));
        add(tdownloadExcel);
        add(bCompila);
        add(tuploadExcel);
        add(b2Compila);
    }
}
