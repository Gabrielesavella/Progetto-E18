package gui.finestre;

import gui.panels.PannelloRegistrazione;
import locale.GestoreLocale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author lecciovich
 */

public class FinestraRegistrazione extends JFrame {
    public FinestraRegistrazione(){//ArrayList<GestoreLocale> locali
        setSize(800,700);
        setLocation(300,25);
        PannelloRegistrazione p= new PannelloRegistrazione(this);//locali,
        Container c=getContentPane();
        c.add(p);

    }
}
