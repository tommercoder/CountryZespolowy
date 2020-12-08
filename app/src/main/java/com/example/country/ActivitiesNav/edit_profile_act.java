package com.example.country.ActivitiesNav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.country.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class edit_profile_act extends AppCompatActivity {


    EditText  EmailText, PhoneText;

    Button updateEmail,updatePhone,updatePassword;

    DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users");

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private boolean isPhoneChanged(EditText text1,String text2) {
        if(!text1.equals(text2)){
            users.child(user.getUid()).child("phone").setValue(text2);
            return true;
        }
        else return false;
    }

    private boolean isEmailChanged(EditText text1, String text2) {
        if(!text1.equals(text2)){
            users.child(user.getUid()).child("email").setValue(text2);
            return true;
        }
        else return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_act);

        updateEmail = findViewById(R.id.update_mail_button);
        updatePhone = findViewById(R.id.update_phone_button);
        updatePassword = findViewById(R.id.update_password_button);

        EmailText = findViewById(R.id.id_edit_mail);
        PhoneText = findViewById(R.id.id_edit_phone);


        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //toolbar.setBackgroundColor(getResources().getString(R.color.white));

        users.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String Phone = snapshot.child("phone").getValue().toString();
                String Email = snapshot.child("email").getValue().toString();

                EmailText.setText(Email);
                PhoneText.setText(Phone);

                updateEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String EmailEdit = EmailText.getText().toString();

                        if(isEmailChanged(EmailText,EmailEdit)) {
                            Toast.makeText(edit_profile_act.this, "Data has been updated", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                updatePhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String PhoneEdit = PhoneText.getText().toString();

                        if(isPhoneChanged(PhoneText,PhoneEdit)) {
                            Toast.makeText(edit_profile_act.this, "Data has been updated", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), update_pass_profile.class));
            }
        });

/*
        ImageView backToProfileButton = findViewById(R.id.backButtonProfile);

        backToProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_profile_act.super.onBackPressed();
            }
        });

 */

    }

}