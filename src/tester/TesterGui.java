package tester;

import gui.finestre.FinestraUtente;
import locale.Locale;
import locale.Tavolo;

import javax.swing.*;
import java.util.*;

public class TesterGui {
    public static void main(String[] args) {

        Tavolo tav1 = new Tavolo("tav1",4);
        Tavolo tav2 = new Tavolo("tav2",6);
        Tavolo tav3 = new Tavolo("tav3",8);

        ArrayList<Tavolo> listaTavoli = new ArrayList <Tavolo>();
        listaTavoli.add(tav1);
        listaTavoli.add(tav2);
        listaTavoli.add(tav3);

        String orarioapertura = Integer.toString(9);
        String chiusura = Integer.toString(18);

        String giornoChiusura = Integer.toString(GregorianCalendar.MONDAY);


        Locale daMimmo = new Locale("da Giulio",20, orarioapertura,chiusura, giornoChiusura);
        Locale bellaNapoli = new Locale ("Bella Napoli", 30, orarioapertura,chiusura,giornoChiusura);

        ArrayList<Locale> locali= new ArrayList<>();
        locali.add(daMimmo);
        locali.add(bellaNapoli);

        FinestraUtente frame= new FinestraUtente(locali);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);



    }
}
