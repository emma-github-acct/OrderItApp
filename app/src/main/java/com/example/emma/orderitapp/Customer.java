package com.example.emma.orderitapp;

/**
 * The Customer model object.
 */

public class Customer {

    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean vegetarian;


    public void  setName(String name){this.name = name;}
    public String getName() {return name;}

    public void  setEmail(String email){this.email = email;}
    public String getEmail() {return email;}

    public void  setPhone(String phone){this.phone = phone;}
    public String getPhone() {return phone;}

    public void  setAddress(String address){this.address = address;}
    public String getAddress() {return address;}

    public void  setVegetarian(String address){this.vegetarian = vegetarian;}
    public boolean getVegetarian() {return vegetarian;}
}
