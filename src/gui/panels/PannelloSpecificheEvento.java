
package gui.panels;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import database.*;
import gui.controller.*;
import gui.finestre.*;
import locale.*;
import locale.GestoreLocale;
import persone.*;
import vincoli.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author lecciovich
 */

public class PannelloSpecificheEvento extends PaintedPanel {

    public PannelloSpecificheEvento(GestoreLocale gestoreLocale, Evento gestoreEvento, FinestraSpecificheEvento frame){
        super("images/notebook-pen-table-97076.jpg",frame);

        // etichette specifiche
        JLabel compila= new JLabel("compila lista invitati");
        JLabel carica= new JLabel("carica lista invitati");

        compila.setOpaque(true);
        compila.setBackground(new Color(255,255,255,170));

        carica.setOpaque(true);
        carica.setBackground(new Color(255,255,255,170));

        //immagini
        final int WIDHT=20,HEIGHT=20;

        ImageIcon imgI= new ImageIcon("images/www.jpg");
        Image img= imgI.getImage();
        Image newImage= img.getScaledInstance(WIDHT,HEIGHT,1);
        ImageIcon newI=new ImageIcon(newImage);
        ImageIcon imgI2= new ImageIcon("images/WwW1.jpg");
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
        JLabel tdownloadExcel= new JLabel("Download");
        tdownloadExcel.setHorizontalAlignment(SwingConstants.CENTER);
        tdownloadExcel.setVerticalAlignment(SwingConstants.CENTER);
        tdownloadExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        tdownloadExcel.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel tuploadExcel= new JLabel("Upload");
        tuploadExcel.setHorizontalAlignment(SwingConstants.CENTER);
        tuploadExcel.setVerticalAlignment(SwingConstants.CENTER);
        tuploadExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        tuploadExcel.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel tuploadOblig= new JLabel("UploadOblig");
        tuploadOblig.setHorizontalAlignment(SwingConstants.CENTER);
        tuploadOblig.setVerticalAlignment(SwingConstants.CENTER);
        tuploadOblig.setHorizontalTextPosition(SwingConstants.CENTER);
        tuploadOblig.setVerticalTextPosition(SwingConstants.CENTER);

        Font fontB=new Font ("Monotype Corsiva", Font.ROMAN_BASELINE, 18);
        tdownloadExcel.setFont(fontB);
        tuploadExcel.setFont(fontB);
        tuploadOblig.setFont(fontB);


        tdownloadExcel.setOpaque(true);
        tdownloadExcel.setBackground(new Color(255,255,255,200));

        tuploadExcel.setOpaque(true);
        tuploadExcel.setBackground(new Color(255,255,255,200));

        tuploadOblig.setOpaque(true);
        tuploadOblig.setBackground(new Color(255,255,255,200));

        setLayout(new GridLayout(3,2));
        add(tdownloadExcel);
        add(bDownload);
        add(tuploadExcel);
        add(bUpload);
        add(tuploadOblig);
        add(bUploadOblig);

        SistemaDiPrenotazione sisPr= new SistemaDiPrenotazione();

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
                GestoreVincoliTavolo gestoreVincoliTavolo= new GestoreVincoliTavolo(gestoreEvento.getName(),gestoreLocale);
                FinestraDisposizioneTavoli fd=new FinestraDisposizioneTavoli(gestoreLocale, gestoreEvento,gestoreVincoliTavolo,sisPr);
                fd.setVisible(true);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
        });
    }

}