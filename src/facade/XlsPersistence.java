package facade;

import database.*;
import locale.Evento;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import persone.*;
import vincoli.PreferenzaInvitato;
import vincoli.SpecificaTavolo;

import java.awt.*;
import java.io.*;
import java.util.*;

//questa classe si occupa di generare l'excel, prelevare i dati,elaborarli, inserirli nel database e restituirli
public class XlsPersistence {
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
    private String stringEndReading = "X X X X X";

    //genera l'excel
    public boolean generateXlsGuests(String nomeEvento){
        boolean generated = true;
        columns= new ArrayList<>();
        setInitialStyle(nomeEvento);
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
    //setta lo stile iniziale
    public void setInitialStyle(String nomeEvento){
        Evento evento=Facade.getInstance().getEvento(nomeEvento);
        workbook = new HSSFWorkbook();
        createHelper = workbook.getCreationHelper();
        sheet = workbook.createSheet(nomeEvento);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        Row headerRow = sheet.createRow(0);
        Row endRow= sheet.createRow(evento.getNumInvitati()+1);
        columns.add("Nome");
        columns.add("Cognome");
        columns.add("Eta'");

        for (int count = 0; count < columns.size()+10; count++) {
            cell = headerRow.createCell(count);
            cell.setCellStyle(headerCellStyle);
            if(count<3)
                cell.setCellValue(columns.get(count));
            cell = endRow.createCell(count);
            cell.setCellValue(stringEndReading);
        }



    }
    //legge il file excel e preleva  dati
    public ArrayList<Invitato> readXlsGuests(String nomeEvento){
        ArrayList<Invitato> invitati= new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(nomeEvento+".xls"));
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
                        if(currentCell.getStringCellValue().equals(stringEndReading))
                            break;
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
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return invitati;
    }
    //riscrive il file aggiornando i campi
    public boolean reWriteXls(String nomeEvento,ArrayList<Invitato> invitati) {
        try {
            FileInputStream fIPS= new FileInputStream(nomeEvento+".xls");
                workbook = new HSSFWorkbook(fIPS);
                sheet = workbook.getSheetAt(0);
            uploadColumns();
            Row row = sheet.getRow(0);
            for(int i=3;i<(columns.size());i++){
                Cell c = row.createCell(i,CellType.STRING);
                c.setCellValue(columns.get(i));
            }
            //adatta le colonne
            for (int count = 0; count < columns.size()+3; count++)
                sheet.autoSizeColumn(count);
            //crea le righe nell'excel e le implementa
            for (int i=1;i<=invitati.size()+1;i++){//
                row=sheet.getRow(i);
                if (row==null){ break; }

                Cell c = row.createCell(3);
                c.setCellType(CellType.STRING);
                if(i==invitati.size()+1){
                    c.setCellValue(stringEndReading);
                    break; }
                c.setCellValue(invitati.get(i-1).getID_Inv());
            }
            //aggiunge i campi specificaTavolo e specificaInvitato
            for (int i=1;i<=invitati.size();i++){
                row=sheet.getRow(i);
                if (row==null){
                    break;
                }
                for(int j=4;j<4+columns.size();j++){
                    Cell c= row.createCell(j);
                    c.setCellType(CellType.STRING);
                }
            }
            fIPS.close();
            FileOutputStream output_file = new FileOutputStream(nomeEvento + ".xls");//Open FileOutputStream to write updates
            workbook.write(output_file);
            workbook.close();
            output_file.close();
            openfile(nomeEvento+".xls");
        } catch (IOException e) {
            System.err.println("Attenzione "+", ricordati di salvare il file e chiuderlo per continuare!");
            return false;
        }
        return true;
    }

    private void uploadColumns() {
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
    }


    public boolean openfile(String nameFile) throws IOException{
        File file = new File(nameFile);
        if(!Desktop.isDesktopSupported()){
            System.err.println("Desktop is not supported");
            return false;
        }
        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
            desktop.open(file);
        return true;
    }
    //legge nell'excel le specifiche del tavolo
    public ArrayList<SpecificaTavolo> readSpecificheTavolo(String nameEvent){
        ArrayList<SpecificaTavolo> vincoliTavoli = new ArrayList<>();
        int campiVincolo[] = new int[6];
        try {
            String contenuto= null ;
            FileInputStream excelFile = new FileInputStream(new File(nameEvent+".xls"));
            workbook = new HSSFWorkbook(excelFile);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            Iterator iterator = dataTypeSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                campiVincolo=new int[]{0,0,0,0,0,0};
                Row currentRow = (Row) iterator.next();
                Iterator cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = (Cell) cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if(currentCell.getColumnIndex()==3)
                            contenuto = currentCell.getStringCellValue();
                        if( currentCell.getColumnIndex()>=4 && currentCell.getColumnIndex()<=9 && currentCell.getStringCellValue().equals(""))
                            campiVincolo[currentCell.getColumnIndex()-4]=0;
                        if(currentCell.getStringCellValue().equals(stringEndReading))
                            break;
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
            System.err.println(ex.getMessage());
        }
        return vincoliTavoli;
    }

    //crea l'oggetto vincolo tavolo riga per riga e aggiorna il database
    public SpecificaTavolo splitVincoliTavolo(String nomeEvento,String soggetto,int [] campiVincolo)throws DatabaseException, DatabaseNullException{
        SpecificaTavolo sp;
        int i=0;
        sp = new SpecificaTavolo(nomeEvento,soggetto,campiVincolo[i],campiVincolo[i+1],campiVincolo[i+2],campiVincolo[i+3],campiVincolo[i+4],campiVincolo[i+5]);
        Facade.getInstance().inserisciVincoliTavolo(nomeEvento,soggetto,campiVincolo[i],campiVincolo[i+1],campiVincolo[i+2],campiVincolo[i+3],campiVincolo[i+4],campiVincolo[i+5]);
        return sp;
    }
    //legge le preferenze invitato e instanzia gli oggetti aggiornando il database
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
            System.err.println(ex.getMessage());
        }
        return vincoliPreferenze;
    }
    //crea l'oggetto preferenza aggiornando il database
    public PreferenzaInvitato splitPreferenze(String nameEvent,ArrayList<String> campiPreferenze)throws DatabaseException, DatabaseNullException{
        PreferenzaInvitato pref = null;
        String preferenze = null;
        String avversione = null;
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
        Facade.getInstance().inserisciVincoloInvitati(nameEvent,campiPreferenze.get(0),preferenze,avversione);
        this.campiPreferenze.clear();
        return pref;
    }


}