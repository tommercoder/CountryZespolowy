package com.example.country.ActivitiesNav;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.country.MainActivity;
import com.example.country.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class profile_act extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users, government;
    FirebaseAuth.AuthStateListener listener;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    public Uri imageUri;


    TextView userName,userEmail,userCity,userPassport,userPhone;
    Button editButton;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar_2);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editButton = findViewById(R.id.id_editButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        users = FirebaseDatabase.getInstance().getReference("Users");
        government = FirebaseDatabase.getInstance().getReference("GovermentUsersDB");

        userName = (TextView)findViewById(R.id.id_user_name);
        userEmail = (TextView)findViewById(R.id.id_user_email);
        userCity = (TextView)findViewById(R.id.id_user_city);
        userPassport = (TextView)findViewById(R.id.id_user_passport);
        userPhone = (TextView)findViewById(R.id.id_user_phone);
        profilePic = findViewById(R.id.profile_image);

        editButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), edit_profile_act.class));
            }
        });

        if(user != null) {
            users.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String passport = snapshot.child("passport").getValue().toString();///passport of current user
                    String phone = snapshot.child("phone").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    //TODO UploadTask uploadTask = (UploadTask) snapshot.child("imageUrl").getValue(); if(avatar) diplay avatat pic; else displai user avatar;

                    userPhone.setText(phone);
                    userPassport.setText(passport);
                    userEmail.setText(email);
                    //TODO profilePic.setImageURI(uploadTask);

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

                    profilePic.setOnClickListener(new android.view.View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            choosePicture();
                        }
                    });
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    /*
    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!=null && data.getData()!=null){
            imageUri = data.getData();
            /*
            Bitmap imageBit = null;
            Bitmap rotateImage = null;

            try {
                imageBit = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (getOrientation(getApplicationContext(), imageUri) != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(getOrientation(getApplicationContext(), imageUri));
                    if (rotateImage != null)
                        rotateImage.recycle();
                    rotateImage = Bitmap.createBitmap(imageBit, 0, 0, imageBit.getWidth(), imageBit.getHeight(), matrix,true);
                    profilePic.setImageBitmap(rotateImage);
            }
                else {
                    profilePic.setImageBitmap(imageBit);
                }
*/
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageRef.child("images/"+randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(profile_act.this, "Image Uploaded.", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(profile_act.this, "Failed To Upload.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}