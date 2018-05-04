package gui.controller;

import gui.finestre.FinestraUtente;
import locale.Locale;

import javax.swing.*;
import java.util.ArrayList;


public class SistemaDiPrenotazione {

    public static void main(String[] args) {
        ArrayList<Locale> locali=new ArrayList<Locale>(2);

        locali.add(new Locale(5,10));
        locali.add(new Locale(5,10));


        FinestraUtente frame= new FinestraUtente(locali);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

}
