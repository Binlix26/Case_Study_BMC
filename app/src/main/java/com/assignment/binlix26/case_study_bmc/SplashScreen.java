package com.assignment.binlix26.case_study_bmc;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#26B6EB"))
                .withLogo(R.drawable.logo)
                .withHeaderText("Welcome Guest!")
                .withFooterText("Copyright 2017")
                .withAfterLogoText("Best Moto Company");

        // set text colour
        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        // set to view
        View view = config.create();

        // set view to content
        setContentView(view);
    }
}
