package com.example.emma.orderitapp;

/**
 * Query order database to display past orders.
 *
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private Business business;
    private LayoutManager layoutManager;
    private SharedPreferences prefs;
    OrderDatabase dbManager;

    /**
     * onCreate
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new LayoutManager();
        setContentView(R.layout.activity_history_java_cafe);

        dbManager = new OrderDatabase( this );

        ArrayAdapter dateAdapter = dbManager.fillAutoCompleteTextFields( this, OrderDatabase.DATE );
        if ( dateAdapter != null ) {
            AutoCompleteTextView dateEntry = (AutoCompleteTextView)findViewById(R.id.date_entry);
            dateEntry.setAdapter(dateAdapter);
        }
        ArrayAdapter subjectAdapter = dbManager.fillAutoCompleteTextFields( this, OrderDatabase.ORDERNUMBER );
        if ( subjectAdapter !=  null ) {
            AutoCompleteTextView subjectEntry = (AutoCompleteTextView)findViewById(R.id.subject_entry);
            subjectEntry.setAdapter(subjectAdapter);
        }
    }

    /**
     * Search by Subject or Date
     * @param v
     */

    public void showDataByColumn( View v ) {


        //hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if ( imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getCurrentFocus( ).getWindowToken(), 0);
        }

        ArrayList<MenuItem> results = new ArrayList<MenuItem>();
        RadioButton rDateButton = (RadioButton)findViewById(R.id.radio_date);
        RadioButton rSubjectButton = (RadioButton)findViewById(R.id.radio_subject);

        // Search by Date
        if ( rDateButton.isChecked( )) {
            AutoCompleteTextView dateEntry = (AutoCompleteTextView)findViewById(R.id.date_entry);
            String columnValue = dateEntry.getText( ).toString( );
            if ( columnValue.isEmpty( ) ) {
                Toast.makeText( this, "errorDate", Toast.LENGTH_LONG).show();
            } else {
                results = dbManager.selectByColumn( OrderDatabase.DATE, columnValue);
                String header = OrderDatabase.DATE.toUpperCase( ) + ": " + columnValue;
                //results.add( 0, header);
                displayData( results);
            }
        }
        // Search by Subject
        else if ( rSubjectButton.isChecked( )){
            AutoCompleteTextView subjectEntry = (AutoCompleteTextView)findViewById(R.id.subject_entry);
            String columnValue = subjectEntry.getText( ).toString( );
            if ( columnValue.isEmpty( ) ) {
                Toast.makeText( this, "errorSubject", Toast.LENGTH_LONG).show();
            } else {
                results = dbManager.selectByColumn( OrderDatabase.ORDERNUMBER, columnValue);
                String header = OrderDatabase.ORDERNUMBER.toUpperCase( ) + ": " + columnValue;
                //results.add( 0, header);
                displayData( results);
            }
        }

    }

    /**
     * Display the search results.
     * @param data
     */

    public void displayData( ArrayList<MenuItem> data) {
        TextView historyDisplay = (TextView) findViewById(R.id.db_contents);
        String historyData = "";
        for ( MenuItem s : data ) {
            historyData += s.getName() + " " + s.getQuantity() + " " + s.getPrice() + "\n";
        }
        historyDisplay.setText( historyData);
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
        // Inflate the menu; adds items to the action bar
        // if it is present
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
            case R.id.menu_checkout:
                startCheckout(null);
                return true;
            case R.id.menu_settings:
                startSettings(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Below are the handlers that start new activities
     * @param v
     */
    public void startSettings(View v) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    public void startCheckout(View v) {
        startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
    }

    public void startScan(View v) {
        startActivity(new Intent(getApplicationContext(), QRCodeReaderRestaurant.class));
    }

    public void startMain(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
// End menu code


}
