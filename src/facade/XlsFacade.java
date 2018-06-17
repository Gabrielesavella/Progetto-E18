package facade;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.*;
import org.apache.poi.ss.usermodel.Font;
import persone.Invitato;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class XlsFacade {
    Workbook workbook;

    FileOutputStream fileOut = null;
    private Sheet sheet;
    private String xlsGuest = "Guest.xls", xlsVincoli = "Vincoli.xls";


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
        columns.add("IDentificativo");
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


        int rowNum = 1;


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

    public boolean readXlsGuests(String nomeEvento){
        boolean done = true;

        try {
            String file = nomeEvento+".xls";

            FileInputStream excelFile = new FileInputStream(new File(file));
            workbook = new HSSFWorkbook(excelFile);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            Iterator iterator = dataTypeSheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = (Row) iterator.next();
                Iterator cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = (Cell) cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "  ");
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + " anni ");
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            done = false;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            done = false;
        }
        return done;
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

    


}



