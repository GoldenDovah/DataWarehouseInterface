package com.example.application.controllers;

import com.example.application.model.Order;
import com.example.application.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLServerExtractor extends DataExtractorAbstrait implements Affichable{
    protected String tableName;
    private String databaseName;
    private String user;
    private String password;

    public SQLServerExtractor(String tableName, String databaseName, String user, String password){
        this.tableName = tableName;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
    }

    private static Connection connection;

    public void connectToDB() throws ClassNotFoundException, SQLException {
        this.data = new ArrayList<Person>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl =
                "jdbc:sqlserver://DESKTOP-8GR1LGA\\SQLSERVER2019:1433;"
                        + "database="+this.databaseName+";"
                        + "user="+this.user+";"
                        + "password="+this.password+";"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        this.connection = DriverManager.getConnection(connectionUrl);
    }

    public void extractData() throws SQLException, ClassNotFoundException, InterruptedException {
        connectToDB();
        String selectSql = "SELECT count(*) FROM dbo."+this.tableName;
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSql);
        resultSet.next();
        int count = resultSet.getInt(1);
        List<SQLExtractorThread> sqlExtractorThreads = new ArrayList<SQLExtractorThread>();
        for (int i = 0; i < count; i += 100) {
            SQLExtractorThread sqlExtractorThread = new SQLExtractorThread(
                    i,
                    i+100,
                    this.data,
                    this.connection
            );
            sqlExtractorThread.start();
            sqlExtractorThreads.add(sqlExtractorThread);
        }
        for (SQLExtractorThread sqlExtractorThread : sqlExtractorThreads){
            sqlExtractorThread.join();
        }
    }


    @Override
    public void afficherResume() {
        // TODO Auto-generated method stub
        System.out.println("Nombres des lignes de Data SQL: "+this.data.size());
        System.out.println("\n10 premier lignes de Data SQL: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(this.data.get(i));
        }
    }

    public static List<Order> getOrdersData(){
        List<Order> orders = new ArrayList<>();
        try {
            String selectSql = "SELECT OrderID, OrderDate, CustomerID, TotalAmount FROM dbo.SalesOrderData";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                orders.add(order);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

}