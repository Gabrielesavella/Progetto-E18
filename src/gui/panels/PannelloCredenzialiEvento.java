package gui.panels;

import gui.finestre.FinestraSpecificheEvento;
import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PannelloCredenzialiEvento extends JPanel{
    public PannelloCredenzialiEvento(ArrayList<locale.Locale> locali){
        JButton ok=new JButton("OK");

        JLabel nome= new JLabel("Nome locale.Evento:");
        JLabel data= new JLabel("Data locale.Evento:");
        JLabel selLoc= new JLabel("Selezione locale.Locale:");
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

        dropDownLocali.addItem("caccole");
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
                FinestraSpecificheEvento fs= new FinestraSpecificheEvento(locali);
                fs.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                fs.setVisible(true);
            }
        });
    }
}
