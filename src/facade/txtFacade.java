package facade;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class txtFacade extends AbstractFacade {

    private String path;
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

    @Override
    public void generate() throws IOException{
        //genera un file i cui campi sono separati da un tab
        for (String campo:field) {
            bufferWriter.write(campo+"\t");
        }
        bufferWriter.write("\n");
        writings++;
        bufferWriter.flush();

        if(writings == numberofObject){
            closeAll();
        }
        else{
            bufferWriter.newLine();
            super.generate();
        }
    }

    @Override
    public ArrayList<String> fetch(){

        int countFetched=0;
        ArrayList<String> record=null;

        for (String campo: field) {
            try {
                String line=buffReader.readLine();
                if (line==null){
                    break;
                }
                String[] campiTxt=line.split("\t");
                for (String s:campiTxt) {
                    if (s.equals(campo)){
                        countFetched++;
                        if(field.size()==countFetched) {
                            record=new ArrayList<>();
                            record.addAll(Arrays.asList(line.split("\t")));
                            break;
                        }
                    }
                }
                if (record!=null)
                    break;

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        field=record;
        try {
            return super.fetch();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    //faccio chiusura sia del bufferWriter che del file
    public void closeAll() throws IOException{
        bufferWriter.close();
        buffReader.close();
        txtFileW.close();
        txtFileR.close();
        super.generate();
    }
}
