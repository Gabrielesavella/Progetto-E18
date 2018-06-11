package gui.finestre;

import gui.panels.PannelloCredenzialiEvento;
import persone.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class FinestraCreazioneEvento extends JFrame {
    public FinestraCreazioneEvento(ArrayList<locale.Locale> locali, Cliente cliente){
        setSize(500,500);
        PannelloCredenzialiEvento pc=new PannelloCredenzialiEvento(locali,cliente);
        Container c=getContentPane();
        c.add(pc);

    }

}
