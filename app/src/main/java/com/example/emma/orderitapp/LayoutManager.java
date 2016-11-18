package com.example.emma.orderitapp;

import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Emma on 11/11/16.
 */

public class LayoutManager {

    // Tag Names for style types
    private final String WELCOME_TAG = "_Welcome";
    private final String ORDER_TAG = "_Order";
    private final String TEXT_STYLE_TAG = "_TextStyle";
    // Names of Businesses
    private final String JAVA = "Java Cafe";

    // Layout HashMap
    private HashMap<String, Integer> layouts;
    private HashMap<String, Integer> styles;


    // Constructor
    public LayoutManager() {
        layouts = new HashMap<String, Integer>();
        styles = new HashMap<String, Integer>();

        // adds layouts
        layouts.put( JAVA + WELCOME_TAG, R.layout.java_cafe_welcome_layout );
        //layouts.put( JAVA + ORDER_TAG, R.layout.java_cafe_order_layout );

        // adds styles
        styles.put( JAVA + TEXT_STYLE_TAG, R.style.JavaCafeTextStyle );
    }

    /**
     * hasWelcomeLayout
     * @param key -- business Name
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
     * @param key -- business Name
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
     * hasTextStyle
     * @param key -- business Name
     * @return boolean
     */
    public boolean hasTextStyle( String key ) {
        String k = key + TEXT_STYLE_TAG;
        if ( styles.get( k ) == null ){
            return false;

        } else {
            return true;
        }
    }

    /**
     * getWelcomeLayout
     * @param key -- business Name
     * @return int -- value of layout id
     */
    public int getWelcomeLayout( String key ) {
        String k = key + WELCOME_TAG;
        if ( layouts.get( k ) == null ){
            return R.layout.activity_main;

        } else {
            return layouts.get( k );
        }
    }

    /**
     * getOrderLayout
     * @param key -- business Name
     * @return int -- value of layout id
     */
    public int getOrderLayout( String key ) {
        String k = key + ORDER_TAG;
        if ( layouts.get( k ) == null ){
            return R.layout.activity_main;

        } else {
            return layouts.get( k );
        }
    }

    /**
     * getTextStyle
     * @param key -- business Name
     * @return int -- value of style id
     */
    public int getTextStyle( String key ) {
        String k = key + TEXT_STYLE_TAG;
        if ( styles.get( k ) == null ){
            return R.style.AppTheme;

        } else {
            return styles.get( k );
        }
    }
}
