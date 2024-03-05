package com.example.application.controllers;

import com.example.application.controllers.DataExtractor;
import com.example.application.model.Person;

import java.sql.SQLException;
import java.util.List;

abstract class DataExtractorAbstrait implements DataExtractor {
    protected String tableName;
    protected Object jdbcConnection;
    protected List<Person> data;

    public void extractData() throws SQLException, ClassNotFoundException, InterruptedException {
    }

    public List<Person> getData(){
        return this.data;
    }
}
