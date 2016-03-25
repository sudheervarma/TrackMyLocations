package com.samjhota.trackmylocations;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDataBase;

    EditText etFirstName, etLastName, etEmail, etCity, etState, etZipCode, etUserName, etPassword;
    Button bRegister;
    Button bViewRegistered;
    Button bUpdateInfo;
    Button bDelete;

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
        bViewRegistered = (Button) findViewById(R.id.bViewRegistered);
        //bUpdateInfo = (Button) findViewById(R.id.bUpdateInfo);
        bDelete = (Button) findViewById(R.id.bDelete);

        bRegister.setOnClickListener(this);
        bViewRegistered.setOnClickListener(this);
        //bUpdateInfo.setOnClickListener(this);
        bDelete.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bRegister:
                String firstname = etFirstName.getText().toString();
                String lastname = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String city = etCity.getText().toString();
                String state = etState.getText().toString();
                String zipcode = etZipCode.getText().toString();
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                User registeredData = new User(firstname, lastname, email, city, state, zipcode, username, password);

                boolean isInserted = myDataBase.insertData(firstname, lastname, email, city, state, zipcode, username, password);
                if (isInserted == true) {
                    Toast.makeText(Register.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Register.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.bViewRegistered:
                Cursor res = myDataBase.getAllData();
                if (res.getCount() ==0) {
                    // Show Message
                    showMessage("Error:", "No Data Found!!!");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("UserID : "+ res.getString(0)+"\n");
                    buffer.append("FirstName : "+ res.getString(1)+"\n");
                    buffer.append("LastName : "+ res.getString(2)+"\n");
                    buffer.append("Email : "+ res.getString(3)+"\n");
                    buffer.append("City : "+ res.getString(4)+"\n");
                    buffer.append("State : "+ res.getString(5)+"\n");
                    buffer.append("Zip Code : "+ res.getString(6)+"\n");
                    buffer.append("UserName : "+ res.getString(7)+"\n");
                    buffer.append("Password : "+ res.getString(8)+"\n\n");
                }

                // Show all data
                showMessage("Data:", buffer.toString());
                break;

//            case R.id.bUpdateInfo:
//                String firstname_u = etFirstName.getText().toString();
//                String lastname_u = etLastName.getText().toString();
//                String email_u = etEmail.getText().toString();
//                String city_u = etCity.getText().toString();
//                String state_u = etState.getText().toString();
//                String zipcode_u = etZipCode.getText().toString();
//                String username_u = etUserName.getText().toString();
//                String password_u = etPassword.getText().toString();
//
//                boolean isUpdate = myDataBase.updateData(firstname_u, lastname_u, email_u, city_u, state_u, zipcode_u, username_u, password_u);
//                if (isUpdate == true) {
//                    Toast.makeText(Register.this, "Data Updated Successfully", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(Register.this, "Data not Updated", Toast.LENGTH_LONG).show();
//                }
//
//                break;

            case R.id.bDelete:
                Integer deleteRows = myDataBase.deleteData(etUserName.getText().toString());
                if (deleteRows > 0){
                    Toast.makeText(Register.this, "Data Deleted Successfully", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Register.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                }
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

}
