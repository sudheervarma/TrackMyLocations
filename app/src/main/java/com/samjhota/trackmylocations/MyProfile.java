package com.samjhota.trackmylocations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDataBase;
    UserLocalStore userLocalStore;

    Button bUpdateProfile;
    Button bUnRegister;
    EditText etFirstName, etLastName, etEmail, etAddress, etCity, etState, etZipCode, etUserName, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBase = new DatabaseHelper(this);
        userLocalStore = new UserLocalStore(this);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etCity = (EditText) findViewById((R.id.etCity));
        etState = (EditText) findViewById(R.id.etState);
        etZipCode = (EditText) findViewById(R.id.etZipCode);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        bUpdateProfile = (Button) findViewById(R.id.bUpdateProfile);
        bUnRegister = (Button) findViewById(R.id.bUnRegister);

        bUpdateProfile.setOnClickListener(this);
        bUnRegister.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
          if (authenticate() == true){
            displayUserDetails();
        }else {
            startActivity(new Intent(MyProfile.this, Login.class));
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
        etAddress.setText(user.address);
        etCity.setText(user.city);
        etState.setText(user.state);
        etZipCode.setText(user.zipcode);
        etUserName.setText(user.username);
        etPassword.setText(user.password);
        etConfirmPassword.setText(user.password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bUpdateProfile:
                final String firstname = etFirstName.getText().toString();
                final String lastname = etLastName.getText().toString();
                final String email = etEmail.getText().toString();
                final String address = etAddress.getText().toString();
                final String city = etCity.getText().toString();
                final String state = etState.getText().toString();
                final String zipcode = etZipCode.getText().toString();
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final String confirmpassword = etConfirmPassword.getText().toString();

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

                if (address.isEmpty()){
                    showErrorMessage("'Address' cannot be empty !!!");
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

                if (confirmpassword.isEmpty()){
                    showErrorMessage("'Confirm Password' cannot be empty !!!");
                    return;
                }

                if (!(password).equals(confirmpassword)){
                    showErrorMessage("Password mismatch!!!");
                    return;
                }

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage("Are you sure you want to save the changes ???")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                boolean isUpdate = myDataBase.updateData(firstname, lastname, email, address, city, state, zipcode, username, password);
                                if (isUpdate == true) {
                                    Toast.makeText(MyProfile.this, "Data Updated Successfully", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MyProfile.this, Landing.class));
                                }else{
                                    Toast.makeText(MyProfile.this, "Data not Updated", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();

                break;

            case R.id.bUnRegister:

                dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage("Are you sure you want to Unregister ???")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Integer deleteRows = myDataBase.deleteData(etUserName.getText().toString());
                                if (deleteRows > 0){
                                    Toast.makeText(MyProfile.this, "Successfully Unregistered", Toast.LENGTH_LONG).show();
                                    userLocalStore.clearUserData();
                                    userLocalStore.setUserLoggedIn(false);
                                    startActivity(new Intent(MyProfile.this, Login.class));
                                }else {
                                    Toast.makeText(MyProfile.this, "Unregistration is not successful", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();

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