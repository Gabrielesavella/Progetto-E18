package gui.finestre;

import facade.AbstractFacade;
import gui.panels.PannelloLogin;
import gui.panels.PannelloRegistrazione;
import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FinestraRegistrazione extends JFrame {
    public FinestraRegistrazione(ArrayList<Locale> locali, AbstractFacade facade){
        setSize(800,800);
        PannelloRegistrazione p= new PannelloRegistrazione(locali,facade);
        Container c=getContentPane();
        c.add(p);

    }
}
