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
    private String default_name;
    private String default_address;
    private String default_email;
    private String default_phone;

    // Constructor
    public Customer(Context c) {
        PreferenceManager.setDefaultValues(c, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        this.default_name = c.getResources().getString(R.string.default_name);
        this.default_email = c.getResources().getString(R.string.default_email);
        this.default_phone = c.getResources().getString(R.string.default_phone_number);
        this.default_address = c.getResources().getString(R.string.default_address);
    }

    public void setName(String name){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name_preference", name);
        editor.apply();
    }

    public String getName() {return prefs.getString("name_preference", this.default_name);}


    public void  setEmail(String email){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email_preference", email);
        editor.apply();
    }

    public String getEmail() {return prefs.getString("email_preference", this.default_phone);}

    public void  setAddress(String address){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("address_preference", address);
        editor.apply();
    }

    public String getAddress() {return prefs.getString("address_preference", this.default_address);}


    public void  setPhone(String phone){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phone_preference", phone);
        editor.apply();
    }

    public String getPhone() {return prefs.getString("phone_preference", "111-1234");}


    public void  setAllergy(boolean allergy1){allergy = allergy1;}

    public boolean getAllergy() {return allergy;}
}
