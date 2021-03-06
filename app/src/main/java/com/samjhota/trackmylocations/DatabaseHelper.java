package com.samjhota.trackmylocations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Samjhota on 3/15/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MyDataBase.db";
    public static final String TABLE_NAME = "Register_table";
    public static final String COL_1 = "USER_ID";
    public static final String COL_2 = "FIRST_NAME";
    public static final String COL_3 = "LAST_NAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "ADDRESS";
    public static final String COL_6 = "CITY";
    public static final String COL_7 = "STATE";
    public static final String COL_8 = "ZIPCODE";
    public static final String COL_9 = "USER_NAME";
    public static final String COL_10 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRST_NAME TEXT,LAST_NAME TEXT,EMAIL TEXT,ADDRESS TEXT, CITY TEXT, STATE TEXT, ZIPCODE TEXT, USER_NAME TEXT UNIQUE,PASSWORD TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String firstname, String lastname, String email, String address, String city, String state, String zipcode, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstname);
        contentValues.put(COL_3, lastname);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, address);
        contentValues.put(COL_6, city);
        contentValues.put(COL_7, state);
        contentValues.put(COL_8, zipcode);
        contentValues.put(COL_9, username);
        contentValues.put(COL_10, password);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME + " WHERE USER_NAME = '" + username + "'", null);
        return res;

    }

    public boolean updateData(String firstname, String lastname, String email, String address, String city, String state, String zipcode, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, firstname);
        contentValues.put(COL_2, lastname);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, address);
        contentValues.put(COL_5, city);
        contentValues.put(COL_6, state);
        contentValues.put(COL_7, zipcode);
        contentValues.put(COL_8, username);
        contentValues.put(COL_9, password);

        db.execSQL("UPDATE " + TABLE_NAME + " SET FIRST_NAME = '" + firstname + "', LAST_NAME = '" + lastname + "', EMAIL = '" + email + "', ADDRESS = '" + address + "', CITY = '" + city + "', STATE = '" + state + "', ZIPCODE = '" + zipcode + "', PASSWORD = '" + password + "' WHERE USER_NAME = '" + username + "'");
        return true;
    }

    public Integer deleteData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "USER_NAME = ?", new String[] {username});
    }

    public Cursor fetchData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME + " WHERE USER_NAME = '" + username + "'", null);
        return res;
    }

    public Cursor fetchUserData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME + " WHERE USER_NAME = '" + username + "' AND PASSWORD = '" + password + "'", null);
        return res;
    }
}
