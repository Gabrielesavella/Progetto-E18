package locale;

import locale.Locale;
import persone.Invitato;
import vincoli.Vincolo;

import java.util.*;


public class Evento {
    private String nomeEvento,nomeLocation;

    private Locale location = null;
    private ArrayList <Invitato> invitati;
    private  ArrayList <Vincolo> lista_vincoli;
    private GregorianCalendar dataEvento;
    private int numInvitati;



    /*nell'uml manca la data dell'evento ( l'aggiungo al costruttore )]*/

    public Evento(String nomeEvento, GregorianCalendar dataEvento, Locale location, int numInvitati){

        /*Crea un Evento caratterizzato da un nome, una data e un Locale. Al suo interno verranno successivamente inseriti
        una lista di Invitati e di Vincoli*/


        this.nomeEvento = nomeEvento;
        this.location = location;
        this.dataEvento = dataEvento;
        lista_vincoli = new ArrayList();
        this.invitati = new ArrayList(numInvitati);
        this.numInvitati=numInvitati;

    }

    public Evento(String nomeEvento, GregorianCalendar dataEvento, String nomeLocation, int numInvitati){

        /*Crea un Evento caratterizzato da un nome, una data e un Locale. Al suo interno verranno successivamente inseriti
        una lista di Invitati e di Vincoli*/


        this.nomeEvento = nomeEvento;
        this.nomeLocation = nomeLocation;
        this.dataEvento = dataEvento;
        lista_vincoli = new ArrayList();
        this.invitati = new ArrayList(numInvitati);
        this.numInvitati=numInvitati;

    }

    public Evento(String nomeEvento, GregorianCalendar dataEvento, int numInvitati) {
        this.nomeEvento = nomeEvento;
        this.dataEvento = dataEvento;
        this.numInvitati = numInvitati;
    }

    public String getName(){return nomeEvento;}



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


    public Locale getLocation(){ return location;}

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public void setLocation(locale.Locale location) {
        this.location = location;
    }

    public void addInvitato(Invitato invitato){
        invitati.add(invitato);
    }


    /*public void setInvitati(ArrayList <persone.Invitato> invitati) {
        this.invitati = invitati;
    }*/

    public int getNumInvitati(){return numInvitati;}

    public void setLista_vincoli(ArrayList <Vincolo> lista_vincoli) {
        this.lista_vincoli = lista_vincoli;
    }

    public ArrayList<Invitato> getListaInvitati(){return invitati;}

    public void showListaInvitati(){
        for (Invitato singleGuest:invitati) {
            System.out.println(singleGuest.toString());

        }
    }

    public void setDataEvento(GregorianCalendar dataEvento) {
        this.dataEvento = dataEvento;
    }

    public GregorianCalendar getDataEvento() {
        return dataEvento;
    }
}