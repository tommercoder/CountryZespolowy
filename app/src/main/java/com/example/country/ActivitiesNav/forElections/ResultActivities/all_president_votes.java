package com.example.country.ActivitiesNav.forElections.ResultActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.country.ActivitiesNav.forElections.adapterAllpresidents;
import com.example.country.ActivitiesNav.forElections.model;
import com.example.country.ActivitiesNav.forElections.myadapterPresident;
import com.example.country.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class all_president_votes extends AppCompatActivity {

    RecyclerView recyclerView;
    adapterAllpresidents adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_president_votes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        toolbar.setTitle("All president results");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPresidents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseRecyclerOptions<model> options =
                    new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("PresidentCandidates").orderByChild("order"), model.class)
                            .build();//options for recycle view with reference to candidates

            adapter = new adapterAllpresidents(options);//setting adapter which sets all data to fields like image etc
            recyclerView.setAdapter(adapter);//adding adapter to recycle view
            adapter.startListening();//start listening from firebase
            //wchodzimy do current user


        }
        else
        {
            Toast.makeText(this,"User is not logged in",Toast.LENGTH_LONG).show();
            adapter.stopListening();
        }
    }
}