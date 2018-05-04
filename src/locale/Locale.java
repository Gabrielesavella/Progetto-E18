package locale;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author salvi
 */
public class Locale {

    static int numLoc=0;
    private Evento e;
    public String id_locale;
    private int numMaxTavoli;
    private int numMaxPosti;
    private String giornodichiusura;
    private GregorianCalendar oraApertura, oraChiusura;
    Calendar calendar;
    private ArrayList<Tavolo> tavoli;

    public Locale(String id_locale,int numMaxTavoli, String giornoChiusura, GregorianCalendar oraApertura, GregorianCalendar oraChiusura) {
        this.calendar = new GregorianCalendar();
        calendar.setWeekDate(01,01,1980);
        this.id_locale=id_locale;
        this.numMaxTavoli=numMaxTavoli;
        this.giornodichiusura=giornoChiusura;
        this.oraApertura=oraApertura;
        this.oraChiusura=oraChiusura;
    }

    public Locale(int numMaxTavoli,int numMaxPosti){
        this.id_locale= "Ristorante"+numLoc++;
        this.numMaxPosti=numMaxPosti;
        this.numMaxTavoli=numMaxTavoli;
        oraApertura=new GregorianCalendar();
        oraApertura.add(GregorianCalendar.HOUR,9);
        oraApertura.add(GregorianCalendar.MINUTE,0);
        oraChiusura=new GregorianCalendar();
        oraChiusura.add(GregorianCalendar.HOUR,18);
        oraChiusura.add(GregorianCalendar.MINUTE,0);
    }


    public int getMinTavoli(int num_invitati){
        int num_tavoli = 0;
        int posti_tavolo;
        int contatore_posti = 0;
        for(Tavolo t: tavoli){
            if (contatore_posti < num_invitati){
                posti_tavolo = t.getNumPosti();
                contatore_posti += posti_tavolo;
                num_tavoli++;
            }
        }
        return num_tavoli;
    }

}
