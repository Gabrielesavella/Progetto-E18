package facade;

import persone.Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AbstractFacade {
    protected ArrayList <String> field;


    public ArrayList<String> fetchClient(String username,String password){
        field=new ArrayList<String>(2);
        field.add(username);
        field.add(password);
        try {
            return fetch();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void WriteClient(String name, String surname, String email, String password)throws IOException{
        field=new ArrayList<String>(2);
        field.add(name);
        field.add(surname);
        field.add(email);
        field.add(password);
        generate();
    }

    public void WriteGuests(String fiscaleCode,String nameGuest, String surnameGuest,int age) throws IOException{
        field=new ArrayList<String>(2);
        field.add(fiscaleCode);
        field.add(nameGuest);
        field.add(surnameGuest);
        field.add(Integer.toString(age));
        generate();


    }

    public void WriteEvent(String nameEvent, GregorianCalendar dateEvent,int guestNumber)throws IOException{
       //promemoria per @author Gabrielesavella : da convertire dateevent in string (ora mancanzi di tempo causa lezione
        field=new ArrayList<String>(2);
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

    public ArrayList<String> fetch() throws IOException{
        return field;
    }

    public ArrayList<String> getField() {
        return field;
    }
}
