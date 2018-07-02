package tester;

import locale.Evento;
import locale.Locale;
import locale.Tavolo;
import persone.Invitato;
import vincoli.PreferenzaInvitato;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

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
    	Tavolo tav1 = new Tavolo("tav1");
    	tav1.setNum_posti(4);
    	Tavolo tav2 = new Tavolo("tav2");
    	tav2.setNum_posti(6);
    	Tavolo tav3 = new Tavolo("tav3");
    	tav3.setNum_posti(8);
        Tavolo tav4 = new Tavolo("tav4");
        tav4.setNum_posti(4);
        Tavolo tav5 = new Tavolo("tav5");
        tav5.setNum_posti(6);
        Tavolo tav6 = new Tavolo("tav6");
        tav6.setNum_posti(8);



    	ArrayList<Tavolo> listaTavoli = new ArrayList <Tavolo>();
    	listaTavoli.add(tav1);
    	listaTavoli.add(tav2);
    	listaTavoli.add(tav3);
        listaTavoli.add(tav4);
        listaTavoli.add(tav5);
        listaTavoli.add(tav6);



        Date orarioapertura = new Date();
        orarioapertura.setHours(9);
        Date chiusura = new Date();
        chiusura.setHours(18);
        Date orarioEvento = new Date();
        orarioEvento.setHours(12);
        orarioEvento.setMinutes(30);
        Date giornoChiusura = new Date();
        giornoChiusura.setDate(GregorianCalendar.MONDAY);
        /*
        i locali chiudono tutti il lunedì (questa è l'intenzione)
        link per vedere che il giorno di chiusura lunedì ha costante 2
        https://docs.oracle.com/javase/7/docs/api/constant-values.html#java.util.Calendar.MONDAY
         */
        //

       // Locale daMimmo = new Locale("da Giulio",20,listaTavoli,orarioapertura, chiusura);
        Locale bellaNapoli = new Locale ("Bella Napoli", 30, orarioapertura.toString(), chiusura.toString(), giornoChiusura.toString());

        bellaNapoli.aggiungiTavolo(tav1);
        bellaNapoli.aggiungiTavolo(tav2);
        bellaNapoli.aggiungiTavolo(tav3);
        bellaNapoli.aggiungiTavolo(tav4);
        bellaNapoli.aggiungiTavolo(tav5);
        bellaNapoli.aggiungiTavolo(tav6);


        //eventi;
        Evento e = new Evento("Matrimonio", orarioEvento.toString(),  bellaNapoli.getId_locale(), 22);
        Evento k = new Evento("Battesimo", orarioEvento.toString(), bellaNapoli.getId_locale(), 20);

        //invitati;
        Invitato a = new Invitato("Marco","Maffoni",39);
        Invitato b = new Invitato("Gabriele","Savella",39);
        Invitato c = new Invitato("Marco","Lecce",39);
        Invitato d = new Invitato("Federico","Dorigo",39);
        Invitato t = new Invitato("Mario","Rossi",39);
        Invitato f = new Invitato("Salvatore","Parisi",39);
        Invitato g = new Invitato("Maurizio","Costanzo",39);
        Invitato h = new Invitato("Carlo","Conti",39);
        Invitato i = new Invitato("Matteo","Salvini",39);
        Invitato l = new Invitato("Luigi","Di Maio",39);
        Invitato m = new Invitato("Silvio","Berlusconi",39);
        Invitato n = new Invitato("Gonzalo","Higuain",39);
        Invitato o = new Invitato("Belen","Rodriguez",39);
        Invitato p = new Invitato("Maurizio","Sarri",39);
        Invitato q = new Invitato("Antonio","Conte",39);
        Invitato r = new Invitato("Maria","De Filippi",39);
        Invitato s = new Invitato("Francesco","Totti",39);
        Invitato u = new Invitato("Filippo","Inzaghi",39);
        Invitato v = new Invitato("Paolo","Maldini",39);
        Invitato z = new Invitato("Massimiliano","Allegri",39);
        Invitato w = new Invitato("Ilary", "Blasi", 39);
        Invitato y = new Invitato("Enrico", "Mentana", 39);
        Invitato j = new Invitato("Enzo", "Sorrentino", 55);
        Invitato y2 = new Invitato("Gerry", "Scotti", 60);

        //        //inserimento invitati nelle liste evento 1;
        e.addInvitati(a);
        e.addInvitati(b);
        e.addInvitati(c);
        e.addInvitati(d);
        e.addInvitati(f);
        e.addInvitati(g);
        e.addInvitati(h);
        e.addInvitati(i);
        e.addInvitati(l);
        e.addInvitati(m);
        e.addInvitati(n);
        e.addInvitati(o);
        e.addInvitati(p);
        e.addInvitati(q);
        e.addInvitati(r);
        e.addInvitati(s);
        e.addInvitati(t);
        e.addInvitati(u);
        e.addInvitati(v);
        e.addInvitati(z);
        e.addInvitati(w);
        e.addInvitati(j);
        e.addInvitati(y);
        e.addInvitati(y2);
        //evento 2//
        k.addInvitati(a);
        k.addInvitati(b);
        k.addInvitati(c);
        k.addInvitati(d);
        k.addInvitati(f);
        k.addInvitati(g);
        k.addInvitati(h);
        k.addInvitati(i);
        k.addInvitati(l);
        k.addInvitati(m);
        k.addInvitati(n);
        k.addInvitati(o);
        k.addInvitati(p);
        k.addInvitati(q);
        k.addInvitati(r);
        k.addInvitati(s);
        k.addInvitati(t);
        k.addInvitati(u);
        k.addInvitati(v);
        k.addInvitati(z);
         /*
        // stampe per prova;
        System.out.println(bellaNapoli.stampaNomeEventi());
        try {
            txtFacade prova = new txtFacade("primidueinvitati.txt", 2);
            prova.WriteGuests(a.getID_Inv(),a.getNome(),a.getCognome(),a.getEta());
            prova.WriteGuests(b.getID_Inv(),b.getNome(),b.getCognome(),b.getEta());



        }catch(IOException e1){
            System.out.println("Eccezione: " + e1);
        }
        e.showListaInvitati();

        k.showListaInvitati();



        bellaNapoli.smistamentoTavoli(e);
        System.out.println("maffo balordo ");
        System.out.println(bellaNapoli.getInvitatiAlTavolo(tav1));

        */
        // test per la gestione dei vincoli;

        ArrayList<Invitato> listaVincolati  = new ArrayList<>();
        ArrayList<Invitato> listaVincolati2  = new ArrayList<>();
        ArrayList<Invitato> listaVincolati3  = new ArrayList<>();
        ArrayList<Invitato> listaVincolati4  = new ArrayList<>();
        ArrayList<Invitato> listaVincolati5  = new ArrayList<>();

        listaVincolati.add(b);
        listaVincolati.add(c);
        listaVincolati.add(d);
        listaVincolati.add(f);
        listaVincolati.add(p);
        listaVincolati.add(q);

        listaVincolati2.add(g);

        listaVincolati3.add(h);
        listaVincolati3.add(l);
        listaVincolati3.add(m);
        //listaVincolati3.add(n);


        listaVincolati4.add(h);
        listaVincolati4.add(l);
        listaVincolati4.add(z);
        listaVincolati4.add(r);
        //listaVincolati4.add(s);
        listaVincolati4.add(u);
        //listaVincolati4.add(v);
        //listaVincolati4.add(z);
        //listaVincolati4.add(w);

        listaVincolati5.add(g);

        /*
        listaVincolati.add(g);
        listaVincolati.add(h);
        listaVincolati.add(i);
        listaVincolati.add(l);
        listaVincolati.add(m);
        listaVincolati.add(n);
        listaVincolati.add(o);
        */

        //PreferenzaInvitato provaVincolo = new PreferenzaInvitato(a,listaVincolati,e,PreferenzaInvitatoEnum.STA_VICINO_A);
        //PreferenzaInvitato provaVincolo2 = new PreferenzaInvitato(b,listaVincolati2,e,PreferenzaInvitatoEnum.STA_VICINO_A);
        //PreferenzaInvitato provaVincolo3 = new PreferenzaInvitato(i,listaVincolati3,e,PreferenzaInvitatoEnum.NON_STA_VICINO_A);
        //PreferenzaInvitato provaVincolo4 = new PreferenzaInvitato(o,listaVincolati4,e,PreferenzaInvitatoEnum.NON_STA_VICINO_A);
        //PreferenzaInvitato provaVincolo5 = new PreferenzaInvitato(b, listaVincolati5, e, PreferenzaInvitatoEnum.NON_STA_VICINO_A);

        System.out.println("Smisto le persone secondo i vincoli:\n\n");
        System.out.println(e.getLocation().showInvitatiAiTavoli());

        System.out.println("\n\n\n\n\nOra smisto gli invitati rimanenti:\n\n");

        bellaNapoli.smistamentoTavoli(e);
        System.out.println(e.getLocation().showInvitatiAiTavoli());


    }

}
