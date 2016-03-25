package com.samjhota.trackmylocations;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by srudraraju3 on 3/23/16.
 */
public class ServerRequests {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;

    public ServerRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Authenticating");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();

    }
    public void fetchUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();

    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback){
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

}
