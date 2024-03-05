package com.example.application.controllers;

import java.sql.SQLException;
public interface DataExtractor {
    public void extractData() throws SQLException, ClassNotFoundException, InterruptedException;
}