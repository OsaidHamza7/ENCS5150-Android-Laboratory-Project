package com.example.andriodlabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button button_registerSignIn = (Button) findViewById(R.id.button_registerSignIn);
        ImageButton button_backToMain = (ImageButton) findViewById(R.id.button_registerBackToMain);

        // set the listener for the sign in button
        button_registerSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the sign in activity
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                SignUpActivity.this.startActivity(intent);
                finish();
            }
        });

        // set the listener for the back button
        button_backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the main activity
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                SignUpActivity.this.startActivity(intent);
                finish();
            }
        });




    }



}