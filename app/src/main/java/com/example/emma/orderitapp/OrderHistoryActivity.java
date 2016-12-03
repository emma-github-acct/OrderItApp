package com.example.emma.orderitapp;

/**
 * Query order database to display past orders.
 *
 */


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private Business businessObject;
    private Order orderObject;
    private LayoutManager layoutManager;
    private ArrayAdapter dateAdapter;
    private ArrayAdapter restaurantAdapter;
    private SharedPreferences prefs;
    private ListView historyList;
    private Cursor historyCursor;
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

        Intent intent = getIntent();
        this.businessObject= (Business) intent.getSerializableExtra("business");
        this.orderObject = (Order) intent.getSerializableExtra("order");

        dbManager = new OrderDatabase( this );
        historyCursor = dbManager.getCursor();

        dateAdapter = dbManager.fillAutoCompleteTextFields( this, OrderDatabase.DATE );
        if ( dateAdapter != null ) {
            AutoCompleteTextView dateEntry = (AutoCompleteTextView)findViewById(R.id.date_entry);
            dateEntry.setAdapter(dateAdapter);
        }
        restaurantAdapter = dbManager.fillAutoCompleteTextFields( this, OrderDatabase.RESTAURANT );
        if ( restaurantAdapter !=  null ) {
            AutoCompleteTextView subjectEntry = (AutoCompleteTextView)findViewById(R.id.restaurant_entry);
            subjectEntry.setAdapter(restaurantAdapter);
        }
        showDatabaseContents();
    }

    /**
     * Search by Order Number or Date (date not working now)
     * @param v
     */

    public void showDataByColumn( View v ) {
        AutoCompleteTextView dateEntry = (AutoCompleteTextView)findViewById(R.id.date_entry);
        AutoCompleteTextView restaurantEntry = (AutoCompleteTextView)findViewById(R.id.restaurant_entry);


        //hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if ( imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getCurrentFocus( ).getWindowToken(), 0);
        }

        hideKeyboard();

        ArrayList<String> results;
        RadioButton rDateButton = (RadioButton)findViewById(R.id.radio_date);
        RadioButton rResButton = (RadioButton)findViewById(R.id.radio_restaurant);

        // Search by Date
        if ( rDateButton.isChecked( )) {
            String columnValue = dateEntry.getText( ).toString( );
            restaurantEntry.setText("");
            if ( columnValue.isEmpty( ) ) {
                Toast.makeText( this, "error Date", Toast.LENGTH_LONG).show();
            } else {
                results = dbManager.selectByColumn( OrderDatabase.DATE, columnValue);
                displayData( results );
            }
        }
        // Search by Order Number
        else if ( rResButton.isChecked( )){
            String columnValue = restaurantEntry.getText().toString();
            dateEntry.setText("");

            if ( columnValue.isEmpty( ) ) {
                Toast.makeText( this, "error Restaurant Name", Toast.LENGTH_LONG).show();
            } else {
                results = dbManager.selectByColumn( OrderDatabase.RESTAURANT, columnValue);
                displayData( results);
            }
        }
    }

    /**
     * Display the search results.
     * @param
     */

    public void displayData( ArrayList<String> orders) {
        TextView historyDisplay = (TextView) findViewById(R.id.db_contents);
        String historyString = "Order Date Restaurant Total\n";
        for ( String s : orders ) {
            historyString += s;
        }
        historyDisplay.setText( historyString);
    }

    /**
     *The code below handles menus
     *
     */

    public void showDatabaseContents( ) {
        hideKeyboard();

        TextView historyDisplay = ( TextView ) findViewById( R.id.db_contents );
        String allHistory = "";

        ArrayList<String> allRecords = dbManager.selectAll( );

        for ( String s : allRecords ) {
            allHistory += s + "\n";
        }
        historyDisplay.setText( allHistory );
    }



    private void hideKeyboard(){
        InputMethodManager imm = ( InputMethodManager )getSystemService( INPUT_METHOD_SERVICE );
        if ( imm.isAcceptingText() ){
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

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
        Intent i = new Intent( getApplicationContext(), SettingsActivity.class );
        i.putExtra( "business", businessObject );
        i.putExtra( "order", orderObject );
        startActivity(i);
    }

    public void startScan(View v) {
        Intent i = new Intent( getApplicationContext(), QRCodeReaderRestaurant.class );
        i.putExtra( "business", businessObject );
        i.putExtra( "order", orderObject );
        startActivity(i);
    }

    public void startMain(View v) {
        confirmAppRestart();
    }
// End menu code

    private void confirmAppRestart(){
        final Dialog restartConfirmation = new Dialog(OrderHistoryActivity.this);
        restartConfirmation.setContentView(R.layout.confirm_restart);
        restartConfirmation.setTitle("Restarting App");
        Button confirm = (Button) restartConfirmation.findViewById(R.id.restart_confirm);
        Button cancel = (Button) restartConfirmation.findViewById(R.id.restart_cancel);

        restartConfirmation.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext(), MainActivity.class );
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
