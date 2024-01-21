package com.example.andriodlabproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SignInActivity extends AppCompatActivity {

    static final SignInFragment signInFragment = new SignInFragment();
    static final SignInButtonFragment signInButtonFragment = new SignInButtonFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onResume() {
        super.onResume();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.root_layout, signInButtonFragment, "SignInButtonFrag");
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ImageButton backToMain= (ImageButton) findViewById(R.id.backToMain);


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder details = new StringBuilder();
                details.append("Are you sure you want to disconnect to the server?");

                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setTitle("Confirm Disconnect")
                        .setMessage(details.toString())
                        .setNegativeButton("cancel",null)
                        .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                SignInActivity.this.startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();

            }
        });


    }

    public void addSignInFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(signInButtonFragment);

        fragmentTransaction.add(R.id.root_layout, signInFragment, "SignInFrag");
        fragmentTransaction.commit();
    }
    public void removeSignInFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(signInFragment);
        fragmentTransaction.commit();
    }


}