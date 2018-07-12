package gui.controller;


import database.DatabaseException;
import database.DatabaseNullException;
import facade.*;
import locale.GestoreEvento;
import persone.Cliente;
import persone.Invitato;
import vincoli.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import database.ConnessioneDB;

/**
 *
 * @author lecciovich
 */


public class SistemaDiPrenotazioneController{
    private boolean loggedIn=false,notdone = true;
    private AbstractFacade facade;
    private XlsFacade xlsFacade;
    private ConnessioneDB connessione ;
    ArrayList<Invitato> listainvitatiEvento=null;

    public SistemaDiPrenotazioneController(){
        connessione = new ConnessioneDB();
        try {
            facade=new txtFacade(1);
            xlsFacade=new XlsFacade();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //aggiunta connessione DB, se non c'è il cliente registrato , allora lo inserisco in registrazioni sul txt e nel db
    public boolean signUp(String name,String surname,String email,String username,String password)throws DatabaseException, DatabaseNullException{
        try {
            Cliente fetching=facade.fetchClient(username,password);
            if (fetching!=null){
                System.err.println("Trovato cliente con lo stesso username.Per favore riprova con un altro username.");
                return false;
            }
            ConnessioneDB connessione = new ConnessioneDB();
            connessione.inserisciDatiCliente(username,name,surname,email,password);
            facade.WriteClient(username,password,name,surname,email);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Cliente login(String username, String password){
        Cliente fetching= null;
        try {
            fetching = facade.fetchClient(username,password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fetching!=null) { loggedIn = true; }
        return fetching;
    }


    public void logout(){
        loggedIn=false;
    }

    //adattato al DB inserisce l'evento anche nel DB (controllare la data!)
    public boolean creaEvento(String nomeEvento, GregorianCalendar data, int guestNum, Cliente cliente,String nomelocale)throws DatabaseException, DatabaseNullException{
        String datadb = data.get(Calendar.DAY_OF_MONTH)+"-"+data.get(Calendar.MONTH)+"-"+data.get(Calendar.YEAR);
        System.out.println(datadb);
        try {
            facade.WriteEvent(nomeEvento,data,guestNum);
            //ho bisogno del nome locale
            connessione.inserisciDatiEvento(cliente.getUsername(),nomeEvento,datadb,nomelocale,guestNum);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public GestoreEvento getEvento(String nomeEvento){
        String[] columns= new String[10];
        GestoreEvento gestoreEvento =null;
        try {
            gestoreEvento =facade.fetch(nomeEvento,columns);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gestoreEvento;
    }


    public boolean acquisisciInvitati(ArrayList<Invitato> invitati){
        for (Invitato i:invitati) {
            try {
                facade.WriteGuests(i.getID_Inv(),i.getNome(),i.getCognome(),i.getEta());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ArrayList<Invitato> getInvitati(){
        ArrayList<Invitato> invitati= null;
        try {
            txtFacade txtFacade= new txtFacade(1);
            invitati.addAll(txtFacade.fetchAllGuests());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return invitati;
    }

    public boolean createXlsGenerality(String nomeEvento){
        xlsFacade.generateXlsGuests(nomeEvento);
        try {
            xlsFacade.openfile(nomeEvento+".xls");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Invitato> loadXlsGenerality(String nomeEvento)throws DatabaseException, DatabaseNullException{

        ConnessioneDB connessione = new ConnessioneDB();
        try {
            listainvitatiEvento = xlsFacade.readXlsGuests(nomeEvento);
            int sizelist = listainvitatiEvento.size();
            txtFacade t = new txtFacade(sizelist);
            if(this.notdone)
        for (Invitato element:listainvitatiEvento) {
            t.WriteGuests(element.getID_Inv(),element.getNome(),element.getCognome(),element.getEta());
            this.notdone=false;
            connessione.inserisciDatiInvitato(nomeEvento,element.getID_Inv(),element.getNome(),element.getCognome(),element.getEta());
        }
        }catch (IOException e){ e.printStackTrace(); }

        return listainvitatiEvento ;
    }
    //NOTA BENE: da correggere

    public ArrayList<Invitato> writeXlsObligations(String nomeEvento)throws DatabaseException, DatabaseNullException{
        ArrayList<Invitato> result = new ArrayList<Invitato>();
        if (!xlsFacade.reWriteXls(nomeEvento,loadXlsGenerality(nomeEvento))){
            return null;
        }
        this.notdone=true;
        return listainvitatiEvento;
    }

    public ArrayList<SpecificaTavolo> saveOnObligations(String nomeEvento){
        ArrayList<SpecificaTavolo> specificaTavolos=xlsFacade.readSpecificheTavolo(nomeEvento);
        return specificaTavolos;
    }

}

