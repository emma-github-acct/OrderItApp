package com.example.emma.orderitapp;

/**
 * Controller for scanning Menu Items.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ScanActivity extends AppCompatActivity {

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        order = new Order(this);
    }


    /**
     * Debug code adds a menu item to the order.
     * @param v
     */

    public void itemScan(View v){
        String item = "Cheeseburger";
        String quantity = "1";
        String price = "1.00";
        order.addItem(item, quantity, price);
        Toast.makeText(this, "Item added.", Toast.LENGTH_LONG).show();
    }

    /**
     * The code below handles menus
     */

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
            case R.id.menu_history:
                startHistory(null);
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
     *
     * @param v
     */
    public void startSettings(View v) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    public void startCheckout(View v) {
        startActivity(new Intent(getApplicationContext(), Checkout.class));
    }

    public void startHistory(View v) {
        startActivity(new Intent(getApplicationContext(), OrderHistory.class));
    }

    public void startScan(View v) {
        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
    }

    public void startMain(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
// End menu code



}
