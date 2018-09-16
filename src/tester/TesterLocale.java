package tester;

import locale.*;
import locale.Locale;

import java.util.*;


/**
 *
 * @author salvi
 */
public class TesterLocale {

    /**
     * creazione dei locali che ossono essere utilizzati dall'utente
     * @author Gabrielesavella
     */
    public static void main(String[] args) {
    	Tavolo tav1 = new Tavolo("bellaNapoli","tav1",4);
    	Tavolo tav2 = new Tavolo("bellaNapoli","tav2",6);
    	Tavolo tav3 = new Tavolo("bellaNapoli","tav3",8);
    	
    	ArrayList <Tavolo> listaTavoli = new ArrayList <Tavolo>();
    	listaTavoli.add(tav1);
    	listaTavoli.add(tav2);
    	listaTavoli.add(tav3);

    	GregorianCalendar orarioapertura = new GregorianCalendar();
        orarioapertura.add(GregorianCalendar.HOUR,9);
        GregorianCalendar chiusura = new GregorianCalendar();
        chiusura.add(GregorianCalendar.HOUR,18);
        GregorianCalendar orarioEvento = new GregorianCalendar();
        orarioEvento.add(GregorianCalendar.HOUR, 12);
        orarioEvento.add(GregorianCalendar.MINUTE, 30);
        GregorianCalendar giornoChiusura= new GregorianCalendar();
        giornoChiusura.add(GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
        /*
        i locali chiudono tutti il lunedì (questa è l'intenzione)
        link per vedere che il giorno di chiusura lunedì ha costante 2
        https://docs.oracle.com/javase/7/docs/api/constant-values.html#java.util.Calendar.MONDAY
         */
        //
        //chiusura.add(GregorianCalendar.DAY_OF_WEEK,2);
        Locale daMimmo = new Locale("da Giulio",20,orarioapertura, chiusura, giornoChiusura);
        Locale bellaNapoli = new Locale("Bella Napoli", 30, orarioapertura, chiusura, giornoChiusura);
        daMimmo.aggiungiTavoli(listaTavoli);
        bellaNapoli.aggiungiTavoli(listaTavoli);

        Evento e = new Evento("Matrimonio", orarioEvento,  bellaNapoli, 40);

        Evento k = new Evento("Battesimo", orarioEvento, bellaNapoli, 50);


    }
    
}
