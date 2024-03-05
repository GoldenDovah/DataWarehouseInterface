package com.example.application.controllers;

import com.example.application.model.Person;

import java.util.List;

public interface DataTransformer {

    void transformData(List<Person> data);
}
