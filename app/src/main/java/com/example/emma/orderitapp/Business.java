package com.example.emma.orderitapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * The Business model object.
 * Name
 * Address
 * Phone
 * Email
 * <p>
 * Uses shared preferences to store data
 */

public class Business implements Serializable {

    private String type;
    private String name;
    private String email;
    private String phone;
    private String address;
    private ArrayList<String> attributes;
    private SharedPreferences prefs;

    // Constructor
    public Business() {
        attributes = new ArrayList<String>();
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }


    public ArrayList<String> getAttributes() {
        if (name != null) {
            attributes.add(name);
        }
        if (phone != null) {
            attributes.add(phone);
        }
        if (email != null) {
            attributes.add(email);
        }
        if (address != null) {
            attributes.add(address);
        }
        return attributes;
    }

}
