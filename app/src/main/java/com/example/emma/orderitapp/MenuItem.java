package com.example.emma.orderitapp;

import java.io.Serializable;

/**
 * Model for a menu item scanned.
 */

public class MenuItem implements Serializable {

    private String name;
    private String description;
    private String price;
    private String quantity;

    // Constructor
    public MenuItem() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public String toString() {

        return "Menu Item: " + name + "\nDescription: " + description + "\nPrice: " + price;
    }
}