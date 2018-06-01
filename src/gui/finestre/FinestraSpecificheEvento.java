package gui.finestre;

import gui.panels.PannelloSpecificheEvento;
import locale.Evento;
import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FinestraSpecificheEvento extends JFrame{
    public FinestraSpecificheEvento(ArrayList<Locale> locali, Evento evento){
        setSize(200,200);
        PannelloSpecificheEvento ps=new PannelloSpecificheEvento(locali,evento);
        Container c=getContentPane();
        c.add(ps);
    }

}
