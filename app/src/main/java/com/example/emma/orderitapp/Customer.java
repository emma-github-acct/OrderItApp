package com.example.emma.orderitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * The Customer model object.
 * Name
 * Address
 * Phone
 * Email
 *
 * Uses shared preferences to store data
 */

public class Customer{

    private boolean allergy;
    private SharedPreferences prefs;


    // Constructor
    public Customer(Context c) {
        PreferenceManager.setDefaultValues(c, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public void  setName(String name){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name_preference", name);
        editor.apply();
    }

    public String getName() {return prefs.getString("name_preference", "jDoe@gmail.com");}


    public void  setEmail(String email){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email_preference", email);
        editor.apply();
    }

    public String getEmail() {return prefs.getString("email_preference", "jDoe@gmail.com");}

    public void  setAddress(String address){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("address_preference", address);
        editor.apply();
    }

    public String getAddress() {return prefs.getString("address_preference", "123 Oak st");}


    public void  setPhone(String phone){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phone_preference", phone);
        editor.apply();
    }

    public String getPhone() {return prefs.getString("phone_preference", "111-1234");}


    public void  setAllergy(boolean allergy1){allergy = allergy1;}

    public boolean getAllergy() {return allergy;}
}
