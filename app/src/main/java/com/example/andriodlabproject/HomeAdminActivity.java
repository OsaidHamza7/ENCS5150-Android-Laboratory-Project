package com.example.andriodlabproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    public Toolbar toolbar;
    public static NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername= (TextView) headerView.findViewById(R.id.view_name);
        TextView navEmail= (TextView) headerView.findViewById(R.id.view_email);
        navUsername.setText(User.currentUser.getString(0) +" " +User.currentUser.getString(1));
        navEmail.setText(User.currentUser.getString(3));

        toolbar=findViewById(R.id.toolbarAdmin);
        toolbar.setTitle("PROFILE");
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout_admin);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new ProfileCustomerFragment()).commit();
        navigationView.setCheckedItem(R.id.navAdmin_profile);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.navAdmin_profile){
            toolbar.setTitle("PROFILE");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new ProfileCustomerFragment()).commit();
        }
        if (item.getItemId()==R.id.navAdmin_deleteCustomers){
            toolbar.setTitle("DELETE CUSTOMERS");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new DeleteCustomersFragment()).commit();
        }
        if (item.getItemId()==R.id.navAdmin_logout){
            Intent intent = new Intent(HomeAdminActivity.this, SignInActivity.class);
            HomeAdminActivity.this.startActivity(intent);
            finish();
        }



        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}