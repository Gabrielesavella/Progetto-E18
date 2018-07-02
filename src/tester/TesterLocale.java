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
    	Tavolo tav1 = new Tavolo("tav1",4);
    	Tavolo tav2 = new Tavolo("tav2",6);
    	Tavolo tav3 = new Tavolo("tav3",8);
    	
    	ArrayList <Tavolo> listaTavoli = new ArrayList <Tavolo>();
    	listaTavoli.add(tav1);
    	listaTavoli.add(tav2);
    	listaTavoli.add(tav3);

        String orarioapertura = Integer.toString(9);
        String chiusura = Integer.toString(18);
        String orarioEvento = Integer.toString(12);
        String giornoChiusura = Integer.toString(GregorianCalendar.MONDAY);

        Locale bellaNapoli = new Locale ("Bella Napoli", 30, orarioapertura, chiusura, giornoChiusura);


        Locale daGiulio = new Locale("da Giulio",20,orarioapertura,chiusura, giornoChiusura);

        Evento e = new Evento("Matrimonio", orarioEvento,  bellaNapoli.getId_locale(), 40);

        Evento k = new Evento("Battesimo", orarioEvento, bellaNapoli.getId_locale(), 50);


        System.out.println(bellaNapoli.stampaNomeEventi());
    }
    
}
