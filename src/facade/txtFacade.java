package facade;

import locale.Evento;
import persone.Cliente;

import java.io.*;
import java.util.GregorianCalendar;

public class txtFacade extends AbstractFacade {

    private String pathClient ="registrazioni.txt",pathGuests="invitati.txt",pathEvents="eventi.txt";
    private int numberofObject,writings = 0;
    private FileWriter txtFileW;
    private FileReader txtFileR;
    private BufferedWriter bufferWriter;
    private BufferedReader buffReader;
    private Evento evento = null;
    private Cliente client = null;
    private boolean registered = false;

    public txtFacade(String namefile, int numberofObject) throws IOException {
        super();
        txtFileW = new FileWriter(namefile,true);
        this.numberofObject = numberofObject;
        bufferWriter = new BufferedWriter(txtFileW);
        txtFileR = new FileReader(namefile);
        buffReader = new BufferedReader(txtFileR);

    }
    public txtFacade(int numberofObject) throws IOException {
        super();
        this.numberofObject = numberofObject;


    }

    @Override
    public void generate() throws IOException{
        //genera un file i cui campi sono separati da un tab
        for (String campo:field) {
            bufferWriter.write(campo+"\t");
        }
        bufferWriter.newLine();
        writings++;
        bufferWriter.flush();

        if(writings == numberofObject){
            closeWriting();
        }
        else{
            bufferWriter.newLine();
            super.generate();
        }
    }

    @Override
    public Cliente fetch(String username, String password, String[] colonna) throws IOException{
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

    public Evento fetchEvento(String nomeEvento) throws IOException {

        String line;
        String [] colonna;
        FileWriter writing = new FileWriter(pathEvents,true);
        writing.close();
        BufferedReader reader = new BufferedReader(new FileReader(pathEvents));

        while(reader.ready()) {
            line=reader.readLine();
            colonna = line.split("\t");
            evento = fetch(nomeEvento,colonna);
        }
        return evento;
    }
    @Override
    public Evento fetch(String nomeEvento, String[] colonna) throws IOException{

        if(colonna[0].equals(nomeEvento)){
            GregorianCalendar orarioapertura = new GregorianCalendar();
            orarioapertura.add(GregorianCalendar.HOUR,Integer.parseInt(colonna[1]));
            evento = new Evento(colonna[0],orarioapertura,Integer.parseInt(colonna[2]));
            return evento;

        }
        else
        {
            return evento;
        }

    }






    @Override
    public void WriteClient(String username,String password,String name, String surname, String email)throws IOException {
        boolean exist = false;
        txtFileW = new FileWriter(pathClient, true);
        exist = check(pathClient,username);
        if(!exist) {
            bufferWriter = new BufferedWriter(txtFileW);
            super.WriteClient(username, password, name, surname, email);
        }
    }

    @Override
    public void WriteGuests(String fiscaleCode, String nameGuest, String surnameGuest, int age) throws IOException {
        boolean exist = false;
        txtFileW = new FileWriter(pathGuests, true);
        exist=check(pathGuests,fiscaleCode);
            if(!exist) {
                bufferWriter = new BufferedWriter(txtFileW);
                super.WriteGuests(fiscaleCode, nameGuest, surnameGuest, age);
            }




    }

    @Override
    public void WriteEvent(String nameEvent, GregorianCalendar dateEvent,int guestNumber) throws IOException {
        boolean exist = false;
        txtFileW = new FileWriter(pathEvents, true);
        exist = check(pathEvents,nameEvent);
        if(!exist) {
            bufferWriter = new BufferedWriter(txtFileW);
            super.WriteEvent(nameEvent, dateEvent, guestNumber);
        }
    }


    public boolean check(String path,String key)throws IOException{
        boolean esito = false;
        String line;
        String [] colonna;
        txtFileR = new FileReader(path);
        buffReader = new BufferedReader( txtFileR);

        while( buffReader.ready()) {
            line =  buffReader.readLine();
            colonna = line.split("\t");
            if (colonna[0].equals(key)) {
                esito = true;
            }
        }
        closeReading();
        return esito;
    }


    //faccio chiusura sia del bufferWriter che del file
    public void closeAll() throws IOException{
        closeWriting();
        closeReading();
        super.generate();
    }

    public void closeWriting() throws IOException{
        bufferWriter.close();
        txtFileW.close();
    }

    public void closeReading() throws IOException{
        buffReader.close();
        txtFileR.close();
    }

    public String getPathClient() {
        return pathClient;
    }

    public String getPathGuests() {
        return pathGuests;
    }

    public String getPathEvents() {
        return pathEvents;
    }

    @Override
    public Cliente fetchClient(String username, String password) throws IOException {
        String line;
        String [] colonna;
         FileWriter writing = new FileWriter(pathClient,true);
         writing.close();



        BufferedReader reader = new BufferedReader(new FileReader(pathClient));

        while(reader.ready()) {
            line=reader.readLine();
            colonna = line.split("\t");
            fetch(username,password,colonna);
        }
        return client;
    }
}

