package com.example.emma.orderitapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
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

}

