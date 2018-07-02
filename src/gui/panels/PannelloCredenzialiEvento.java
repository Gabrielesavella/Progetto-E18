package gui.panels;

import gui.finestre.FinestraSpecificheEvento;
import locale.Evento;
import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class PannelloCredenzialiEvento extends JPanel{
    public PannelloCredenzialiEvento(ArrayList<locale.Locale> locali){
        JButton ok=new JButton("OK");

        JLabel nome= new JLabel("Nome Evento:");
        JLabel data= new JLabel("Data Evento:");
        JLabel selLoc= new JLabel("Selezione Locale:");
        JLabel nInv= new JLabel("Numero Invitati:");

        JTextField tNome= new JTextField();
        JTextField tData= new JTextField();
        JTextField tSelLoc= new JTextField();
        JTextField tnInv= new JTextField();

        JPanel campi=new JPanel();
        campi.setLayout(new GridLayout(4,2));

        campi.add(nome);
        campi.add(tNome);
        campi.add(data);

        JPanel pData= new JPanel();
        pData.setLayout(new GridLayout(1,5));

        JTextField giorno=new JTextField("gg");
        JTextField mese= new JTextField("mm");
        JTextField anno= new JTextField("aaaa");
        JLabel token= new JLabel("/");

        pData.add(giorno);
        pData.add(mese);
        pData.add(anno);

        campi.add(pData);

        JComboBox<String> dropDownLocali= new JComboBox<String>();

        dropDownLocali.addItem("da Giulio");
        dropDownLocali.addItem("respiriani");
        dropDownLocali.addItem("persone da bullizzare");



        campi.add(selLoc);
        campi.add(dropDownLocali);
        campi.add(nInv);
        campi.add(tnInv);

        this.setLayout(new BorderLayout());
        add(campi);
        add(ok,BorderLayout.SOUTH);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Locale localeSelezionato=null;

                int invitati=Integer.parseInt(tnInv.getText());
                int annoInt= Integer.parseInt(anno.getText());
                int meseInt= Integer.parseInt(mese.getText());
                int giornoInt= Integer.parseInt(giorno.getText());
                Date calendar = new Date(annoInt,meseInt,giornoInt);

                for (Locale l:locali) {
                    if(l.id_locale.equals(dropDownLocali.getSelectedItem())) {
                        localeSelezionato=l;
                    }
                }

                Evento evento=new Evento(tNome.getText(),calendar.toString(),localeSelezionato.getId_locale(),invitati);
                FinestraSpecificheEvento fs= new FinestraSpecificheEvento(localeSelezionato,evento);
                //fs.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                fs.setVisible(true);
            }
        });
    }
}
