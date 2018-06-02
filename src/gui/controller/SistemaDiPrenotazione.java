package gui.controller;

import gui.finestre.FinestraUtente;
import locale.Locale;
import locale.Tavolo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class SistemaDiPrenotazione {

    public static void main(String[] args) {
        ArrayList<Locale> locali=new ArrayList<Locale>(2);

        locali.add(new Locale(5));
        locali.add(new Locale(6));
        Tavolo tav1 = new Tavolo("tav1",4);
        Tavolo tav2 = new Tavolo("tav2",6);
        Tavolo tav3 = new Tavolo("tav3",8);

        ArrayList <Tavolo> listaTavoli = new ArrayList <Tavolo>();
        listaTavoli.add(tav1);
        listaTavoli.add(tav2);
        listaTavoli.add(tav3);

        GregorianCalendar orarioapertura = new GregorianCalendar();
        orarioapertura.add(GregorianCalendar.HOUR,9);
        GregorianCalendar chiusura = new GregorianCalendar();
        chiusura.add(GregorianCalendar.HOUR,18);
        /*
        inizializzazione del giorno di chiusura da fuori tolto, settaggio definito del giorno di chiusura lunedì ( come
        tutti gli esercizi commerciali di tipo ristorante) nei costruttori
         */
        Locale daMimmo = new Locale("da Giulio",20,listaTavoli,orarioapertura, chiusura);

        locali.add(daMimmo);

        FinestraUtente frame= new FinestraUtente(locali);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

}
