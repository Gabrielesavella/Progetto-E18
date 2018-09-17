package gui.finestre;

import gui.controller.SistemaDiPrenotazione;
import gui.panels.PannelloRegistrazione;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lecciovich
 */

public class FinestraRegistrazione extends JFrame {
    public FinestraRegistrazione(SistemaDiPrenotazione sisPr){
        setSize(800,700);
        setLocation(300,25);
        PannelloRegistrazione p= new PannelloRegistrazione(sisPr,this);
        Container c=getContentPane();
        c.add(p);

    }
}
