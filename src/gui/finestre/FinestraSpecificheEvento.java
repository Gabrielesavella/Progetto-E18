package gui.finestre;

import gui.controller.SistemaDiPrenotazione;
import gui.panels.PannelloSpecificheEvento;
import locale.*;
import locale.Evento;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lecciovich
 */

public class FinestraSpecificheEvento extends JFrame{
    public FinestraSpecificheEvento(Locale locale, Evento gestoreEvento, SistemaDiPrenotazione sisPr){
        setSize(200,200);
        setLocation(600,200);
        PannelloSpecificheEvento ps=new PannelloSpecificheEvento(locale, gestoreEvento,sisPr,this);
        Container c=getContentPane();
        c.add(ps);
    }

}
