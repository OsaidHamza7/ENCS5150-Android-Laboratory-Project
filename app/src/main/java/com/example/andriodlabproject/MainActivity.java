package com.example.andriodlabproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AsyncTaskCallback{
    private DataBaseHelper dataBaseHelper;
    private ProgressBar progressBar;
    private View screenOverlay;

    String httpRequest="https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button connect= findViewById(R.id.button_Connect);
        progressBar = findViewById(R.id.barConnection);
        screenOverlay = findViewById(R.id.screenOverlay);

        dataBaseHelper = new DataBaseHelper(this);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar(true); // show the progress bar
                ConnectionAsyncTask connectionAsyncTask = new
                        ConnectionAsyncTask(MainActivity.this,MainActivity.this);
                connectionAsyncTask.execute(httpRequest);

            }

        });


    }

    @Override
    public void onTaskComplete(boolean success) {
        if (success) {
            // if the connection is succeeded
            showProgressBar(false); // hide the progress bar
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }

    public DataBaseHelper getDatabaseHelper() {
        return dataBaseHelper;
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            screenOverlay.setVisibility(View.VISIBLE); // Show the overlay
        } else {
            progressBar.setVisibility(View.GONE);
            screenOverlay.setVisibility(View.GONE); // Hide the overlay
        }
    }
}