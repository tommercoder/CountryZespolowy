package com.example.country;

import android.content.DialogInterface;
import android.support.v4.app.RemoteActionCompatParcelizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.DoubleAccumulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.country.ActivitiesNav.election_act;
import com.example.country.ActivitiesNav.profile_act;
import com.example.country.ActivitiesNav.results;
import com.example.country.ActivitiesNav.forElections.mayor_elections;
import com.example.country.ActivitiesNav.forElections.city_elections;
import com.example.country.ActivitiesNav.forElections.president_election;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class mainmenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar1;

    //CompactCalendarView calendarView;
    TextView btn1;
    TextView btn2;
    TextView btn3;
    TextView btn4;
    TextView btn5;
    TextView btn6;

    CompactCalendarView compactCalendar;

    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
//final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM, yyyy", new Locale("uk","UA"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_act);

        btn1 = findViewById(R.id.textViewMain1);
        btn2 = findViewById(R.id.textViewMain2);
        btn3 = findViewById(R.id.textViewMain3);
        btn4 = findViewById(R.id.textViewMain4);
        btn5 = findViewById(R.id.textViewMain5);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), election_act.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), results.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), profile_act.class));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), phone.class));
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), email.class));
            }
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar1 = findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.WHITE);
        ////
        setSupportActionBar(toolbar1);
        ////
        navigationView.bringToFront();//setting drawer to fron of screen
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);//burger button
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();//turning burger button off after drawer was opened
        navigationView.setNavigationItemSelectedListener(this);//clickable buttons
        navigationView.setCheckedItem(R.id.home_id);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        final Date date1 = new Date(2021,12,9);
        //Event ev1 = new Event(Color.RED, date1.getTime(),"text1");
        final Event ev1 = new Event(Color.RED, 1610236800000L, "President");
        final Event ev2 = new Event(Color.BLUE, 1609974000000L, "Mayor");
        final Event ev3 = new Event(Color.GREEN, 1610233200000L, "City Elections");

        compactCalendar.addEvent(ev1);
        compactCalendar.addEvent(ev2);
        compactCalendar.addEvent(ev3);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                Context context = getApplicationContext();


                if(dateClicked.getTime() == ev1.getTimeInMillis()){
                    openDialog("City Election",1);
                    Toast.makeText(mainmenu.this,"if 1",Toast.LENGTH_SHORT).show();
                }
                if(dateClicked.getTime() == ev2.getTimeInMillis()){
                    openDialog("Mayor Election",2);
                    Toast.makeText(mainmenu.this,"if 2",Toast.LENGTH_SHORT).show();
                }
                if(dateClicked.getTime() == ev3.getTimeInMillis()){
                    openDialog("President Election",3);
                    Toast.makeText(mainmenu.this,"if 3",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

    }

    private void openDialog(String message, final int num) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mainmenu.this);
        builder.setTitle("Do you want to go to the "+ message + "?");
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (num){
                    case 1:
                        startActivity(new Intent(getApplicationContext(), city_elections.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), mayor_elections.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), president_election.class));
                        break;

                }

            }
        }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void openRegistrationPage () {

        Intent intent = new Intent(mainmenu.this, election_act.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {//android back button

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);//tapping close
            //Toast.makeText(mainmenu.this, "2222222", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(mainmenu.this, "1111111", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){

        switch (menuItem.getItemId()) {
            case R.id.home_id:

                break;
            case R.id.election_id:

                startActivity(new Intent(mainmenu.this, election_act.class));
                break;
            case R.id.logout_id:
                FirebaseAuth.getInstance().signOut();
                //Intent logout = new Intent(mainmenu.this, MainActivity.class);
                finish();
                // startActivity(logout);
                break;
            case R.id.phoneSup_id:
                startActivity(new Intent(mainmenu.this, phone.class));
                break;
            case R.id.emailSup_id:
                startActivity(new Intent(mainmenu.this, email.class));
                break;
            case R.id.profile_id:
                startActivity(new Intent(mainmenu.this, profile_act.class));
                break;
            case R.id.results_id:
                startActivity(new Intent(mainmenu.this, results.class));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);//after pressing closing the drawer

        return false;//checking what was clicked
    }
}
