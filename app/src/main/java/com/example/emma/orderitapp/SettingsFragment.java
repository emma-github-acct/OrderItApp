package com.example.emma.orderitapp;


import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Settings fragment
 */

public class SettingsFragment extends PreferenceFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}