package com.example.emma.orderitapp;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.emma.orderitapp.MenuItem;

/**
 * An Order object is currently an array list of strings.
 * Each string is an item composed of "Date Restaurant Item, Quantity, Price"
 *
 * Methods:
 *
 * Constructor Order(Context)
 *
 * addItem(item, quantity, price)
 *
 * getOrder() returns a string array of items.
 *
 *
 * to do: deleteItem method. Turn the string array into an
 * Item object with methods Item.getPrice, Item.getQuantity, etc.
 *
 *
 */

public class Order {

    private ArrayList<String> items = new ArrayList<String>();
    private  OrderDatabase dbManager;
    Restaurant restaurant;


    // Constructor
    public Order(Context c) {
        dbManager = new OrderDatabase(c);
        restaurant = new Restaurant(c);
    }

    public void addItem(String item, String quantity, String price){
        // if (item already in list)
        // increment quantity
        //else
        String date= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dbManager.insert(date, restaurant.getName(), item, quantity, price);
        //
        //items.add(item);
    }

    public void deleteItem(MenuItem item){
        // remove item from list

    }

    public ArrayList<String> getOrder(){
        String date= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        items = dbManager.selectByColumn( "date", date);
        return items;
    }
}
