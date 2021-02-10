package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.google.android.gms.common.internal.Constants;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {
  //   @Override
     //protected void onCreate(Bundle savedInstanceState) {
    // super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_splash_screen);


  //  }

    @Override
    public void initSplash(ConfigSplash configSplash) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.blue); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1400); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.applogo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1600); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Landing);

        //Customize Title
        configSplash.setTitleSplash("Welcome to COVID-19 HanApp");
        configSplash.setTitleTextSize(25f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FadeInLeft);

    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(SplashScreen.this,MainActivity.class));
    }
}