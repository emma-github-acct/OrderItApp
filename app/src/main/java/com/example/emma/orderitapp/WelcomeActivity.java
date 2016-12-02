package com.example.emma.orderitapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Emma on 11/11/16.
 */

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout welcomeLayout;
    private LinearLayout.LayoutParams params;
    private GridLayout businessGrid; // displays business Information
    private final int COLUMN_COUNT = 1;
    private int windowWidth;
    private ArrayList<String> attributes;
    private LayoutManager layoutManager;
    private String businessName;
    private Business businessObject;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        businessObject= (Business) intent.getSerializableExtra("business");
        this.attributes = businessObject.getAttributes();

        // Set business name for LayoutManager Keys
        businessName = businessObject.getName();

        // Load correct Layout
        layoutManager = new LayoutManager();
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

        // Programically Show business Info
        setupBusinessInfo( );
    }

    // Programmatically Set Up Grid to display business Info in activity
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

    /**
     * The code below handles menus
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_scan).setVisible(false);
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
        Intent i = new Intent( getApplicationContext(), SettingsActivity.class );
        i.putExtra( "business", businessObject );
        startActivity(i);
    }

    public void startHistory(View v) {
        Intent i = new Intent( getApplicationContext(), OrderHistoryActivity.class );
        i.putExtra( "business", businessObject );
        startActivity(i);
    }

    public void startScan(View v) {
        Order orderObject = new Order();
        Intent i = new Intent( getApplicationContext(), QRCodeReaderRestaurant.class );
        i.putExtra( "business", businessObject );
        i.putExtra( "order", orderObject );
        startActivity(i);
    }

    public void startMain(View v) {
        confirmAppRestart();
    }
// End menu code

    private void confirmAppRestart(){
        final Dialog restartConfirmation = new Dialog(WelcomeActivity.this);
        restartConfirmation.setContentView(R.layout.confirm_restart);
        restartConfirmation.setTitle("Restarting App");
        Button confirm = (Button) restartConfirmation.findViewById(R.id.restart_confirm);
        Button cancel = (Button) restartConfirmation.findViewById(R.id.restart_cancel);

        restartConfirmation.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext(), MainActivity.class );
                startActivity(i);
                restartConfirmation.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartConfirmation.dismiss();
            }
        });
    }



}
