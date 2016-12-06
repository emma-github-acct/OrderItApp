package com.example.emma.orderitapp;

/**
 * Controller for the Order History view.
 * Database operations are threaded.
 */


import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class OrderHistoryActivity extends AppCompatActivity {

    private Business businessObject;
    private Order orderObject;
    OrderDatabase dbManager;

    /**
     * onCreate
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_java_cafe);
        Intent intent = getIntent();
        this.businessObject = (Business) intent.getSerializableExtra("business");
        this.orderObject = (Order) intent.getSerializableExtra("order");
        dbManager = new OrderDatabase(this);

        // Autofill the Restaurant and Date search fields.
        autoFillThreaded();

        //Show the whole database initially.
        showDatabaseContents();
    }


    /**
     * Autofill the search fields when the History layout is created.
     *
     * @param
     */

    public void autoFillThreaded() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayAdapter dateAdapter
                        = dbManager.fillAutoCompleteTextFields(OrderHistoryActivity.this, OrderDatabase.DATE);
                final ArrayAdapter restaurantAdapter
                        = dbManager.fillAutoCompleteTextFields(OrderHistoryActivity.this, OrderDatabase.RESTAURANT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dateAdapter != null) {
                            AutoCompleteTextView dateEntry = (AutoCompleteTextView) findViewById(R.id.date_entry);
                            dateEntry.setAdapter(dateAdapter);
                            AutoCompleteTextView restaurantEntry = (AutoCompleteTextView) findViewById(R.id.restaurant_entry);
                            restaurantEntry.setAdapter(restaurantAdapter);
                        }
                    }
                });
            }
        }).start();
    }


    /**
     * Handler for the search button.
     * Search by Restaurant or Date and display the results.
     * @param v
     */

    public void showDataByColumn(View v) {

        hideKeyboard();

        AutoCompleteTextView dateEntry = (AutoCompleteTextView) findViewById(R.id.date_entry);
        AutoCompleteTextView restaurantEntry = (AutoCompleteTextView) findViewById(R.id.restaurant_entry);
        RadioButton rDateButton = (RadioButton) findViewById(R.id.radio_date);
        RadioButton rResButton = (RadioButton) findViewById(R.id.radio_restaurant);

        // Search by Date
        if (rDateButton.isChecked()) {
            String columnValue = dateEntry.getText().toString();
            dateEntry.setText("");
            if (columnValue.isEmpty()) {
                Toast.makeText(this, R.string.dateError, Toast.LENGTH_LONG).show();
            } else {
                selectByColumnThreaded(OrderDatabase.DATE, columnValue);
            }
        }
        // Search by Restaurant
        else if (rResButton.isChecked()) {
            String columnValue = restaurantEntry.getText().toString();
            restaurantEntry.setText("");
            if (columnValue.isEmpty()) {
                Toast.makeText(this, R.string.restaurantError, Toast.LENGTH_LONG).show();
            } else {
                selectByColumnThreaded(OrderDatabase.RESTAURANT, columnValue);
            }
        }
    }


    /**
     *
     * Do the actual search and display threaded.
     *
     */

    public void selectByColumnThreaded(final String columnName, final String columnValue) {

        final TextView historyDisplay = (TextView) findViewById(R.id.db_contents);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> results = dbManager.selectByColumn(columnName, columnValue);
                String historyString = getString(R.string.historyHeader);
                for (String r : results) {
                    historyString += r;
                }
                final String hs = historyString;
                historyDisplay.post(new Runnable() {
                    @Override
                    public void run() {
                        historyDisplay.setText(hs);
                    }
                });
            }
        }).start();
    }


    // Show the whole database

    public void showDatabaseContents() {

        hideKeyboard();
        final TextView historyDisplay = (TextView) findViewById(R.id.db_contents);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String allHistory = "";
                ArrayList<String> allRecords = dbManager.selectAll();
                for (String s : allRecords) {
                    allHistory += s + "\n";
                }
                final String hs = allHistory;
                historyDisplay.post(new Runnable() {
                    @Override
                    public void run() {
                        historyDisplay.setText(hs);
                    }
                });
                historyDisplay.setText(allHistory);
            }
        }).start();

    }


    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     *The code below handles menus
     *
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_history).setVisible(false);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_start:
                startMain(null);
                return true;
            case R.id.menu_scan:
                startScan(null);
                return true;
            case R.id.menu_settings:
                startSettings(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void startSettings(View v) {
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        if (businessObject != null) {
            i.putExtra("business", businessObject);
        }
        if (orderObject != null) {
            i.putExtra("order", orderObject);
        }
        startActivity(i);
    }

    public void startScan(View v) {
        Intent i;
        if (businessObject != null) {
            i = new Intent(getApplicationContext(), QRCodeReaderRestaurant.class);
        } else {
            i = new Intent(getApplicationContext(), QRCodeReaderBusiness.class);
        }
        i.putExtra("business", businessObject);
        i.putExtra("order", orderObject);
        startActivity(i);
    }

    // End menu code


    public void startMain(View v) {
        confirmAppRestart();
    }

    private void confirmAppRestart() {
        final Dialog restartConfirmation = new Dialog(OrderHistoryActivity.this);
        restartConfirmation.setContentView(R.layout.confirm_restart);
        restartConfirmation.setTitle("Restarting App");
        Button confirm = (Button) restartConfirmation.findViewById(R.id.restart_confirm);
        Button cancel = (Button) restartConfirmation.findViewById(R.id.restart_cancel);

        restartConfirmation.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                restartConfirmation.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartConfirmation.dismiss();
            }
        });
    }

}
