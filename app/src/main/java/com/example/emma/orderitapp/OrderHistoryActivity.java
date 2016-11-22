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

public class OrderHistoryActivity extends AppCompatActivity {

    private String businessName;
    private Business business;
    private LayoutManager layoutManager;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.business = new Business(this);
        this.businessName = business.getName();
        layoutManager = new LayoutManager();
        setContentView( layoutManager.getHistoryLayout( businessName ));
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
        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
    }

    public void startMain(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
// End menu code


}
