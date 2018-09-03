package gui.finestre;

import gui.panels.PannelloCredenzialiEvento;
import locale.GestoreLocale;
import persone.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author lecciovich
 */

public class FinestraCreazioneEvento extends JFrame {
    public FinestraCreazioneEvento(Cliente cliente){//ArrayList<GestoreLocale> locali,
        setSize(500,500);
        setLocation(425,25);
        PannelloCredenzialiEvento pc=new PannelloCredenzialiEvento(cliente,this);//locali,
        Container c=getContentPane();
        c.add(pc);

    }

}
