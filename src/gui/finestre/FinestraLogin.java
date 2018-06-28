package gui.finestre;

import facade.AbstractFacade;
import facade.txtFacade;
import gui.controller.SistemaDiPrenotazioneController;
import gui.panels.PannelloLogin;
import locale.Locale;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author lecciovich
 */

public class FinestraLogin extends JFrame {
    public FinestraLogin(ArrayList<Locale> locali){
        setSize(800,800);
            PannelloLogin p= new PannelloLogin(locali);
            Container c=getContentPane();
            c.add(p);
    }

}
