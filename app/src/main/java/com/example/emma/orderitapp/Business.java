package com.example.emma.orderitapp;

import java.util.ArrayList;

/**
 * Created by Emma on 11/10/16.
 */

public class Business {

    private String type;
    private String name;
    private String email;
    private String phone;
    private String address;
    private ArrayList<String> attributes;


    // Constructor
    public Business() {

        attributes = new ArrayList<String>();

    }

    public void  setType(String name){this.type = name;}
    public String getType() {return type;}

    public void  setName(String name){this.name = name;}
    public String getName() {return name;}

    public void  setEmail(String email){this.email = email;}
    public String getEmail() {return email;}

    public void  setPhone(String phone){this.phone = phone;}
    public String getPhone() {return phone;}

    public void  setAddress(String address){this.address = address;}
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
