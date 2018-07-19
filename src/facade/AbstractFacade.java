package facade;

import locale.GestoreEvento;
import persone.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
   classe astratta che sarà alla base di tutti i facade
   @AUTHOR Gabrielesavella
    */
public class AbstractFacade {

    protected ArrayList <String> field;
    private Cliente client = null;
    private GestoreEvento gestoreEvento = null;

   //metodo che va a recuperare un cliente
    public Cliente fetchClient(String username,String password)throws IOException{
        return null;
    }
    //metodo che memorizza un cliente
    public void WriteClient(String username, String password,String name, String surname, String email)throws IOException{
        field = new ArrayList<String>(5);
        field.add(username);
        field.add(password);
        field.add(name);
        field.add(surname);
        field.add(email);
        generate();
    }
    //metodo che memorizza un invitato
    public void WriteGuests(String fiscaleCode,String nameGuest, String surnameGuest,int age) throws IOException{
        field=new ArrayList<String>(4);
        field.add(fiscaleCode);
        field.add(nameGuest);
        field.add(surnameGuest);
        field.add(Integer.toString(age));
        generate();


    }
    //metodo che memorizza un evento
    public void WriteEvent(String nameEvent, GregorianCalendar dateEvent,int guestNumber)throws IOException{

        field=new ArrayList<String>(3);
        field.add(nameEvent);
        field.add(Integer.toString(dateEvent.get(Calendar.DATE)));
        field.add(Integer.toString(guestNumber));
        generate();

    }
    /*
    questo è il metodo che andrà a generare il file (ovviamente non posso implementarlo perchè questo metodo andrà
    cambiato in base alla classe ereditata, finito di generare il file svuoto il campo (visto che è protected
     */

    public void generate()throws IOException {
        field.clear();
    }
    //metodo che va a recuperare un oggetto che è stato memorizzato
    public Cliente fetch(String username,String password,String[] colonna) throws IOException{
        return client;
    }
    //metodo che va a recuperare un evento memorizzato
    public GestoreEvento fetch(String nomeEvento, String[] colonna) throws IOException{
        return gestoreEvento;
    }
    //metodo getter dei campi
    public ArrayList<String> getField() {
        return field;
    }
}
