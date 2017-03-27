/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mailp;

/**
 *
 * @author Rastislav
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler {

    public void Excel(List<ArrayList<String>> export,String filename)throws IOException {
//        Definice workbooku
        Workbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("Sheet1");

// Zadefinovani hlavicky
        Row row1 = sheet.createRow(0);
        Cell cell_head0 = row1.createCell(0);
        cell_head0.setCellValue("Odesilatel");
        Cell cell_head1 = row1.createCell(1);
        cell_head1.setCellValue("Datum");
        Cell cell_head2 = row1.createCell(2);
        cell_head2.setCellValue("Subjekt");
        Cell cell_head3 = row1.createCell(3);
        cell_head3.setCellValue("Obsah emailu");

//        Precti file
       // List<String> lines = Files.readAllLines(Paths.get("newfile.txt"));
        
//        smycka nad radky
        int row_index = 1;
        for (List<String> email : export) {
            //index noveho radku, zacina od 1, protoze jiz mame na indexu 0 zadefinovanou hlavicku
           

            String[] words;//oddělí v texťáku data oddělené čárkou

            Row row = sheet.createRow(row_index);
//            parsovani na jednotliva slova
            //words = line.split(",");
            int email_size = email.size();
//            smycka nad slovy
            int word_index = 0;
            for(String part:email){
                //zacina se od prvniho slova a ->index 0
                if (part==null) part = "";
                part = part.trim();
                if (part.length()>=32767) part = part.substring(0, 32760);
                Cell cell = row.createCell(word_index);
                cell.setCellValue(part);
                word_index++;
            }
//            for (int i = 0; i < email_size; i++) {
//                String word = email[i].trim();
//                
//                Cell cell = row.createCell(i);
//                cell.setCellValue(word);
//            }
            row_index++;
        }

        FileOutputStream fileOut = new FileOutputStream(filename);
        wb.write(fileOut);
        fileOut.close();
    }

}
