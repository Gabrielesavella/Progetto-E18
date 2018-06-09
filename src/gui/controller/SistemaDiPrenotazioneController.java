package gui.controller;


import facade.*;
import persone.Cliente;
import persone.Invitato;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class SistemaDiPrenotazioneController{
    private boolean loggedIn=false;
    private AbstractFacade facade;

    public SistemaDiPrenotazioneController(){
        try {
            facade=new txtFacade("registratoreUtenti.txt",1);
//            signUp("a","a","a","a","a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean signUp(String nome,String cognome,String email,String username,String password){
        try {
            ArrayList<String> fetching=facade.fetchClient(username,password);
            if (fetching!=null){
                System.out.println("aaaaaaaaaaaaaa");
                return false;
            }
            facade.WriteClient(nome,cognome,email,username);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cliente login(String username, String password){
        ArrayList<String> fetching=facade.fetchClient(username,password);
        if (fetching!=null) {
            loggedIn = true;
            return new Cliente(fetching.get(0),fetching.get(1),fetching.get(2),fetching.get(3));
        }
        return null;
    }

    public void logout(){
        loggedIn=false;
    }

    public boolean creaEvento(String nomeEvento, GregorianCalendar data, int guestNum, Cliente cliente){
        try {
            facade.WriteEvent(nomeEvento,data,guestNum);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean acquisisciInvitati(ArrayList<Invitato> invitati){
        for (Invitato i:invitati) {
            try {
                facade.WriteGuests(i.getCf(),i.getNome(),i.getCognome(),i.getEtà());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
