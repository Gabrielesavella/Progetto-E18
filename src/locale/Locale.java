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

    static int numLoc = 0;
    private Evento e;
    public String id_locale;
    private int numMaxTavoli;
    private int numMaxPosti;
    private GregorianCalendar oraApertura, oraChiusura, giornodichiusura;
    Calendar calendar;
    private ArrayList<Tavolo> tavoli;
    
    // cambiato il tipo di dato giorno chiusura da String a Gregoria calendar , molto più facile da gestire
    // aggiunta inoltre del passaggio dei tavoli tramite parametro

    public Locale(String id_locale,int numMaxTavoli,ArrayList<Tavolo> tavoli, GregorianCalendar giornoChiusura, GregorianCalendar oraApertura, GregorianCalendar oraChiusura) {
        this.calendar = new GregorianCalendar();
        calendar.setWeekDate(01,01,6);
        this.id_locale=id_locale;
        this.numMaxTavoli=numMaxTavoli;
        this.tavoli = new ArrayList(numMaxTavoli);
        this.tavoli.addAll(tavoli);
        this.giornodichiusura=giornoChiusura;
        this.oraApertura=oraApertura;
        this.oraChiusura=oraChiusura;
    }
    /*
    AGGIUNTA: in questo costruttore ho passato anche i tavoli
     */
    public Locale(int numMaxTavoli,int numMaxPosti,ArrayList<Tavolo> tavoli){
        this.id_locale= "Ristorante"+numLoc++;
        this.numMaxPosti=numMaxPosti;
        this.numMaxTavoli=numMaxTavoli;
        this.tavoli = new ArrayList(numMaxTavoli);
        oraApertura=new GregorianCalendar();
        oraApertura.add(GregorianCalendar.HOUR,9);
        oraApertura.add(GregorianCalendar.MINUTE,0);
        oraChiusura=new GregorianCalendar();
        oraChiusura.add(GregorianCalendar.HOUR,18);
        oraChiusura.add(GregorianCalendar.MINUTE,0);
        //giorno di chiusura lunedì
        this.giornodichiusura.add(GregorianCalendar.DAY_OF_WEEK,2);
    }


    public int getMinTavoli(int num_invitati){
        int num_tavoli = 0;
        int posti_tavolo;
        int contatore_posti = 0;
        for(Tavolo t: tavoli){
            if (contatore_posti <= num_invitati){
                posti_tavolo = t.getNumPosti();
                contatore_posti += posti_tavolo;
                num_tavoli++;
            }
        }
        return num_tavoli;
    }
    
    /*creo un metodo per calcolare il massimo numero di posti 
     * derivato dalla capienza dei tavoli presenti nel locale
     * @author: Gabrielesavella
     */
    
    public int getMaxSeats() {
    	int capienza = 0;
    	for(Tavolo tavolo:tavoli) {
    		capienza += tavolo.getNumPosti();    		
    	}
    	numMaxPosti = capienza;
    	return capienza;
    	
    }
    /*
    creo un getter per il numero max di posti
    che li conta ogni volta tramite il metodo getMaxSeats()
    @author Gabrielesavella
     */
    public int getNumMaxPosti() {
        getMaxSeats();
        return numMaxPosti;
    }
    /*
    controllo che il numero massimo di tavoli sia minore di quello che ho nel locale se è così
    aggiungo un tavolo al locale, di conseguenza calcolo nuovamente il numero massimo di posti @author Gabrielesavella
     */
    public void addTable(Tavolo newTable){
        boolean added = false;
        if(tavoli.size()< numMaxTavoli) {
            added = tavoli.add(newTable);
        }
        //se il tavolo è stato aggiunto aggiungo il numero massimo dei posti
        if(added) {
            numMaxPosti += newTable.getNumPosti();
        }

    }
}
