package com.example.scott.rest;

import java.util.ArrayList;
import com.example.scott.rest.MenuItem;

/**
 * Restaurant order object is an array of menu items.
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
