package gui.panels;

import gui.finestre.FinestraCreazioneEvento;
import gui.finestre.FinestraRegistrazione;
import locale.Locale;
import persone.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PannelloLogin extends JPanel {
    public PannelloLogin(ArrayList<Locale> locali){
        //Bottoni
        JButton signUp = new JButton("SignUp");
        JButton logIn = new JButton("LogIn");
        //etichette per descrizione campi di testo
        JLabel username = new JLabel("Username:");
        JLabel password = new JLabel("Password:");
        //campi di inserimento testo
        JTextField tUsername = new JTextField("");
        JTextField tPassword = new JTextField("");
        username.setVisible(true);
        password.setVisible(true);
        int i=12;
        int j=3;
        JPanel[][] panelHolder= new JPanel[i][j];
        this.setLayout(new GridLayout(12,3));
        for (int m=0;m<i;m++){
            for (int n=0;n<j;n++){
                panelHolder[m][n]=new JPanel();
                add(panelHolder[m][n]);
                panelHolder[m][n].setLayout(new BorderLayout());
            }
        }
        panelHolder[4][1].add(username);
        panelHolder[5][1].add(tUsername);
        panelHolder[6][1].add(password);
        panelHolder[7][1].add(tPassword);
        panelHolder[9][1].add(logIn);
        panelHolder[0][2].add(signUp,BorderLayout.CENTER);


        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinestraRegistrazione fr=new FinestraRegistrazione(locali);
                fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                fr.setVisible(true);
            }
        });

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PannelloRegistrazione.clienti!=null){
                    for (Cliente c:PannelloRegistrazione.clienti) {
                        if (c.getID().equals(username.getText()) && c.getPsw().equals(password.getText())){
                            FinestraCreazioneEvento fe=new FinestraCreazioneEvento(locali);
                            fe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                            fe.setVisible(true);
                        }
                    }
                }
            }
        });

    }

}
