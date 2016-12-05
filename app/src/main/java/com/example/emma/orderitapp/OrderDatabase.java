package com.example.emma.orderitapp;

/**
 * Database of Take Out Orders.
 * Stores Order number, date, restaurant, quantity and price in a single table.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class OrderDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SavedOrders";
    public static final int DATABASE_VERSION = 1;
    public static final String ORDER_TABLE = "tblOrders";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String RESTAURANT = "restaurant";
    public static final String TOTAL = "total";

    private final int DATE_INDEX = 1;
    private final int RESTAURANT_INDEX = 2;
    private final int TOTAL_INDEX = 3;
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

        String sqlCreate = "create table " + ORDER_TABLE + " ( "
                + ID + " integer primary key autoincrement, "
                + DATE + " text, "
                + RESTAURANT + " text, "
                + TOTAL + " text "
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
     * Insert an order.
     */

    public void insert(String date, String restaurant, String total) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DATE, date);
            values.put(RESTAURANT, restaurant);
            values.put(TOTAL, total);
            db.insert(ORDER_TABLE, null, values);
            db.close();
        } catch (SQLException se) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }

    }



    /**
     * Search database by column name and value.
     *
     * @param columnName
     * @param columnValue
     */

    public ArrayList<String> selectByColumn(String columnName, String columnValue) {

        ArrayList<String> orders = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(ORDER_TABLE, null, columnName + "=?",
                    new String[]{columnValue}, null, null, columnName);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String o;
                o = (cursor.getString(0)) + " ";
                o += (cursor.getString(1)) + " ";
                o += (cursor.getString(2)) + " ";
                o += (cursor.getString(3)) + "\n";
                orders.add(o);
                cursor.moveToNext();
            }
        } catch (SQLException se) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
        return orders;
    }

    // Autocomplete code for Order History Activity

    public ArrayAdapter<String> fillAutoCompleteTextFields(Context context, String column) {

        ArrayAdapter<String> adapter = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            // select distinct values in column
            Cursor cursor = db.query(true, ORDER_TABLE, new String[]{column},
                    null, null, null, null, column, null);

            int numberOfRecords = cursor.getCount();
            if (numberOfRecords > 0) {
                cursor.moveToFirst();
                String[] autoTextOptions = new String[numberOfRecords];
                for (int i = 0; i < numberOfRecords; i++) {
                    autoTextOptions[i] = cursor.getString(cursor.getColumnIndex(column));
                    cursor.moveToNext();
                }
                adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_dropdown_item_1line,
                        autoTextOptions);
                db.close();
            }
        } catch (SQLException se) {
            Toast.makeText(appContext, se.getMessage(), Toast.LENGTH_LONG).show();
        }
        return adapter;
    }

    public Cursor getFilteredCursor(String name, String value) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(ORDER_TABLE, null, name + "=?",
                new String[]{value}, null, null, name);

        return c;
    }

    public Cursor getCursor() {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select * from " + ORDER_TABLE;
        Cursor c = db.rawQuery(query, null);

        return c;
    }

    public String getTotal(Long id) {
        String price = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.query(ORDER_TABLE, new String[]{ID, DATE, RESTAURANT, TOTAL}, ID + "=" + id,
                    null, null, null, null);

            c.moveToFirst();

            price = c.getString(TOTAL_INDEX);

            c.close();
        } catch (SQLException sqe) {
            Toast.makeText(appContext, sqe.getMessage(), Toast.LENGTH_LONG).show();
        }
        return price;
    }

    public String getRestaurant(Long id) {
        String restaurant = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.query(ORDER_TABLE, new String[]{ID, DATE, RESTAURANT, TOTAL}, ID + "=" + id,
                    null, null, null, null);

            c.moveToFirst();

            restaurant = c.getString(RESTAURANT_INDEX);

            c.close();
        } catch (SQLException sqe) {
            Toast.makeText(appContext, sqe.getMessage(), Toast.LENGTH_LONG).show();
        }
        return restaurant;
    }

    public String getDate(Long id) {
        String date = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.query(ORDER_TABLE, new String[]{ID, DATE, RESTAURANT, TOTAL}, ID + "=" + id,
                    null, null, null, null);

            c.moveToFirst();

            date = c.getString(DATE_INDEX);

            c.close();
        } catch (SQLException sqe) {
            Toast.makeText(appContext, sqe.getMessage(), Toast.LENGTH_LONG).show();
        }
        return date;

    }

    /** selectAll method
     *
     * @return ArrayList of records retrieved
     */
    public ArrayList<String> selectAll( ) {

        ArrayList<String> historyList = new ArrayList<String>( );
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String query = "Select * from " + ORDER_TABLE;
            Cursor cursor = db.rawQuery( query, null);

            cursor.moveToFirst();
            while ( !cursor.isAfterLast() ) {
                String oneRecord = "";
                for ( int i = 1; i < cursor.getColumnCount(); i++) {
                    oneRecord += cursor.getString(i) + " ";
                }
                historyList.add( oneRecord );
                cursor.moveToNext();
                }
            }
        catch ( SQLException se ) {
            Toast.makeText( appContext, se.getMessage( ), Toast.LENGTH_LONG).show();
        }

        return historyList;
    }
}