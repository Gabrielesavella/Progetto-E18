package gui.finestre;

import gui.controller.SistemaDiPrenotazione;
import gui.panels.PannelloDisposizioneTavoli;
import locale.*;
import locale.Locale;
import vincoli.*;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lecciovich
 */

public class FinestraDisposizioneTavoli extends JFrame {
    public FinestraDisposizioneTavoli(Locale locale, Evento gestoreEvento, GestoreVincoliTavolo gestoreVincoliTavolo, SistemaDiPrenotazione sisPr){
        setSize(800,700);
        setLocation(300,25);
        PannelloDisposizioneTavoli pd=new PannelloDisposizioneTavoli(locale, gestoreEvento,gestoreVincoliTavolo,this,sisPr);
        Container c=getContentPane();
        c.add(pd);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
