package com.example.application.controllers;

import com.example.application.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class CSVFileExtractor extends DataExtractorAbstrait implements Affichable{
    protected String tableName;

    public CSVFileExtractor(String tableName){
        this.tableName = tableName;
    }

    public void extractData(){
        data = new ArrayList<Person>();
        try {
            Scanner sc;
            sc = new Scanner(new File(tableName));
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] values = line.split(",");
                Person person = new Person(values[0], values[1], values[2], values[3], values[4]);
                data.add(person);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void afficherResume() {
        System.out.println("\n10 premier lignes de Data CSV: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(data.get(i));
        }
    }

}
