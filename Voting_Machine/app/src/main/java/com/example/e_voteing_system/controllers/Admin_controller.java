package com.example.e_voteing_system.controllers;

import android.os.CountDownTimer;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.e_voteing_system.Models.Admin;
import com.example.e_voteing_system.Models.Vote_Record;
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

public class Admin_controller {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public List<Admin>admins=new ArrayList<>();
    public boolean Add_Admin(Admin admin){
        final boolean[] re = {true};
        Map<String,Object> admin_map=new HashMap<>();
        admin_map.put("National_id",admin.Natoinal_id);
        admin_map.put("User_name",admin.User_name);
        admin_map.put("Pass_word",admin.password);
        db.collection("Admins")
                .add(admin_map)
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
    public void Get_Admin_data(String user,String pass)  {
        Admin admin=new Admin();
       db.collection("Admins").
                whereEqualTo("User_name",user).
                whereEqualTo("Pass_word",pass).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot sn : queryDocumentSnapshots) {
                        admin.Fb_id = sn.getId().toString();
                        System.out.println(admin.Fb_id);
                        admin.Natoinal_id = sn.getString("National_id").toString();
                        admin.User_name = sn.getString("User_name").toString();
                        admin.password = sn.getString("Pass_word").toString();
                        admins.add(admin);
                    }
                }
            }

        } ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failure");
            }
        });

    }

    public void delete_admin(String Fb_id){
        db.collection("Admins").document(Fb_id).delete();
    }
    public void update_admin(String Fb_id,Admin admin){
        Map<String,Object> admin_map=new HashMap<>();
        admin_map.put("National_id",admin.Natoinal_id);
        admin_map.put("User_name",admin.User_name);
        admin_map.put("Pass_word",admin.password);
        db.collection("Admins").document(Fb_id).update(admin_map);
    }
}
