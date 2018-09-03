package gui.finestre;

import gui.panels.PannelloLogin;
import locale.GestoreLocale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author lecciovich
 */

public class FinestraLogin extends JFrame {
    public FinestraLogin(){
        setSize(800,700);
        setLocation(300,25);
            PannelloLogin p= new PannelloLogin(this);
            Container c=getContentPane();
            c.add(p);
    }

}
