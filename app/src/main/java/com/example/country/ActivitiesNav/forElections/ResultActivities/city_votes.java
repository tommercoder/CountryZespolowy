package com.example.country.ActivitiesNav.forElections.ResultActivities;
//<a href='https://www.freepik.com/vectors/fashion'>Fashion vector created by studiogstock - www.freepik.com</a>

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.country.ActivitiesNav.forElections.adapterCityVotes;
import com.example.country.ActivitiesNav.forElections.model;
import com.example.country.ActivitiesNav.forElections.myadapter;
import com.example.country.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class city_votes extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView recyclerView;
    adapterCityVotes adapter;
    String city, email, passport, password, phone;
    TextView city_name;
    DatabaseReference reff_city;
    String reff_city_id;
    FirebaseDatabase db;
    List<adapterCityVotes> list;
    public String city_for_ad;
    String user_id;
    RelativeLayout click_cand;
    ConstraintLayout color;
    public String encodeDiscussionId(int Id) {

        String tempEn = Id + "";
        String encryptNum ="";
        for(int i=0;i<tempEn.length();i++) {
            int a = (int)tempEn.charAt(i);
            a += 21;
            encryptNum += (char)a;
        }
        return encryptNum;
    }

    public Integer decodeDiscussionId(String encryptText) {

        String decodeText = "";
        for(int i=0;i<encryptText.length();i++) {
            int a= (int)encryptText.charAt(i);
            a -= 21;
            decodeText +=(char)a;
        }
        int decodeId = Integer.parseInt(decodeText);
        return decodeId;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_votes);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//phone up bar off
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        color = findViewById(R.id.CityWinnerConstraint);
        //color.setBackgroundColor(Color.GRAY);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // To display the Recycler view linearly
        click_cand = (RelativeLayout) findViewById(R.id.relativeCand);
        city_name = (TextView) findViewById(R.id.city_name_id);
        db = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reff_city = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference("Users");//.child(user.getUid()).child("passport");
        final DatabaseReference gov_city_ref = FirebaseDatabase.getInstance().getReference("GovermentUsersDB");
        final DatabaseReference refCityCand = FirebaseDatabase.getInstance().getReference("CityCandidates");
        Log.d("UIDUIDUDIDIDIDIDIIDIDID" + user.getUid(),"");
        //if user exist
        if (user != null) {

            //wchodzimy do current user

            user_ref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final String passport = snapshot.child("passport").getValue().toString();///passport of current user
                    city = snapshot.child("city").getValue().toString();///city of current user
                    gov_city_ref.child(passport).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            refCityCand.child(city).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int sum = 0;
                                    int counter = 0;

                                    for(DataSnapshot data : snapshot.getChildren())
                                    {
                                        int vote = decodeDiscussionId(data.child("votes").getValue().toString());
                                        sum += vote;
                                        counter ++;
                                    }
                                    TextView winnersCount = (TextView)findViewById(R.id.TextWinnersCount);
                                    if(sum < 1000 && counter >=12)
                                    {
                                        winnersCount.setText("First 12 are winners");
                                    }
                                    else if((sum >=1000) && (sum < 3000) && counter >=14)
                                    {
                                        winnersCount.setText("first 14 are winners");
                                    }
                                    else if(sum > 3000 && sum <5000  && counter >=22)
                                    {
                                        winnersCount.setText("first 22 are winners");
                                    }
                                    else if(sum > 5000 && sum< 20000 && counter >=26)
                                    {
                                        winnersCount.setText("first 26 are winners");
                                    }
                                    else if(sum > 20000 && sum < 50000 && counter >=34)
                                    {
                                        winnersCount.setText("first 34 are winners");
                                    }
                                    else if(sum > 50000 && sum < 100000 && counter >=36)
                                    {
                                        winnersCount.setText("first 36 are winners");
                                    }else if(sum > 100000 && sum < 250000 && counter >=42)
                                    {
                                        winnersCount.setText("first 42 are winners");
                                    }
                                    else if(sum > 250000 && sum < 500000 && counter >=54)
                                    {
                                        winnersCount.setText("first 54 are winners");
                                    }
                                    else if(sum > 500000 && sum < 1000000 && counter >=64)
                                    {
                                        winnersCount.setText("first 64 are winners");
                                    }
                                    else if(sum > 1000000 && sum < 2000000 && counter >=84)
                                    {
                                        winnersCount.setText("first 84 are winners");
                                    }
                                    else if(sum > 2000000 && counter >=120)
                                    {
                                        winnersCount.setText("first 120 are winners");
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            if (city.equals(snapshot.child("city").getValue().toString())) {//check if city of user equals to city in gov data base

                                city_name.setText(city);//set city to EditText



                                FirebaseRecyclerOptions<model> options =
                                        new FirebaseRecyclerOptions.Builder<model>()
                                                .setQuery(FirebaseDatabase.getInstance().getReference().child("CityCandidates").child(city).orderByChild("order"), model.class)
                                                .build();//options for recycle view with reference to candidates

                                adapter = new adapterCityVotes(options);//setting adapter which sets all data to fields like image etc
                                recyclerView.setAdapter(adapter);//adding adapter to recycle view

                                adapter.startListening();//start listening from firebase

                            }
                            else
                            {
                                Toast.makeText(city_votes.this, "Your city is'nt like in your passport\ntry go to support", Toast.LENGTH_LONG).show();
                            }


                           // ArrayList<Integer> list = (ArrayList<Integer>)getIntent().getIntegerArrayListExtra("list");
                            Log.d("VOTES", "LIST " + list);





                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        } else {
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_LONG).show();
            adapter.stopListening();
        }


    }


}