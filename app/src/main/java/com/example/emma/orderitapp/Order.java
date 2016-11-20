package com.example.emma.orderitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.emma.orderitapp.MenuItem;

/**
 * Model of a Take Out Order.
 * <p>
 * An Order object is an array list of MenuItems.
 * <p>
 * to do: deleteItem method. Turn the string array into an
 * Item object with methods Item.getPrice, Item.getQuantity, etc.
 */

public class Order {

    // Order object is an arraylist of Menu Items.
    private ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    private OrderDatabase dbManager;
    private Restaurant restaurant;
    private String orderNumber;
    private SharedPreferences prefs;

    /**
     * Constructor
     *
     * @param c
     */

    public Order(Context c) {
        dbManager = new OrderDatabase(c);
        restaurant = new Restaurant(c);
        PreferenceManager.setDefaultValues(c, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        orderNumber = prefs.getString("order_number", "0");
    }


    /**
     * Method: setOrderNumber increments the Order Number plus one.
     */

    public void setOrderNumber() {
        int o = Integer.parseInt(orderNumber);
        o++;
        orderNumber = Integer.toString(o);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("order_number", orderNumber);
        editor.apply();
    }

    /**
     * Method: getOrderNumber
     *
     * @return a string containing the current Order Number.
     */

    public String getOrderNumber() {
        return orderNumber;
    }


    public void addItem(String item, String quantity, String price) {
        // if (item already in list)
        // increment quantity
        //else
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dbManager.insert(date, restaurant.getName(), item, quantity, price, orderNumber);
    }


    public void deleteItem(MenuItem item) {
        // remove item from list

    }


    /**
     * Method: getOrder
     *
     * @return an arraylist of Menu Items in the current Order.
     */

    public ArrayList<MenuItem> getOrder() {

        ArrayList<String> itemsSA = new ArrayList<String>();
        itemsSA = dbManager.selectByColumn("orderNumber", orderNumber);
        MenuItem item = new MenuItem();
        for (String s : itemsSA) {
            String[] sa = s.split("::");
            item.setPrice(sa[4]);
            item.setName(sa[2]);
            item.setQuantity(sa[3]);
            items.add(item);
        }
        return items;
    }
}
