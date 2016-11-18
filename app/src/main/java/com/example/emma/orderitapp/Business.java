package com.example.emma.orderitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

/**
 * Created by Emma on 11/10/16.
 */

public class business {

    private String type;
    private String name;
    private String email;
    private String phone;
    private String address;
    private ArrayList<String> attributes;
    private SharedPreferences prefs;


    // Constructor
    public business(Context c) {
            PreferenceManager.setDefaultValues(c, R.xml.preferences, false);
            prefs = PreferenceManager.getDefaultSharedPreferences(c);
            attributes = new ArrayList<String>();
    }

    public void  setType(String type1){type = type1;}
    public String getType() {return type;}


    public void  setName(String name){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("business_name", name);
        editor.apply();
        this.name = name;
    }
    public String getName() {return prefs.getString("business_name", "Java Cafe");}


    public void  setEmail(String email){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("business_email", email);
        editor.apply();
        this.email = email;
    }
    public String getEmail() {return prefs.getString("business_email", "javacafe@gmail.com");}

    public void  setPhone(String phone1){phone = phone1;}
    public String getPhone() {return phone;}

    public void  setAddress(String address1){address = address1;}
    public String getAddress() {return address;}


    public ArrayList<String> getAttributes() {
        if( name != null) {
            attributes.add(name);
        }
        if ( phone != null ) {
            attributes.add(phone);
        }
        if ( email!= null ) {
            attributes.add(email);
        }
        if ( address != null ) {
            attributes.add(address);
        }
        return attributes;
    }

}
