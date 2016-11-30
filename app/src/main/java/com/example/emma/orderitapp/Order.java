package com.example.emma.orderitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;


import static android.content.Context.MODE_PRIVATE;

/**
 * Model of a Take Out Order.
 * <p>
 * An Order object is an array list of MenuItems.
 * <p>
 * to do: deleteItem method. Turn the string array into an
 * Item object with methods Item.getPrice, Item.getQuantity, etc.
 */

public class Order implements Serializable{

    // Order object is an arraylist of Menu Items.
    private ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    private OrderDatabase dbManager;
    private Business businessObject;
    private ArrayList<MenuItem> menuItems;

    /**
     * Constructor
     *
     * @param
     */

    public Order() {
        this.menuItems = new ArrayList<MenuItem>();
    }


    public void addItem(MenuItem item) {
        boolean isNewItem = true;

        for( MenuItem oldItem : menuItems ) {
            String name = oldItem.getName();
            if (name.equals(item.getName())){
                isNewItem = false;
                int newQuantity =
                        Integer.parseInt(oldItem.getQuantity()) + Integer.parseInt(item.getQuantity());
                oldItem.setQuantity(Integer.toString(newQuantity));
            }
        }
        if ( isNewItem ) {
            menuItems.add( item );
        }
    }


    public void deleteItem(MenuItem item) {
        menuItems.remove( item );
    }


    /**
     * Method: getOrder
     *
     * @return an arraylist of Menu Items in the current Order.
     */

    public ArrayList<MenuItem> getOrder() {
        return menuItems;
    }

}
