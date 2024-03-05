package com.example.application.controllers;

import com.example.application.model.Person;

import java.util.List;

public class UpperCaseTransformer extends Thread implements DataTransformer {

    private List<Person> data;
    public UpperCaseTransformer(List<Person> data){
        this.data = data;
    }

    @Override
    public void transformData(List<Person> data) {
        for (Person person : data){
            person.setLastName(person.getLastName().toUpperCase());
        }
    }

    @Override
    public void run() {
        this.transformData(this.data);
    }
}
