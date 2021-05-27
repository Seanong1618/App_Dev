package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class Homepage extends AppCompatActivity {
    Button start;
    String Username_home1 = "", Password_home1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        Username_home1 = sharedPreferences.getString("Username_login", "");
        Password_home1 = sharedPreferences.getString("Password_login", "");
        //initialize
        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Username_login", Username_home1);
                editor.putString("Password_login", Password_home1);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), QrScanner.class);
                startActivity(intent);
            }
        });
    }

    public void logout(View view) {
        //if logout icon was click
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    //to stay log in even the ap is closed

}


