package tester;

import database.ConnessioneDB;
import database.DatabaseException;
import database.DatabaseNullException;
import facade.Facade;
import locale.*;
import locale.Evento;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import persone.Invitato;
import vincoli.CreatePreferenza;
import vincoli.GestoreVincoliTavolo;
//import vincoli.GestorePreferenzaInvitato;
//import vincoli.PreferenzaInvitatoEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class JTest {
    private Evento e;
    private ArrayList<Tavolo> Tavoli = new ArrayList<Tavolo>();
    ConnessioneDB connetto = new ConnessioneDB();
    Locale locale=null;
    ArrayList<Tavolo> tavoli_finali;

    @Before
    public void testVincoli(){

        try {
            Facade.getInstance().inserisciDatiEvento("BruWay39","TesterJUnit", "13/02/2018", "Fortezza della Solitudine", 5);

            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "marmaf23", "marco", "maffoni", 23);
            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "marlec23", "marco", "lecce", 23);
            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "gabsav24", "gabriele", "savella", 24);
            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "salpar31", "salvatore", "parisi", 31);
            Facade.getInstance().inserisciDatiInvitato("TesterJUnit", "feddor23", "federico", "dorigo", 23);

            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "marmaf23", 0, 0, 0, 0, 0, 0);
            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "marlec23", 0, 0, 0, 0, 0, 0);
            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "gabsav24", 0, 0, 0, 0, 0, 0);
            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "salpar31", 0, 0, 0, 0, 0, 0);
            Facade.getInstance().inserisciVincoliTavolo("TesterJUnit", "feddor23", 0, 0, 0, 0, 0, 0);

            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "marmaf23", "marlec23", "gabsav24");
            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "marlec23", " ", " ");
            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "gabsav24", " ", " ");
            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "salpar31", " ", " ");
            Facade.getInstance().inserisciVincoloInvitati("TesterJUnit", "feddor23", "salpar31", "gabsav24");

            locale = Facade.getInstance().getLocale("Fortezza della Solitudine");
            locale.getTavoliLocale().addAll(Facade.getInstance().getTavoli("Fortezza della Solitudine"));

        } catch (DatabaseException e1) {
            e1.printStackTrace();
        } catch (DatabaseNullException e1) {
            e1.printStackTrace();
        }

        //mostra tavoli e invitati vincolati
        GestoreVincoliTavolo gestoreTavolo = new GestoreVincoliTavolo("TesterJUnit", locale);

        ArrayList<Tavolo> tavoli = gestoreTavolo.getTavoliTotali();

        //mostra tavoli e invitati non vincolati
        CreatePreferenza create = new CreatePreferenza("TesterJUnit", tavoli);

        create.smista();

        tavoli_finali = create.getTavoliUtilizzati();

    }

    @Before
    public  void  creazionetavoli(){
        Tavolo tav1 = new Tavolo("bellaNapoli","tav1",6);
        Tavolo tav2 = new Tavolo("bellaNapoli","tav2",4);
        Tavolo tav3 = new Tavolo("bellaNapoli","tav3",8);
        Tavolo tav4 = new Tavolo("bellaNapoli","tav4",5);
        Tavolo tav5 = new Tavolo("bellaNapoli","tav5",6);
        Tavolo tav6 = new Tavolo("bellaNapoli","tav6",7);

        Tavoli.add(tav1); //6
        Tavoli.add(tav2);//4
        Tavoli.add(tav3);//8
        Tavoli.add(tav4);//5
        Tavoli.add(tav5);//6
        Tavoli.add(tav6);//7
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
        GregorianCalendar giornoChiusura= new GregorianCalendar();
        giornoChiusura.add(GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
        // Locale daMimmo = new Locale("da Giulio",20,listaTavoli,orarioapertura, chiusura);
        Locale bellaNapoli = new Locale("bellaNapoli", 30, orarioapertura, chiusura, giornoChiusura);
        bellaNapoli.aggiungiTavoli(Tavoli);
        e = new Evento("Matrimonio", orarioEvento,  bellaNapoli, 50);
        bellaNapoli.getEventi().add(e);
    }
    @Before
    public void SettaInvitati(){

        //invitati;
        Invitato a = new Invitato("MarMaf01","Marco","Maffoni",39);
        Invitato b = new Invitato("GabSav01","Gabriele","Savella",39);
        Invitato c = new Invitato("MarLec01", "Marco","Lecce",39);
        Invitato d = new Invitato("FedDor01", "Federico","Dorigo",39);
        Invitato t = new Invitato("MarRos01","Mario","Rossi",39);
        Invitato f = new Invitato("SalPar01","Salvatore","Parisi",39);
        Invitato g = new Invitato("MauCost01", "Maurizio","Costanzo",39);
        Invitato h = new Invitato("CarCon01", "Carlo","Conti",39);
        Invitato i = new Invitato("MatSal01","Matteo","Salvini",39);
        Invitato l = new Invitato("LuiDiM01", "Luigi","Di Maio",39);
        Invitato m = new Invitato("SilBer01", "Silvio","Berlusconi",39);
        Invitato n = new Invitato("GonHig01", "Gonzalo","Higuain",39);
        Invitato o = new Invitato("BelRod01", "Belen","Rodriguez",39);
        Invitato p = new Invitato("MauSar01", "Maurizio","Sarri",39);
        Invitato q = new Invitato("AntCon01","Antonio","Conte",39);
        Invitato r = new Invitato("MarDeF01","Maria","De Filippi",39);
        Invitato s = new Invitato("FraTot01","Francesco","Totti",39);
        Invitato u = new Invitato("FilInz01","Filippo","Inzaghi",39);
        Invitato v = new Invitato("PaoMald01","Paolo","Maldini",39);
        Invitato z = new Invitato("MasAll01", "Massimiliano","Allegri",39);
        Invitato w = new Invitato("IlaBla01","Ilary", "Blasi", 39);
        Invitato y = new Invitato("EnrMen01","Enrico", "Mentana", 39);
        Invitato j = new Invitato("EnzSor01","Enzo", "Sorrentino", 55);
        Invitato y2 = new Invitato("GerSco01","Gerry", "Scotti", 60);
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
        e.addInvitati(p);
        e.addInvitati(q);
        e.addInvitati(r);
        e.addInvitati(t);
        e.addInvitati(w);
        e.addInvitati(j);
        e.addInvitati(y);
    }



    @Test
    public void Provatavoli() {

        Assert.assertEquals(6,e.getLocation().getNPostiTavolo("tav1"));
        Assert.assertEquals(4,e.getLocation().getNPostiTavolo("tav2"));
        Assert.assertEquals(8,e.getLocation().getNPostiTavolo("tav3"));
        Assert.assertEquals(5,e.getLocation().getNPostiTavolo("tav4"));
        Assert.assertEquals(6,e.getLocation().getNPostiTavolo("tav5"));
        Assert.assertEquals(7,e.getLocation().getNPostiTavolo("tav6"));

    }

    @Test
    public void ProvaTavoliPieni(){
        e.getLocation().smistamentoTavoli(e);

        Assert.assertEquals(6, Tavoli.get(0).getArraylistInvitati().size());
        Assert.assertEquals(4, Tavoli.get(1).getArraylistInvitati().size());
        Assert.assertEquals(8, Tavoli.get(2).getArraylistInvitati().size());
        Assert.assertEquals(1, Tavoli.get(3).getArraylistInvitati().size());
        Assert.assertEquals(0, Tavoli.get(4).getArraylistInvitati().size());
        Assert.assertEquals(0, Tavoli.get(5).getArraylistInvitati().size());

    }

    @Test
    public void ProvaPreferenzaVicini(){
        Assert.assertEquals(4, Tavoli.get(0).getArraylistInvitati().size());
        Assert.assertEquals(1, Tavoli.get(1).getArraylistInvitati().size());
        Assert.assertEquals(1, Tavoli.get(2).getArraylistInvitati().size());
    }

    @Test
    public void ProvaDB(){
        connetto.startConn();
        Assert.assertEquals(true,connetto.checkConn());
        connetto.closeConn();
        Assert.assertEquals(false,connetto.checkConn());
    }

    @Test
    public void ProvaVincoli(){
        Assert.assertEquals(2, tavoli_finali.get(0).getArraylistInvitati().size());
        Assert.assertEquals(2, tavoli_finali.get(1).getArraylistInvitati().size());
        Assert.assertEquals(1, tavoli_finali.get(2).getArraylistInvitati().size());
    }

    @After
    public void deleteDatabase(){
        Facade.getInstance().removeVincoliOnly(Facade.getInstance().getEvento("TesterJUnit"));
        Facade.getInstance().deleteEvento("TesterJUnit");
    }

}
