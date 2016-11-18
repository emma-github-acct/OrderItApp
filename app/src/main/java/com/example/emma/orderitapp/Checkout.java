package com.example.emma.orderitapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TextView cNameView = (TextView) findViewById(R.id.name_label);
        TextView cEmailView = (TextView) findViewById(R.id.email_label);
        cNameView.setText(Customer.getName());
        cEmailView.setText(Customer.getEmail());

        TextView rNameView = (TextView) findViewById(R.id.rest_name);
        TextView rEmailView = (TextView) findViewById(R.id.rest_email);
        rNameView.setText(Business.getName());
        rEmailView.setText(Business.getEmail());

    }

    /**
     * Send order by email
     * Threaded. Starts Receipt activity on result code.
     * @param v
     */


    public void sendOrder(View v) {

        new Thread(new Runnable() {
            public void run() {
                String[] addresses = {"sengle64@gmail.com"};
                String subject = "order";
                String body = "cheeseburger";

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
    }


    /**
     * Start Receipt activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        startActivity(new Intent(getApplicationContext(), Receipt.class));
    }


    /**
     *The code below handles menus
     *
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
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
// End menu code




}

