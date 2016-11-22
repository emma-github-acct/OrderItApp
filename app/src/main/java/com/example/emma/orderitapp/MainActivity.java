package com.example.emma.orderitapp;

import android.app.Activity;
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

public class MainActivity extends Activity {

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

    public void businessQRScan( View view ) {
        //instantiateBusinessInfo();
        startActivity(new Intent(getApplicationContext(), QRCodeReaderBusiness.class));

        // Where
        //loadWelcomePage( null );
    }

    //--------- NOT WORKING -----------------
    /*
    private void instantiateBusinessInfo() {
        this.business = new Business();

        // Insert Parser Class here to get info
        // parser.getType(), parser.getName()

        // Get info from QR Code
        // --- For now using text file for testing ---

        try {

            InputStream iFile = this.getResources().openRawResource( R.raw.java_cafe_info );
            Scanner mScan = new Scanner( iFile );
            mScan.useDelimiter("::");
            String nextInput = "";

            if ( mScan.next().trim().equals("<Business>") ) {
                nextInput = mScan.next().trim();

                while (mScan.hasNext() && !nextInput.equals("</Business>")) {

                    // Getting Type of Business
                    if (nextInput.equals("<Type>")) {
                        this.businessType = mScan.next().trim();
                        if (businessType.equals("Restaurant")) {
                            this.business = new Restaurant();
                        }
                        this.business.setType(businessType);
                        nextInput = mScan.next().trim();
                    }

                    // Getting Name of Business
                    if (nextInput.equals("<Name>")) {
                        nextInput = mScan.next().trim();
                        business.setName(nextInput);
                        nextInput = mScan.next().trim();

                    }
                    if (nextInput.equals("<Email>")) {
                        nextInput = mScan.next().trim();
                        business.setEmail(nextInput);
                        nextInput = mScan.next().trim();

                    }
                    if (nextInput.equals("<PhoneNumber>")) {
                        nextInput = mScan.next().trim();
                        business.setPhone(nextInput);
                        nextInput = mScan.next().trim();

                    }
                    if (nextInput.equals("<Address>")) {
                        nextInput = mScan.next().trim();
                        business.setPhone(nextInput);
                        nextInput = mScan.next().trim();

                    }
                }
            }

        } catch ( IllegalStateException ise ){
            Toast.makeText( this, R.string.input_error, Toast.LENGTH_LONG).show();

        } catch ( IllegalArgumentException ire ){
            Toast.makeText( this, R.string.input_error, Toast.LENGTH_LONG).show();
        }

    }

*/
    /*
    private void loadWelcomePage( View view ) {

        Intent i = new Intent( getApplicationContext(), WelcomeActivity.class );
        i.putExtra("Type", business.getType());
        i.putExtra("Name", business.getName());
        i.putExtra("Phone", business.getPhone());
        i.putExtra("Address", business.getAddress());
        i.putExtra("Email", business.getEmail());
        startActivity(i);

    }
    */


}
