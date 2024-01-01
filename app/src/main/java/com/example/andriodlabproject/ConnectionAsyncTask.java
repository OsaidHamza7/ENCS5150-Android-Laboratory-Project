package com.example.andriodlabproject;


import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class ConnectionAsyncTask extends AsyncTask<String, String,
        String> {
    private WeakReference<Activity> weakActivity;
    public static boolean isConnectionSuccessful = true;
    public ConnectionAsyncTask(Activity activity) {
        this.weakActivity = new WeakReference<>(activity);
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            return HttpManager.getData(params[0]);
        } catch (Exception e) {
            isConnectionSuccessful = false;
            return null;
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Activity activity = weakActivity.get();
        if (activity == null || activity.isFinishing()) return;

        if (!isConnectionSuccessful || s == null) {
            Toast.makeText(activity, "the connection is unsuccessful", Toast.LENGTH_LONG).show();
        } else {
            HomeNormalCustomerActivity.carListss = CarJsonParser.getObjectFromJson(s);
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


}