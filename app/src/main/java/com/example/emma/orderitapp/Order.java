package com.example.emma.orderitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.emma.orderitapp.MenuItem;

import static android.content.Context.MODE_PRIVATE;

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

        prefs = c.getSharedPreferences("businessPreferences", MODE_PRIVATE);
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
        return prefs.getString("order_number","0");
    }


    public void addItem(MenuItem newItem) {

        // if item is already in the database then increment quantity
        items = getOrder();
        for(MenuItem oldItem : items) {
            String name = oldItem.getName();
            if (name.equals(newItem.getName())){
                int newQuantity =
                        Integer.parseInt(oldItem.getQuantity()) + Integer.parseInt(newItem.getQuantity());
                newItem.setQuantity(Integer.toString(newQuantity));
                dbManager.changeQuantity(newItem, orderNumber);
                return;
            }
        }
        // else add the item to the database
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dbManager.insert(date, restaurant.getName(), newItem.getName(), newItem.getQuantity(), newItem.getPrice(), orderNumber);
    }

    //public void updateItem(String item, String quantity, String price ){
    //    dbManager.update(String item, String quantity, String price );
    //}

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
        //MenuItem item = new MenuItem();
        items.clear();
        for (int i = 0; i < itemsSA.size(); i++) {
            MenuItem item = new MenuItem();
            //String[] sa = s.split("::");
            item.setPrice(dbManager.getPrice(orderNumber));
            item.setName(dbManager.getName(orderNumber));
            item.setQuantity(dbManager.getQuantity(orderNumber));
            /*
            item.setName(sa[2]);
            item.setQuantity(sa[3]);
            */
            items.add(item);

        }
        return items;
    }
}
