package com.example.e_voteing_system;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voteing_system.Models.Candidate;
import com.example.e_voteing_system.Models.Vote_Record;
import com.example.e_voteing_system.controllers.Vote_Record_controller;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ArrayList<Candidate>mainModels;
    Context context;
    String voter_id;
    int[] images= new int[]{R.drawable.osama,R.drawable.yara1};

    public MainAdapter(Context context, List<Candidate> mainModels,String voter_id){
        this.context=context;
        this.mainModels= (ArrayList<Candidate>) mainModels;
        this.voter_id=voter_id;
    }
    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_item,parent,false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {


        holder.candView.setImageResource(images[position]);
        holder.candName.setText(mainModels.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView candView;
        TextView candName;
        Button voting_Check;
        int itempos=getAdapterPosition();

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);

            candView=itemView.findViewById(R.id.Candidate_image);
            candName=itemView.findViewById(R.id.Cand_name);
            voting_Check=itemView.findViewById(R.id.chooseBtn);
            itemView.setOnClickListener(this);
            voting_Check.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {

            //String sms=String.valueOf(R.string.alert_txt);
            if(voting_Check.isPressed()) {
                /*
                Intent intent = new Intent(context, ChosenCandidate.class);
                intent.putExtra("Chosen_Name", mainModels.get(getAdapterPosition()).names);
                context.startActivity(intent);

                 */


               alert(context.getString(R.string.alert_txt));
            }
            


        }
        private void alert(String sms){
            AlertDialog dlg=new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.Conf_sms))
                    .setMessage(sms)
                    .setNegativeButton(context.getString(R.string.No_Choose), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(context, context.getString(R.string.Choose_cand), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton(context.getString(R.string.Yes_Choose), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Vote_Record vote_record=new Vote_Record();
                            db.collection("Vote_Records").whereEqualTo("Voter_National_id",voter_id).get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<DocumentSnapshot>snapshots=queryDocumentSnapshots.getDocuments();int i=0;

                                                Vote_Record_controller vote_record_controller=new Vote_Record_controller();
                                                Vote_Record vote_record1=new Vote_Record();
                                                vote_record1.candidate_n_id=mainModels.get(itempos).National_id;
                                                vote_record1.date_of_voting=new Date();
                                                vote_record1.voter_n_id=voter_id;
                                                vote_record1.Statues="done";
                                                vote_record_controller.Add_vote_record(vote_record1);
                                                dialog.dismiss();
                                                Toast.makeText(context, context.getString(R.string.Voting_done), Toast.LENGTH_SHORT).show();
                                                Intent intent =new Intent(context,MainActivity.class);
                                                context.startActivity(intent);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Failure");
                                }
                            });

                        }
                    })
                    .create();
            dlg.show();
        }



    }


}
