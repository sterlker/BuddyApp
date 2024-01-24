package com.example.buddyapps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
//            FriendsFragment friendsFragment = new FriendsFragment();
//            friendsFragment.loadFromDBToMemory();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FriendsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_friends);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if (itemID == R.id.nav_friends) {
//            FriendsFragment friendsFragment = new FriendsFragment();
//            friendsFragment.loadFromDBToMemory();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FriendsFragment()).commit();
            Toast.makeText(this, "My Friends", Toast.LENGTH_SHORT).show();
        }
        else if (itemID == R.id.nav_birthday) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new BirthdayFragment()).commit();
            Toast.makeText(this, "Birthday", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



}