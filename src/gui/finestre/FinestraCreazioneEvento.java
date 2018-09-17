package gui.finestre;

import gui.controller.SistemaDiPrenotazione;
import gui.panels.PannelloCredenzialiEvento;
import persone.Cliente;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lecciovich
 */

public class FinestraCreazioneEvento extends JFrame {
    public FinestraCreazioneEvento(Cliente cliente, SistemaDiPrenotazione sisPr){//ArrayList<Locale> locali,
        setSize(500,500);
        setLocation(425,25);
        PannelloCredenzialiEvento pc=new PannelloCredenzialiEvento(cliente,sisPr,this);//locali,
        Container c=getContentPane();
        c.add(pc);

    }

}
