package facade;

import database.*;
import locale.*;
import persone.*;
import vincoli.*;

import java.io.*;
import java.util.*;

/*
   classe astratta che sarà alla base di tutti i facade
   @AUTHOR Gabrielesavella
    */
public class Facade {


    private txtPersistence txt;
    private XlsPersistence xls;
    private ConnessioneDB connessioneDB;
    private static Facade istanza;

    public Facade() throws IOException{
            txt = new txtPersistence(1);
            xls = new XlsPersistence();
            connessioneDB = new ConnessioneDB();
    }

    public static Facade getInstance(){
        if (istanza == null)
        {
            try {
                istanza = new Facade();
            }catch (IOException eccezione){
                System.err.println(eccezione.getMessage());
            }
        }

        return istanza;
    }

   //metodo che va a recuperare un cliente
    public Cliente fetchClient(String username,String password)throws IOException{
        return txt.fetchClient(username,password);
    }

    public ArrayList<Invitato> fetchAllGuests() throws IOException{
        return txt.fetchAllGuests();
    }

    public GestoreEvento fetchEvento(String nomeEvento) throws IOException {
        return txt.fetchEvento(nomeEvento);
    }

    public Invitato fetchGuest(String idInvitato) throws IOException {
        return txt.fetchGuest(idInvitato);
    }
    //metodo che memorizza un cliente
    public void WriteClient(String username, String password,String name, String surname, String email)throws IOException{
        txt.WriteClient(username, password, name, surname, email);
    }
    //metodo che memorizza un invitato
    public void WriteGuests(String fiscaleCode,String nameGuest, String surnameGuest,int age) throws IOException{
        txt.WriteGuests(fiscaleCode, nameGuest, surnameGuest, age);
    }
    //metodo che memorizza un evento
    public void WriteEvent(String nameEvent, GregorianCalendar dateEvent,int guestNumber)throws IOException{
        txt.WriteEvent(nameEvent, dateEvent, guestNumber);
    }

    public void setNumberofObject(int numberofObject) {
        txt.setNumberofObject(numberofObject);
    }

    public boolean generateXlsGuests(String nomeEvento){
       return xls.generateXlsGuests(nomeEvento);
    }

    public boolean openfile(String nameFile) throws IOException{
        return xls.openfile(nameFile);
    }

    public ArrayList<Invitato> readXlsGuests(String nomeEvento){
        return xls.readXlsGuests(nomeEvento);
    }

    public boolean reWriteXls(String nomeEvento,ArrayList<Invitato> invitati) {
        return xls.reWriteXls(nomeEvento, invitati);
    }
    public ArrayList<SpecificaTavolo> readSpecificheTavolo(String nameEvent){
        return xls.readSpecificheTavolo(nameEvent);
    }

    //connessione
    public void inserisciDatiCliente(String ID_Cliente, String nomeCliente, String cognomeCliente, String emailCliente, String passwordCliente) throws DatabaseException {
        connessioneDB.inserisciDatiCliente(ID_Cliente, nomeCliente, cognomeCliente, emailCliente, passwordCliente);
    }

    public void inserisciDatiEvento(String ID_Cliente, String ID_Evento, String dataEvento, String nomeLocale, int numeroInvitati) throws DatabaseException, DatabaseNullException {
        connessioneDB.inserisciDatiEvento(ID_Cliente, ID_Evento, dataEvento, nomeLocale, numeroInvitati);
    }

    public void inserisciDatiInvitato(String ID_Evento, String ID_Inv, String nomeInv, String cognomeInv, int etaInv) throws DatabaseException, DatabaseNullException {
        connessioneDB.inserisciDatiInvitato(ID_Evento, ID_Inv, nomeInv, cognomeInv, etaInv);
    }

    public void inserisciVincoliTavolo(String ID_Evento, String ID_Inv, int tavoloOnore, int difficoltaMotorie, int vegetariano, int vicinoTV, int bambini, int tavoloIsolato) throws DatabaseException, DatabaseNullException {
        connessioneDB.inserisciVincoliTavolo(ID_Evento, ID_Inv, tavoloOnore, difficoltaMotorie, vegetariano, vicinoTV, bambini, tavoloIsolato);
    }

    public void inserisciVincoloInvitati(String ID_Evento, String ID_Inv, String starVicino, String starLontano) throws DatabaseException, DatabaseNullException {
        connessioneDB.inserisciVincoloInvitati(ID_Evento, ID_Inv, starVicino, starLontano);
    }
    public void inserisciAgenda(String ID_Locale,String data,String tavoliOccupati) throws DatabaseException, DatabaseNullException {
        String tavoliGiaPresenti=connessioneDB.getInAgenda(ID_Locale,data);
        if ( tavoliGiaPresenti!=null && !tavoliGiaPresenti.equals("") ){
            connessioneDB.deleteDoubleKeyEntry("agenda","ID_locale",ID_Locale,"data",data);
            tavoliOccupati=tavoliGiaPresenti+" "+tavoliOccupati;

            if (tavoliOccupati.substring(tavoliOccupati.length()-1).equals(" ")){
                tavoliOccupati=tavoliOccupati.substring(0,tavoliOccupati.length()-1);
            }
        }
        connessioneDB.inserisciInAgenda(ID_Locale,data,tavoliOccupati);//,tavoliGiaRinominati

    }
    public void aggiornaNomeTavolo(String ID_Locale, String ID_VecchioTavolo, String ID_NuovoTavolo) throws DatabaseException, DatabaseNullException {
        Tavolo tavVecchio= connessioneDB.getTavoloSingolo(ID_VecchioTavolo);
        connessioneDB.deleteDoubleKeyEntry("tavoli","ID_locale",ID_Locale,"ID_Tavolo",ID_VecchioTavolo);
        connessioneDB.inserisciTavoli(ID_Locale,ID_NuovoTavolo,tavVecchio.getPostiTot());
    }



    //metodo da richiamare nel caso l'utente voglia ricompilare i campi perchè ha immesso vincoli incongruenti
    public void removeVincoli(String nameEvent){
        connessioneDB.deleteEntry("preferenza_invitato","ID_Evento",nameEvent);
        connessioneDB.deleteEntry("specifica_tavolo","ID_Evento",nameEvent);
    }

    public ArrayList<PreferenzaInvitato> getVincoloInvitato(String ID_Ev) {
        return connessioneDB.getVincoloInvitato(ID_Ev);
    }

    public Cliente getCliente(String ID_Cliente) {
       return connessioneDB.getCliente(ID_Cliente);
    }

    public ArrayList<Cliente> getClienti(){
        return connessioneDB.getClienti();
    }

    public Evento getEvento(String ID_Evento){
        return connessioneDB.getEventoSingolo(ID_Evento);
    }

    public ArrayList<GestoreLocale> getLocali() {
        return connessioneDB.getLocali();
    }

    public ArrayList<Invitato> getInvitato(String ID_Ev) {
        return connessioneDB.getInvitato(ID_Ev);
    }

    public ArrayList<Tavolo> getTavoli(String ID_Locale){
        return connessioneDB.getTavolo(ID_Locale);
    }

    public Map<String, ArrayList<Tavolo>> getAgenda(String id_loc) {
        return connessioneDB.getAgendaLocale(id_loc);
    }

    public void reinizializzaDb(String nomeEvento) {
        removeVincoli(nomeEvento);
        connessioneDB.deleteEntry("invitati","ID_Evento",nomeEvento);
    }

}
