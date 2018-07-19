
package gui.panels;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import database.*;
import gui.controller.*;
import gui.finestre.*;
import locale.*;
import locale.GestoreLocale;
import persone.*;
import vincoli.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author lecciovich
 */

public class PannelloSpecificheEvento extends JPanel {

    private Image backgroundImage;

    public PannelloSpecificheEvento(GestoreLocale gestoreLocale, GestoreEvento gestoreEvento, FinestraSpecificheEvento frame){
        try{
            backgroundImage = ImageIO.read(new File("images/notebook-pen-table-97076.jpg"));
            backgroundImage=backgroundImage.getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }


        ConnessioneDB connessione = new ConnessioneDB();

        // etichette specifiche
        JLabel compila= new JLabel("compila lista invitati");
        JLabel carica= new JLabel("carica lista invitati");

        compila.setOpaque(true);
        compila.setBackground(new Color(255,255,255,127));

        carica.setOpaque(true);
        carica.setBackground(new Color(255,255,255,127));

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
        JButton bDownload= new JButton(newI);
        bDownload.setPreferredSize(new Dimension(WIDHT,HEIGHT));
        JButton bUpload= new JButton(newI2);
        bDownload.setPreferredSize(new Dimension(WIDHT,HEIGHT));
        JButton bUploadOblig=new JButton("invia :)");
        bDownload.setPreferredSize(new Dimension(WIDHT,HEIGHT/2));

        bUpload.setEnabled(false);
        bUploadOblig.setEnabled(false);

        // campi testo
        JLabel tdownloadExcel= new JLabel("download");
        JLabel tuploadExcel= new JLabel("upload");
        JLabel tuploadOblig= new JLabel("uploadOblig");

        tdownloadExcel.setOpaque(true);
        tdownloadExcel.setBackground(new Color(255,255,255,127));

        tuploadExcel.setOpaque(true);
        tuploadExcel.setBackground(new Color(255,255,255,127));

        tuploadOblig.setOpaque(true);
        tuploadOblig.setBackground(new Color(255,255,255,127));

        setLayout(new GridLayout(3,2));
        add(tdownloadExcel);
        add(bDownload);
        add(tuploadExcel);
        add(bUpload);
        add(tuploadOblig);
        add(bUploadOblig);

        SistemaDiPrenotazioneController sisPr= new SistemaDiPrenotazioneController();

        bDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sisPr.createXlsGenerality(gestoreEvento.getName());
                bUpload.setEnabled(true);
            }
        });

        bUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    for (Invitato i : sisPr.loadXlsGenerality(gestoreEvento.getName())) {
                        gestoreEvento.addInvitati(i);

                    }
                } catch (DatabaseException e1) {
                    e1.printStackTrace();
                } catch (DatabaseNullException e1) {
                    e1.printStackTrace();
                }

                try {
                    sisPr.writeXlsObligations(gestoreEvento.getName());
                    bUploadOblig.setEnabled(true);
                } catch (DatabaseException e1) {
                    e1.printStackTrace();
                } catch (DatabaseNullException e1) {
                    e1.printStackTrace();
                }
            }
        });

        bUploadOblig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sisPr.saveOnObligations(gestoreEvento.getName());
                GestoreVincoliTavolo gestoreVincoliTavolo= new GestoreVincoliTavolo(gestoreEvento.getName());
                FinestraDisposizioneTavoli fd=new FinestraDisposizioneTavoli(gestoreLocale, gestoreEvento,gestoreVincoliTavolo);
                fd.setVisible(true);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
        });
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