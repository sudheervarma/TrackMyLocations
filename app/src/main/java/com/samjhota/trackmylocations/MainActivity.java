package com.samjhota.trackmylocations;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDataBase;

    Button bLogOut;
    EditText etFirstName, etLastName, etEmail, etCity, etState, etZipCode, etUserName;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBase = new DatabaseHelper(this);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCity = (EditText) findViewById((R.id.etCity));
        etState = (EditText) findViewById(R.id.etState);
        etZipCode = (EditText) findViewById(R.id.etZipCode);
        etUserName = (EditText) findViewById(R.id.etUserName);

        bLogOut = (Button) findViewById(R.id.bLogOut);

        bLogOut.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
          if (authenticate() == true){
            displayUserDetails();
        }else {
            startActivity(new Intent(MainActivity.this, Login.class));
        }

//        Cursor res = myDataBase.getAllData();
//        if (res.getCount() ==0) {
//            // Show Message
//            //showMessage("Error:", "No Data Found!!!");
//            return;
//        }

    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();

        etFirstName.setText(user.firstname);
        etLastName.setText(user.lastname);
        etEmail.setText(user.email);
        etCity.setText(user.city);
        etState.setText(user.state);
        etZipCode.setText(user.zipcode);
        etUserName.setText(user.username);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogOut:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}