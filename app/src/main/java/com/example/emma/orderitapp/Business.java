package com.example.emma.orderitapp;

import java.util.ArrayList;

/**
 * Created by Emma on 11/10/16.
 */

public class Business {

    private static String type;
    private static String name;
    private static String email;
    private static String phone;
    private static String address;
    private static ArrayList<String> attributes;


    // Constructor
    public Business() {

        attributes = new ArrayList<String>();

    }

    public static void  setType(String type1){type = type1;}
    public static String getType() {return type;}

    public static void  setName(String name1){name = name1;}
    public static String getName() {return name;}

    public static void  setEmail(String email1){email = email1;}
    public static String getEmail() {return email;}

    public static void  setPhone(String phone1){phone = phone1;}
    public static String getPhone() {return phone;}

    public static void  setAddress(String address1){address = address1;}
    public static String getAddress() {return address;}


    public static ArrayList<String> getAttributes() {
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
