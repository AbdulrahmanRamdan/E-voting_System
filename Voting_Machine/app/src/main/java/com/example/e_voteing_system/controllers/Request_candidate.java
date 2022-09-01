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

public class Request_candidate {
    private String collection_name="Request_Candisates";
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Bitmap_helper bitmap_helper=new Bitmap_helper();
    @RequiresApi(api = Build.VERSION_CODES.O)
    private Map<String,Object> Map_candidate(Candidate candidate){

        Map<String,Object> map=new HashMap<>();
        map.put("National_id",candidate.National_id);
        map.put("Slogan",candidate.slogan);
        map.put("Party",candidate.party);
        map.put("Progarm",candidate.program);
        map.put("Link_requird_paper",candidate.link_requied_paper);
        map.put("Votes_Number",candidate.Number_of_votes);
        map.put("Image", (candidate.image));
        map.put("Slogan_Image", bitmap_helper.convertfrom_Bitmap_to_string(candidate.Slogan_image));
        return map;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Add_Candidate_Request(Candidate candidate){
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
    public void Retrieve_Candidates_Request(){
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
    public void Delete_Candidate_Request(String Fb_id){
        db.collection(collection_name).document(Fb_id).delete();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Apply_Candidate_Request(Candidate candidate){
        Delete_Candidate_Request(candidate.Fb_id);
        Candidate_controller candidate_controller=new Candidate_controller();
        candidate_controller.Add_Candidate(candidate);
    }

}
