package com.example.application.controllers;

import com.example.application.model.Person;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.stream.Stream;

public class Extract {

    private static String tableName;
    private static String databaseName;
    private static String user;
    private static String password;
    private static String CSVName;
    private static String ExcelName;

    private static DataWarehouse dataWarehouse;

    public static void setCSVName(String CSVName) {
        Extract.CSVName = CSVName;
    }

    public static void setDatabaseName(String databaseName) {
        Extract.databaseName = databaseName;
    }

    public static void setExcelName(String excelName) {
        ExcelName = excelName;
    }

    public static void setPassword(String password) {
        Extract.password = password;
    }

    public static void setTableName(String tableName) {
        Extract.tableName = tableName;
    }

    public static void setUser(String user) {
        Extract.user = user;
    }

    public Extract() throws SQLException, ClassNotFoundException, InterruptedException {
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
        dataWarehouse = new DataWarehouse(
                1000,
                adventureWorks.size()+sampleCSV.size()+sampleXLS.size(),
                personData
        );
    }

    public static void transform() throws InterruptedException {
        dataWarehouse.transformData();
    }
}