package facade;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import persone.Invitato;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XlsFacade {
    Workbook workbook;

    FileOutputStream fileOut = null;
    private Sheet sheet;
    private String xlsGuest = "Guest.xls", xlsVincoli = "Vincoli.xls";

    public XlsFacade() {

       // new HSSFWorkbook() for generating `.xls` file

    }

    public boolean generateXlsGuests(String nomeEvento){
        workbook = new HSSFWorkbook();

        boolean generated = true;


        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();
        // Create a Sheet
        sheet = workbook.createSheet(nomeEvento+" la vendetta");
        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        ArrayList<String> columns = new ArrayList<>();
        columns.add("IDentificativo");
        columns.add("Name");
        columns.add("Surname");
        columns.add("eta'");


        // Create cells
        for (int count = 0; count < columns.size(); count++) {
            Cell cell = headerRow.createCell(count);
            cell.setCellValue(columns.get(count));
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;

        // Resize all columns to fit the content size
        for (int count = 0; count < columns.size(); count++) {
            sheet.autoSizeColumn(count);
        }


        try {
            fileOut = new FileOutputStream(nomeEvento+".xls");
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
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

    


}



