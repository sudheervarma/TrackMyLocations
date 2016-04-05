package com.samjhota.trackmylocations;

/**
 * Created by Samjhota on 3/15/16.
 */
public class User {
    String firstname, lastname, email, city, state, zipcode, username, password;

    public User(String firstname, String lastname, String email, String city, String state, String zipcode, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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
        this.city = "";
        this.state = "";
        this.zipcode = "";
        this.username = username;
        this.password = password;
    }

}
