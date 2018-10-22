package com.samjhota.trackmylocations;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

/**
 * Created by Samjhota on 3/15/16.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(Cursor user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        while (user.moveToNext()) {
            spEditor.putString("firstname", user.getString(1));
            spEditor.putString("lastname", user.getString(2));
            spEditor.putString("email", user.getString(3));
            spEditor.putString("address", user.getString(4));
            spEditor.putString("city", user.getString(5));
            spEditor.putString("state", user.getString(6));
            spEditor.putString("zipcode", user.getString(7));
            spEditor.putString("username", user.getString(8));
            spEditor.putString("password", user.getString(9));
        }
        spEditor.commit();
    }

//    public void storeUserData(User user){
//        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
//        spEditor.putString("firstname", user.firstname);
//        spEditor.putString("lastname", user.lastname);
//        spEditor.putString("email", user.email);
//        spEditor.putString("city", user.city);
//        spEditor.putString("state", user.state);
//        spEditor.putString("zipcode", user.zipcode);
//        spEditor.putString("username", user.username);
//        spEditor.putString("password", user.password);
//        spEditor.commit();
//    }

    public User getLoggedInUser(){
        String firstname = userLocalDatabase.getString("firstname", "");
        String lastname = userLocalDatabase.getString("lastname", "");
        String email = userLocalDatabase.getString("email", "");
        String address = userLocalDatabase.getString("address", "");
        String city = userLocalDatabase.getString("city", "");
        String state = userLocalDatabase.getString("state", "");
        String zipcode = userLocalDatabase.getString("zipcode", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");

        User storedUser = new User(firstname, lastname, email, address, city, state, zipcode, username, password);

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
