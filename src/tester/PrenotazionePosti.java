package tester;

import facade.txtFacade;
import locale.Evento;
import locale.Locale;
import locale.Tavolo;
import persone.Invitato;

import java.io.IOException;
import java.util.ArrayList;
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
    	Tavolo tav1 = new Tavolo("tav1",4);
    	Tavolo tav2 = new Tavolo("tav2",6);
    	Tavolo tav3 = new Tavolo("tav3",8);
        Tavolo tav4 = new Tavolo("tav4",4);
        Tavolo tav5 = new Tavolo("tav5",6);
        Tavolo tav6 = new Tavolo("tav6",8);



    	ArrayList<Tavolo> listaTavoli = new ArrayList <Tavolo>();
    	listaTavoli.add(tav1);
    	listaTavoli.add(tav2);
    	listaTavoli.add(tav3);
        listaTavoli.add(tav4);
        listaTavoli.add(tav5);
        listaTavoli.add(tav6);



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
       // Locale daMimmo = new Locale("da Giulio",20,listaTavoli,orarioapertura, chiusura);
        Locale bellaNapoli = new Locale ("Bella Napoli", 30, listaTavoli, orarioapertura, chiusura);


        //eventi;
        Evento e = new Evento("Matrimonio", orarioEvento,  bellaNapoli, 20);
        Evento k = new Evento("Battesimo", orarioEvento, bellaNapoli, 20);

        //invitati;
        Invitato a = new Invitato("maffo","marco",39);
        Invitato b = new Invitato("luca","marco",39);
        Invitato c = new Invitato("gilbe","marco",39);
        Invitato d = new Invitato("gabri","marco",39);
        Invitato t = new Invitato("lecce","marco",39);
        Invitato f = new Invitato("fudi","marco",39);
        Invitato g = new Invitato("dori","marco",39);
        Invitato h = new Invitato("ischia","marco",39);
        Invitato i = new Invitato("salvo","marco",39);
        Invitato l = new Invitato("fede","marco",39);
        Invitato m = new Invitato("ale","marco",39);
        Invitato n = new Invitato("fox","marco",39);
        Invitato o = new Invitato("volpi","marco",39);
        Invitato p = new Invitato("saro","marco",39);
        Invitato q = new Invitato("slto","marco",39);
        Invitato r = new Invitato("carlo","marco",39);
        Invitato s = new Invitato("gavino","marco",39);
        Invitato u = new Invitato("pops","marco",39);
        Invitato v = new Invitato("paolo","marco",39);
        Invitato z = new Invitato("mauro","marco",39);
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

        //

        bellaNapoli.smistamentoTavoli(e);
        System.out.println("maffo balordo ");
        System.out.println(bellaNapoli.getInvitatiAlTavolo(tav1));


    }

}
