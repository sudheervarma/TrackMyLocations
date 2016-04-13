package com.samjhota.trackmylocations;

import android.app.AlertDialog;
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
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDataBase;

    Button bUpdateProfile;
    Button bUnRegister;
    EditText etFirstName, etLastName, etEmail, etCity, etState, etZipCode, etUserName, etPassword;
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
        etPassword = (EditText) findViewById(R.id.etPassword);

        bUpdateProfile = (Button) findViewById(R.id.bUpdateProfile);
        bUnRegister = (Button) findViewById(R.id.bUnRegister);

        bUpdateProfile.setOnClickListener(this);
        bUnRegister.setOnClickListener(this);

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
        etPassword.setText(user.password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bUpdateProfile:
                String firstname = etFirstName.getText().toString();
                String lastname = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String city = etCity.getText().toString();
                String state = etState.getText().toString();
                String zipcode = etZipCode.getText().toString();
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if (firstname.isEmpty()){
                    showErrorMessage("'First Name' cannot be empty !!!");
                    return;
                }

                if (lastname.isEmpty()){
                    showErrorMessage("'Last Name' cannot be empty !!!");
                    return;
                }

                if (email.isEmpty()){
                    showErrorMessage("'Email' cannot be empty !!!");
                    return;
                }

                if(isValidEmailId(email) == false) {
                    showErrorMessage("Invalid Email address !!!");
                    return;
                }

                if (city.isEmpty()){
                    showErrorMessage("'City' cannot be empty !!!");
                    return;
                }

                if (state.isEmpty()){
                    showErrorMessage("'State' cannot be empty !!!");
                    return;
                }

                if (state.length() != 2){
                    showErrorMessage("'State' must be 2 characters !!!");
                    return;
                }

                if (zipcode.isEmpty()){
                    showErrorMessage("'Zip Code' cannot be empty !!!");
                    return;
                }

                if (zipcode.length() != 5){
                    showErrorMessage("'Zip Code' must be 5 digits !!!");
                    return;
                }

                if (username.isEmpty()){
                    showErrorMessage("'Username' cannot be empty !!!");
                    return;
                }

                if (password.isEmpty()){
                    showErrorMessage("'Password' cannot be empty !!!");
                    return;
                }

                boolean isUpdate = myDataBase.updateData(firstname, lastname, email, city, state, zipcode, username, password);
                if (isUpdate == true) {
                    Toast.makeText(MainActivity.this, "Data Updated Successfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.bUnRegister:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                Integer deleteRows = myDataBase.deleteData(etUserName.getText().toString());
                if (deleteRows > 0){
                    Toast.makeText(MainActivity.this, "Member Data Unregistered Successfully", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Member Data not Unregistered", Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.bLogOut:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    private void showErrorMessage(String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}