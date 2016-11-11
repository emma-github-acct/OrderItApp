package com.example.emma.orderitapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.provider.SyncStateContract.Constants;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
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
    private int row_count;
    private int windowWidth;
    private ArrayList<String> attributes;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        TextView tv = (TextView) findViewById(R.id.TextId);
        tv.setText("Emma Testing");
    }

}
