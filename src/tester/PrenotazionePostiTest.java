package tester;

import locale.Evento;
import locale.Locale;
import locale.Tavolo;
import persone.Invitato;
import vincoli.PreferenzaInvitato;
import vincoli.PreferenzaInvitatoEnum;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.*;
import org.junit.Test;


public class PrenotazionePostiTest {
    private Evento e;
    private ArrayList<Tavolo> Tavoli = new ArrayList<Tavolo>();

    @Before
    public  void  Gabri (){
        Tavolo a = new Tavolo("tav1",6);
        Tavolo b = new Tavolo("tav2",4);
        Tavolo x = new Tavolo("tav3",8);
        Tavolo d = new Tavolo("tav4",5);
        Tavolo e = new Tavolo("tav5",6);
        Tavolo f = new Tavolo("tav6",7);

        Tavoli.add(a); //6
        Tavoli.add(b);//4
        Tavoli.add(x);//8
        Tavoli.add(d);//5
        Tavoli.add(e);//6
        Tavoli.add(f);//7
      //  System.out.println(Tavoli);

    }
    @Before
    public void  SettaEvento(){
        // settaggio parametri evento
        GregorianCalendar orarioapertura = new GregorianCalendar();
        orarioapertura.add(GregorianCalendar.HOUR,9);
        GregorianCalendar chiusura = new GregorianCalendar();
        chiusura.add(GregorianCalendar.HOUR,18);
        GregorianCalendar orarioEvento = new GregorianCalendar();
        orarioEvento.add(GregorianCalendar.HOUR, 12);
        orarioEvento.add(GregorianCalendar.MINUTE, 30);
        chiusura.add(GregorianCalendar.DAY_OF_WEEK,2);
        // Locale daMimmo = new Locale("da Giulio",20,listaTavoli,orarioapertura, chiusura);
        Locale bellaNapoli = new Locale ("Bella Napoli", 30, Tavoli, orarioapertura, chiusura);
        e = new Evento("Matrimonio", orarioEvento,  bellaNapoli, 50);
    }
    @Before
    public void SettaInvitati(){

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
        Invitato w = new Invitato("francesco", "turri", 39);
        //        //inserimento invitati nelle liste evento 1;
        SettaEvento();
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
        e.addInvitati(p );
        e.addInvitati(q);
        e.addInvitati(r);




        ArrayList<Invitato> listaVincolati  = new ArrayList<Invitato>();
        ArrayList<Invitato> listaVincolati2  = new ArrayList<Invitato>();
        ArrayList<Invitato> listaVincolati3  = new ArrayList<Invitato>();
        ArrayList<Invitato> listaVincolati4  = new ArrayList<Invitato>();

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
        listaVincolati3.add(n);


        listaVincolati4.add(h);
        listaVincolati4.add(l);
        listaVincolati4.add(z);
        listaVincolati4.add(r);
        listaVincolati4.add(s);
        listaVincolati4.add(u);
        listaVincolati4.add(v);

        PreferenzaInvitato provaVincolo = new PreferenzaInvitato(a,listaVincolati,e,PreferenzaInvitatoEnum.STA_VICINO_A);
        PreferenzaInvitato provaVincolo2 = new PreferenzaInvitato(b,listaVincolati2,e,PreferenzaInvitatoEnum.STA_VICINO_A);
        PreferenzaInvitato provaVincolo3 = new PreferenzaInvitato(i,listaVincolati3,e,PreferenzaInvitatoEnum.NON_STA_VICINO_A);
        PreferenzaInvitato provaVincolo4 = new PreferenzaInvitato(o,listaVincolati4,e,PreferenzaInvitatoEnum.NON_STA_VICINO_A);
        //System.out.println(e.getName());
        //System.out.println( e.getLocation().smistamentoTavoli(e));


    }



    @Test
    public void Provatavoli1() {

        Assert.assertEquals(6,e.getLocation().getNPostiTavolo("tav1"));
        Assert.assertEquals(4,e.getLocation().getNPostiTavolo("tav2"));
        Assert.assertEquals(8,e.getLocation().getNPostiTavolo("tav3"));
        Assert.assertEquals(5,e.getLocation().getNPostiTavolo("tav4"));
        Assert.assertEquals(6,e.getLocation().getNPostiTavolo("tav5"));
        Assert.assertEquals(7,e.getLocation().getNPostiTavolo("tav6"));

    }



}