package com.example.country.ActivitiesNav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.Credentials;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.country.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

public class update_pass_profile extends Activity {

    EditText OldPasswordText, NewPasswordText;

    Button confirmButton;

    DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users");

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
            public void onDataChange(@NonNull final DataSnapshot snapshot) {

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String OldPassFromDB = snapshot.child("password").getValue().toString();
                        String OldPass = OldPasswordText.getText().toString();
                        final String NewPass = NewPasswordText.getText().toString();
                        String MailfromDB = snapshot.child("email").getValue().toString();


                        if (user!=null) {
                            if (OldPass.equals(OldPassFromDB)) {

                                //new FirebaseAuthRecentLoginRequiredException(MailfromDB,OldPassFromDB);

                                AuthCredential credential = EmailAuthProvider.getCredential(MailfromDB, OldPassFromDB);

                                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(NewPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        users.child(user.getUid()).child("password").setValue(NewPass);
                                                        Toast.makeText(update_pass_profile.this, "Password has been updated", Toast.LENGTH_LONG).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(update_pass_profile.this, "Something went wrong:(", Toast.LENGTH_LONG).show();
                                                        Log.d("TAG","Messege: "+task.getException().getMessage().toString());
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(update_pass_profile.this, "Something(cred) went wrong:(", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(update_pass_profile.this, "Old password is incorrect!", Toast.LENGTH_LONG).show();
                            }
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