package gui.panels;

import gui.controller.SistemaDiPrenotazione;
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

public class PannelloLogin extends PaintedPanel {

    private Image backgroundImage;
    private boolean checkTextUsername;
    private boolean checkTextPassword;

    public PannelloLogin(FinestraLogin frame) {
        super("images/cake-chairs-cutlery-395134.jpg",frame);
        checkTextUsername = false;
        checkTextPassword = false;

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
        Font fontB=new Font ("Monotype Corsiva", Font.ROMAN_BASELINE, 23);
        JButton signUp = new JButton("SignUp");
        JButton logIn = new JButton("LogIn");
        //etichette per descrizione campi di testo
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
                    panelHolder[m][n].setBackground(new Color(255, 255, 255, 190));
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


        SistemaDiPrenotazione sisPr = new SistemaDiPrenotazione();
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinestraRegistrazione fr = new FinestraRegistrazione();//locali
                //fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                fr.setVisible(true);

            }
        });

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = sisPr.login(tUsername.getText(), String.valueOf(tPassword.getPassword()));
                if (cliente != null) {
                    FinestraCreazioneEvento fe = new FinestraCreazioneEvento(cliente);//locali,
                    fe.setVisible(true);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                } else {
                    errore.setVisible(true);
                    errore.setOpaque(true);
                    errore.setBackground(Color.BLACK.darker());
                    errore.setHorizontalAlignment(SwingConstants.CENTER);
                    errore.setVerticalAlignment(SwingConstants.CENTER);
                    errore.setHorizontalTextPosition(SwingConstants.CENTER);
                    errore.setVerticalTextPosition(SwingConstants.CENTER);
                    errore.setText("<html><div style='text-align: center;'>" + "Username or password not correct" + "<br>Please fill with correct username and password values " + "</div></html>");
                    errore.setForeground(Color.RED);
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
}
