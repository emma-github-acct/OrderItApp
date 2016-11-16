package com.example.emma.orderitapp;

/**
 * Created by Hannah on 11/15/16.
 */

import org.codehaus.jackson.map.annotate.JsonView;

class Views {
    static class Name {}
    public static class Price {}
}

public class OrderTest {
    @JsonView(Views.Name.class)
    private String name;

    @JsonView(Views.Price.class)
    private double price;

    public String getName() {
        return this.name;
    }

    public double getCost() {
        return this.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order Name " + name + "\nToal Cost (with all extras):" + price ;
    }


}
