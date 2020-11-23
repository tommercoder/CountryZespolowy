package com.example.country.ActivitiesNav;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.country.ActivitiesNav.forElections.myadapter;
import com.example.country.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class results extends AppCompatActivity {

    Boolean resultCity = false;
    Boolean resultMayor = false;
    Boolean resultPresident = false;
    Boolean resultVer = false;
    Boolean alreadeExecuted = false;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Date date = new Date();///check date in another way because user can change date
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final int year = localDate.getYear();
        final int month = localDate.getMonthValue();
        final int day = localDate.getDayOfMonth();
        if (day > 10 && month > 10) {
            resultCity = true;
        }
        if (day > 10 && month > 10) {
            resultMayor = true;
        }
        if (day > 10 && month > 10) {
            resultPresident = true;
        }
        if (day > 10 && month > 10) {
            resultVer = true;
        }
        TextView citytext = findViewById(R.id.CityWinnerSign);
        RelativeLayout cityRelative = findViewById(R.id.relativeCand);
        Button cityButt = findViewById(R.id.CityAllvotes);

        TextView Mayortext = findViewById(R.id.mayorWinnerSign);
        RelativeLayout mayorRelative = findViewById(R.id.mayorWinner);
        Button mayorButt = findViewById(R.id.MayorAllvotes);

        TextView Presidenttext = findViewById(R.id.presidentWinnerSign);
        RelativeLayout presidentRelative = findViewById(R.id.PresidentWinner);
        Button presidentButt = findViewById(R.id.PresidentAllvotes);

        TextView partytext = findViewById(R.id.partytWinnerSign);
        RelativeLayout partyRelative = findViewById(R.id.partyWinnerId);
        Button partyButt = findViewById(R.id.PartyAllvotes);
        if(!alreadeExecuted) {
            if (resultCity) {
                citytext.setVisibility(View.VISIBLE);
                cityRelative.setVisibility(View.VISIBLE);
                cityButt.setVisibility(View.VISIBLE);
                countResultsCity();
                alreadeExecuted = true;
            }
            if(resultMayor)
            {
                Mayortext.setVisibility(View.VISIBLE);
                mayorRelative.setVisibility(View.VISIBLE);
                mayorButt.setVisibility(View.VISIBLE);
                countResultsMayor();
            }
            if(resultPresident)
            {
                Presidenttext.setVisibility(View.VISIBLE);
                presidentRelative.setVisibility(View.VISIBLE);
                presidentButt.setVisibility(View.VISIBLE);
                countResultsPresident();
            }
            if(resultVer)
            {
                partytext.setVisibility(View.VISIBLE);
                partyRelative.setVisibility(View.VISIBLE);
                partyButt.setVisibility(View.VISIBLE);
                PartyWinner();
            }

        }

    }
    public void countResultsCity()
    {

        final DatabaseReference referenceCity = FirebaseDatabase.getInstance().getReference("CityCandidates");
        final List<Integer> votesList = new ArrayList<>();
        if(resultCity)
        {

            final FirebaseUser curruser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference user_reff = FirebaseDatabase.getInstance().getReference("Users");
            user_reff.child(curruser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    final String city = snapshot.child("city").getValue().toString();

                    referenceCity.child(city).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int counter = 0;
                            for (DataSnapshot votes : snapshot.getChildren()) {
                                counter++;

                                String maxVotes = votes.child("votes").getValue().toString();

                                int i = Integer.parseInt(maxVotes);
                                votesList.add(i);
                            }
                            counter = 0;
                            int max = Collections.max(votesList);
                            Log.d(String.valueOf(max),"LOG KIWAWAA");
                            for(DataSnapshot dt : snapshot.getChildren())
                            {
                                counter++;

                                String votes = dt.child("votes").getValue().toString();

                                int intVotes = Integer.parseInt(votes);
                                if(intVotes==max)
                                {
                                    TextView name = (TextView)findViewById(R.id.nametext);
                                    TextView age = (TextView)findViewById(R.id.age_id);
                                    TextView party = (TextView)findViewById(R.id.party_id);
                                    CircleImageView image = (CircleImageView)findViewById(R.id.img1);

                                    String nameDB = dt.child("name").getValue().toString();
                                    String ageDB = dt.child("age").getValue().toString();
                                    String partyDB = dt.child("party").getValue().toString();
                                    String imgDB = dt.child("purl").getValue().toString();

                                    name.setText(nameDB);
                                    age.setText(ageDB);
                                    party.setText(partyDB);

                                    DatabaseReference res = FirebaseDatabase.getInstance().getReference("result");
                                    res.child("CityResult").child("name").setValue(nameDB);
                                    res.child("CityResult").child("age").setValue(ageDB);
                                    res.child("CityResult").child("party").setValue(partyDB);
                                    res.child("CityResult").child("votes").setValue(max);
                                }
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

        }
    }
    public void countResultsMayor()
    {


        final DatabaseReference referenceMayor = FirebaseDatabase.getInstance().getReference("MayorCandidates");
        DatabaseReference referencePresident = FirebaseDatabase.getInstance().getReference("PresidentCandidates");
        DatabaseReference referenceVer = FirebaseDatabase.getInstance().getReference("VerkhovnaRadaParties");
        final List<Integer> votesList = new ArrayList<>();
        if(resultCity)
        {

            final FirebaseUser curruser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference user_reff = FirebaseDatabase.getInstance().getReference("Users");
            user_reff.child(curruser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    final String city = snapshot.child("city").getValue().toString();

                    referenceMayor.child(city).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int counter = 0;
                            for (DataSnapshot votes : snapshot.getChildren()) {
                                counter++;

                                String maxVotes = votes.child("votes").getValue().toString();

                                int i = Integer.parseInt(maxVotes);
                                votesList.add(i);
                            }
                            counter = 0;
                            int max = Collections.max(votesList);
                            Log.d(String.valueOf(max),"LOG KIWAWAA");
                            for(DataSnapshot dt : snapshot.getChildren())
                            {
                                counter++;

                                String votes = dt.child("votes").getValue().toString();

                                int intVotes = Integer.parseInt(votes);
                                if(intVotes==max)
                                {
                                    TextView name = (TextView)findViewById(R.id.nametext2);
                                    TextView age = (TextView)findViewById(R.id.age_id_mayor);
                                    TextView party = (TextView)findViewById(R.id.party_id_mayor);
                                    CircleImageView image = (CircleImageView)findViewById(R.id.img2);

                                    String nameDB = dt.child("name").getValue().toString();
                                    String ageDB = dt.child("age").getValue().toString();
                                    String partyDB = dt.child("party").getValue().toString();
                                    String imgDB = dt.child("purl").getValue().toString();

                                    name.setText(nameDB);
                                    age.setText(ageDB);
                                    party.setText(partyDB);

                                    DatabaseReference res = FirebaseDatabase.getInstance().getReference("result");
                                    res.child("mayorResult").child("name").setValue(nameDB);
                                    res.child("mayorResult").child("age").setValue(ageDB);
                                    res.child("mayorResult").child("party").setValue(partyDB);
                                    res.child("mayorResult").child("votes").setValue(max);
                                }
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

        }
    }
    public void countResultsPresident()
    {



        final DatabaseReference referencePresident = FirebaseDatabase.getInstance().getReference("PresidentCandidates");
        DatabaseReference referenceVer = FirebaseDatabase.getInstance().getReference("VerkhovnaRadaParties");
        final List<Integer> votesList = new ArrayList<>();
        if(resultCity)
        {

            final FirebaseUser curruser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference user_reff = FirebaseDatabase.getInstance().getReference("Users");
            user_reff.child(curruser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    final String city = snapshot.child("city").getValue().toString();

                    referencePresident.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int counter = 0;
                            for (DataSnapshot votes : snapshot.getChildren()) {
                                counter++;

                                String maxVotes = votes.child("votes").getValue().toString();

                                int i = Integer.parseInt(maxVotes);
                                votesList.add(i);
                            }
                            counter = 0;
                            int max = Collections.max(votesList);
                            Log.d(String.valueOf(max),"LOG KIWAWAA");
                            for(DataSnapshot dt : snapshot.getChildren())
                            {
                                counter++;

                                String votes = dt.child("votes").getValue().toString();

                                int intVotes = Integer.parseInt(votes);
                                if(intVotes==max)
                                {
                                    TextView name = (TextView)findViewById(R.id.nametext3);
                                    TextView age = (TextView)findViewById(R.id.age_id_president);
                                    TextView party = (TextView)findViewById(R.id.party_id_president);
                                    CircleImageView image = (CircleImageView)findViewById(R.id.img3);

                                    String nameDB = dt.child("name").getValue().toString();
                                    String ageDB = dt.child("age").getValue().toString();
                                    String partyDB = dt.child("party").getValue().toString();
                                    String imgDB = dt.child("purl").getValue().toString();

                                    name.setText(nameDB);
                                    age.setText(ageDB);
                                    party.setText(partyDB);

                                    DatabaseReference res = FirebaseDatabase.getInstance().getReference("result");
                                    res.child("PresidentResult").child("name").setValue(nameDB);
                                    res.child("PresidentResult").child("age").setValue(ageDB);
                                    res.child("PresidentResult").child("party").setValue(partyDB);
                                    res.child("PresidentResult").child("votes").setValue(max);
                                }
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

        }
    }
    public void PartyWinner()
    {




        DatabaseReference referenceVer = FirebaseDatabase.getInstance().getReference("VerkhovnaRadaParties");
        final List<Integer> votesList = new ArrayList<>();
        if(resultCity)
        {

            final FirebaseUser curruser = FirebaseAuth.getInstance().getCurrentUser();


                    referenceVer.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int counter = 0;
                            for (DataSnapshot votes : snapshot.getChildren()) {
                                counter++;

                                String maxVotes = votes.child("votes").getValue().toString();

                                int i = Integer.parseInt(maxVotes);
                                votesList.add(i);
                            }
                            counter = 0;
                            int max = Collections.max(votesList);
                            Log.d(String.valueOf(max),"LOG KIWAWAA");
                            for(DataSnapshot dt : snapshot.getChildren())
                            {
                                counter++;

                                String votes = dt.child("votes").getValue().toString();

                                int intVotes = Integer.parseInt(votes);
                                if(intVotes==max)
                                {

                                    TextView party = (TextView)findViewById(R.id.party_id_p);



                                    String partyDB = dt.child("party").getValue().toString();



                                    party.setText(partyDB);

                                    DatabaseReference res = FirebaseDatabase.getInstance().getReference("result");

                                    res.child("VerResult").child("party").setValue(partyDB);
                                    res.child("VerResult").child("votes").setValue(max);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }



        }
    }

