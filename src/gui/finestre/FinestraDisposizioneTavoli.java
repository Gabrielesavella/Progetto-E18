package gui.finestre;

import gui.panels.PannelloDisposizioneTavoli;
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

public class FinestraDisposizioneTavoli extends JFrame {
    public FinestraDisposizioneTavoli(Locale locale, Evento evento){
        setSize(200,200);
        PannelloDisposizioneTavoli pd=new PannelloDisposizioneTavoli(locale,evento);
        Container c=getContentPane();
        c.add(pd);

    }
}