package com.saurabh.lettucelens;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saurabh.homepage.R;


public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId())
                    {
                        case R.id.menu:
                            startActivity(new Intent(getApplicationContext(), menupage.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.home:
                            return true;


                    case R.id.you:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    return false;
                }
            });


        ImageView treeLogo = findViewById(R.id.treeLogo);
        TextView plant_Disease = findViewById(R.id.plant_Disease);
        TextView pest_Detection = findViewById(R.id.pest_Detection);
        TextView soilDetection = findViewById(R.id.soilDetection);


        treeLogo.setOnClickListener(v -> Toast.makeText(MainActivity.this, "I am your Friend", Toast.LENGTH_SHORT).show());

        plant_Disease.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CamActive.class);
            startActivity(i);
        });


        pest_Detection.setOnClickListener(v -> {
            Intent pest = new Intent(MainActivity.this, pest_Detection.class);
            startActivity(pest);
        });

        soilDetection.setOnClickListener(v -> {
            Intent soil = new Intent(MainActivity.this, CamActive.class);
            startActivity(soil);
        });
    }


}