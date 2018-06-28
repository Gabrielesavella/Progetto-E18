package tester;

import facade.XlsFacade;
import facade.txtFacade;
import locale.Evento;
import locale.Locale;
import locale.Tavolo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import persone.Cliente;
import persone.Invitato;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author salvi
 */
public class PrenotazionePosti {

    /**
     * creazione dei locali che ossono essere utilizzati dall'utente
     * @author Gabrielesavella
     */

    private static String[] columns = {"Surname", "Name", "FC", "Age"};
    private static List<Invitato> employees =  new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Tavolo tav1 = new Tavolo("tav1", 4);
        Tavolo tav2 = new Tavolo("tav2", 6);
        Tavolo tav3 = new Tavolo("tav3", 8);
        Tavolo tav4 = new Tavolo("tav4", 4);
        Tavolo tav5 = new Tavolo("tav5", 6);
        Tavolo tav6 = new Tavolo("tav6", 8);


        ArrayList<Tavolo> listaTavoli = new ArrayList<Tavolo>();
        listaTavoli.add(tav1);
        listaTavoli.add(tav2);
        listaTavoli.add(tav3);
        listaTavoli.add(tav4);
        listaTavoli.add(tav5);
        listaTavoli.add(tav6);


        GregorianCalendar orarioapertura = new GregorianCalendar();
        orarioapertura.add(GregorianCalendar.HOUR, 9);
        GregorianCalendar chiusura = new GregorianCalendar();
        chiusura.add(GregorianCalendar.HOUR, 18);
        GregorianCalendar orarioEvento = new GregorianCalendar();
        orarioEvento.add(GregorianCalendar.HOUR, 12);
        orarioEvento.add(GregorianCalendar.MINUTE, 30);
        /*
        i locali chiudono tutti il lunedì (questa è l'intenzione)
        link per vedere che il giorno di chiusura lunedì ha costante 2
        https://docs.oracle.com/javase/7/docs/api/constant-values.html#java.util.Calendar.MONDAY
         */
        //
        chiusura.add(GregorianCalendar.DAY_OF_WEEK, 2);
        // Locale daMimmo = new Locale("da Giulio",20,listaTavoli,orarioapertura, chiusura);
        Locale bellaNapoli = new Locale("Bella Napoli", 30, listaTavoli, orarioapertura, chiusura);


        //eventi;
        Evento e = new Evento("Matrimonio", orarioEvento, bellaNapoli, 20);
        Evento k = new Evento("Battesimo", orarioEvento, bellaNapoli, 20);

        //invitati;
        Invitato a = new Invitato( "marco", "maffoni", 39);//"mfnmrc95",
        Invitato b = new Invitato( "luca", "cavo", 39);//"cvlc97",
        Invitato c = new Invitato( "gilbe", "marco", 39);//"3",
        Invitato d = new Invitato("gabri", "marco", 39);//"4",
        Invitato t = new Invitato("lecce", "marco", 39);//"5",
        Invitato f = new Invitato("fudi", "marco", 39);//"6",
        Invitato g = new Invitato("dori", "marco", 39);//"7",
        Invitato h = new Invitato("ischia", "marco", 39);//"8",
        Invitato i = new Invitato("salvo", "marco", 39);//"9",
        Invitato l = new Invitato("fede", "marco", 39);//"10",
        Invitato m = new Invitato("ale", "marco", 39);//"11",
        Invitato n = new Invitato("fox", "marco", 39);//"12",
        Invitato o = new Invitato("volpi", "marco", 39);//"13",
        Invitato p = new Invitato("saro", "marco", 39);//"14",
        Invitato q = new Invitato("slto", "marco", 39);//"15",
        Invitato r = new Invitato("carlo", "marco", 39);//"16",
        Invitato s = new Invitato("gavino", "marco", 39);//"17",
        Invitato u = new Invitato("pops", "marco", 39);//"18",
        Invitato v = new Invitato("paolo", "marco", 39);//"19",
        Invitato z = new Invitato("mauro", "marco", 39);//"20",
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
            txtFacade prova = new txtFacade(2);
            prova.WriteGuests(a.getCf(), a.getNome(), a.getCognome(), a.getEtà());
            prova.WriteGuests(b.getCf(), b.getNome(), b.getCognome(), b.getEtà());
            prova.WriteGuests(c.getCf(), c.getNome(), c.getCognome(), c.getEtà());
            prova.WriteClient("Myusername", "mypassword", "luca", "usb", "mygmail@gmail.com");
            prova.WriteEvent(e.getName(), e.getDataEvento(), e.getNumInvitati());
            prova.WriteEvent(k.getName(), k.getDataEvento(), k.getNumInvitati());
            Cliente clienteregistrato = prova.fetchClient("Myusername", "mypassword");
            Evento eventoToFind = prova.fetchEvento(k.getName());

            System.out.println("cliente registrato: " + clienteregistrato.getUsername() + "  " + clienteregistrato.getCognome() + "  " + clienteregistrato.getNome());
            System.out.println(eventoToFind.getName());


        } catch (IOException e1) {
            System.out.println("Eccezione: " + e1);
        }
        e.showListaInvitati();

        k.showListaInvitati();

        //

        bellaNapoli.smistamentoTavoli(e);
        ArrayList<Invitato> invitatiTotali = new ArrayList<>();

        invitatiTotali = bellaNapoli.getInvitatiOgniTavolo();

        for (Invitato invitati:invitatiTotali) {
            System.out.println(invitati.toString());

        }




        XlsFacade excel = new XlsFacade();
        //excel.generateXlsGuests(e.getName());

        excel.readXlsGuests(e.getName());
        excel.openfile(e.getName()+".xls");
    }

}
