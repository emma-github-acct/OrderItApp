package com.example.emma.orderitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private Business business;
    private String businessType;
    private SharedPreferences prefs;
    private Customer customer;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        business = new Business();
        customer = new Customer();

        // Set Customer information from Preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        customer.setName(prefs.getString("name_preference", "Jane Doe"));
        customer.setEmail(prefs.getString("email_preference", "jDoe@gmail.com"));
        customer.setAllergy(prefs.getBoolean("allergy_preference", false));
    }

    public void scanBusinessQR( View view ) {
        //instantiateBusinessInfo();

        business.setType("Restaurant");
        business.setName("Java Cafe");
        business.setPhone("908-456-8888");
        business.setAddress("123 Holt Ave Winter Park, FL");
        business.setEmail("JavaCafe@gmail.com");

        // Where
        loadWelcomePage( null );
    }

    private void loadWelcomePage( View view ) {

        Intent i = new Intent( getApplicationContext(), WelcomeActivity.class );
        i.putExtra("Type", business.getType());
        i.putExtra("Name", business.getName());
        i.putExtra("Phone", business.getPhone());
        i.putExtra("Address", business.getAddress());
        i.putExtra("Email", business.getEmail());
        startActivity(i);

    }


}
