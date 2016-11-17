package com.example.emma.orderitapp;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;


import java.util.ArrayList;

/**
 * Created by Emma on 11/11/16.
 */

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout welcomeLayout;
    private LinearLayout.LayoutParams params;
    private Business business;
    private GridLayout businessGrid; // displays Business Information
    private final int COLUMN_COUNT = 1;
    private int windowWidth;
    private ArrayList<String> attributes;
    private LayoutManager layoutManager;
    private String businessName;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        // ******** Get Business Info Passed in Intent  ******** //
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("Type");
        if ( type.equals("Restaurant")){
            business = new Restaurant();
        }
        business.setName( bundle.getString("Name") );
        business.setPhone( bundle.getString("Phone") );
        business.setAddress( bundle.getString("Address") );
        business.setEmail( bundle.getString("Email") );
        attributes = business.getAttributes();

        // Set business name for LayoutManager Keys
        businessName = business.getName();

        // Load correct Layout
        layoutManager = new LayoutManager();

        if ( ! layoutManager.hasWelcomeLayout( businessName )) {
            Toast.makeText( this, R.string.layout_error, Toast.LENGTH_LONG ).show();
        }
        // Defaults to main activity if no custom layout
        setContentView( layoutManager.getWelcomeLayout( businessName ));

        // Layouts
        setupLayout();
    }

    //  Set Up General Layout for the Welcome Activity
    private void setupLayout() {
        welcomeLayout = (LinearLayout) findViewById( R.id.businessInfoView );

        // Setup welcomeLayout params
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        // Get width of screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int paddingLeft = welcomeLayout.getPaddingLeft();
        int paddingRight = welcomeLayout.getPaddingRight();
        windowWidth = size.x - paddingLeft - paddingRight;

        // Programically Show Business Info
        setupBusinessInfo( );
    }

    // Programmatically Set Up Grid to display Business Info in activity
    private void setupBusinessInfo() {

        businessGrid = new GridLayout( this );
        businessGrid.setPadding( 10, 10, 10, 10 );
        businessGrid.setColumnCount( COLUMN_COUNT );
        businessGrid.setRowCount( attributes.size() );

        try {

            for (int i = 0; i < attributes.size(); i++) {

                TextView t = new TextView( this );
                t.setText( attributes.get( i ));
                t.setTextAppearance( layoutManager.getTextStyle( businessName ));

                // Set the view, the width and its height
                businessGrid.addView( t, windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            businessGrid.setLayoutParams( params );
            welcomeLayout.addView( businessGrid );

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void launchOrderingActivity( View view ) {

        // check layout manager
        System.out.println("Button Pressed");
    }

    public void launchMainActivity( View view ){
        startActivity( new Intent( getApplicationContext( ), MainActivity.class));
    }

}
