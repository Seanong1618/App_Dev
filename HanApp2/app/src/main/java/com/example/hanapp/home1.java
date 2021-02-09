package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class home1 extends AppCompatActivity {
    FloatingActionButton add_btn,qrscanner,counter;
    Animation rotateOpen,rotateClose,fromBotton,toBotton;

    String Username_login="",Password_login="";
    boolean clicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_1);
        rotateOpen= AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose= AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBotton= AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBotton= AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        //Setting the Title of the ActionBar
        getSupportActionBar().setTitle("Home");
        //from Shared pref
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        Username_login = sharedPreferences.getString("Username_login","");
        Password_login = sharedPreferences.getString("Password_login","");

        add_btn = findViewById(R.id.add_btn);
        qrscanner = findViewById(R.id.qrscanner);
        counter = findViewById(R.id.counter);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });
        //Once Click it will Popup QR Scanner
        qrscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QR Scanner Will start to open
                SharedPreferences sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Username_home1", Username_login);
                editor.putString("Password_home1", Password_login);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(),QrScanner.class);
                startActivity(intent);
            }
        });
        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),counterActivity.class);
                startActivity(intent);
            }
        });
    }
    public void logout (View v){
        // logout is click
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);

        Toast.makeText(home1.this, "You are already logged out...", Toast.LENGTH_SHORT).show();
    }

    //For The Animation
    public void onAddButtonClicked(){
        setVisible(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        if (!clicked){
            clicked = true;
        }
        else{
            clicked = false;
        }


    }
    public void setVisible(boolean clicked){
        if (!clicked){
            qrscanner.setVisibility(View.VISIBLE);
            counter.setVisibility(View.VISIBLE);
        }else{
            qrscanner.setVisibility(View.INVISIBLE);
            counter.setVisibility(View.INVISIBLE);
        }
    }
    public void setAnimation(boolean clicked){
        if (!clicked){
            qrscanner.startAnimation(fromBotton);
            counter.startAnimation(fromBotton);
            add_btn.startAnimation(rotateOpen);
        }else{
            qrscanner.startAnimation(toBotton);
            counter.startAnimation(toBotton);
            add_btn.startAnimation(rotateClose);
        }
    }
    public void setClickable(boolean clicked){
        boolean clickable=false;
        boolean click=true;
        if (!clicked){
            qrscanner.setClickable(click);
            counter.setClickable(click);
        }else {
            qrscanner.setClickable(clickable);
            counter.setClickable(clickable);
        }
    }

}