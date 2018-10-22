package com.samjhota.trackmylocations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Landing extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDataBase;

    Button bViewMyLocation;
    Button bStepCounter;
    Button bViewMyProfile;
    Button bLogOut;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        myDataBase = new DatabaseHelper(this);
        userLocalStore = new UserLocalStore(this);

        bViewMyLocation = (Button) findViewById(R.id.bViewMyLocation);
        bStepCounter = (Button) findViewById(R.id.bStepCounter);
        bViewMyProfile = (Button) findViewById(R.id.bViewMyProfile);
        bLogOut = (Button) findViewById(R.id.bLogOut);

        bViewMyLocation.setOnClickListener(this);
        bStepCounter.setOnClickListener(this);
        bViewMyProfile.setOnClickListener(this);
        bLogOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bViewMyLocation:

                startActivity(new Intent(this, MapsActivity.class));
                break;

            case R.id.bStepCounter:

                startActivity(new Intent(this, StepCounter.class));
                break;

            case R.id.bViewMyProfile:

                startActivity(new Intent(this, MyProfile.class));
                break;

            case R.id.bLogOut:

                showLogOffMessage();
                break;
        }
    }

    private void showLogOffMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Are you sure you want to Log Off ???")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(Landing.this, Login.class));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.show();
    }
}
