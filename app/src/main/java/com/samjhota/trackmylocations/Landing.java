package com.samjhota.trackmylocations;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Landing extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDataBase;

    Button bViewMaps;
    Button bViewMyProfile;
    Button bLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        myDataBase = new DatabaseHelper(this);

        bViewMaps = (Button) findViewById(R.id.bViewMaps);
        bViewMyProfile = (Button) findViewById(R.id.bViewMyProfile);
        bLogOut = (Button) findViewById(R.id.bLogOut);

        bViewMaps.setOnClickListener(this);
        bViewMyProfile.setOnClickListener(this);
        bLogOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bViewMaps:

                //showErrorMessage("Page is under construction !!!");

                startActivity(new Intent(this, MapsActivity.class));

                break;

            case R.id.bViewMyProfile:
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

            case R.id.bLogOut:

                showLogOffMessage();

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

    private void showLogOffMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Are you sure you want to Log Off ???").setPositiveButton("YES",null).setNegativeButton("NO", null);
        dialogBuilder.show();
    }
}
