package com.example.playerx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.playerx.Fragments.HomeFragment;
import com.example.playerx.Fragments.LikedFragment;
import com.example.playerx.Fragments.SearchFragment;
import com.example.playerx.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static TextView songDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songDisplay = findViewById(R.id.songDisplay);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        songDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaPlayer.class);
                startActivity(intent);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.home :
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.liked :
                    selectedFragment = new LikedFragment();
                    break;
                case R.id.search :
                    selectedFragment = new SearchFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, selectedFragment).commit();
            return true;
        }
    };
}
