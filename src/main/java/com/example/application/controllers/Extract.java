package com.example.application.controllers;

import com.example.application.model.Person;

import java.sql.*;
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
    private static Connection connectionAzure;

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

    public static void connectToAzure(String server, String database, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl =
                "jdbc:sqlserver://"+server+";"
                        + "database="+database+";"
                        + "user="+user+";"
                        + "password="+password+";"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        connectionAzure = DriverManager.getConnection(connectionUrl);
    }

    public static void createTableInAzure(String table) throws SQLException {
        String createTableSQL = "CREATE TABLE "+table+" ("
                + "id INT PRIMARY KEY IDENTITY,"
                + "first_name VARCHAR(255),"
                + "last_name VARCHAR(255),"
                + "email VARCHAR(255),"
                + "phone VARCHAR(255),"
                + "total_sales_amount INT"
                + ")";
        System.out.println(createTableSQL);
        Statement statement = connectionAzure.createStatement();
        statement.executeUpdate(createTableSQL);
        System.out.println("Table created successfully!");
    }

    public static void uploadToCloud(String table) throws SQLException {
        try {
            createTableInAzure(table);
        } catch (SQLException e){
            System.out.println(e);
            System.out.println("Table most likely already exists");
        }
        String insertDataSQL;
        for (Person person: DataWarehouse.getPersonData()){
            insertDataSQL = "INSERT INTO "+table+" (first_name, last_name, email, phone, total_sales_amount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connectionAzure.prepareStatement(insertDataSQL);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setString(4, person.getPhone());
            preparedStatement.setDouble(5, person.getTotalSalesAmount());
            preparedStatement.executeUpdate();
        }
    }
}