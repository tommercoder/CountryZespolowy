package com.example.country.ActivitiesNav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.country.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update_pass_profile extends Activity {

    EditText OldPasswordText, NewPasswordText;

    Button confirmButton;

    DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users");

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass_profile);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity .CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);


        OldPasswordText = findViewById(R.id.old_password);
        NewPasswordText = findViewById(R.id.new_password);
        confirmButton = findViewById(R.id.id_confirm_button);

        users.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String OldPassFromDB = snapshot.child("password").getValue().toString();
                        String OldPass = OldPasswordText.getText().toString();
                        String NewPass = NewPasswordText.getText().toString();

                        if (OldPass.equals(OldPassFromDB)) {
                            users.child(user.getUid()).child("password").setValue(NewPass);
                            Toast.makeText(update_pass_profile.this, "Password has been updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(update_pass_profile.this, "Old password is incorrect!", Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}