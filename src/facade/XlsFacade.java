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
    private CreationHelper createHelper = null;
    FileOutputStream fileOut = null;
    private Sheet sheet;
    private String name,surname;
    private int eta;
    private Cell cell;
    private ArrayList<String> campiPreferenze,columns;
    private ArrayList<PreferenzaInvitato> vincoliPreferenze = new ArrayList<>();
    private CellStyle headerCellStyle;


    public boolean generateXlsGuests(String nomeEvento){
        boolean generated = true;
        columns= new ArrayList<>();
        setInitialStyle(nomeEvento);
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        for (int count = 0; count < columns.size()+10; count++)
            sheet.autoSizeColumn(count);
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

    public void setInitialStyle(String nomeEvento){
        workbook = new HSSFWorkbook();
        createHelper = workbook.getCreationHelper();
        sheet = workbook.createSheet(nomeEvento);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        columns.add("Nome");
        columns.add("Cognome");
        columns.add("Eta'");
        for (int count = 0; count < 14; count++) {
            cell = headerRow.createCell(count);
            cell.setCellStyle(headerCellStyle);
            if (count < 3)
                cell.setCellValue(columns.get(count));
        }
    }

    public ArrayList<Invitato> readXlsGuests(String nomeEvento){
        ArrayList<Invitato> invitati= new ArrayList<Invitato>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(nomeEvento+".xls"));
            workbook = new HSSFWorkbook(excelFile);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            Iterator iterator = dataTypeSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                //Invitato fantoccio=null;
                Row currentRow = (Row) iterator.next();
                Iterator cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = (Cell) cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if(currentCell.getColumnIndex()==0)
                            name=currentCell.getStringCellValue();
                        else if(currentCell.getColumnIndex()==1)
                            surname=currentCell.getStringCellValue();
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC)
                        eta=(int)currentCell.getNumericCellValue();
                    if (!cellIterator.hasNext())
                        invitati.add(new Invitato(name, surname, eta));
                }
            }
            excelFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return invitati;
    }

    public boolean reWriteXls(String nomeEvento,ArrayList<Invitato> invitati) {
        ArrayList <String> columns= new ArrayList<>();

        try {
            FileInputStream fIPS= new FileInputStream(nomeEvento+".xls"); //Read the spreadsheet that needs to be updated
            Workbook wb;
            Sheet worksheet;
            if(fIPS.available()>=0) {
                wb = new HSSFWorkbook(fIPS); //If there is already data in a workbook
                worksheet = wb.getSheetAt(0);
            }else{
                wb = new HSSFWorkbook();    //if the workbook was just created
                worksheet = wb.getSheet(nomeEvento);
            }
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
            Row row = worksheet.getRow(0);  //0 = row number
            for(int i=3;i<(columns.size()+3);i++){
            Cell c = row.createCell(i,CellType.STRING);
            //c.setCellStyle(headerCellStyle);
            c.setCellValue(columns.get(i-3));
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
            FileOutputStream output_file = new FileOutputStream(nomeEvento + ".xls");//Open FileOutputStream to write updates

            wb.write(output_file); //write changes
            wb.close();
            output_file.close();  //close the stream
            openfile(nomeEvento+".xls");

        } catch (FileNotFoundException e) {
            System.err.println("Attenzione "+", ricordati di salvare il file e chiuderlo per continuare!");

            return false;
        } catch (IOException e) {
            System.err.println("Input OutpuT Exception, qualcosa è andato storto!");
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




