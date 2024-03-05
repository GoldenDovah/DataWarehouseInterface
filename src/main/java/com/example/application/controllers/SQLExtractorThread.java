package com.example.application.controllers;

import com.example.application.model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLExtractorThread extends Thread{
    private int indexStart, indexEnd;
    private List<Person> data;
    private final Connection connection;
    public SQLExtractorThread(int indexStart, int indexEnd, List<Person> data, Connection connection) throws SQLException, ClassNotFoundException {
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.data = data;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            String selectSql = "SELECT EmployeeKey, FirstName, LastName, EmailAddress, Phone FROM dbo.DimEmployee WHERE EmployeeKey BETWEEN "+this.indexStart+" AND "+this.indexEnd;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                        );
                data.add(person);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
