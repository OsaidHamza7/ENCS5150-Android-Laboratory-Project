package com.example.andriodlabproject;


import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class ConnectionAsyncTask extends AsyncTask<String, String,
        String> {
    private WeakReference<Activity> weakActivity;
    public static boolean isConnectionSuccessful = true;
    private AsyncTaskCallback callback;

    public ConnectionAsyncTask(Activity activity, AsyncTaskCallback callback) {
        this.weakActivity = new WeakReference<>(activity);
        this.callback = callback;

    }
    @Override
    protected String doInBackground(String... params) {
        try {
            return HttpManager.getData(params[0]);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Activity activity = weakActivity.get();
        if (activity == null || activity.isFinishing()) return;

        if (s == null) {
            Toast.makeText(activity, "the connection is unsuccessful", Toast.LENGTH_LONG).show();
            callback.onTaskComplete(false);
        }
        else {
            Toast.makeText(activity, "the connection is successful", Toast.LENGTH_LONG).show();
            HomeNormalCustomerActivity.allCars = CarJsonParser.getObjectFromJson(s);
            callback.onTaskComplete(true);
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


}