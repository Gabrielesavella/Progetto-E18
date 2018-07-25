package gui.panels;

import database.*;
import gui.controller.SistemaDiPrenotazioneController;
import gui.finestre.FinestraCreazioneEvento;
import gui.finestre.FinestraSpecificheEvento;
import locale.GestoreEvento;
import locale.GestoreLocale;
import org.jdesktop.swingx.JXDatePicker;
import persone.Cliente;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author lecciovich
 */

public class PannelloCredenzialiEvento extends JPanel{

    private Image backgroundImage;

    public PannelloCredenzialiEvento(ArrayList<GestoreLocale> locali, Cliente cliente, FinestraCreazioneEvento frame){
        try{
            backgroundImage = ImageIO.read(new File("images/TRATTORIA-PARIONE-facciata-notturna-home51.jpg"));
            backgroundImage=backgroundImage.getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }


        JButton ok=new JButton("OK");
        ok.setEnabled(false);

        JLabel nome= new JLabel("Nome Evento:");
        JLabel data= new JLabel("Data Evento:");
        JLabel selLoc= new JLabel("Selezione Locale:");
        JLabel nInv= new JLabel("Numero Invitati:");

        nome.setOpaque(true);
        nome.setBackground(new Color(255,255,255,127));
        data.setOpaque(true);
        data.setBackground(new Color(255,255,255,127));
        selLoc.setOpaque(true);
        selLoc.setBackground(new Color(255,255,255,127));
        nInv.setOpaque(true);
        nInv.setBackground(new Color(255,255,255,127));

        JTextField tNome= new JTextField();
        JTextField tData= new JTextField();
        JTextField tSelLoc= new JTextField();
        JTextField tnInv= new JTextField();

        JPanel campi=new JPanel();
        campi.setLayout(new GridLayout(4,2));

        campi.add(nome);
        campi.add(tNome);
        campi.add(data);

        JXDatePicker calendario=new JXDatePicker();

        campi.add(calendario);

        JComboBox<String> dropDownLocali= new JComboBox<String>();

            for(GestoreLocale l:locali)
                dropDownLocali.addItem(l.getId_locale());


        campi.add(selLoc);
        campi.add(dropDownLocali);
        campi.add(nInv);
        campi.add(tnInv);
        campi.setOpaque(false);
        ok.setOpaque(false);

        this.setLayout(new BorderLayout());
        add(campi);
        add(ok,BorderLayout.SOUTH);

        SistemaDiPrenotazioneController sisPr = new SistemaDiPrenotazioneController();
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GestoreLocale gestoreLocaleSelezionato =null;

                int invitati=Integer.parseInt(tnInv.getText());
                Date data=calendario.getDate();
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(data);

                for (GestoreLocale l:locali) {
                    if(l.id_locale.equals(dropDownLocali.getSelectedItem())) {
                        gestoreLocaleSelezionato =l;

                        try {
                             sisPr.creaEvento(tNome.getText(),calendar,Integer.parseInt(tnInv.getText()),cliente,l.getId_locale());

                        } catch (DatabaseException e1) {
                            e1.printStackTrace();
                        } catch (DatabaseNullException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                GestoreEvento gestoreEvento =new GestoreEvento(tNome.getText(),calendar, gestoreLocaleSelezionato,invitati);
                FinestraSpecificheEvento fs= new FinestraSpecificheEvento(gestoreLocaleSelezionato, gestoreEvento);//fetchEvento
                fs.setVisible(true);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
        });
        // actionListener per attivare bottone confermaCreazioneEvento
        tNome.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && tData.getText() != null && tSelLoc.getText() != null && Integer.parseInt(tnInv.getText()) > 0;
                if (registrationCompleted)
                    ok.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        tData.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && tData.getText() != null && tSelLoc.getText() != null && Integer.parseInt(tnInv.getText()) > 0;
                if (registrationCompleted)
                    ok.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        tSelLoc.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && tData.getText() != null && tSelLoc.getText() != null && Integer.parseInt(tnInv.getText()) > 0;
                if (registrationCompleted)
                    ok.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        tnInv.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && tData.getText() != null && tSelLoc.getText() != null && Integer.parseInt(tnInv.getText()) > 0;
                if (registrationCompleted)
                    ok.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        //
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
