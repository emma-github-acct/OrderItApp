package com.example.emma.orderitapp;

/**
 * Query order database to display past orders.
 *
 */


import android.app.Dialog;
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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private Business businessObject;
    private Order orderObject;
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

        Intent intent = getIntent();
        this.businessObject= (Business) intent.getSerializableExtra("business");
        this.orderObject = (Order) intent.getSerializableExtra("order");

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
     * Search by Order Number or Date (date not working now)
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
        // Search by Order Number
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
        Intent i = new Intent( getApplicationContext(), SettingsActivity.class );
        i.putExtra( "business", businessObject );
        i.putExtra( "order", orderObject );
        startActivity(i);
    }

    public void startCheckout(View v) {
        Intent i = new Intent( getApplicationContext(), CheckoutActivity.class );
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
