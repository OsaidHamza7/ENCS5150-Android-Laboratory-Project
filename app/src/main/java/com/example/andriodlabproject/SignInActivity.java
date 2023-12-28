package com.example.andriodlabproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                SignInActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    public void addSignInFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag("SignInButtonFrag") != null) {
            fragmentTransaction.remove(signInButtonFragment);
        }

        fragmentTransaction.add(R.id.root_layout, signInFragment, "SignInFrag");
        fragmentTransaction.commit();
    }



}