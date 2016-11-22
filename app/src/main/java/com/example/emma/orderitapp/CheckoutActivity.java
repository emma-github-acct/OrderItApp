package com.example.emma.orderitapp;

/**
 * Controller to display, confirm and email the Take Out Order.
 *
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private Customer customer;
    private Business business;
    private Order order;
    private String businessName;
    private LayoutManager layoutManager;
    private SharedPreferences prefs;

    String orderData; //Text display and email body of the order.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.business = new Business(this);
        this.businessName = business.getName();

        layoutManager = new LayoutManager();
        setContentView( layoutManager.getCheckoutLayout( businessName ));

        customer = new Customer(this);
        order = new Order(this);

        ArrayList<MenuItem> o = order.getOrder();
        TextView orderDisplay = (TextView) findViewById(R.id.order_items);
        orderData = "Restaurant: " + business.getName() + "  " + business.getEmail() + "\n\n";
        orderData += "Customer: " + customer.getName() + " " + customer.getEmail() +"\n\n";
        orderData += "Order Number " + order.getOrderNumber() + "\n\n";
        orderData += "Item    Price    Quantity \n";

        for (MenuItem i: o){
            orderData += i.getName() + "  ";
            orderData += i.getPrice() + "  ";
            orderData += i.getQuantity() + "\n";
        }
        orderDisplay.setText( orderData);
    }

    /**
     * Send order by email
     * Threaded. Starts Receipt activity on result code.
     * @param v
     */

    public void sendOrder(View v) {

        new Thread(new Runnable() {
            public void run() {
                String[] addresses = {"sengle64@gmail.com"}; //{restaurant.getEmail(), customer.getEmail()}
                String subject = "TakeOut Order";
                String body = orderData;

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(emailIntent, 1);
                }
            }
        }).start();

        Toast.makeText( this, "Sending Order, pick up in 30 minutes", Toast.LENGTH_LONG).show();
    }


    /**
     * Start Receipt activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    /**
     * The code below handles menus
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_checkout).setVisible(false);
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

    public void startHistory(View v) {
        startActivity(new Intent(getApplicationContext(), OrderHistoryActivity.class));
    }

    public void startScan(View v) {
        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
    }

    public void startMain(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
// End menu code



}

