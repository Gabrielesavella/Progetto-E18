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


public class SistemaDiPrenotazione {
    private boolean loggedIn = false, notdone = true;
    ArrayList<Invitato> listainvitatiEvento = null;

    public SistemaDiPrenotazione() {

    }



        public boolean signUp (String name, String surname, String email, String username, String password)throws
        DatabaseException {
            try {
                if (Facade.getInstance().getCliente(username) != null) {
                    System.err.println("Trovato cliente con lo stesso username.Per favore riprova con un altro username.");
                    return false;
                }
                Facade.getInstance().inserisciDatiCliente(username, name, surname, email, password);
                Facade.getInstance().WriteClient(username, password, name, surname, email);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }




    public Cliente login(String username, String password) {
        Cliente fetching = null;
        ArrayList<Cliente> clienti = null;

        clienti = Facade.getInstance().getClienti();
        for (Cliente c : clienti) {
            if (c.getUsername().equals(username) && c.getPsw().equals(password)) {
                fetching = c;
                break;
            }
        }
        if (fetching == null) {
            try {
                fetching = Facade.getInstance().fetchClient(username, password);
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
        String datadb = data.get(Calendar.DAY_OF_MONTH)+"/"+(data.get(Calendar.MONTH)+1)+"/"+data.get(Calendar.YEAR);
        if(data.get(Calendar.MONTH)<9)
            datadb= data.get(Calendar.DAY_OF_MONTH)+"/0"+(data.get(Calendar.MONTH)+1)+"/"+data.get(Calendar.YEAR);
        if(Facade.getInstance().getEvento(nomeEvento)!=null)
            return false;
        try {
            Facade.getInstance().WriteEvent(nomeEvento,data,guestNum);
            Facade.getInstance().inserisciDatiEvento(cliente.getUsername(),nomeEvento,datadb,nomelocale,guestNum);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public GestoreEvento getEvento(String nomeEvento){
        GestoreEvento gestoreEvento =null;
        try {
            gestoreEvento =Facade.getInstance().fetchEvento(nomeEvento);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gestoreEvento;
    }


    public boolean acquisisciInvitati(ArrayList<Invitato> invitati){
        for (Invitato i:invitati) {
            try {
                Facade.getInstance().WriteGuests(i.getID_Inv(),i.getNome(),i.getCognome(),i.getEta());
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
            invitati.addAll(Facade.getInstance().fetchAllGuests());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return invitati;
    }

    public boolean createXlsGenerality(String nomeEvento){
        Facade.getInstance().generateXlsGuests(nomeEvento);
        try {
            Facade.getInstance().openfile(nomeEvento+".xls");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Invitato> loadXlsGenerality(String nomeEvento)throws DatabaseException,DatabaseNullException{

        try {
            listainvitatiEvento = Facade.getInstance().readXlsGuests(nomeEvento);
            int sizelist = listainvitatiEvento.size();
            txtPersistence t = new txtPersistence(sizelist);
            if(this.notdone)
        for (Invitato element:listainvitatiEvento) {
            t.WriteGuests(element.getID_Inv(),element.getNome(),element.getCognome(),element.getEta());
            this.notdone=false;
            Facade.getInstance().inserisciDatiInvitato(nomeEvento,element.getID_Inv(),element.getNome(),element.getCognome(),element.getEta());
        }
        }catch (IOException e){ e.printStackTrace(); }

        return listainvitatiEvento ;
    }

    public ArrayList<Invitato> writeXlsObligations(String nomeEvento)throws DatabaseException,DatabaseNullException{
        if (!Facade.getInstance().reWriteXls(nomeEvento,loadXlsGenerality(nomeEvento))){
            return null;
        }
        this.notdone=true;
        return listainvitatiEvento;
    }

    public ArrayList<SpecificaTavolo> saveOnObligations(String nomeEvento){

        return Facade.getInstance().readSpecificheTavolo(nomeEvento);
    }

    //reinizializza database
    public void reinizializzaDb(String nomeEvento){
        Facade.getInstance().reinizializzaDb(nomeEvento);
    }



}

