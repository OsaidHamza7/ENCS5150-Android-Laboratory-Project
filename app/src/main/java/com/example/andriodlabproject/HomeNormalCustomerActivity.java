package com.example.andriodlabproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public static List<Car> allCars = new ArrayList<>();
    public static List<Car> favCars = new ArrayList<>();
    public static List<Car> reserveCars = new ArrayList<>();
    public static List<Car> chevroletCars = new ArrayList<>();
    public static List<Car> jeepCars = new ArrayList<>();
    public static List<Car> fordCars = new ArrayList<>();
    public static List<Car> dodgeCars = new ArrayList<>();
    public static List<Car> lamborghiniCars = new ArrayList<>();
    public static List<Car> teslaCars = new ArrayList<>();
    public static List<Car> hondaCars = new ArrayList<>();
    public static List<Car> toyotaCars = new ArrayList<>();
    public static List<Car> koenigseggCars = new ArrayList<>();
    public Toolbar toolbar;
    public static NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_normal_customer);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set nav header based on information of user
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername= (TextView) headerView.findViewById(R.id.view_name);
        TextView navEmail= (TextView) headerView.findViewById(R.id.view_email);
        navUsername.setText(User.currentUser.getString(0) +" " +User.currentUser.getString(1));
        navEmail.setText(User.currentUser.getString(3));

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("HOME");
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);

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
            toolbar.setTitle("PROFILE");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileCustomerFragment()).commit();
        }
        if (item.getItemId()==R.id.nav_home){
            toolbar.setTitle("HOME");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeCustomerFragment()).commit();
        }
        if (item.getItemId()==R.id.nav_logout){
            Intent intent = new Intent(HomeNormalCustomerActivity.this, SignInActivity.class);
            HomeNormalCustomerActivity.this.startActivity(intent);
            finish();
        }
        if (item.getItemId()==R.id.nav_call_find_us){
            toolbar.setTitle("CALL US OR FIND US");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CallOrFindUsFragment()).commit();
        }
        if (item.getItemId()==R.id.nav_carMenu){
            toolbar.setTitle("CAR MENU");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CarMenuFragment()).commit();
        }
        if (item.getItemId()==R.id.nav_yourFavorites){
            toolbar.setTitle("FAVORITE CARS");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FavoriteCarsFragment()).commit();
        }
        if (item.getItemId()==R.id.nav_yourReservations){
            toolbar.setTitle("RESERVED CARS");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ReservedCarsFragment()).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}