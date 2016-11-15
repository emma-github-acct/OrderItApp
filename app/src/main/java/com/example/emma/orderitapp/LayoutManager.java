package com.example.emma.orderitapp;

import java.util.HashMap;

/**
 * Created by Emma on 11/11/16.
 */

public class LayoutManager {

    // Tag Names for style types
    private final String WELCOME_TAG = "_Welcome";
    private final String ORDER_TAG = "_Order";

    // Names of Businesses
    private final String JAVA = "Java Cafe";

    // Layout HashMap
    private HashMap<String, Integer> layouts;


    // Constructor
    public LayoutManager() {
        layouts = new HashMap<String, Integer>();
        layouts.put( JAVA + WELCOME_TAG, R.layout.java_cafe_welcome_layout );
        //layouts.put( JAVA + ORDER_TAG, R.layout.java_cafe_order_layout );
    }

    /**
     * hasWelcomeLayout
     * @param key -- Business Name
     * @return boolean
     */
    public boolean hasWelcomeLayout( String key ) {
        String k = key + WELCOME_TAG;
        if ( layouts.get( k ) == null ){
            return false;

        } else {
            return true;
        }
    }

    /**
     * hasOrderLayout
     * @param key -- Business Name
     * @return boolean
     */
    public boolean hasOrderLayout( String key ) {
        String k = key + ORDER_TAG;
        if ( layouts.get( k ) == null ){
            return false;

        } else {
            return true;
        }
    }

    /**
     * getWelcomeLayout
     * @param key -- Business Name
     * @return int -- value of layout id
     */
    public int getWelcomeLayout( String key ) {
        String k = key + WELCOME_TAG;
        if ( layouts.get( k ) == null ){
            return R.style.AppTheme;

        } else {
            return layouts.get( k );
        }
    }

    /**
     * getOrderLayout
     * @param key -- Business Name
     * @return int -- value of layout id
     */
    public int getOrderLayout( String key ) {
        String k = key + ORDER_TAG;
        if ( layouts.get( k ) == null ){
            return R.style.AppTheme;

        } else {
            return layouts.get( k );
        }
    }
}
