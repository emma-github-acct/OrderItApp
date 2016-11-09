package com.example.emma.orderitapp;

import java.util.ArrayList;
import com.example.emma.orderitapp.MenuItem;

/**
 * An Order object is an arraylist of menu items which have been scanned.
 */

public class Order {

    private ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    public void addItem(MenuItem item){
        // if (item already in list)
        // increment quantity
        //else
        items.add(item);
    }

    public void deleteItem(MenuItem item){
        // remove item from list

    }

    public ArrayList<MenuItem> getOrder(){return items;}

}
