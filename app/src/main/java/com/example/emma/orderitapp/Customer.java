package com.example.emma.orderitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * The Customer model object.
 */

public class Customer{

    private String phone;
    private String address;
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

    public void  setPhone(String phone1){phone = phone1;}
    public String getPhone() {return phone;}

    public void  setAddress(String address1){address = address1;}
    public String getAddress() {return address;}

    public void  setAllergy(boolean allergy1){allergy = allergy1;}
    public boolean getAllergy() {return allergy;}
}
