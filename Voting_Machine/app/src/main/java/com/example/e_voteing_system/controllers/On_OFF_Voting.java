package com.example.e_voteing_system.controllers;

import androidx.annotation.NonNull;

import com.example.e_voteing_system.Models.Admin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class On_OFF_Voting {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public void Check_Voting(){
        db.collection("Result").document("1").
                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                boolean statues=documentSnapshot.getBoolean("finished");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failure");
            }
        });
    }
    private void Update_status(boolean status){
        Map<String,Object> map=new HashMap<>();
        map.put("finished",status);
        db.collection("Result").document("1").update(map);
    }
    private void Finish_operation(){
        Update_status(true);
    }
    private void Rest_operation(){
        Update_status(false);
    }
}
