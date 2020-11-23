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


public class myadapterPresident extends FirebaseRecyclerAdapter<model, myadapterPresident.myviewholder>
{


    public myadapterPresident(@NonNull FirebaseRecyclerOptions<model> options) {
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
        holder.info.setText(model.getInfo());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
        //holder.voteList.add(holder.vote_btn);

        //expande

        final boolean isVote = model.isVote();


        final boolean isExpanded = model.isExpanded();
        holder.expandable.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.cand.setOnClickListener(new View.OnClickListener() {//click to any of candidates
            //boolean vote_seen = false;
            @Override
            public void onClick(final View view) {

                final String number = getRef(position).getKey();//number of candidate

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//current user

                final DatabaseReference reff_users = FirebaseDatabase.getInstance().getReference("Users");//reff to users
                reff_users.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {//data from current user
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String voted = snapshot.child("votedPresident").getValue().toString();


                            final String city = snapshot.child("city").getValue().toString();///city of current user
                            final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("PresidentCandidates");//reff to candidates

                        reff.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull final DataSnapshot snapshot) {

                               String info = snapshot.child(number).getValue().toString();//data of clicked city candidate
                                String name = snapshot.child(number).child("name").getValue().toString();





                                if(voted.equals("no"))
                                {
                                    Log.d("INFO",info);
                                    //if (!vote_seen) {
                                    final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext(), R.style.AlertDialogTheme);
                                    View view1 = LayoutInflater.from(view.getContext()).inflate(
                                            R.layout.sure_window,
                                            (ConstraintLayout)view.findViewById(R.id.layoutDialog)

                                    );
                                    alert.setView(view1);
                                    alert.setCancelable(false);
                                    TextView sure = (TextView)view1.findViewById(R.id.textTitle);
                                    sure.setText(R.string.title_sure);
                                    TextView textMessage = (TextView)view1.findViewById(R.id.textMessage);
                                    textMessage.setText("Are you sure you want to vote for "+ name + " ?");
                                    Button yes = (Button)view1.findViewById(R.id.buttonYes);
                                    yes.setText(R.string.vote);
                                    Button no = (Button)view1.findViewById(R.id.buttonNo);
                                    no.setText(R.string.voteback);
                                    ImageView image = (ImageView)view1.findViewById(R.id.imageIcon);
                                    image.setImageResource(R.drawable.vote_logo);


                                    final AlertDialog alertDialog = alert.create();

                                    view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //vote
                                            String votes = snapshot.child(number).child("votes").getValue().toString();
                                            int votes1 = Integer.parseInt(votes.trim());
                                            votes1++;
                                            String new_votes = Integer.toString(votes1);

                                            reff.child(number).child("votes").setValue(new_votes);
                                            reff_users.child(user.getUid()).child("votedPresident").setValue("yes");
                                            alertDialog.dismiss();
                                        }
                                    });
                                    view1.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                    if(alertDialog.getWindow()!=null)
                                    {
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                    }

                                    alertDialog.show();

                                }
                                else
                                {

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
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        List<Button> voteList = new ArrayList<>();
        ImageView img;
        TextView name,party,age,info;
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
            info = (TextView)itemView.findViewById(R.id.textViewExp);
            expandable = (ConstraintLayout)itemView.findViewById(R.id.expandableLayout);
            expandMore = (ImageView)itemView.findViewById(R.id.expandMore);
            cand=(RelativeLayout)itemView.findViewById(R.id.relativeCand);
           // vote_btn=(Button)itemView.findViewById(R.id.vote_btn);




            expandMore.setOnClickListener(new View.OnClickListener() {
                public boolean ok = false;
                @Override
                public void onClick(View view) {

                    if(!ok) {
                        expandable.setVisibility(View.VISIBLE);
                        ok = true;
                        expandMore.setSelected(true);
                    }
                    else
                    {
                        expandable.setVisibility(View.GONE);
                        ok = false;
                        expandMore.setSelected(false);
                    }

                }
            });




        }
    }


}
