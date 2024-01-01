package com.example.andriodlabproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeNormalCustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public LinearLayout secondLinearLayout ;
    final Handler handler = new Handler();
    private CarAdapter adapter;

    public static List<Car> carListss = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_normal_customer);


        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Car Dealer");
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeCustomerFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);


    }
    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.nav_profile){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new profileCustomerFragment()).commit();
        }
        if (item.getItemId()==R.id.nav_home){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeCustomerFragment()).commit();
        }
        if (item.getItemId()==R.id.nav_logout){
            Intent intent = new Intent(HomeNormalCustomerActivity.this, SignInActivity.class);
            HomeNormalCustomerActivity.this.startActivity(intent);
            finish();
        }
        if (item.getItemId()==R.id.nav_call_find_us){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CallOrFindUsFragment()).commit();
        }
        if (item.getItemId()==R.id.nav_carMenu){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CarMenuFragment()).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}