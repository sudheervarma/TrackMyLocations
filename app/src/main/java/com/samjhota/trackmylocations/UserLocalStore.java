package com.samjhota.trackmylocations;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Samjhota on 3/15/16.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("firstname", user.firstname);
        spEditor.putString("lastname", user.lastname);
        spEditor.putString("email", user.email);
        spEditor.putString("city", user.city);
        spEditor.putString("state", user.state);
        spEditor.putString("zipcode", user.zipcode);
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String firstname = userLocalDatabase.getString("firstname", "");
        String lastname = userLocalDatabase.getString("lastname", "");
        String email = userLocalDatabase.getString("email", "");
        String city = userLocalDatabase.getString("city", "");
        String state = userLocalDatabase.getString("state", "");
        String zipcode = userLocalDatabase.getString("zipcode", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");

        User storedUser = new User(firstname, lastname, email, city, state, zipcode, username, password);

        return storedUser;

    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn", false) == true){
            return true;
        }else {
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
