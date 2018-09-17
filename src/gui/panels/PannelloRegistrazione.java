package gui.panels;

import database.DatabaseException;
import gui.controller.SistemaDiPrenotazione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author lecciovich
 */

public class PannelloRegistrazione extends PaintedPanel {

    public PannelloRegistrazione(SistemaDiPrenotazione sisPr, JFrame frame){
        super("images/alcohol-banquet-beverage-306046.jpg",frame);

        //bottone conferma
        Font fontB=new Font ("Monotype Corsiva", Font.ROMAN_BASELINE, 21);
        JButton conferma = new JButton(" Registrati");
        conferma.setFont(fontB);

        //etichette per descrizione campi di testo
        JLabel eMail = new JLabel("E-Mail:");
        eMail.setFont(fontB);
        eMail.setHorizontalAlignment(SwingConstants.CENTER);
        eMail.setVerticalAlignment(SwingConstants.CENTER);
        eMail.setHorizontalTextPosition(SwingConstants.CENTER);
        eMail.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel nome = new JLabel("Nome:");
        nome.setFont(fontB);
        nome.setHorizontalAlignment(SwingConstants.CENTER);
        nome.setVerticalAlignment(SwingConstants.CENTER);
        nome.setHorizontalTextPosition(SwingConstants.CENTER);
        nome.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel cognome = new JLabel("Cognome:");
        cognome.setFont(fontB);
        cognome.setHorizontalAlignment(SwingConstants.CENTER);
        cognome.setVerticalAlignment(SwingConstants.CENTER);
        cognome.setHorizontalTextPosition(SwingConstants.CENTER);
        cognome.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel username = new JLabel("Username:");
        username.setFont(fontB);
        username.setHorizontalAlignment(SwingConstants.CENTER);
        username.setVerticalAlignment(SwingConstants.CENTER);
        username.setHorizontalTextPosition(SwingConstants.CENTER);
        username.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel password = new JLabel("Password:");
        password.setFont(fontB);
        password.setHorizontalAlignment(SwingConstants.CENTER);
        password.setVerticalAlignment(SwingConstants.CENTER);
        password.setHorizontalTextPosition(SwingConstants.CENTER);
        password.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel confPassword = new JLabel("Conferma Password:");
        confPassword.setFont(fontB);
        confPassword.setHorizontalAlignment(SwingConstants.CENTER);
        confPassword.setVerticalAlignment(SwingConstants.CENTER);
        confPassword.setHorizontalTextPosition(SwingConstants.CENTER);
        confPassword.setVerticalTextPosition(SwingConstants.CENTER);
        JLabel errore = new JLabel();

        //campi di inserimento testo
        JTextField tEMail = new JTextField("");
        JTextField tNome = new JTextField("");
        JTextField tCognome = new JTextField("");
        JTextField tUsername = new JTextField("");
        JTextField tPassword = new JPasswordField("");
        JTextField tConfPassword=new JPasswordField("");

        JTextField clienteAdded= new JTextField(" qui nuovi clienti ");
        clienteAdded.setVisible(false);

        errore.setVisible(false);
        username.setVisible(true);
        password.setVisible(true);

        //modified label aligment and background color and conferma is not enabled at start
        nome.setOpaque(true);
        nome.setBackground(new Color(255,255,255,200));
        //nome.setHorizontalAlignment(SwingConstants.RIGHT);
        nome.setPreferredSize(new Dimension(nome.getWidth()/4,nome.getHeight()));

        cognome.setOpaque(true);
        cognome.setBackground(new Color(255,255,255,200));
        //cognome.setHorizontalAlignment(SwingConstants.RIGHT);

        eMail.setOpaque(true);
        eMail.setBackground(new Color(255,255,255,200));
        //eMail.setHorizontalAlignment(SwingConstants.RIGHT);

        username.setOpaque(true);
        username.setBackground(new Color(255,255,255,200));
        //username.setHorizontalAlignment(SwingConstants.RIGHT);

        password.setOpaque(true);
        password.setBackground(new Color(255,255,255,200));
        //password.setHorizontalAlignment(SwingConstants.RIGHT);

        confPassword.setOpaque(true);
        confPassword.setBackground(new Color(255,255,255,200));
        //confPassword.setHorizontalAlignment(SwingConstants.RIGHT);

        conferma.setEnabled(false);


        //pannelli
        JPanel campi=new JPanel();
        JPanel bottoni=new JPanel();
        bottoni.setLayout(new GridLayout(0,1));
        campi.setLayout(new GridLayout(6,2));

        campi.add(nome);
        campi.add(tNome);
        campi.add(cognome);
        campi.add(tCognome);
        campi.add(eMail);
        campi.add(tEMail);
        campi.add(username);
        campi.add(tUsername);
        campi.add(password);
        campi.add(tPassword);
        campi.add(confPassword);
        campi.add(tConfPassword);

        campi.setOpaque(false);

        bottoni.add(conferma);
        bottoni.setOpaque(false);

        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        this.add(campi,BorderLayout.CENTER);
        this.add(bottoni,BorderLayout.SOUTH);


        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean registrationCompleted=!tNome.getText().equals("") && !tCognome.getText().equals("") && tEMail.getText().equals("") && ((JPasswordField) tPassword).echoCharIsSet() && ((JPasswordField) tConfPassword).echoCharIsSet();
                if(registrationCompleted)
                    conferma.setEnabled(true);

//                SistemaDiPrenotazione sisPr= new SistemaDiPrenotazione();
                boolean registrazione= false;
                try {
                    registrazione = sisPr.signUp(tNome.getText(),tCognome.getText(),tEMail.getText(),tUsername.getText(),tPassword.getText());
                } catch (DatabaseException e1) {
                    e1.printStackTrace();
                }
                boolean psswrdCorretta=tPassword.getText().equals(tConfPassword.getText());
                if(registrazione & psswrdCorretta){
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
                else {
                    if (!psswrdCorretta) {
                        if (bottoni.getComponent(0).equals(errore))
                            bottoni.remove(errore);
                        bottoni.add(errore,0);
                        errore.setVisible(true);
                        errore.setOpaque(true);
                        errore.setBackground(new Color(0,0,0,70));
                        errore.setHorizontalAlignment(SwingConstants.CENTER);
                        errore.setVerticalAlignment(SwingConstants.CENTER);
                        errore.setHorizontalTextPosition(SwingConstants.CENTER);
                        errore.setVerticalTextPosition(SwingConstants.CENTER);
                        errore.setText("<html><div style='text-align: center;'>" + "Not same password in password areas!"+"<br>Please fill with same password value in both text areas! " + "</div></html>");
                        errore.setForeground(Color.RED);
                    }
                    else {
                        if (bottoni.getComponent(0).equals(errore))
                            bottoni.remove(errore);
                        bottoni.add(errore,0);
                        errore.setVisible(true);
                        errore.setOpaque(true);
                        errore.setBackground(new Color(0,0,0,70));
                        errore.setHorizontalAlignment(SwingConstants.CENTER);
                        errore.setVerticalAlignment(SwingConstants.CENTER);
                        errore.setHorizontalTextPosition(SwingConstants.CENTER);
                        errore.setVerticalTextPosition(SwingConstants.CENTER);
                        errore.setText("<html><div style='text-align: center;'>" + "username is already used!"+"<br>Please fill with unused values the sign up form" + "</div></html>");
                        errore.setForeground(Color.RED);
                    }
                }
            }
        });

        // actionListener per attivare bottone confermaCreazioneEvento
        tNome.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && !tCognome.getText().equals("") && !tEMail.getText().equals("") && !tUsername.getText().equals("") && ((JPasswordField) tPassword).echoCharIsSet() && ((JPasswordField) tConfPassword).echoCharIsSet();
                if (registrationCompleted)
                    conferma.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        tCognome.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && !tCognome.getText().equals("") && !tEMail.getText().equals("") && !tUsername.getText().equals("") && ((JPasswordField) tPassword).echoCharIsSet() && ((JPasswordField) tConfPassword).echoCharIsSet();
                if (registrationCompleted)
                    conferma.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        tEMail.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && !tCognome.getText().equals("") && !tEMail.getText().equals("") && !tUsername.getText().equals("") && ((JPasswordField) tPassword).echoCharIsSet() && ((JPasswordField) tConfPassword).echoCharIsSet();
                if (registrationCompleted)
                    conferma.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        tUsername.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && !tCognome.getText().equals("") && !tEMail.getText().equals("") && !tUsername.getText().equals("") && ((JPasswordField) tPassword).echoCharIsSet() && ((JPasswordField) tConfPassword).echoCharIsSet();
                if (registrationCompleted)
                    conferma.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        tPassword.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && !tCognome.getText().equals("") && !tEMail.getText().equals("") && !tUsername.getText().equals("") && ((JPasswordField) tPassword).echoCharIsSet() && ((JPasswordField) tConfPassword).echoCharIsSet();
                if (registrationCompleted)
                    conferma.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        tConfPassword.addActionListener(e -> {
            try {
                boolean registrationCompleted = !tNome.getText().equals("") && !tCognome.getText().equals("") && !tEMail.getText().equals("") && !tUsername.getText().equals("") && ((JPasswordField) tPassword).echoCharIsSet() && ((JPasswordField) tConfPassword).echoCharIsSet();
                if (registrationCompleted)
                    conferma.setEnabled(true);
            }catch (NumberFormatException ex){
                //do nothing
            }
        });
        //


    }

}
