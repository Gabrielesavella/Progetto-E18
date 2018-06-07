package gui.finestre;

import facade.AbstractFacade;
import facade.txtFacade;
import gui.panels.PannelloLogin;
import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class FinestraUtente extends JFrame {
    public FinestraUtente(ArrayList<Locale> locali){
        setSize(800,800);
        try {
            AbstractFacade facade= new txtFacade("regitroUtenti.txt",1);
            PannelloLogin p= new PannelloLogin(locali,facade);
            Container c=getContentPane();
            c.add(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
