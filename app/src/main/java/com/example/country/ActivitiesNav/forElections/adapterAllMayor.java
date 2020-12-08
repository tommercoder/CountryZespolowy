package com.example.country.ActivitiesNav.forElections;



import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.country.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class adapterAllMayor extends FirebaseRecyclerAdapter<model, adapterAllMayor.myviewholder>
{


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
    public adapterAllMayor(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);

    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        super.onBindViewHolder(holder, position);


    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull model model) {

        holder.name.setText(model.getName());
        holder.party.setText(model.getParty());
        holder.age.setText(model.getAge());
        int votes = decodeDiscussionId(model.getVotes());
        holder.votes.setText(String.valueOf(votes));
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);//loading image into circle



    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.president_recycle_design,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        List<Button> voteList = new ArrayList<>();
        ImageView img;
        TextView name,party,age,votes;
        ConstraintLayout expandable;
        ImageView expandMore;
        RelativeLayout cand;
        // Button vote_btn;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.nametext);
            party = (TextView)itemView.findViewById(R.id.party_id);
            age = (TextView)itemView.findViewById(R.id.age_id);
            votes = (TextView)itemView.findViewById(R.id.votes_id);








        }
    }


}

