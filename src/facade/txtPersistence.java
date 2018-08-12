package facade;
import locale.*;
import persone.*;
import java.io.*;
import java.util.*;

import static persone.Invitato.setID_Inv;

/*classe che memorizza gli oggetti : cliente, invitato ed evento in un file txt
* @author GabrieleSavella*/
public class txtPersistence {

    private String pathClient = "registrazioni.txt", pathGuests = "invitati.txt", pathEvents = "eventi.txt";
    private int numberofObject, writings = 0;
    private FileWriter txtFileW;
    private FileReader txtFileR;
    private BufferedWriter bufferWriter;
    private BufferedReader buffReader;
    public Invitato invitato = null;
    private ArrayList <String> field;


    public txtPersistence(int numberofObject) throws IOException {
        field = new ArrayList<>();
        this.numberofObject = numberofObject;
    }

    //scrive un invitato su txt


    public void WriteClient(String username, String password, String name, String surname, String email) throws IOException {
        txtFileW = new FileWriter(pathClient, true);
        boolean exist = check(pathClient, username);
        if (!exist) {
            bufferWriter = new BufferedWriter(txtFileW);
            field.add(username);
            field.add(password);
            field.add(name);
            field.add(surname);
            field.add(email);
            generate();
        }
    }

    //scrive un invitato su txt


    public void WriteGuests(String fiscaleCode, String nameGuest, String surnameGuest, int age) throws IOException {
        txtFileW = new FileWriter(pathGuests, true);
        boolean exist = check(pathGuests, fiscaleCode);
        if (!exist) {
            bufferWriter = new BufferedWriter(txtFileW);
            field.add(fiscaleCode);
            field.add(nameGuest);
            field.add(surnameGuest);
            field.add(Integer.toString(age));
            generate();
        }

    }

    //scrive un evento su txt


    public void WriteEvent(String nameEvent, GregorianCalendar dateEvent, int guestNumber) throws IOException {
        txtFileW = new FileWriter(pathEvents, true);
        boolean exist = check(pathEvents, nameEvent);
        if (!exist) {
            bufferWriter = new BufferedWriter(txtFileW);
            field.add(nameEvent);
            field.add(Integer.toString(dateEvent.get(5))+"/"+(Integer.toString(dateEvent.get(2)))+"/"+Integer.toString(dateEvent.get(1)));
            field.add(Integer.toString(guestNumber));
            generate();
        }
    }

    //genera un file i cui campi sono separati da un tab


    public void generate() throws IOException {
        for (String campo : field) {
            bufferWriter.write(campo + "\t");
        }
        bufferWriter.newLine();
        writings++;
        bufferWriter.flush();
        if (writings == numberofObject) {
            closeWriting();
        } else {
            bufferWriter.newLine();
            field.clear();
        }
    }
    //fetchClient :  legge riga per riga e spezza le righe nei campi che vengono passati al relativo metodo fetch


    public Cliente fetchClient(String username, String password) throws IOException {
        String line;
        String[] colonna;
        Cliente client = null;
        FileWriter writing = new FileWriter(pathClient, true);
        writing.close();
        buffReader = new BufferedReader(new FileReader(pathClient));
        while (buffReader.ready()) {
            line = buffReader.readLine();
            colonna = line.split("\t");
            fetch(username, password, colonna);
        }
        return client;
    }

    //questo metodo crea il relativo oggetto da restituire ( se corrisponde a quello voluto)

    public Cliente fetch( String username, String password, String[] colonna) throws IOException {
        Cliente client = null;
        if (colonna[0].equals(username) && colonna[1].equals(password)) {
             boolean registered = true;
            client = new Cliente(colonna[2], colonna[3], colonna[0], colonna[4], colonna[1]);
            return client;
        } else
            return client;
    }

    //fetchEvento: legge riga per riga e spezza le righe nei campi che vengono passati al metodo fetch

    public GestoreEvento fetchEvento(String nomeEvento) throws IOException {
        String line;
        String[] colonna;
        GestoreEvento gestoreEvento= null;
        FileWriter writing = new FileWriter(pathEvents, true);
        writing.close();
        BufferedReader reader = new BufferedReader(new FileReader(pathEvents));
        while (reader.ready()) {
            line = reader.readLine();
            colonna = line.split("\t");
            gestoreEvento = fetch(nomeEvento, colonna);
        }
        return gestoreEvento;
    }

    //questo metodo crea il relativo oggetto da restituire ( se corrisponde a quello voluto)

    public GestoreEvento fetch(String nomeEvento, String[] colonna) throws IOException {
        GestoreEvento gestoreEvento= null;
        if (colonna[0].equals(nomeEvento)) {
            GregorianCalendar orarioapertura = new GregorianCalendar();
            orarioapertura.add(GregorianCalendar.HOUR, Integer.parseInt(colonna[1]));
            gestoreEvento = new GestoreEvento(colonna[0], orarioapertura, Integer.parseInt(colonna[2]));
            return gestoreEvento;

        } else return gestoreEvento;
    }

    //recupera tutti gli invitati nel txt, da in uscita un Arraylist di invitati

    public ArrayList<Invitato> fetchAllGuests() throws IOException{
        ArrayList<Invitato> AllGuests = new ArrayList<>();
        String line;
        String[] colonna;
        Invitato invitato = null;
        FileWriter writing = new FileWriter(pathGuests, true);
        writing.close();
        buffReader = new BufferedReader(new FileReader(pathGuests));
        while ( buffReader.ready()) {
            line = buffReader.readLine();
            colonna = line.split("\t");
            invitato = new Invitato(setID_Inv(colonna[1], colonna[2],Integer.parseInt(colonna[3])), colonna[1],colonna[2],Integer.parseInt(colonna[3]));
            AllGuests.add(invitato);
        }
        closeReading();
        return AllGuests;
    }

    //fetchGuest: va a recuperare l'invitato nel file e fa ritornare l'oggetto associato (con il metodo getGuest )

    public Invitato fetchGuest(String idInvitato) throws IOException {
        String line;
        String[] colonna;
        Invitato invitato = null;
        FileWriter writing = new FileWriter(pathGuests, true);
        writing.close();
        buffReader = new BufferedReader(new FileReader(pathGuests));
        while ( buffReader.ready()) {
            line =  buffReader.readLine();
            colonna = line.split("\t");
            invitato = getGuest(idInvitato, colonna);
        }
        return invitato;
    }

    //metodo che va a generare un invitato particolare dal txt (precedentemente creato) [ da usare in caso la
    // connessione non funzionasse ]

    public Invitato getGuest(String idInvitato, String[] colonna) throws IOException {
        Invitato invitato = null;
        if (colonna[0].equals(idInvitato)) {
            invitato = new Invitato(setID_Inv(colonna[1],colonna[2],Integer.parseInt(colonna[3])), colonna[1],colonna[2],Integer.parseInt(colonna[3]));
            return invitato;
        } else return invitato;
    }

    //controllo che l'oggetto (un oggetto tra cui invitato,GestoreEvento,Cliente..) non sia già memorizzato nel file

    public boolean check(String path, String key) throws IOException {
        boolean esito = false;
        String line;
        String[] colonna;
        txtFileR = new FileReader(path);
        buffReader = new BufferedReader(txtFileR);
        while (buffReader.ready()) {
            line = buffReader.readLine();
            colonna = line.split("\t");
            if (colonna[0].equals(key)) {
                esito = true;
            }
        }
        closeReading();
        return esito;
    }

    //chiusura dei flussi di scrittura

    public void closeWriting() throws IOException {
        bufferWriter.close();
        txtFileW.close();
    }
    //chiusura dei flussi di lettura
    public void closeReading() throws IOException {
        buffReader.close();
        txtFileR.close();
    }
    //chiusura sia dei file in lettura che in scrittura (e relativi flussi)
    public void closeAll() throws IOException {
        closeWriting();
        closeReading();
        field.clear();
    }

    public void setNumberofObject(int numberofObject) {
        this.numberofObject = numberofObject;
    }

    //metodi getter dei percorsi

    public String getPathClient() {
        return pathClient;
    }

    public String getPathGuests() {
        return pathGuests;
    }

    public String getPathEvents() {
        return pathEvents;
    }
}



