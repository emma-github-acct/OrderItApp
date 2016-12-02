package com.example.emma.orderitapp;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Settings activity starts settings fragment.
 */
public class SettingsActivity extends Activity{

    private Business businessObject;
    private Order orderObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        Intent intent = getIntent();
        this.businessObject= (Business) intent.getSerializableExtra("business");
        this.orderObject= (Order) intent.getSerializableExtra("order");

        getFragmentManager( ).beginTransaction( )
                .add( android.R.id.content, new SettingsFragment( ))
                .commit( );
    }

}