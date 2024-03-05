package com.example.application.controllers;

import com.example.application.controllers.SQLServerExtractor;
import com.example.application.model.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.stream.Stream;

public class Extract {
    public Extract(String tableName, String databaseName, String user, String password, String CSVName, String ExcelName) throws SQLException, ClassNotFoundException, InterruptedException {
        SQLServerExtractor sqlServerExtractor = new SQLServerExtractor(tableName, databaseName, user, password);
        sqlServerExtractor.extractData();
        //sqlServerExtractor.afficherResume();
        List<Person> adventureWorks = sqlServerExtractor.getData();
        CSVFileExtractor csvFileExtractor = new CSVFileExtractor(CSVName);
        csvFileExtractor.extractData();
        //csvFileExtractor.afficherResume();
        List<Person> sampleCSV = csvFileExtractor.getData();
        ExcelFileExtractor excelFileExtractor = new ExcelFileExtractor(ExcelName);
        excelFileExtractor.extractData();
        //excelFileExtractor.afficherResume();
        List<Person> sampleXLS = excelFileExtractor.getData();
        List<Person> personData = Stream.of(adventureWorks, sampleCSV, sampleXLS)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        DataWarehouse dataWarehouse = new DataWarehouse(
                1000,
                adventureWorks.size()+sampleCSV.size()+sampleXLS.size(),
                personData
        );

        dataWarehouse.loadPersonData();
    }
}