package com.example.application.controllers;

import java.sql.DriverManager;
import java.sql.SQLException;

public class AzureInserter {

    public void connectToDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        /*
        String connectionUrl =
                "jdbc:sqlserver://sdsidatawarehouse.database.windows.net;"
                        + "database="+this.databaseName+";"
                        + "user="+adminsdsi+";"
                        + "password="+this.password+";"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        this.connection = DriverManager.getConnection(connectionUrl);
         */
    }
}
