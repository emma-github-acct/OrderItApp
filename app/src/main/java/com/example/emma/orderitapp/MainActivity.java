package com.example.emma.orderitapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.emma.orderitapp.Customer;

public class MainActivity extends AppCompatActivity {

    private Customer customer = new Customer();
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Customer information from Preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        customer.setName(prefs.getString("name_preference", "Jane Doe"));
        customer.setEmail(prefs.getString("email_preference", "jDoe@gmail.com"));
        customer.setAllergy(prefs.getBoolean("veg_preference", false));


    }

    


}
