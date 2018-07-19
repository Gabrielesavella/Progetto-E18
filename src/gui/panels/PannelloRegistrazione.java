package gui.panels;

import database.DatabaseException;
import database.DatabaseNullException;
import gui.controller.SistemaDiPrenotazioneController;
import javafx.stage.Screen;
import locale.GestoreLocale;
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

/**
 *
 * @author lecciovich
 */

public class PannelloRegistrazione extends JPanel {
    static Cliente lecciovich= new Cliente("lecciovich","Marco","Lecce", "marcolecce@gmail.com","prova");
    static ArrayList<Cliente> clienti=new ArrayList<Cliente>(2);
    private Image backgroundImage;

    public PannelloRegistrazione(ArrayList<GestoreLocale> locali,JFrame frame){

        try{
            backgroundImage = ImageIO.read(new File("images/alcohol-banquet-beverage-306046.jpg"));
            backgroundImage=backgroundImage.getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }

        //bottone conferma
        JButton conferma = new JButton("Registrati");

        //etichette per descrizione campi di testo
        JLabel eMail = new JLabel("E-Mail:");
        JLabel nome = new JLabel("Nome:");
        JLabel cognome = new JLabel("Cognome:");
        JLabel username = new JLabel("Username:");
        JLabel password = new JLabel("Password:");
        JLabel confPassword = new JLabel("Conferma Password:");
        JLabel errore = new JLabel();

        //campi di inserimento testo
        JTextField tEMail = new JTextField("");
        JTextField tNome = new JTextField("");
        JTextField tCognome = new JTextField("");
        JTextField tUsername = new JTextField("");
        JTextField tPassword = new JPasswordField("");
        JTextField tConfPassword=new JPasswordField("");

        JTextField clienteAdded= new JTextField("qui nuovi clienti");
        clienteAdded.setVisible(false);

        errore.setVisible(false);
        username.setVisible(true);
        password.setVisible(true);

        //modified label aligment and background color and conferma is not enabled at start
        nome.setOpaque(true);
        nome.setBackground(new Color(255,255,2,90));
        nome.setHorizontalAlignment(SwingConstants.RIGHT);
        nome.setPreferredSize(new Dimension(nome.getWidth()/4,nome.getHeight()));

        cognome.setOpaque(true);
        cognome.setBackground(new Color(255,255,2,90));
        cognome.setHorizontalAlignment(SwingConstants.RIGHT);

        eMail.setOpaque(true);
        eMail.setBackground(new Color(255,255,2,90));
        eMail.setHorizontalAlignment(SwingConstants.RIGHT);

        username.setOpaque(true);
        username.setBackground(new Color(255,255,2,90));
        username.setHorizontalAlignment(SwingConstants.RIGHT);

        password.setOpaque(true);
        password.setBackground(new Color(255,255,2,90));
        password.setHorizontalAlignment(SwingConstants.RIGHT);

        confPassword.setOpaque(true);
        confPassword.setBackground(new Color(255,255,2,90));
        confPassword.setHorizontalAlignment(SwingConstants.RIGHT);

        conferma.setEnabled(false);


        //pannelli
        JPanel campi=new JPanel();
        JPanel bottoni=new JPanel();
//        int i=6;
//        int j=2;
//        JPanel[][] panelHolder= new JPanel[i][j];
        bottoni.setLayout(new GridLayout(2,1));
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
//        campi.add(conferma);
//        campi.add(errore);
//        campi.add(clienteAdded);

        campi.setOpaque(false);

//        bottoni.add(clienteAdded);
        bottoni.add(errore);
        bottoni.add(conferma);
        bottoni.setOpaque(false);
//        bottoni.setLayout(new GridLayout(1,3));
//        add(new JPanel());
//        add(conferma);
//
//        this.setLayout(new GridLayout(1,2));

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

                SistemaDiPrenotazioneController sisPr= new SistemaDiPrenotazioneController();
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
                        errore.setVisible(true);
                        errore.setOpaque(true);
                        errore.setBackground(new Color(0,0,255,70));
                        errore.setHorizontalAlignment(SwingConstants.CENTER);
                        errore.setVerticalAlignment(SwingConstants.CENTER);
                        errore.setHorizontalTextPosition(SwingConstants.CENTER);
                        errore.setVerticalTextPosition(SwingConstants.CENTER);
                        errore.setText("<html><div style='text-align: center;'>" + "Not same password in password areas!"+"<br>Please fill with same password value in both text areas! " + "</div></html>");
                        errore.setForeground(Color.RED);
                    }
                    else {
                        errore.setVisible(true);
                        errore.setOpaque(true);
                        errore.setBackground(new Color(0,0,0,70));
                        errore.setHorizontalAlignment(SwingConstants.CENTER);
                        errore.setVerticalAlignment(SwingConstants.CENTER);
                        errore.setHorizontalTextPosition(SwingConstants.CENTER);
                        errore.setVerticalTextPosition(SwingConstants.CENTER);
                        errore.setText("<html><div style='text-align: center;'>" + "username is already used!"+"<br>Please fill with unused values the sign up form" + "</div></html>");
                        //errore.setText();
                        errore.setForeground(Color.green);
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

    //parte adibita alla "pittura" della foto sullo sfondo
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Toolkit toolkit= Toolkit.getDefaultToolkit();//Toolkit.getDefaultToolkit();
        //Dimension dim= toolkit.;
        Dimension dim= this.getSize();
        int width= dim.width;
        int height= dim.height;

        g.drawImage(backgroundImage, 0, 0,width,height, this);

    }

    public static ArrayList<Cliente> getClienti() {
        return clienti;
    }


}
