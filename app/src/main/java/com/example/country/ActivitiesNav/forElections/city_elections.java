package com.example.country.ActivitiesNav.forElections;
//<a href='https://www.freepik.com/vectors/fashion'>Fashion vector created by studiogstock - www.freepik.com</a>

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.country.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class city_elections extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView recyclerView;
    myadapter adapter;
    String city, email, passport, password, phone;
    TextView city_name;
    DatabaseReference reff_city;
    String reff_city_id;
    FirebaseDatabase db;
    List<myadapter> list;
    public String city_for_ad;
    String user_id;
    RelativeLayout click_cand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_elections);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//phone up bar off
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

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
        Log.d("UIDUIDUDIDIDIDIDIIDIDID" + user.getUid(),"");
        //if user exist
        if (user != null) {

            //wchodzimy do current user

            user_ref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String passport = snapshot.child("passport").getValue().toString();///passport of current user
                    city = snapshot.child("city").getValue().toString();///city of current user
                    gov_city_ref.child(passport).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(city.equals(snapshot.child("city").getValue().toString())) {//check if city of user equals to city in gov data base

                                city_name.setText(city);//set city to EditText

                                FirebaseRecyclerOptions<model> options =
                                        new FirebaseRecyclerOptions.Builder<model>()
                                                .setQuery(FirebaseDatabase.getInstance().getReference().child("CityCandidates").child(city), model.class)
                                                .build();//options for recycle view with reference to candidates

                                adapter = new myadapter(options);//setting adapter which sets all data to fields like image etc
                                recyclerView.setAdapter(adapter);//adding adapter to recycle view
                                adapter.startListening();//start listening from firebase

                            }
                            else
                            {
                                Toast.makeText(city_elections.this, "Your city is'nt like in your passport\ntry go to support", Toast.LENGTH_LONG).show();
                            }
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