package gui.controller;


import database.DatabaseException;
import database.DatabaseNullException;
import facade.*;
import locale.GestoreEvento;
import persone.Cliente;
import persone.Invitato;
import vincoli.*;

import java.io.IOException;
import java.util.*;

import database.ConnessioneDB;

/**
 *
 * @author lecciovich
 */


public class SistemaDiPrenotazioneController {
    private boolean loggedIn = false, notdone = true;
    private Facade facade;
    ArrayList<Invitato> listainvitatiEvento = null;

    public SistemaDiPrenotazioneController() {
        try {
            facade = new Facade();
        }catch(IOException eccezione){
            System.err.println(eccezione.getMessage());
        }


    }

    //aggiunta connessione DB, se non c'è il cliente registrato , allora lo inserisco in registrazioni sul txt e nel db

        public boolean signUp (String name, String surname, String email, String username, String password)throws
        DatabaseException {
            try {
                if (facade.getCliente(username) != null) {
                    System.err.println("Trovato cliente con lo stesso username.Per favore riprova con un altro username.");
                    return false;
                }
                facade.inserisciDatiCliente(username, name, surname, email, password);
                facade.WriteClient(username, password, name, surname, email);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }




    public Cliente login(String username, String password) {
        Cliente fetching = null;
        ArrayList<Cliente> clienti = null;

        clienti = facade.getClienti();
        for (Cliente c : clienti) {
            if (c.getUsername().equals(username) && c.getPsw().equals(password)) {
                fetching = c;
                break;
            }
        }
        if (fetching == null) {
            try {
                fetching = facade.fetchClient(username, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fetching != null) {
            loggedIn = true;
        }
        return fetching;
    }

    public void logout(){
        loggedIn=false;
    }

    //adattato al DB inserisce l'evento anche nel DB (controllare la data!)
    public boolean creaEvento(String nomeEvento, GregorianCalendar data, int guestNum, Cliente cliente,String nomelocale)throws DatabaseException, DatabaseNullException{
        String datadb = data.get(Calendar.DAY_OF_MONTH)+"-"+data.get(Calendar.MONTH)+"-"+data.get(Calendar.YEAR);
        try {
            facade.WriteEvent(nomeEvento,data,guestNum);
            facade.inserisciDatiEvento(cliente.getUsername(),nomeEvento,datadb,nomelocale,guestNum);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //MODIFICA
  // ho fatto una modifica qua  al posto di fetch (nomeevento, colonne) sostituito fetchEvento (nomeEvento)
    public GestoreEvento getEvento(String nomeEvento){
        GestoreEvento gestoreEvento =null;
        try {
            gestoreEvento =facade.fetchEvento(nomeEvento);
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
            invitati.addAll(facade.fetchAllGuests());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return invitati;
    }

    public boolean createXlsGenerality(String nomeEvento){
        facade.generateXlsGuests(nomeEvento);
        try {
            facade.openfile(nomeEvento+".xls");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Invitato> loadXlsGenerality(String nomeEvento)throws DatabaseException,DatabaseNullException{

        try {
            listainvitatiEvento = facade.readXlsGuests(nomeEvento);
            int sizelist = listainvitatiEvento.size();
            txtPersistence t = new txtPersistence(sizelist);
            if(this.notdone)
        for (Invitato element:listainvitatiEvento) {
            t.WriteGuests(element.getID_Inv(),element.getNome(),element.getCognome(),element.getEta());
            this.notdone=false;
            facade.inserisciDatiInvitato(nomeEvento,element.getID_Inv(),element.getNome(),element.getCognome(),element.getEta());
        }
        }catch (IOException e){ e.printStackTrace(); }

        return listainvitatiEvento ;
    }
    //NOTA BENE: da correggere

    public ArrayList<Invitato> writeXlsObligations(String nomeEvento)throws DatabaseException,DatabaseNullException{
        if (!facade.reWriteXls(nomeEvento,loadXlsGenerality(nomeEvento))){
            return null;
        }
        this.notdone=true;
        return listainvitatiEvento;
    }

    public ArrayList<SpecificaTavolo> saveOnObligations(String nomeEvento){
        ArrayList<SpecificaTavolo> specificaTavolos= facade.readSpecificheTavolo(nomeEvento);
        return specificaTavolos;
    }


}

