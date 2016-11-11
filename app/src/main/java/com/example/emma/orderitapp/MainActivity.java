package com.example.emma.orderitapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements Serializable{

    private Business business;
    private String businessType;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        business = new Business();
    }

    public void scanBusinessQR( View view ) {
        //instantiateBusinessInfo();
        /*
        business.setName("java");
        business.setPhone("908-456-8888");
        business.setAddress("123 village lane wint park, FL");
        setAppTheme();
        */
        loadWelcomePage( null );
    }

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

                    } else if (nextInput.equals("<Email>")) {
                        nextInput = mScan.next().trim();
                        business.setEmail(nextInput);
                        nextInput = mScan.next().trim();

                    } else if (nextInput.equals("<PhoneNumber>")) {
                        nextInput = mScan.next().trim();
                        business.setPhone(nextInput);
                        nextInput = mScan.next().trim();

                    } else if (nextInput.equals("<Address>")) {
                        nextInput = mScan.next().trim();
                        business.setPhone(nextInput);
                        nextInput = mScan.next().trim();

                    } else if (nextInput.equals("<AppTheme>")) {
                        nextInput = mScan.next().trim();
                        business.setTheme(nextInput);
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

    private void setAppTheme() {

    }

    private void loadWelcomePage( View view ) {
        startActivity( new Intent( getApplicationContext(), WelcomeActivity.class ) );
    }


}
