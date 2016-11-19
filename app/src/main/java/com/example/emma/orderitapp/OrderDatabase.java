package com.example.emma.orderitapp;

/**
 * Database of restaurant orders.
 * Stores Order id, date, restaurant, customer and total in a single table.
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}




    public long insert(String date, String restaurant, String item, String quantity, String price, String orderNumber) {
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
        return newId;
    }


    /**
     * Search database by column name and value.
     * @param columnName
     * @param columnValue
     * @return
     */

    public ArrayList<String> selectByColumn(String columnName, String columnValue) {

        ArrayList<String> historyList = new ArrayList<String>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(HISTORY_TABLE, null, columnName + "=?",
                    new String[]{columnValue}, null, null, columnName);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String oneRecord = "";
                for (int i = 1; i < cursor.getColumnCount(); i++) {
                    oneRecord += cursor.getString(i) + "::";
                }
                historyList.add(oneRecord);
                cursor.moveToNext();
            }
        } catch (SQLException se) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
        return historyList;
    }


}
