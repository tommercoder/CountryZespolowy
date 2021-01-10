package com.example.country;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.country.ActivitiesNav.profile_act;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class Intro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_intro);


       addSlide(AppIntroFragment.newInstance(
               "Welcome to the Country", "Here you can vote for anyone you want:)",R.drawable.hello, ContextCompat.getColor(getApplicationContext(),R.color.stopgrad)
       ));

        addSlide(AppIntroFragment.newInstance(
                "Our Calendar", "Quick way to locate our recent events. You can also choose date to navigate to the event",R.drawable.calendar_r, ContextCompat.getColor(getApplicationContext(),R.color.stopgrad)
        ));

       showSkipButton(true);
       setProgressBarVisibility(true);



    }

    @Override
    public void onSkipPressed() {
        super.onSkipPressed();
        startActivity(new Intent(this, mainmenu.class));
        finish();
    }

    @Override
    public void onDonePressed() {
        super.onDonePressed();
        startActivity(new Intent(this, mainmenu.class));
        finish();
    }
}