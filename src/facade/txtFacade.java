package facade;

import persone.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class txtFacade extends AbstractFacade {

    private String pathClient ="registrazioni.txt",pathGuests="invitati.txt",pathEvents="eventi.txt";
    private int numberofObject,writings = 0;
    private FileWriter txtFileW;
    private FileReader txtFileR;
    private BufferedWriter bufferWriter;
    private BufferedReader buffReader;

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
       return super.fetch(username, password, colonna);
    }


    @Override
    public void WriteClient(String username,String password,String name, String surname, String email)throws IOException {
        boolean exist = false;
        exist = check(pathClient,username);
        if(!exist) {
            txtFileW = new FileWriter(pathClient, true);
            bufferWriter = new BufferedWriter(txtFileW);
            super.WriteClient(username, password, name, surname, email);
        }
    }

    @Override
    public void WriteGuests(String fiscaleCode, String nameGuest, String surnameGuest, int age) throws IOException {
        boolean exist = false;
        exist=check(pathGuests,fiscaleCode);
            if(!exist) {
                txtFileW = new FileWriter(pathGuests, true);
                bufferWriter = new BufferedWriter(txtFileW);
                super.WriteGuests(fiscaleCode, nameGuest, surnameGuest, age);
            }




    }

    @Override
    public void WriteEvent(String nameEvent, GregorianCalendar dateEvent,int guestNumber) throws IOException {
        boolean exist = false;
        exist = check(pathEvents,nameEvent);
        if(!exist) {
            txtFileW = new FileWriter(pathEvents, true);
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

}

