package com.example.application.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.application.model.Person;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import javax.lang.model.type.PrimitiveType;

public class ExcelFileExtractor extends DataExtractorAbstrait implements Affichable{
    protected String tableName;
    protected Workbook jdbcConnection;

    public ExcelFileExtractor(String tableName){
        this.tableName = tableName;
    }

    public void extractData() {
        data = new ArrayList<Person>();
        try {
            File excel = new File(tableName);
            jdbcConnection = Workbook.getWorkbook(excel);
            Sheet sheet = jdbcConnection.getSheet(0);
            int rows = sheet.getRows();
            int columns = sheet.getColumns();

            for (int i = 0; i < rows; i++) {
                data.add(new Person(
                        sheet.getCell(0, i).getContents(),
                        sheet.getCell(1, i).getContents(),
                        sheet.getCell(2, i).getContents(),
                        sheet.getCell(3, i).getContents(),
                        sheet.getCell(4, i).getContents()
                ));
            }
            data.remove(0);
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void afficherResume() {
        System.out.println("\n10 premier lignes de Data Excel: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(data.get(i));
        }
    }
}
