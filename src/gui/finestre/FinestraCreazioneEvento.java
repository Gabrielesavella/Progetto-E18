package gui.finestre;

import gui.panels.PannelloCredenzialiEvento;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class FinestraCreazioneEvento extends JFrame {
    public FinestraCreazioneEvento(ArrayList<locale.Locale> locali){
        setSize(500,500);
        PannelloCredenzialiEvento pc=new PannelloCredenzialiEvento(locali);
        Container c=getContentPane();
        c.add(pc);

    }

}
