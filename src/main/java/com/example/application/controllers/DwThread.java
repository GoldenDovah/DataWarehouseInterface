package com.example.application.controllers;

import com.example.application.model.Person;

import java.sql.*;
import java.util.List;

public class DwThread extends Thread{
    private int indexStart, indexEnd;
    private List<Person> data;
    private final Connection connection;
    public DwThread(int indexStart, int indexEnd, List<Person> data, Connection connection) throws SQLException, ClassNotFoundException {
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.data = data;
        this.connection = connection;
    }

    @Override
    public void run() {
        for (int i = this.indexStart; i < this.indexEnd; i++) {
            try {
                String insertSql = "INSERT INTO dbo.PersonData (personId, firstName, lastName) VALUES "
                        + "('"+data.get(i).getKey()+"', '"+data.get(i).getFirstName().replace("'", "")+"', '"+data.get(i).getLastName().replace("'", "")+"');";
                ResultSet resultSet = null;
                //System.out.println(insertSql);
                try (
                        PreparedStatement prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
                    prepsInsertProduct.execute();
                    resultSet = prepsInsertProduct.getGeneratedKeys();
                    while (resultSet.next()) {
                        //System.out.println("Generated: " + resultSet.getString(1));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e){
                System.out.println("Reached max index "+i);
                break;
            }
        }
    }
}
