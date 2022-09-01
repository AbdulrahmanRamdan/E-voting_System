package com.example.e_voteing_system.controllers;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.e_voteing_system.Helper.Date_helper;
import com.example.e_voteing_system.Models.Vote_Record;
import com.example.e_voteing_system.Models.Voter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.type.DateTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vote_Record_controller {
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    public boolean Add_vote_record(Vote_Record vote){
        final boolean[] re = {true};
        Map<String,Object> vote_map=new HashMap<>();
        vote_map.put("Voter_National_id",vote.voter_n_id);
        vote_map.put("Candidate_National_id",vote.candidate_n_id);
        vote_map.put("Date",vote.date_of_voting.toString());
        vote_map.put("Statues",vote.Statues);
        vote_map.put("Machine_id",vote.Machine_id);
        db.collection("Vote_Records")
                .add(vote_map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        re[0] =true;
                        //Log.d(, "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                re[0]=false;
                // Log.w(TAG, "Error adding document", e);
            }
        });
        return re[0];
    }

   public List<Vote_Record> Retrieve_Votes(){
        List<Vote_Record>votes=new ArrayList<Vote_Record>();
       Date_helper date_helper=new Date_helper();
       db.collection("Vote_Records").get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
               {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               List<DocumentSnapshot>snapshots=queryDocumentSnapshots.getDocuments();int i=0;
               for (DocumentSnapshot doc:snapshots) {

                   Vote_Record vote_record=new Vote_Record();
                   vote_record.FB_id= doc.getId().toString();
                       vote_record.date_of_voting= doc.getDate("Date");

                   vote_record.Statues=doc.getString("Statues").toString();
                   vote_record.voter_n_id=doc.getString("Voter_National_id").toString();
                   vote_record.candidate_n_id=doc.getString("Candidate_National_id").toString();
                   vote_record.Machine_id=doc.getString("Machine_id").toString();
                   votes.add(i,vote_record);
                   i++;
               }
           }
       })
               .addOnFailureListener(new OnFailureListener()
               {
           @Override
           public void onFailure(@NonNull Exception e) {
               System.out.println("Failure");
           }
       });
        return votes;
    }
   public boolean Are_voteing_or_Not(String Na_id){
       Vote_Record vote_record=new Vote_Record();
       db.collection("Vote_Records").whereEqualTo("Voter_National_id",Na_id).get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               List<DocumentSnapshot>snapshots=queryDocumentSnapshots.getDocuments();int i=0;
               for (DocumentSnapshot doc:snapshots) {
                   vote_record.FB_id= doc.getId().toString();
                   vote_record.date_of_voting= doc.getDate("Date");

                   vote_record.Statues=doc.getString("Statues").toString();
                   vote_record.voter_n_id=doc.getString("Voter_National_id").toString();
                   vote_record.candidate_n_id=doc.getString("Candidate_National_id").toString();
                   vote_record.Machine_id=doc.getString("Machine_id").toString();

               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               System.out.println("Failure");
           }
       });
       if(vote_record.voter_n_id==Na_id)
           return true;

        else
        return false;
   }

}
