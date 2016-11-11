package com.example.emma.orderitapp;

import java.util.HashMap;

/**
 * Created by Emma on 11/11/16.
 */

public class StyleManager {

    // Tag Names for style types
    private final String TEXT_TAG = "_Text";
    private final String BUTTON_TAG = "_Button";

    // Names of Businesses
    private final String JAVA = "Java Cafe";

    // styles HashMap
    private HashMap<String, Integer> styles;


    //Constructor
    public StyleManager() {
        styles = new HashMap<String, Integer>();
        styles.put( JAVA + TEXT_TAG, R.style.JavaCafeTextStyle );
        styles.put( JAVA + BUTTON_TAG, R.style.JavaCafeButtonStyle );
    }

    /**
     * hasTextStyle
     * @param key -- Business Name
     * @return boolean
     */
    public boolean hasTextStyle( String key ) {
        String k = key + TEXT_TAG;
        if ( styles.get( k ) == null ){
            return false;

        } else {
            return true;
        }
    }

    /**
     * hasButtonStyle
     * @param key -- Business Name
     * @return boolean
     */
    public boolean hasButtonStyle( String key ) {
        String k = key + BUTTON_TAG;
        if ( styles.get( k ) == null ){
            return false;

        } else {
            return true;
        }
    }

    /**
     * getTextStyle
     * @param key -- Business Name
     * @return int -- value of text style id
     */
    public int getTextStyle( String key ) {
        String k = key + TEXT_TAG;
        if ( styles.get( k ) == null ){
            return R.style.AppTheme;

        } else {
            return styles.get( k );
        }
    }

    /**
     * getButtonStyle
     * @param key -- Business Name
     * @return int -- value of button style id
     */
    public int getButtonStyle( String key ) {
        String k = key + BUTTON_TAG;
        if ( styles.get( k ) == null ){
            return R.style.AppTheme;

        } else {
            return styles.get( k );
        }
    }
}
