package com.samjhota.trackmylocations;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.AndroidCharacter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDataBase;

    EditText etFirstName, etLastName, etEmail, etCity, etState, etZipCode, etUserName, etPassword;
    TextView tvLoginLink;
    Button bRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDataBase = new DatabaseHelper(this);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCity = (EditText) findViewById((R.id.etCity));
        etState = (EditText) findViewById(R.id.etState);
        etZipCode = (EditText) findViewById(R.id.etZipCode);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bRegister = (Button) findViewById(R.id.bRegister);
        tvLoginLink = (TextView) findViewById(R.id.tvLoginLink);

        bRegister.setOnClickListener(this);
        tvLoginLink.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bRegister:
                String firstname = etFirstName.getText().toString().trim();
                String lastname = etLastName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String state = etState.getText().toString().trim();
                String zipcode = etZipCode.getText().toString().trim();
                String username = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

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

                User registeredData = new User(firstname, lastname, email, city, state, zipcode, username, password);

                // Checking if user is already registered
                Cursor res1 = myDataBase.fetchData(username);
                if (res1.getCount() ==0) {
                    boolean isInserted = myDataBase.insertData(firstname, lastname, email, city, state, zipcode, username, password);
                    if (isInserted == true) {
                        Toast.makeText(Register.this, "Registration is successful !!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, Login.class));
                    }else{
                        Toast.makeText(Register.this, "Registration is not successful !!!", Toast.LENGTH_LONG).show();
                    }
                } else {

                    showErrorMessage("Member is already registered !!!");
                    //startActivity(new Intent(this, Register.class));
                    return;
                }

                break;
            case R.id.tvLoginLink:

                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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
