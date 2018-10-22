package com.samjhota.trackmylocations;

/**
 * Created by Samjhota on 3/15/16.
 */
public class User {
    String firstname, lastname, email, address, city, state, zipcode, username, password;

    public User(String firstname, String lastname, String email, String address, String city, String state, String zipcode, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.username = username;
        this.password = password;

    }

    public User(String username, String password){
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.address = "";
        this.city = "";
        this.state = "";
        this.zipcode = "";
        this.username = username;
        this.password = password;
    }

}
