package com.example.emma.orderitapp;

import android.content.Context;

/**
 * Restaurant model object.
 */

public class Restaurant extends Business {

    private final String TYPE = "Restaurant";

    // Constructor

    public Restaurant(Context c) {
        super(c);
    }

    public String getType() {
        return TYPE;
    }

}
