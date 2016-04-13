package com.samjhota.trackmylocations;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    Button bStepCounter;
    Button bLogOut;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        myDataBase = new DatabaseHelper(this);

        bViewMaps = (Button) findViewById(R.id.bViewMaps);
        bViewMyProfile = (Button) findViewById(R.id.bViewMyProfile);
        bStepCounter = (Button) findViewById(R.id.bStepCounter);
        bLogOut = (Button) findViewById(R.id.bLogOut);

        bViewMaps.setOnClickListener(this);
        bViewMyProfile.setOnClickListener(this);
        bStepCounter.setOnClickListener(this);
        bLogOut.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bViewMaps:

                //showErrorMessage("Page is under construction !!!");

                startActivity(new Intent(this, MapsActivity.class));

                break;

            case R.id.bViewMyProfile:
//                User user = userLocalStore.getLoggedInUser();
//
//                Cursor res = myDataBase.getAllData(user.username);
//                if (res.getCount() ==0) {
//                    // Show Message
//                    showMessage("Error:", "No Data Found!!!");
//                    return;
//                }
//
//                StringBuffer buffer = new StringBuffer();
//                while (res.moveToNext()) {
//                    buffer.append("UserID : "+ res.getString(0)+"\n");
//                    buffer.append("FirstName : "+ res.getString(1)+"\n");
//                    buffer.append("LastName : "+ res.getString(2)+"\n");
//                    buffer.append("Email : "+ res.getString(3)+"\n");
//                    buffer.append("City : "+ res.getString(4)+"\n");
//                    buffer.append("State : "+ res.getString(5)+"\n");
//                    buffer.append("Zip Code : "+ res.getString(6)+"\n");
//                    buffer.append("UserName : "+ res.getString(7)+"\n");
//                    buffer.append("Password : "+ res.getString(8)+"\n\n");
//                }
//
//                // Show all data
//                showMessage("Data:", buffer.toString());
//
                startActivity(new Intent(this, MainActivity.class));

                break;

            case R.id.bStepCounter:

                startActivity(new Intent(this, StepCounter.class));
                break;

            case R.id.bLogOut:

                showLogOffMessage();
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
        dialogBuilder.setMessage("Are you sure you want to Log Off ???")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
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
