package com.example.country.ActivitiesNav;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.country.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_act extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users, government;
    FirebaseAuth.AuthStateListener listener;
    TextView userName,userEmail,userCity,userPassport,userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_act);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        users = FirebaseDatabase.getInstance().getReference("Users");
        government = FirebaseDatabase.getInstance().getReference("GovermentUsersDB");

        userName = (TextView)findViewById(R.id.id_user_name);
        userEmail = (TextView)findViewById(R.id.id_user_email);
        userCity = (TextView)findViewById(R.id.id_user_city);
        userPassport = (TextView)findViewById(R.id.id_user_passport);
        userPhone = (TextView)findViewById(R.id.id_user_phone);

        if(user != null) {
            users.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String passport = snapshot.child("passport").getValue().toString();///passport of current user
                    String phone = snapshot.child("phone").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();

                    userPhone.setText(phone);
                    userPassport.setText(passport);
                    userEmail.setText(email);

                    government.child(passport).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.child("name").getValue().toString();
                            String city = snapshot.child("city").getValue().toString();

                            userName.setText(name);
                            userCity.setText(city);

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
}