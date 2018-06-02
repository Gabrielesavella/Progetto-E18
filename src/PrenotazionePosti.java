import locale.*;
import locale.Locale;

import java.util.*;


/**
 *
 * @author salvi
 */
public class PrenotazionePosti {

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

    	GregorianCalendar orarioapertura = new GregorianCalendar();
        orarioapertura.add(GregorianCalendar.HOUR,9);
        GregorianCalendar chiusura = new GregorianCalendar();
        chiusura.add(GregorianCalendar.HOUR,18);
        GregorianCalendar orarioEvento = new GregorianCalendar();
        orarioEvento.add(GregorianCalendar.HOUR, 12);
        orarioEvento.add(GregorianCalendar.MINUTE, 30);
        /*
        i locali chiudono tutti il lunedì (questa è l'intenzione)
        link per vedere che il giorno di chiusura lunedì ha costante 2
        https://docs.oracle.com/javase/7/docs/api/constant-values.html#java.util.Calendar.MONDAY
         */
        //
        chiusura.add(GregorianCalendar.DAY_OF_WEEK,2);
        Locale daMimmo = new Locale("da Giulio",20,listaTavoli,chiusura,orarioapertura, chiusura);
        Locale bellaNapoli = new Locale ("Bella Napoli", 30, listaTavoli, chiusura, orarioapertura, chiusura);

        Evento e = new Evento("Matrimonio", orarioapertura,  bellaNapoli, 40);

        Evento k = new Evento("Battesimo", orarioEvento, bellaNapoli, 50);


        System.out.println(bellaNapoli.stampaNomeEventi());
    }
    
}
