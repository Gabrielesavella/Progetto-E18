package gui.panels;

import gui.controller.SistemaDiPrenotazioneController;
import gui.finestre.FinestraCreazioneEvento;
import gui.finestre.FinestraLogin;
import gui.finestre.FinestraRegistrazione;
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

public class PannelloLogin extends JPanel {

    private Image backgroundImage;
    private boolean checkTextUsername;
    private boolean checkTextPassword;

    public PannelloLogin(ArrayList<GestoreLocale> locali, FinestraLogin frame) {

        checkTextUsername = false;
        checkTextPassword = false;
        try {
            backgroundImage = ImageIO.read(new File("images/cake-chairs-cutlery-395134.jpg"));
            backgroundImage = backgroundImage.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Nimbus LookandFeel setup
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Bottoni
        JButton signUp = new JButton("SignUp");
        JButton logIn = new JButton("LogIn");
        //etichette per descrizione campi di testo
        JLabel username = new JLabel("Username:");
        JLabel password = new JLabel("Password:");
        JLabel errore = new JLabel();
        //campi di inserimento testo
        JTextField tUsername = new JTextField("");
        JPasswordField tPassword = new JPasswordField("");
        username.setVisible(true);
        password.setVisible(true);
        signUp.setVisible(true);
        logIn.setVisible(true);
        logIn.setEnabled(false);
        errore.setVisible(false);

        int i = 12;
        int j = 3;
        JPanel[][] panelHolder = new JPanel[i][j];
        this.setLayout(new GridLayout(12, 3));
        for (int m = 0; m < i; m++) {
            for (int n = 0; n < j; n++) {
                boolean isTextFieldsCentered = m >= 4 && m < 8 && n == 1;
                panelHolder[m][n] = new JPanel();
                if ((isTextFieldsCentered))
                    panelHolder[m][n].setBackground(new Color(255, 255, 255, 127));
                else
                    panelHolder[m][n].setOpaque(false);

                add(panelHolder[m][n]);
                panelHolder[m][n].setLayout(new BorderLayout());
            }
        }
        panelHolder[0][2].add(signUp, BorderLayout.CENTER);
        panelHolder[4][1].add(username);
        panelHolder[5][1].add(tUsername);
        panelHolder[6][1].add(password);
        panelHolder[7][1].add(tPassword);
        panelHolder[8][1].add(errore);
        panelHolder[9][1].add(logIn);


        SistemaDiPrenotazioneController sisPr = new SistemaDiPrenotazioneController();
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinestraRegistrazione fr = new FinestraRegistrazione(locali);
                //fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                fr.setVisible(true);

            }
        });

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = sisPr.login(tUsername.getText(), String.valueOf(tPassword.getPassword()));
                if (cliente != null) {
                    FinestraCreazioneEvento fe = new FinestraCreazioneEvento(locali, cliente);
                    fe.setVisible(true);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                } else {
                    errore.setVisible(true);
                    errore.setOpaque(true);
                    errore.setBackground(new Color(0, 0, 0, 90));
                    errore.setHorizontalAlignment(SwingConstants.CENTER);
                    errore.setVerticalAlignment(SwingConstants.CENTER);
                    errore.setHorizontalTextPosition(SwingConstants.CENTER);
                    errore.setVerticalTextPosition(SwingConstants.CENTER);
                    errore.setText("<html><div style='text-align: center;'>" + "Username or password not correct" + "<br>Please fill with correct username and password values " + "</div></html>");
                    errore.setForeground(Color.GREEN);
                    System.err.println("errore in fase login");
                }
            }
        });

        // actionListener per attivare bottone confermaCreazioneEvento
        tUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginCompiledCorrectly = (!tUsername.getText().equals("") || tUsername.getText() != null) && tPassword.echoCharIsSet();
                if (loginCompiledCorrectly) {
                    checkTextUsername = true;
                    logIn.setEnabled(true);
                } else {
                    checkTextUsername = false;
                }
            }
        });

        tPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginCompiledCorrectly = (!tUsername.getText().equals("") || tUsername.getText() != null) && tPassword.echoCharIsSet();
                if (!loginCompiledCorrectly) {
                    checkTextPassword = false;
                } else {
                    checkTextPassword = true;
                    logIn.setEnabled(true);

                }
            }
        });

    }

    //parte adibita alla "pittura" della foto sullo sfondo
    public void paintComponent(Graphics g) {
        this.setBackground(new Color(255, 255, 255, 255));
        super.paintComponent(g);
        Dimension dim = this.getSize();
        int width = dim.width;
        int height = dim.height;
        g.drawImage(backgroundImage, 0, 0, width, height, this);

    }
}
