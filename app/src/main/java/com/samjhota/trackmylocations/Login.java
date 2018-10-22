package com.samjhota.trackmylocations;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDataBase;
    ProgressDialog progressDialog;

    Button bLogin;
    EditText etUserName, etPassword;
    TextView tvRegisterLink;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDataBase = new DatabaseHelper(this);
        userLocalStore = new UserLocalStore(this);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bLogin:
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty()){
                    showUserNameEmptyMessage();
                    return;
                }

                if (password.isEmpty()){
                    showPasswordEmptyMessage();
                    return;
                }

                User user = new User(username, password);
                Cursor res = myDataBase.fetchUserData(username, password);

                if (res.getCount() ==0) {
                    // Show Error Message
                    showErrorMessage();
                } else {

                    userLocalStore.storeUserData(res);
                    userLocalStore.setUserLoggedIn(true);

                    startActivity(new Intent(this, Landing.class));
                }

                break;

            case R.id.tvRegisterLink:

                startActivity(new Intent(this, Register.class));
                break;
        }

    }

    private void showUserNameEmptyMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Please enter Username !!!");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void showPasswordEmptyMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Please enter Password !!!");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect Username or Password !!!");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

}
