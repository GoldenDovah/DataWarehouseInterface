package com.example.application.controllers;

import com.example.application.model.Order;
import com.example.application.model.Person;

import java.util.List;
import java.util.Objects;

public class SalesAmountSummarizer extends Thread implements DataTransformer{

    private List<Person> data;
    List<Order> orders;
    public SalesAmountSummarizer(List<Person> data, List<Order> orders){
        this.data = data;
        this.orders = orders;
    }
    @Override
    public void transformData(List<Person> data) {
        for (Person person : data){
            for (Order order : orders){
                //System.out.println("Person KEY: "+person.getKey()+", OrderID: "+order.getCustomerId());
                if (Objects.equals(person.getKey(), order.getCustomerId())){
                    person.addSalesAmount(Double.parseDouble(order.getTotalAmount()));
                }
            }
        }
    }

    @Override
    public void run() {
        this.transformData(this.data);
    }
}
