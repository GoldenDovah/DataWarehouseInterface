package com.example.application.model;

public class Person {

    private String key;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private double totalSalesAmount;

    public Person(String key, String firstName, String lastName, String email, String phone){
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.totalSalesAmount = 0;
    }

    public String getKey() {
        return key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void addSalesAmount(double amount){
        this.totalSalesAmount += amount;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }
}
