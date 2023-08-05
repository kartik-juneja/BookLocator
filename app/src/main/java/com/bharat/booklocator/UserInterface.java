package com.bharat.booklocator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class UserInterface extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SearchView searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface2);


        searchview = findViewById(R.id.searchview);
        bottomNavigationView = findViewById(R.id.navigationbtn);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Fragment_home()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Fragment_home()).commit();
                        break;
                    case R.id.Book1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new book_fragment()).commit();
                        break;
                    case R.id.issuebook:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new IssueBook_fragment()).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new profile_Fragment()).commit();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Fragment_home()).commit();
                        break;
                }
                return true;
            }
        });


    }

}