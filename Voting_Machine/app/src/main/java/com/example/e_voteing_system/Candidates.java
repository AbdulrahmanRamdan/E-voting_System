package com.example.e_voteing_system;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_voteing_system.Helper.Bitmap_helper;
import com.example.e_voteing_system.Models.Candidate;
import com.example.e_voteing_system.Models.Voter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Candidates extends AppCompatActivity  {

    RecyclerView recyclerView;
    ArrayList<MainModel>mainModels;
    MainAdapter mainAdapter;
    Button vote_btn;
    String[] Candidate_names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String collection_name="Official Candidates";
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Bitmap_helper bitmap_helper=new Bitmap_helper();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates);
        Intent intent =getIntent();
        //String Voter_national_id=intent.getStringExtra("Nationsl_id");
        String Voter_national_id=null;

        List<Candidate> candidates=new ArrayList<>();







        db.collection(collection_name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                        Candidate candidate=new Candidate();
                      //  candidate.Fb_id=documentSnapshot.getId();
                      //  candidate.National_id=documentSnapshot.getString("National_id");
                        candidate.name=documentSnapshot.getString("Name");

                        candidate.Number_of_votes= Integer.parseInt(documentSnapshot.get("Num_Of_Votes").toString());
                       // candidate.slogan=documentSnapshot.getString("Slogan");
                        //candidate.program=documentSnapshot.getString("Progarm");
                      // System.out.println(candidate.National_id);
                       // System.out.println(candidate.name);
                     //   candidate.link_requied_paper=documentSnapshot.getString("Link_requird_paper");
                      //  candidate.Slogan_image=bitmap_helper.convertto_Bitmap_from_string(documentSnapshot.getString("Slogan_Image"));
                        candidate.image=documentSnapshot.getString("Image_Link");
                        System.out.println(candidate.image);
                        candidates.add(candidate);
                    }


                    mainAdapter = new MainAdapter(Candidates.this, candidates, Voter_national_id);

                }
                //mainModels.add((MainModel) candidates);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                        Candidates.this, LinearLayoutManager.HORIZONTAL, false
                );
                recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mainAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failer");
            }
        });


        /*
         recyclerView = findViewById(R.id.recycler_view);
        mainModels = new ArrayList<>();
        for (int i = 0; i < candidates.size(); i++) {
            MainModel model = new MainModel(candidates.get(i).slogan, candidates.get(i).image, candidates.get(i).name);
            mainModels.add(model);
        }

        //mainModels.add((MainModel) candidates);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                Candidates.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapter = new MainAdapter(Candidates.this, candidates, Voter_national_id);
        recyclerView.setAdapter(mainAdapter);


         */



    }


}