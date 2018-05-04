package gui.finestre;

import gui.panels.PannelloLogin;
import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FinestraUtente extends JFrame {
    public FinestraUtente(ArrayList<Locale> locali){
        setSize(800,800);
        PannelloLogin p= new PannelloLogin(locali);
        Container c=getContentPane();
        c.add(p);
    }

}
