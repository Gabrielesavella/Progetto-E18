package facade;

import locale.Evento;
import persone.*;
import java.io.*;
import java.util.*;


public class AbstractFacade {

    protected ArrayList <String> field;
     private boolean registered = false;
    Cliente client = null;

    /*
    ATTENZIONE_ questo fetchClient va implementato nelle classi eredi
    @AUTHOR Gabrielesavella
     */
    public Cliente fetchClient(String username,String password)throws IOException{
        String line;
        String [] colonna;

        BufferedReader reader = new BufferedReader(new FileReader("registrazioni.txt"));

        while(reader.ready()) {
            line=reader.readLine();
            colonna = line.split("\t");
            fetch(username,password,colonna);
        }
        return client;
    }

    public void WriteClient(String username, String password,String name, String surname, String email)throws IOException{
        field = new ArrayList<String>(5);
        field.add(username);
        field.add(password);
        field.add(name);
        field.add(surname);
        field.add(email);
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

    public Cliente fetch(String username,String password,String[] colonna) throws IOException{
        //colonna 1 e 2 perchè la colonna 0 è un intero che indica il numero di riga
        if(colonna[0].equals(username) && colonna[1].equals(password)){
            registered = true;
            client = new Cliente(colonna[0],colonna[2],colonna[3],colonna[4],colonna[1]);
            return client;

        }
        else
        {
            return client;
        }

    }

    public Evento fetch(String nomeEvento, String[] colonna) throws IOException{
        return null;
    }

    public ArrayList<String> getField() {
        return field;
    }
}
