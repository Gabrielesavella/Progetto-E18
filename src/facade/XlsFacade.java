package facade;

import database.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import persone.*;
import vincoli.PreferenzaInvitato;
import vincoli.SpecificaTavolo;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static persone.Invitato.setID_Inv;

public class XlsFacade {
    Workbook workbook;

    FileOutputStream fileOut = null;
    private Sheet sheet;
    private String xlsGuest = "Guest.xls", xlsVincoli = "Vincoli.xls";
    private ArrayList<String> campiPreferenze;
    private ArrayList<PreferenzaInvitato> vincoliPreferenze = new ArrayList<>();;


    public boolean generateXlsGuests(String nomeEvento){
        workbook = new HSSFWorkbook();

        boolean generated = true;



        CreationHelper createHelper = workbook.getCreationHelper();
        sheet = workbook.createSheet(nomeEvento);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());


        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);


        Row headerRow = sheet.createRow(0);

        ArrayList<String> columns = new ArrayList<>();
        //columns.add("IDentificativo");
        columns.add("Name");
        columns.add("Surname");
        columns.add("eta'");

        for (int count = 0; count < columns.size(); count++) {
            Cell cell = headerRow.createCell(count);
            cell.setCellValue(columns.get(count));
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        for (int count = 0; count < columns.size(); count++) {
            sheet.autoSizeColumn(count);
        }
        try {
            fileOut = new FileOutputStream(nomeEvento+".xls");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            generated = false;
        } catch (IOException e1) {
            e1.printStackTrace();
            generated = false;
        }
        return generated;
    }

    public ArrayList<Invitato> readXlsGuests(String nomeEvento){
        //boolean done = true;

        ArrayList<Invitato> invitati= new ArrayList<Invitato>(2);
        try {
            String file = nomeEvento+".xls";
            FileInputStream excelFile = new FileInputStream(new File(file));
            workbook = new HSSFWorkbook(excelFile);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            Iterator iterator = dataTypeSheet.iterator();
            iterator.next();

            while (iterator.hasNext()) {
                Invitato fantoccio=null,elemento=null;
                String name=null,surname=null;
                int eta=-1;
                Row currentRow = (Row) iterator.next();
                Iterator cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {
                    Cell currentCell = (Cell) cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if(currentCell.getColumnIndex()==0){
                            name=currentCell.getStringCellValue();
                        }else if(currentCell.getColumnIndex()==1){
                            surname=currentCell.getStringCellValue();
                        }
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        eta=(int)currentCell.getNumericCellValue();
                    }
                    if (!cellIterator.hasNext()) {
                        fantoccio = new Invitato(name, surname, eta);
                        invitati.add(fantoccio);
                    }

                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return invitati;
    }

    public boolean reWriteXls(String nomeEvento,ArrayList<Invitato> invitati){
        ArrayList <String> columns= new ArrayList<>();
        columns.add("ID_Invitato");
        columns.add("Onorevole");
        columns.add("Difficoltà motorie");
        columns.add("Vegetariano");
        columns.add("VicinoTv");
        columns.add("Bambini");
        columns.add("Isolato");
        columns.add("preferenza 1");
        columns.add("preferenza 2");
        columns.add("avversione 1");
        columns.add("avversione 2");
        try {
            FileInputStream fIPS= new FileInputStream(nomeEvento+".xls"); //Read the spreadsheet that needs to be updated
            HSSFWorkbook wb;
            HSSFSheet worksheet;
            if(fIPS.available()>=0) {
                wb = new HSSFWorkbook(fIPS); //If there is already data in a workbook
                worksheet = wb.getSheetAt(0);
            }else{
                wb = new HSSFWorkbook();    //if the workbook was just created
                worksheet = wb.getSheet(nomeEvento);
            }
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);


            //Access the worksheet, so that we can update / modify it
            //modify Attribute of columns
            HSSFRow row = worksheet.getRow(0);  //0 = row number

            for(int i=3;i<(columns.size()+3);i++){//+3

            Cell c = row.createCell(i);
            c.setCellType(CellType.STRING);
            c.setCellValue(columns.get(i-3));//
            }

            for (int i=1;i<=invitati.size()+1;i++){
                row=worksheet.getRow(i);
                if (row==null){
                    break;
                }
                Cell c = row.createCell(3);
                c.setCellType(CellType.STRING);
                c.setCellValue(invitati.get(i-1).getID_Inv());
            }

            //add campi specificaTavolo e specificaInvitato
            for (int i=1;i<invitati.size()+1;i++){
                row=worksheet.getRow(i);
                if (row==null){
                    break;
                }
                for(int j=4;j<4+columns.size();j++){
                    Cell c= row.createCell(j);
                    c.setCellType(CellType.STRING);
                }
            }

            fIPS.close(); //Close the InputStream
            FileOutputStream output_file =new FileOutputStream(nomeEvento+".xls");//Open FileOutputStream to write updates
            wb.write(output_file); //write changes
            wb.close();
            output_file.close();  //close the stream
            openfile(nomeEvento+".xls");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    public boolean openfile(String nameFile) throws IOException{
        File file = new File(nameFile);
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return false;
        }
        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
        desktop.open(file);
        return true;
    }

    public ArrayList<SpecificaTavolo> readSpecificheTavolo(String nameEvent){
        ArrayList<SpecificaTavolo> vincoliTavoli = new ArrayList<>();
        int campiVincolo[] = new int[6];
        try {
            String file = nameEvent+".xls";
            String contenuto= null ;
            FileInputStream excelFile = new FileInputStream(new File(file));
            workbook = new HSSFWorkbook(excelFile);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            Iterator iterator = dataTypeSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row currentRow = (Row) iterator.next();
                Iterator cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = (Cell) cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if(currentCell.getColumnIndex()==3)
                            contenuto = currentCell.getStringCellValue();
                    }
                    if(currentCell.getCellTypeEnum()==CellType.NUMERIC){
                        if( currentCell.getColumnIndex()>=4 && currentCell.getColumnIndex()<=9)
                            campiVincolo[currentCell.getColumnIndex()-4] = (int)currentCell.getNumericCellValue();
                      }
                    if(currentCell.getCellTypeEnum()==CellType.BLANK){
                        if( currentCell.getColumnIndex()>=4 && currentCell.getColumnIndex()<=9)
                            campiVincolo[currentCell.getColumnIndex()-4]=0;
                    }
                    if (!cellIterator.hasNext())
                        vincoliTavoli.add(splitVincoliTavolo(nameEvent,contenuto,campiVincolo));
                }
            }
            readPreferenzeInvitato(nameEvent);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return vincoliTavoli;
    }

    public SpecificaTavolo splitVincoliTavolo(String nomeEvento,String soggetto,int [] campiVincolo)throws DatabaseException, DatabaseNullException{
        SpecificaTavolo sp;
        int i=0;
        sp = new SpecificaTavolo(nomeEvento,soggetto,campiVincolo[i],campiVincolo[i+1],campiVincolo[i+2],campiVincolo[i+3],campiVincolo[i+4],campiVincolo[i+5]);
        ConnessioneDB connessione = new ConnessioneDB();
        connessione.inserisciVincoliTavolo(nomeEvento,soggetto,campiVincolo[i],campiVincolo[i+1],campiVincolo[i+2],campiVincolo[i+3],campiVincolo[i+4],campiVincolo[i+5]);
        return sp;

    }

    public ArrayList<PreferenzaInvitato> readPreferenzeInvitato(String nameEvent) throws IOException, DatabaseException, DatabaseNullException{
        campiPreferenze = new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(nameEvent+".xls"));
            workbook = new HSSFWorkbook(excelFile);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            Iterator iterator = dataTypeSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row currentRow = (Row) iterator.next();
                Iterator cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = (Cell) cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if(currentCell.getColumnIndex()==3)
                            campiPreferenze.add(currentCell.getStringCellValue());
                        if(currentCell.getColumnIndex()>=10 && currentCell.getColumnIndex()<=13)
                            campiPreferenze.add(currentCell.getStringCellValue());
                    } else
                        if (currentCell.getColumnIndex() >= 10 && currentCell.getColumnIndex() <= 13)
                            campiPreferenze.add(currentCell.getStringCellValue());
                    if (!cellIterator.hasNext())
                        vincoliPreferenze.add(splitPreferenze(nameEvent,campiPreferenze));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return vincoliPreferenze;
    }

    public PreferenzaInvitato splitPreferenze(String nameEvent,ArrayList<String> campiPreferenze)throws DatabaseException, DatabaseNullException{
        PreferenzaInvitato pref = null;
        String preferenze = null;
        String avversione = null;
        ConnessioneDB connessione = new ConnessioneDB();
        //PREFERENZE
        if ((campiPreferenze.get(1)== null && campiPreferenze.get(2) !=null))
            preferenze = campiPreferenze.get(2);
        if ((campiPreferenze.get(1)!= null && campiPreferenze.get(2) ==null))
            preferenze = campiPreferenze.get(1);
        if ((campiPreferenze.get(1)!= null && campiPreferenze.get(2) !=null))
            preferenze = campiPreferenze.get(1)+" "+campiPreferenze.get(2);
        //AVVERSIONE
        if ((campiPreferenze.get(3)== null && campiPreferenze.get(4) !=null))
            avversione = campiPreferenze.get(4);
        if ((campiPreferenze.get(3)!= null && campiPreferenze.get(4) ==null))
            avversione = campiPreferenze.get(3);
        if ((campiPreferenze.get(3)!= null && campiPreferenze.get(4) !=null))
            avversione = campiPreferenze.get(3)+" "+campiPreferenze.get(4);
        pref = new PreferenzaInvitato(nameEvent,campiPreferenze.get(0),preferenze,avversione);
        connessione.inserisciVincoloInvitati(nameEvent,campiPreferenze.get(0),preferenze,avversione);
        this.campiPreferenze.clear();
        return pref;
    }

}




