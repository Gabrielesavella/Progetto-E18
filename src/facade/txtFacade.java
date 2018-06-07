package facade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class txtFacade extends AbstractFacade {

    private String path;
    private int numberofObject,writings = 0;
    private FileWriter txtFile;
    private BufferedWriter buffer;


    public txtFacade(String namefile, int numberofObject) throws IOException {
        txtFile = new FileWriter(namefile);
        this.numberofObject = numberofObject;
        buffer = new BufferedWriter(txtFile);


    }

    @Override
    public void generate() throws IOException{
        //genera un file i cui campi sono separati da un tab
        for (String campo:field) {
            buffer.write(campo+"\t");
        }
        writings++;
        buffer.flush();

        if(writings == numberofObject){
            closeAll();
        }
        else{
            buffer.newLine();
            super.generate();
        }





    }

    @Override
    public void WriteClient(String name, String surname, String email, String password)throws IOException {
        super.WriteClient(name, surname, email, password);
    }

    @Override
    public void WriteGuests(String fiscaleCode, String nameGuest, String surnameGuest, int age) throws IOException {
        super.WriteGuests(fiscaleCode, nameGuest, surnameGuest, age);
    }

    @Override
    public void WriteEvent(String nameEvent, GregorianCalendar dateEvent, int guestNumber) throws IOException {
        super.WriteEvent(nameEvent, dateEvent, guestNumber);
    }

    //faccio chiusura sia del buffer che del file
    public void closeAll() throws IOException{
        buffer.close();
        txtFile.close();
        super.generate();
    }
}
