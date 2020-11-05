package com.example.country.ActivitiesNav;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.country.R;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//import com.example.country.ActivitiesNav.forElections.city_elections;
//import com.example.country.ActivitiesNav.forElections.mayor_elections;
//import com.example.country.ActivitiesNav.forElections.president_election;

public class election_act extends AppCompatActivity {

    Button city_election_btn;
    Button mayor_election_btn;
    Button president_election_btn;
    ImageView back_btn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_act);


        back_btn = findViewById(R.id.back_btn_pressed);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                election_act.super.onBackPressed();
                //startActivity(new Intent(election_act.this,mainmenu.class));
            }
        });
        city_election_btn = findViewById(R.id.city_election_id);
        mayor_election_btn = findViewById(R.id.mayor_election_id);
        president_election_btn = findViewById(R.id.president_election_id);

        Date date = new Date();///check date in another way because user can change date
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final int year = localDate.getYear();
        final int month = localDate.getMonthValue();
        final int day = localDate.getDayOfMonth();

        
        city_election_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
               // if (day >= 10) {
                    //if (month == 10) {
                        //startActivity(new Intent(election_act.this, city_elections.class));
                   // }
               // } else {
                  //  Toast.makeText(getApplicationContext(), "Not available now", Toast.LENGTH_SHORT).show();
               // }
            }
        });
        mayor_election_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (day >= 10) {
                   // if (month == 10) {
                        //startActivity(new Intent(election_act.this, mayor_elections.class));
                   // }
                //} else {
                //    Toast.makeText(getApplicationContext(), "Not available now", Toast.LENGTH_SHORT).show();
                //}
            }

        });
        president_election_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if (day >= 10) {
                //    if (month == 10) {
                       // startActivity(new Intent(election_act.this, president_election.class));
                 //   }
               // } else {
                //    Toast.makeText(getApplicationContext(), "Not available now", Toast.LENGTH_SHORT).show();
               // }
            }

        });
    }


}
