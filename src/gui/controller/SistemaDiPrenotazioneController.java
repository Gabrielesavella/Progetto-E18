package gui.controller;


import facade.*;

import java.io.IOException;

public class SistemaDiPrenotazioneController{
    private boolean loggedIn=false;
    private AbstractFacade facade;

    public SistemaDiPrenotazioneController(){
        try {
            facade=new txtFacade("registratoreUtenti",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean signUp(String nome,String cognome,String email,String username,String password){
        try {
            facade.WriteClient(nome,cognome,email,username);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(String username,String password){
        return true;
    }

    public void logout(){
        loggedIn=false;
    }

    public boolean creaEvento(){

        return true;
    }

    public boolean acquisisciInvitati(){
        return true;
    }

}
