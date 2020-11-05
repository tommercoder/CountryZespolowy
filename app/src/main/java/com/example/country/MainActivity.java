package com.example.country;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button btnLogin;
    Button btnRegister;

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    EditText email, password;
    ConstraintLayout root;
    FirebaseAuth.AuthStateListener listener;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        root = findViewById(R.id.root_element);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showRegisterWindow();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                showMainMenuWindow();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser!=null)
        {
            //startActivity(new Intent(MainActivity.this,mainmenu.class));
        }
        else
        {
            Toast.makeText(MainActivity.this, "Please login", Toast.LENGTH_SHORT).show();
        }

        //
    }
    private void showMainMenuWindow() {

        final EditText email = findViewById(R.id.emailField);
        final EditText password = findViewById(R.id.passField);

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Email is Required.");
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Password is Required.");
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                
                //startActivity(new Intent(MainActivity.this, mainmenu.class));
                progressBar.setVisibility(View.INVISIBLE);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "The email or password is not correct", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                //Snackbar.make(root, "Eroor Sign In " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void showRegisterWindow() {
        startActivity(new Intent(MainActivity.this, register_window.class));
}
}
