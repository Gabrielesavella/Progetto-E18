package locale;

import database.ConnessioneDB;
import locale.Locale;
import persone.Invitato;
import vincoli.Vincolo;

import java.util.*;


public class Evento {

    private String nomeEvento;
    private Locale location;
    private ArrayList <Invitato> invitati;
    private ArrayList <Vincolo> lista_vincoli;
    private ArrayList<Locale> locali;
    private GregorianCalendar dataEvento;
    private int numInvitati;
    private ConnessioneDB c;
    private Date dataEv;
    private String nomeLocale;



    /*nell'uml manca la data dell'evento ( l'aggiungo al costruttore )]*/


    public Evento(String nomeEvento, String dataEvento, String nomeLocale, int numInvitati){

        /*Crea un Evento caratterizzato da un nome, una data e un Locale. Al suo interno verranno successivamente inseriti
        una lista di Invitati e di Vincoli*/
        c= new ConnessioneDB();
        c.startConn();
        Locale locale= c.getLocale(nomeLocale);
        c.closeConn();
        this.nomeEvento = nomeEvento;
        this.location=prendiLocale(nomeLocale);
        this.numInvitati=numInvitati;
        this.dataEvento = ricavaData(dataEvento);

        lista_vincoli = new ArrayList();
        this.invitati = new ArrayList(numInvitati);

        location.getEventi().add(this);

    }

    public Evento(String nomeEvento, Date time, int numInvitati) {
        this.nomeEvento = nomeEvento;
        this.dataEv = time;
        this.numInvitati=numInvitati;
    }

    public Evento(String nomeEvento, GregorianCalendar dataEvento, Locale location,
                  ArrayList <Vincolo> lista_vincoli, ArrayList <Invitato> invitati){

        /*il locale è creato dall'evento?? serve una classe nel mezzo che crei le istanze?
        da dove prendo i locali ?? (secondo me sono memorizzati nel Database) quindi dovrò trovare una soluzione temporanea
                */

        this.nomeEvento = nomeEvento;
        this.location = location;
        this.dataEvento = dataEvento;
        this.lista_vincoli.addAll(lista_vincoli);
        invitati = new ArrayList();
        this.invitati.addAll(invitati);
        location.getEventi().add(this);
    }



    public static GregorianCalendar ricavaData(String data){

        GregorianCalendar date = new GregorianCalendar();

        String[] st = data.split("/");

        date.add(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(st[0]));
        date.add(GregorianCalendar.MONTH, Integer.parseInt(st[1]));
        date.add(GregorianCalendar.YEAR, Integer.parseInt(st[2]));

        return date;
    }

    public String getName(){return nomeEvento;}

    public String getNomeLocale(){return nomeLocale;}

    public Locale prendiLocale(String nomeLoc) {
        Locale loca=null;
        for (Locale l : locali) {
            if (nomeLoc == l.getId_locale()) {
                loca = l;
            }
        }
        return loca;
    }

    public static String getStringData(GregorianCalendar dataGreg){
        String dataString= new String("");
        dataString.concat(String.valueOf(dataGreg.get(GregorianCalendar.DATE)));
        return dataString;
    }


    public Locale getLocation(){ return location;}

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public void setLocation(locale.Locale location) {
        this.location = location;
    }

    public void addInvitati(Invitato invitato){
        invitati.add(invitato);
    }


    /*public void setInvitati(ArrayList <persone.Invitato> invitati) {
        this.invitati = invitati;
    }*/

    public int getNumInvitati(){return numInvitati;}

    public ArrayList<Vincolo> getLista_vincoli(){ return lista_vincoli; }

    public GregorianCalendar getDataEvento(){
        return dataEvento;
    }

    public void setLista_vincoli(ArrayList <Vincolo> lista_vincoli) {
        this.lista_vincoli = lista_vincoli;
    }

    public ArrayList<Invitato> getListaInvitati(){return invitati;}

    public void showListaInvitati(){
        for (Invitato singleGuest:invitati) {
            System.out.println(singleGuest.toString());

        }
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = ricavaData(dataEvento);
    }
}