package com.example.emma.orderitapp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Emma on 11/11/16.
 */

public class LayoutManager {

    // Tag Names for style types
    private final String WELCOME_TAG = "_Welcome";
    private final String SCAN_TAG = "_Scan";
    private final String CHECKOUT_TAG = "_Checkout";
    private final String HISTORY_TAG = "_History";
    private final String TEXT_STYLE_TAG = "_TextStyle";
    private final String SUB_TEXT_STYLE_TAG = "_SubTextStyle";
    private ArrayList<String> tags;
    // Names of Businesses
    private final String JAVA = "Java Cafe";

    // Layout HashMap
    private HashMap<String, Integer> layouts;
    private HashMap<String, Integer> styles;


    // Constructor
    public LayoutManager() {
        layouts = new HashMap<String, Integer>();
        styles = new HashMap<String, Integer>();
        tags = new ArrayList<String>();

        // adds layouts
        layouts.put( JAVA + WELCOME_TAG, R.layout.activity_welcome_page_java_cafe);
        layouts.put( JAVA + CHECKOUT_TAG, R.layout.activity_checkout_java_cafe);
        layouts.put( JAVA + HISTORY_TAG, R.layout.activity_history_java_cafe);

        // adds styles
        styles.put( JAVA + TEXT_STYLE_TAG, R.style.JavaCafeTextStyle );
        styles.put( JAVA + SUB_TEXT_STYLE_TAG, R.style.JavaCafeSubTextStyle );

        // adds tags to array
        tags.add(WELCOME_TAG);
        tags.add(CHECKOUT_TAG);
        tags.add(HISTORY_TAG);
    }

    /**
     * getWelcomeLayout
     * @param businessName -- business Name
     * @return int -- value of layout id
     */
    public int getWelcomeLayout( String businessName ) {
        String k = businessName + WELCOME_TAG;
        if ( layouts.get( k ) == null ){
            return R.layout.activity_main;
        } else {
            return layouts.get( k );
        }
    }

    /**
     * getScanLayout
     * @param businessName -- business Name
     * @return int -- value of layout id
     */
    public int getScanLayout( String businessName ) {
        String k = businessName + SCAN_TAG;
        if ( layouts.get( k ) == null ){
            return R.layout.activity_main;
        } else {
            return layouts.get( k );
        }
    }

    /**
     * getCheckoutLayout
     * @param businessName -- business Name
     * @return int -- value of layout id
     */
    public int getCheckoutLayout( String businessName ) {
        String k = businessName + CHECKOUT_TAG;
        if ( layouts.get( k ) == null ){
            return R.layout.activity_main;
        } else {
            return layouts.get( k );
        }
    }

    /**
     * getHistoryLayout
     * @param businessName -- business Name
     * @return int -- value of layout id
     */
    public int getHistoryLayout( String businessName ) {
        String k = businessName + HISTORY_TAG;
        if ( layouts.get( k ) == null ){
            return R.layout.activity_main;
        } else {
            return layouts.get( k );
        }
    }


    /**
     * getTextStyle
     * @param businessName -- business Name
     * @return int -- value of style id
     */
    public int getTextStyle( String businessName ) {
        String k = businessName + TEXT_STYLE_TAG;
        if ( styles.get( k ) == null ){
            return R.style.AppTheme;

        } else {
            return styles.get( k );
        }
    }

    /**
     * getSubTextStyle
     * @param businessName -- business Name
     * @return int -- value of style id
     */
    public int getSubTextStyle( String businessName ) {
        String k = businessName + SUB_TEXT_STYLE_TAG;
        if ( styles.get( k ) == null ){
            return R.style.AppTheme;

        } else {
            return styles.get( k );
        }
    }
}
