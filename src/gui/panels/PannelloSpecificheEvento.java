package gui.panels;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import gui.finestre.FinestraDisposizioneTavoli;
import locale.Evento;
import locale.Locale;
import persone.Invitato;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PannelloSpecificheEvento extends JPanel {
    public PannelloSpecificheEvento(Locale locale, Evento evento){


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
        JButton bDownload= new JButton(newI);
        bDownload.setPreferredSize(new Dimension(WIDHT,HEIGHT));
        JButton bUpload= new JButton(newI2);
        bDownload.setPreferredSize(new Dimension(WIDHT,HEIGHT));

        // campi testo
        JLabel tdownloadExcel= new JLabel("download");
        JLabel tuploadExcel= new JLabel("upload");

        setLayout(new GridLayout(2,2));
        add(tdownloadExcel);
        add(bDownload);
        add(tuploadExcel);
        add(bUpload);

        bDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("the-file-name.txt", "UTF-8");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                writer.println("\t\tCod.Fiscale\tNome\tCognome\tetà");
                for (int i = 0; i<evento.getNumInvitati(); i++)
                    writer.println(i+1+"\t\t\t\t\t\t\t\t");
                writer.close();
            }
        });

        bUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                Scanner scanner= null;
                try {
                    File fin=new File("the-file-name.txt");
                    scanner = new Scanner(new InputStreamReader(new FileInputStream(fin)));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                String bin=scanner.nextLine();
                while (scanner.hasNextLine()){
                    String[] str=new String[5];
                    String tmp=scanner.nextLine();
                    str=tmp.split("\t\t");
                    if(str.length!=5){
                        System.out.println("Invitati inferiori al numero previsto.");
                        break;
                    }
                    evento.addInvitato(new Invitato(str[1],str[2],str[3],Integer.parseInt(str[4])));

                }
                System.out.println("Acquisizione invitati effettuata.");
                FinestraDisposizioneTavoli fd=new FinestraDisposizioneTavoli(locale,evento);
                fd.setVisible(true);

            }
        });
    }
}
