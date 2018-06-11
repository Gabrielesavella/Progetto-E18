package tester;

import facade.txtFacade;
import locale.Evento;
import locale.Locale;
import locale.Tavolo;
import persone.Cliente;
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
        Invitato a = new Invitato("mfnmrc95","marco","maffoni",39);
        Invitato b = new Invitato("cvlc97","luca","cavo",39);
        Invitato c = new Invitato("3","gilbe","marco",39);
        Invitato d = new Invitato("4","gabri","marco",39);
        Invitato t = new Invitato("5","lecce","marco",39);
        Invitato f = new Invitato("6","fudi","marco",39);
        Invitato g = new Invitato("7","dori","marco",39);
        Invitato h = new Invitato("8","ischia","marco",39);
        Invitato i = new Invitato("9","salvo","marco",39);
        Invitato l = new Invitato("10","fede","marco",39);
        Invitato m = new Invitato("11","ale","marco",39);
        Invitato n = new Invitato("12","fox","marco",39);
        Invitato o = new Invitato("13","volpi","marco",39);
        Invitato p = new Invitato("14","saro","marco",39);
        Invitato q = new Invitato("15","slto","marco",39);
        Invitato r = new Invitato("16","carlo","marco",39);
        Invitato s = new Invitato("17","gavino","marco",39);
        Invitato u = new Invitato("18","pops","marco",39);
        Invitato v = new Invitato("19","paolo","marco",39);
        Invitato z = new Invitato("20","mauro","marco",39);
        //        //inserimento invitati nelle liste evento 1;
        e.addInvitato(a);
        e.addInvitato(b);
        e.addInvitato(c);
        e.addInvitato(d);
        e.addInvitato(f);
        e.addInvitato(g);
        e.addInvitato(h);
        e.addInvitato(i);
        e.addInvitato(l);
        e.addInvitato(m);
        e.addInvitato(n);
        e.addInvitato(o);
        e.addInvitato(p);
        e.addInvitato(q);
        e.addInvitato(r);
        e.addInvitato(s);
        e.addInvitato(t);
        e.addInvitato(u);
        e.addInvitato(v);
        e.addInvitato(z);
        //evento 2//
        k.addInvitato(a);
        k.addInvitato(b);
        k.addInvitato(c);
        k.addInvitato(d);
        k.addInvitato(f);
        k.addInvitato(g);
        k.addInvitato(h);
        k.addInvitato(i);
        k.addInvitato(l);
        k.addInvitato(m);
        k.addInvitato(n);
        k.addInvitato(o);
        k.addInvitato(p);
        k.addInvitato(q);
        k.addInvitato(r);
        k.addInvitato(s);
        k.addInvitato(t);
        k.addInvitato(u);
        k.addInvitato(v);
        k.addInvitato(z);
        // stampe per prova;
        System.out.println(bellaNapoli.stampaNomeEventi());
        try {
            txtFacade prova = new txtFacade( 2);
            prova.WriteGuests(a.getCf(),a.getNome(),a.getCognome(),a.getEtà());
            prova.WriteGuests(b.getCf(),b.getNome(),b.getCognome(),b.getEtà());
            prova.WriteGuests(c.getCf(),c.getNome(),c.getCognome(),c.getEtà());
            prova.WriteClient("Myusername","mypassword","luca","usb","mygmail@gmail.com");
            prova.WriteEvent(e.getName(),e.getDataEvento(),e.getNumInvitati());
           // prova.WriteEvent(e.getName(),orarioEvento,20);
            Cliente clienteregistrato = prova.fetchClient("Myusername","mypassword");
            System.out.println("cliente registrato: "+clienteregistrato.getUsername()+"  "+clienteregistrato.getCognome()+"  "+clienteregistrato.getNome());



        }catch(IOException e1){
            System.out.println("Eccezione: " + e1);
        }
        //e.showListaInvitati();

        //k.showListaInvitati();

        //

        //bellaNapoli.smistamentoTavoli(e);

        //System.out.println(bellaNapoli.getInvitatiAlTavolo(tav1));


    }

}
