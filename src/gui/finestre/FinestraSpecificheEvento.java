package gui.finestre;

import gui.panels.PannelloSpecificheEvento;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FinestraSpecificheEvento extends JFrame{
    public FinestraSpecificheEvento(ArrayList<locale.Locale> locali){
        setSize(200,200);
        PannelloSpecificheEvento ps=new PannelloSpecificheEvento(locali);
        Container c=getContentPane();
        c.add(ps);
    }

}
