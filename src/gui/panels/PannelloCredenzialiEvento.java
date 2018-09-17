package gui.panels;

import database.*;
import gui.controller.SistemaDiPrenotazione;
import gui.finestre.FinestraCreazioneEvento;
import gui.finestre.FinestraSpecificheEvento;
import locale.*;
import locale.Locale;
import org.jdesktop.swingx.JXDatePicker;
import persone.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author lecciovich
 */

public class PannelloCredenzialiEvento extends PaintedPanel{

    ArrayList <Locale> locali;

    public PannelloCredenzialiEvento(Cliente cliente, SistemaDiPrenotazione sisPr, FinestraCreazioneEvento frame){
        super("images/TRATTORIA-PARIONE-facciata-notturna-home51.jpg",frame);//ArrayList<Locale> locali,

        JButton ok=new JButton("OK");
        ok.setEnabled(false);

        Font fontB=new Font ("Monotype Corsiva", Font.ROMAN_BASELINE, 21);

        JLabel nome= new JLabel("Nome Evento:");
        nome.setFont(fontB);
        nome.setHorizontalAlignment(SwingConstants.CENTER);
        nome.setVerticalAlignment(SwingConstants.CENTER);
        nome.setHorizontalTextPosition(SwingConstants.CENTER);
        nome.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel data= new JLabel("Data Evento:");
        data.setFont(fontB);
        data.setHorizontalAlignment(SwingConstants.CENTER);
        data.setVerticalAlignment(SwingConstants.CENTER);
        data.setHorizontalTextPosition(SwingConstants.CENTER);
        data.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel selLoc= new JLabel("Selezione Locale:");
        selLoc.setFont(fontB);
        selLoc.setHorizontalAlignment(SwingConstants.CENTER);
        selLoc.setVerticalAlignment(SwingConstants.CENTER);
        selLoc.setHorizontalTextPosition(SwingConstants.CENTER);
        selLoc.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel nInv= new JLabel("Numero Invitati:");
        nInv.setFont(fontB);
        nInv.setHorizontalAlignment(SwingConstants.CENTER);
        nInv.setVerticalAlignment(SwingConstants.CENTER);
        nInv.setHorizontalTextPosition(SwingConstants.CENTER);
        nInv.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel errore= new JLabel();
        errore.setHorizontalAlignment(SwingConstants.CENTER);
        errore.setVerticalAlignment(SwingConstants.CENTER);
        errore.setHorizontalTextPosition(SwingConstants.CENTER);
        errore.setVerticalTextPosition(SwingConstants.CENTER);
        errore.setForeground(Color.GREEN);
        JLabel backgrNonOp= new JLabel();


        nome.setOpaque(true);
        nome.setBackground(new Color(255,255,255,200));
        data.setOpaque(true);
        data.setBackground(new Color(255,255,255,200));
        selLoc.setOpaque(true);
        selLoc.setBackground(new Color(255,255,255,200));
        nInv.setOpaque(true);
        nInv.setBackground(new Color(255,255,255,200));
        errore.setOpaque(true);
        errore.setBackground(new Color(0,0,0,90));
        backgrNonOp.setOpaque(true);
        backgrNonOp.setBackground(new Color(255,255,255,127));

        JTextField tNome= new JTextField();
        JTextField tData= new JTextField();
        JTextField tSelLoc= new JTextField();
        JTextField tnInv= new JTextField();


        JPanel campi=new JPanel();
        campi.setLayout(new GridLayout(4,2));
        JPanel conferme=new JPanel();
        conferme.setLayout(new GridLayout(0,1));

        campi.add(nome);
        campi.add(tNome);
        campi.add(data);

        conferme.add(ok);

        JXDatePicker calendario=new JXDatePicker();

        campi.add(calendario);

        JComboBox<String> dropDownLocali= new JComboBox<String>();

        ConnessioneDB connessione = new ConnessioneDB();
        locali = connessione.getLocali();

        for(Locale l:locali)
                dropDownLocali.addItem(l.getId_locale());


        campi.add(selLoc);
        campi.add(dropDownLocali);
        campi.add(nInv);
        campi.add(tnInv);
        campi.setOpaque(false);
        ok.setOpaque(false);

        this.setLayout(new BorderLayout());
        add(campi);
        add(conferme,BorderLayout.SOUTH);

        //SistemaDiPrenotazione sisPr = new SistemaDiPrenotazione();
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Locale localeSelezionato =null;

                int invitati=Integer.parseInt(tnInv.getText());
                Date data=calendario.getDate();
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(data);

                SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                format.setCalendar(calendar);
                String stringData= format.format(calendar.getTime());


                for (Locale l:locali) {
                    if(l.id_locale.equals(dropDownLocali.getSelectedItem())) {
                        localeSelezionato = l;
                        if (localeSelezionato.checkDisponibilita(stringData, invitati)) {

                            try {
                                if (sisPr.creaEvento(tNome.getText(), calendar, Integer.parseInt(tnInv.getText()), cliente, l.getId_locale())) {
                                    Evento gestoreEvento = new Evento(tNome.getText(), calendar, localeSelezionato, invitati);
                                    FinestraSpecificheEvento fs = new FinestraSpecificheEvento(localeSelezionato, gestoreEvento,sisPr);
                                    fs.setVisible(true);
                                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                                } else {
                                    if(conferme.getComponent(0).equals(errore))
                                        conferme.remove(errore);
                                    conferme.add(errore,0);
                                    errore.setVisible(true);
                                    errore.setText("<html><div style='text-align: center;'>" + "the Event name " + tNome.getText() + " is just used."+"<br>Please try again changing its name." + "</div></html>");
                                    errore.setBackground(Color.BLACK);
                                    errore.setForeground(Color.RED);
                                }

                            } catch (DatabaseException e1) {
                                e1.printStackTrace();
                            } catch (DatabaseNullException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else{
                            if(conferme.getComponent(0).equals(errore))
                                conferme.remove(errore);
                            //conferme.remove(backgrNonOp);
                            conferme.add(errore,0);
                            errore.setVisible(true);
                            errore.setText("<html><div style='text-align: center;'>" + invitati + "guests cannot be hosted by this restaurant."+"<br>Please try with less guests or change restaurant." + "</div></html>");
                            errore.setBackground(Color.BLACK);
                            errore.setForeground(Color.RED);
                        }

                    }
                }
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

}
