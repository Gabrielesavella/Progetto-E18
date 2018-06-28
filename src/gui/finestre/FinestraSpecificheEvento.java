package gui.finestre;

import gui.panels.PannelloSpecificheEvento;
import locale.Evento;
import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author lecciovich
 */

public class FinestraSpecificheEvento extends JFrame{
    public FinestraSpecificheEvento(Locale locale, Evento evento){
        setSize(200,200);
        PannelloSpecificheEvento ps=new PannelloSpecificheEvento(locale,evento);
        Container c=getContentPane();
        c.add(ps);
    }

}
