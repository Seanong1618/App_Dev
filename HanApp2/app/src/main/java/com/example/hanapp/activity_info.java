package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    public void back (View view) {
        //go back to homeage
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }
}