package com.example.application.controllers;

import com.example.application.model.Order;
import com.example.application.model.Person;

import java.sql.*;
import java.util.List;

public class DataWarehouse{
    private static List<Person> personData;
    private List<List<String>> salesOrderData;
    private final int totalRecordCount;
    private final int threadCount;
    private int threadTrigger;
    private Connection connection;

    public DataWarehouse(int threadTrigger, int totalRecordCount, List<Person> personData){
        this.threadTrigger = threadTrigger;
        this.totalRecordCount = totalRecordCount;
        this.personData = personData;
        this.threadCount = (int) Math.ceil((double) this.totalRecordCount/this.threadTrigger);
    }

    public static List<Person> getPersonData() {
        return personData;
    }

    private Connection connectToDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl =
                "jdbc:sqlserver://DESKTOP-8GR1LGA\\SQLSERVER2019:1433;"
                        + "database=TP;"
                        + "user=tp;"
                        + "password=admin;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        return DriverManager.getConnection(connectionUrl);
    }

    public void loadPersonData() throws SQLException, ClassNotFoundException {
        connection = connectToDB();
        for (int i = 0; i < this.totalRecordCount; i += this.threadTrigger) {
            DwThread dwThread = new DwThread(i, i+this.threadTrigger, personData, connection);
            dwThread.start();
        }
    }

    private List<Order> loadSalesOrderData() {
        return SQLServerExtractor.getOrdersData();
    }

    private void checkAndTriggerThread() {

    }

    public void transformData() throws InterruptedException {
        UpperCaseTransformer upperCaseThread = new UpperCaseTransformer(this.personData);
        SalesAmountSummarizer salesAmountThread = new SalesAmountSummarizer(this.personData, this.loadSalesOrderData());
        upperCaseThread.start();
        salesAmountThread.start();
        upperCaseThread.join();
        salesAmountThread.join();
    }

}