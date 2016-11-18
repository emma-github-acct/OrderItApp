package com.example.emma.orderitapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;

/**
 * Settings activity starts settings fragment.
 */
public class SettingsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);

        getFragmentManager( ).beginTransaction( )
                .add( android.R.id.content, new SettingsFragment( ))
                .commit( );

    }



}