package com.example.andriodlabproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AsyncTaskCallback{
    private DataBaseHelper dataBaseHelper;
    String httpRequest="https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button connect= findViewById(R.id.button_Connect);

        dataBaseHelper = new DataBaseHelper(this);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }

    public DataBaseHelper getDatabaseHelper() {
        return dataBaseHelper;
    }
}