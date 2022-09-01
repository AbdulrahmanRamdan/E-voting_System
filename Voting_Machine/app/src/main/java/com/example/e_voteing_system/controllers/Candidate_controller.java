package com.example.e_voteing_system.controllers;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.e_voteing_system.Helper.Bitmap_helper;
import com.example.e_voteing_system.Models.Candidate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Candidate_controller {
    private String collection_name="Official Candidates";
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Bitmap_helper bitmap_helper=new Bitmap_helper();
     @RequiresApi(api = Build.VERSION_CODES.O)
     private Map<String,Object> Map_candidate(Candidate candidate){

         Map<String,Object> map=new HashMap<>();

         map.put("Votes_Number",candidate.Number_of_votes);
         map.put("Image", (candidate.image));
         return map;
     }
     @RequiresApi(api = Build.VERSION_CODES.O)
     public void Add_Candidate(Candidate candidate){
         Map<String,Object>can_map=Map_candidate(candidate);
         db.collection(collection_name).add(can_map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
             @Override
             public void onSuccess(DocumentReference documentReference) {
                 System.out.println("Success");
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 System.out.println("Failer");
             }
         });
     }

     public void Delete_Candidate(String Fb_id){
             db.collection(collection_name).document(Fb_id).delete();
     }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Update_Candidate(String Fb_id, Candidate candidate){
         Map<String,Object>can_map=Map_candidate(candidate);
        db.collection(collection_name).document(Fb_id).update(can_map);
    }

    public void Vote_to_candidate(String Fb_id){
         db.collection(collection_name).document(Fb_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @RequiresApi(api = Build.VERSION_CODES.O)
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 if(documentSnapshot.exists()){
                     Candidate candidate=new Candidate();
                     candidate.Fb_id=documentSnapshot.getId();
                     candidate.National_id=documentSnapshot.getString("National_id");
                     candidate.Number_of_votes= Integer.parseInt(documentSnapshot.get("Votes_Number").toString());
                     candidate.slogan=documentSnapshot.getString("Slogan");
                     candidate.program=documentSnapshot.getString("Progarm");
                     candidate.link_requied_paper=documentSnapshot.getString("Link_requird_paper");
                     candidate.Slogan_image=bitmap_helper.convertto_Bitmap_from_string(documentSnapshot.getString("Slogan_Image"));
                     candidate.image=documentSnapshot.getString("Image");
                     candidate.Number_of_votes++;
                     Update_Candidate(candidate.Fb_id,candidate);
                 }
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 System.out.println("Don`t voting");
             }
         });
    }

    public void Retrieve_candidates(){
        List<Candidate>candidates=new ArrayList<>();
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
                        candidate.image=documentSnapshot.getString("Image");
                        candidates.add(candidate);
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
    public Candidate Retrun_Winer(List<Candidate> candidates){
        Candidate candidate=new Candidate();
        for (Candidate can:candidates) {
            if(can.Number_of_votes>candidate.Number_of_votes){
                candidate=can;
            }
        }
        return candidate;
    }
}
