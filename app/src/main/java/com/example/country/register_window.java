package com.example.country;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.country.Models.User;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class register_window extends AppCompatActivity {

    EditText EmailText, PhoneText, PassportText, PasswordText, CityText;
    Button RegisterButton;

    TextView BackToLoginText;

    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_window);
        auth = FirebaseAuth.getInstance();

        RegisterButton = findViewById(R.id.registerButton);

        EmailText = findViewById(R.id.emailField);
        PassportText = findViewById(R.id.passportField);
        PhoneText = findViewById(R.id.phoneField);
        PasswordText = findViewById(R.id.passwordField);
        CityText = findViewById(R.id.cityField);

        BackToLoginText = findViewById(R.id.backToLogin);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        BackToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                final String Phone = PhoneText.getText().toString();
                final String Passport = PassportText.getText().toString();
                final String City = CityText.getText().toString();

                final String Email = EmailText.getText().toString().trim();
                final String Password = PasswordText.getText().toString().trim();


                if (TextUtils.isEmpty(Email)) {
                    EmailText.setError("Email is Required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if (TextUtils.isEmpty(Passport)) {
                    PassportText.setError("Passport  is Required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if (TextUtils.isEmpty(Phone)) {
                    PhoneText.setError("Phone  is Required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if (TextUtils.isEmpty(City)) {
                    CityText.setError("City  is Required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if (Password.length() < 6) {
                    PasswordText.setError("Password  is Required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                ///check passports in gov data base(should also check name of him)

                DatabaseReference reff_to_gov = FirebaseDatabase.getInstance().getReference("GovermentUsersDB");
                final DatabaseReference reff_to_users = FirebaseDatabase.getInstance().getReference("Users");

                reff_to_gov.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(PassportText.getText().toString())) {

                            reff_to_users.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    List<String> list = new ArrayList<>();
                                    if (!list.isEmpty())
                                        list.clear();
                                    for (DataSnapshot data : snapshot.getChildren()) {
                                        String value = String.valueOf(data.child("passport").getValue());
                                        list.add(value);
                                    }
                                    //checking existing of password
                                    final String passToFind = PassportText.getText().toString();
                                    boolean passExist = list.contains(passToFind);
                                    Log.d("" + passExist, PassportText.getText().toString());
                                    //check if passport equals to any in Users and if not then register
                                    for (String str : list) {
                                        Log.d(str, "kkkkkkkkk");
                                    }

                                    if (!passExist) {

                                        Log.d("CAN REGISTER", PassportText.getText().toString());

                                        auth.createUserWithEmailAndPassword(EmailText.getText().toString(), PasswordText.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            User user = new User();

                                                            user.setEmail(EmailText.getText().toString());
                                                            user.setPassport(PassportText.getText().toString());
                                                            user.setPhone(PhoneText.getText().toString());
                                                            user.setPassword(PasswordText.getText().toString());
                                                            user.setCity(CityText.getText().toString());
                                                            user.setVotedCity("no");
                                                            user.setVotedMayor("no");
                                                            user.setVotedPresident("no");
                                                            user.setVotedVer("no");
                                                            final DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users");

                                                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                    .setValue(user)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(register_window.this, "Registered", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });

                                                            try {
                                                                Thread.sleep(500);
                                                                auth.signInWithEmailAndPassword(Email, Password)
                                                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                                            @Override
                                                                            public void onSuccess(AuthResult authResult) {
                                                                                Toast.makeText(register_window.this, "Login Succesful", Toast.LENGTH_SHORT).show();

                                                                                startActivity(new Intent(register_window.this, mainmenu.class));
                                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                                finish();
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(register_window.this, "The email or password is not correct", Toast.LENGTH_LONG).show();
                                                                        progressBar.setVisibility(View.INVISIBLE);
                                                                        //Snackbar.make(root, "Eroor Sign In " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }


                                                        }
                                                        else {
                                                            Toast.makeText(register_window.this, "Fail register", Toast.LENGTH_LONG).show();
                                                        }
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                    }

                                                });

                                    } else {
                                        Log.d("e", "quals");
                                        Toast.makeText(register_window.this, "You already have an account,please login", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(register_window.this,MainActivity.class));
                                        //finish();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else {
                            Toast.makeText(register_window.this, "No such passport in gov(You can't register)", Toast.LENGTH_SHORT).show();
                            //call or write to tech support function
                        }
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





            }


        });


    }

}

          /*auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()) {
                            Toast.makeText(register_window.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{

                            Toast.makeText(register_window.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });*/

