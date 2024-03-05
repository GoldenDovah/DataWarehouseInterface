package com.example.application.model;

public class Order {

    private String id;
    private String date;
    private String customerId;
    private String totalAmount;

    public Order(String id, String date, String customerId, String totalAmount){
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
