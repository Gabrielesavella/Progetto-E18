package tester;

import database.*;
import gui.finestre.FinestraLogin;
import locale.GestoreLocale;
import locale.Tavolo;
import sun.util.calendar.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TesterGui {
    public static void main(String[] args) throws DatabaseNullException, DatabaseException {

        ConnessioneDB connessione = new ConnessioneDB();
        String chiusuraStr = new String();
        String orarioEventoStr = new String();
        String giornoChiusuraStr= new String();



        Tavolo tav1 = new Tavolo("da Giulio","tav1",4);
        Tavolo tav2 = new Tavolo("da Giulio","tav2", 6);
        Tavolo tav3 = new Tavolo("da Giulio","tav3",8);

        connessione.inserisciTavoli("da Giulio","tav1",4);
        connessione.inserisciTavoli("da Giulio","tav2", 6);
        connessione.inserisciTavoli("da Giulio","tav3",8);

        ArrayList<Tavolo> listaTavoli = new ArrayList <Tavolo>();
        listaTavoli.add(tav1);
        listaTavoli.add(tav2);
        listaTavoli.add(tav3);


        GregorianCalendar orarioapertura = new GregorianCalendar();
        orarioapertura.add(GregorianCalendar.HOUR,9);
        GregorianCalendar chiusura = new GregorianCalendar();
        chiusura.add(GregorianCalendar.HOUR,23);
        chiusura.add(GregorianCalendar.MINUTE,30);
        chiusuraStr=""+chiusura.get(Calendar.HOUR)+":"+chiusura.get(GregorianCalendar.MINUTE);
        GregorianCalendar orarioEvento = new GregorianCalendar();
        orarioEvento.add(GregorianCalendar.HOUR, 12);
        orarioEvento.add(GregorianCalendar.MINUTE, 30);
        orarioEventoStr=""+orarioEvento.get(Calendar.HOUR)+":"+orarioEvento.get(GregorianCalendar.MINUTE);
        GregorianCalendar giornoChiusura= new GregorianCalendar();
        giornoChiusura.add(GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
        giornoChiusuraStr= "MONDAY";



        chiusura.add(GregorianCalendar.DAY_OF_WEEK,2);
        GestoreLocale daGiulio = new GestoreLocale("da Giulio",20,orarioapertura, chiusura, giornoChiusura);
        connessione.inserisciDatiLocale("da Giulio",20,"9:00",chiusuraStr,giornoChiusuraStr);
        GestoreLocale bellaNapoli = new GestoreLocale("Bella Napoli", 30, orarioapertura, chiusura, giornoChiusura);
        //daGiulio.aggiungiTavoli(listaTavoli);
        daGiulio.addTable(tav1);
        daGiulio.addTable(tav2);
        daGiulio.addTable(tav3);

        bellaNapoli.aggiungiTavoli(listaTavoli);

        ArrayList<GestoreLocale> locali= new ArrayList<>();
        locali = connessione.getLocali();
        //locali.add(daGiulio);
       // locali.add(bellaNapoli);

        //parte grafica
        FinestraLogin frame= new FinestraLogin(locali);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);



    }
}
