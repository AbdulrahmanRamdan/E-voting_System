package com.example.e_voteing_system;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_voteing_system.Helper.Bitmap_helper;
import com.example.e_voteing_system.Models.Candidate;
import com.example.e_voteing_system.controllers.Candidate_controller;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RESULT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String collection_name="Candidates";
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Candidate_controller candidate_controller=new Candidate_controller();
        Bitmap_helper bitmap_helper=new Bitmap_helper();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ImageView image=(ImageView)findViewById(R.id.imagecan);
        ImageView sslogan=(ImageView)findViewById(R.id.slogan);
        TextView code=(TextView)findViewById(R.id.CODE);
        TextView namee=(TextView)findViewById(R.id.Cand_name);
        TextView party=(TextView)findViewById(R.id.party);
        List<Candidate> candidates=new ArrayList<>();
        db.collection(collection_name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                        Candidate candidate=new Candidate();
                        candidate.Fb_id=documentSnapshot.getId();
                        candidate.National_id=documentSnapshot.getString("National_id");
                        candidate.Number_of_votes= Integer.parseInt(documentSnapshot.get("Votes_Number").toString());
                        candidate.slogan=documentSnapshot.getString("Slogan");
                        candidate.program=documentSnapshot.getString("Progarm");
                        System.out.println(candidate.program);
                        candidate.link_requied_paper=documentSnapshot.getString("Link_requird_paper");
                        candidate.Slogan_image=bitmap_helper.convertto_Bitmap_from_string(documentSnapshot.getString("Slogan_Image"));
                       // candidate.image=bitmap_helper.convertto_Bitmap_from_string(documentSnapshot.getString("Image"));
                        candidates.add(candidate);
                        Candidate candidate1=candidate_controller.Retrun_Winer(candidates);
                       // image.setImageBitmap(candidate1.image);
                        sslogan.setImageBitmap(candidate1.Slogan_image);
                        code.setText(candidate1.slogan);
                        namee.setText(candidate1.name);
                        party.setText(candidate1.party);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failer");
            }
        });
    }
}