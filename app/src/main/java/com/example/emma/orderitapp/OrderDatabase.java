package com.example.emma.orderitapp;

/**
 * Database of Take Out Orders.
 * Stores Order number, date, restaurant, quantity and price in a single table.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;


public class OrderDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SavedOrders3";
    public static final int DATABASE_VERSION = 1;
    public static final String HISTORY_TABLE = "tblOrders3";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String RESTAURANT = "restaurant";
    public static final String ITEM = "item";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final String ORDERNUMBER = "orderNumber";
    private final int DATE_INDEX = 1;
    private final int RESTAURANT_INDEX = 2;
    private final int ITEM_INDEX = 3;
    private final int QUANTITY_INDEX = 4;
    private final int PRICE_INDEX = 5;
    private final int ORDERNUMBER_INDEX = 6;
    private Context appContext;


    /**
     * Constructor
     *
     * @param context
     */

    public OrderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        appContext = context;
    }

    /**
     * Create database
     *
     * @param db
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.delete(HISTORY_TABLE, null, null);

        // build the create table statement
        String sqlCreate = "create table " + HISTORY_TABLE + " ( "
                + ID + " integer primary key autoincrement, "
                + DATE + " text, "
                + RESTAURANT + " text, "
                + ITEM + " text, "
                + QUANTITY + " text,"
                + PRICE + " text, "
                + ORDERNUMBER + " text"
                + ")";
        try {
            db.execSQL(sqlCreate);
        } catch (SQLException se) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param db
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Insert a Menu Item.
     *
     * @param date
     * @param restaurant
     * @param item
     * @param quantity
     * @param price
     * @param orderNumber
     * @return
     */

    public void insert(String date, String restaurant, String item, String quantity, String price, String orderNumber) {
        long newId = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DATE, date);
            values.put(RESTAURANT, restaurant);
            values.put(ITEM, item);
            values.put(QUANTITY, quantity);
            values.put(PRICE, price);
            values.put(ORDERNUMBER, orderNumber);

            newId = db.insert(HISTORY_TABLE, null, values);
            db.close();
        } catch (SQLException se) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (newId != -1) {
            Toast.makeText(appContext, item + " added to order!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Change item quantity.
     * @param item
     * @param orderNumber
     * @return
     */

    public void changeQuantity( MenuItem item, String orderNumber) {

        long newId = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(QUANTITY, item.getQuantity());
            newId = db.update(HISTORY_TABLE, values, ITEM + "= '" + item.getName() + "' AND " + ORDERNUMBER + "=" + orderNumber, null);
            db.close();
        } catch (SQLException se) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (newId != -1) {
            Toast.makeText(appContext, item.getName() + " increased quantity in order!", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Search database by column name and value.
     *
     * @param columnName
     * @param columnValue
     * @return
     */

    public ArrayList<MenuItem> selectByColumn(String columnName, String columnValue) {

        ArrayList<MenuItem> list = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(HISTORY_TABLE, null, columnName + "=?",
                    new String[]{columnValue}, null, null, columnName);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MenuItem item = new MenuItem();
                item.setPrice(cursor.getString(4));
                item.setName(cursor.getString(2));
                item.setQuantity(cursor.getString(3));
                list.add(item);
                cursor.moveToNext();
            }
        } catch (SQLException se) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
        return list;
    }





    public String getPrice (String orderNumber) {
        String price = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.query(HISTORY_TABLE, new String[]{ID, DATE, RESTAURANT,
                            ITEM, QUANTITY, PRICE, ORDERNUMBER}, ORDERNUMBER + "=" + orderNumber,
                    null, null, null, null);

            c.moveToFirst();

            price = c.getString(PRICE_INDEX);

            c.close();
        }
        catch (SQLException sqe) {
            Toast.makeText(appContext, sqe.getMessage(), Toast.LENGTH_LONG).show();
        }
        return price;
    }

    public String getName (String orderNumber) {
        String itemName = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.query(HISTORY_TABLE, new String[]{ID, DATE, RESTAURANT,
                            ITEM, QUANTITY, PRICE, ORDERNUMBER}, ORDERNUMBER + "=" + orderNumber,
                    null, null, null, null);

            c.moveToFirst();

            itemName = c.getString(ITEM_INDEX);

            c.close();
        }
        catch (SQLException sqe) {
            Toast.makeText(appContext, sqe.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemName;
    }

    public String getQuantity (String orderNumber) {
        String quantity = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.query(HISTORY_TABLE, new String[]{ID, DATE, RESTAURANT,
                            ITEM, QUANTITY, PRICE, ORDERNUMBER}, ORDERNUMBER + "=" + orderNumber,
                    null, null, null, null);

            c.moveToFirst();

            quantity = c.getString(QUANTITY_INDEX);

            c.close();
        }
        catch (SQLException sqe) {
            Toast.makeText(appContext, sqe.getMessage(), Toast.LENGTH_LONG).show();
        }
        return quantity;
    }




}
