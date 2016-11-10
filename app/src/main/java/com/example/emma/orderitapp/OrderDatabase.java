package com.example.emma.orderitapp;

/**
 * Database of previous restaurant orders.
 * Stores Order id, date, restaurant, customer and total in a single table.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;



public class OrderDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SavedOrders";
    public static final int DATABASE_VERSION = 1;
    public static final String HISTORY_TABLE = "tblOrders";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String RESTAURANT = "restaurant";
    public static final String CUSTOMER = "customer";
    public static final String TOTAL = "total";
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
        // build the create table statement
        String sqlCreate = "create table " + HISTORY_TABLE + " ( "
                + ID + " integer primary key autoincrement, "
                + DATE + " text, "
                + RESTAURANT + " text, "
                + CUSTOMER + " text, "
                + TOTAL + "text "
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
                    oneRecord += cursor.getString(i) + " ";
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
