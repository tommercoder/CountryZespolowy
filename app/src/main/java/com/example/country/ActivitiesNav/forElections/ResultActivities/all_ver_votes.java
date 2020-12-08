package com.example.country.ActivitiesNav.forElections.ResultActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.country.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class all_ver_votes extends AppCompatActivity {

    DatabaseReference ref_to_ver;
    TextView partyName;
    TextView partyName2;
    TextView partyName3;
    TextView partyName4;
    TextView partyName5;
    TextView votes;
    TextView votes2;
    TextView votes3;
    TextView votes4;
    TextView votes5;
    List<Integer> AllvotesList;
    List<Integer> mainFiveVotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ver_votes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        toolbar.setTitle("All president results");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ref_to_ver = FirebaseDatabase.getInstance().getReference("VerkhovnaRadaParties");
        partyName = findViewById(R.id.party_id);
        votes = findViewById(R.id.votes_id);
        partyName2 = findViewById(R.id.party_id2);
        votes2 = findViewById(R.id.votes_id2);
        partyName3 = findViewById(R.id.party_id3);
        votes3 = findViewById(R.id.votes_id3);
        partyName4 = findViewById(R.id.party_id4);
        votes4 = findViewById(R.id.votes_id4);
        partyName5 = findViewById(R.id.party_id5);
        votes5 = findViewById(R.id.votes_id5);

        AllvotesList = new ArrayList<>();
        mainFiveVotes = new ArrayList<>();

        ref_to_ver.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot votes : snapshot.getChildren())
                {
                    String vote = votes.child("votes").getValue().toString();
                    int decoded = decodeDiscussionId(vote);
                    AllvotesList.add(decoded);
                }
                //max
                final int max = Collections.max(AllvotesList);
                final int thirdMax,fourthMax,fifthMax;
                mainFiveVotes.add(max);
                int indexMax = AllvotesList.indexOf(max);
                AllvotesList.remove(indexMax);
                //sec max votes
                final int secondMAx = Collections.max(AllvotesList);
                mainFiveVotes.add(secondMAx);
                int indexSecMax = AllvotesList.indexOf(secondMAx);
                AllvotesList.remove(indexSecMax);
                //third max votes
                thirdMax = Collections.max(AllvotesList);
                mainFiveVotes.add(thirdMax);
                int indexThirdMax = AllvotesList.indexOf(thirdMax);
                AllvotesList.remove(indexThirdMax);
                //fourth
                fourthMax = Collections.max(AllvotesList);
                mainFiveVotes.add(fourthMax);
                int indexFourthMax = AllvotesList.indexOf(fourthMax);
                AllvotesList.remove(indexFourthMax);
                //fifth
                fifthMax = Collections.max(AllvotesList);
                mainFiveVotes.add(fifthMax);
                int indexFifth = AllvotesList.indexOf(fifthMax);
                AllvotesList.remove(indexFifth);

                ref_to_ver.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String vote1,vote2,vote3,vote4,vote5;
                        vote1 = encodeDiscussionId(max);
                        vote2 = encodeDiscussionId(secondMAx);
                        vote3 = encodeDiscussionId(thirdMax);
                        vote4 = encodeDiscussionId(fourthMax);
                        vote5 = encodeDiscussionId(fifthMax);
                        Log.d("vo","te1" + vote1);
                        for(DataSnapshot data : snapshot.getChildren()){

                            if(data.child("votes").getValue().toString().equals(vote1))
                            {
                                partyName.setText(data.child("party").getValue().toString());
                            }
                            if(data.child("votes").getValue().toString().equals(vote2))
                            {
                                partyName2.setText(data.child("party").getValue().toString());
                            }
                            if(data.child("votes").getValue().toString().equals(vote3))
                            {
                                partyName3.setText(data.child("party").getValue().toString());
                            }
                            if(data.child("votes").getValue().toString().equals(vote4))
                            {
                                partyName4.setText(data.child("party").getValue().toString());
                            }
                            if(data.child("votes").getValue().toString().equals(vote5))
                            {
                                partyName5.setText(data.child("party").getValue().toString());
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                votes.setText(String.valueOf(max));
                votes2.setText(String.valueOf(secondMAx));
                votes3.setText(String.valueOf(thirdMax));
                votes4.setText(String.valueOf(fourthMax));
                votes5.setText(String.valueOf(fifthMax));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("votes winners","votes " + mainFiveVotes.toString());

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
}