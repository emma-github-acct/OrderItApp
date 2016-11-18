package com.example.emma.orderitapp;

import android.app.Application;

/**
 * The Customer model object.
 */

public class Customer{

    private static String name;
    private static String email;
    private static String phone;
    private static String address;
    private static boolean allergy;


    // Constructor
    public Customer() {}

    public static void  setName(String name1){name = name1;}
    public static String getName() {return name;}

    public static void  setEmail(String email1){email = email1;}
    public static String getEmail() {return email;}

    public static void  setPhone(String phone1){phone = phone1;}
    public static String getPhone() {return phone;}

    public static void  setAddress(String address1){address = address1;}
    public static String getAddress() {return address;}

    public static void  setAllergy(boolean allergy1){allergy = allergy1;}
    public static boolean getAllergy() {return allergy;}
}
