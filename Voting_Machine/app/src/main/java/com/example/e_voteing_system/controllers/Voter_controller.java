package com.example.e_voteing_system.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.e_voteing_system.Helper.Bitmap_helper;
import com.example.e_voteing_system.Models.Address;
import com.example.e_voteing_system.Models.Voter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Voter_controller {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Bitmap_helper bitmap_helper=new Bitmap_helper();
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean Add_Voter(Voter voter){
        final boolean[] re = {true};
        Map<String,Object>voter_map=Map_voter(voter);
        db.collection("Voters")
                .add(voter_map)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Map<String,Object> Map_voter(Voter voter){
        Map<String,Object> map_voter=new HashMap<>();
        Map<String,Object>fingerprints=Fingerprintsmap(voter.finger_prints);
        Map<String,Object>address=addressmap(voter.address);
        map_voter.put("National_ID",voter.national_id);
        map_voter.put("Name",voter.name);
        map_voter.put("Job",voter.Job);
        map_voter.put("Age",voter.age);
        map_voter.put("Birth_Date",voter.brithdate.toString());
        map_voter.put("Gender",voter.gender);
        map_voter.put("Email",voter.Email);
        map_voter.put("Phone",voter.phone_number);
        map_voter.put("Marital_status",voter.marital_status);
        map_voter.put("Qualifications",voter.qualifications);
        map_voter.put("Disable",voter.Disabled);
        map_voter.put("Image",bitmap_helper.convertfrom_Bitmap_to_string(voter.image));
        map_voter.put("Religion",voter.religion);
        map_voter.put("Fingers",fingerprints);
        map_voter.put("Address",address);
        return map_voter;
    }
    private Map<String,Object> addressmap(Address address) {
    Map<String,Object>map_address=new HashMap<>();
    map_address.put("House Number",address.house_number);
    map_address.put("Streert Name",address.streert_name);
    map_address.put("Area",address.area);
    map_address.put("City",address.city);
    return map_address;
}
    private Map<String,Object> Fingerprintsmap(String[]fingers) {
    Map<String,Object>fingerprints=new HashMap<>();
    fingerprints.put("finger1",fingers[0]);
    fingerprints.put("finger2",fingers[1]);
    fingerprints.put("finger3",fingers[2]);
    fingerprints.put("finger4",fingers[3]);
    fingerprints.put("finger5",fingers[4]);
    fingerprints.put("finger6",fingers[5]);
    fingerprints.put("finger7",fingers[6]);
    fingerprints.put("finger8",fingers[7]);
    fingerprints.put("finger9",fingers[8]);
    fingerprints.put("finger10",fingers[9]);
       return fingerprints;
    }


    public List<Voter> Retrieve_Voters(){
        List<Voter>voters=new ArrayList<Voter>();
      db.collection("Voters").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
          @RequiresApi(api = Build.VERSION_CODES.O)
          @Override
          public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
              if(!queryDocumentSnapshots.isEmpty()){
                  List<DocumentSnapshot> list_of_voters= queryDocumentSnapshots.getDocuments();
                  int i=0;
                  for (DocumentSnapshot doc: list_of_voters) {
                        Voter v=new Voter();
                        v.FB_id=doc.getId();
                        v.national_id= doc.getString("National_ID").toString();
                        v.image=bitmap_helper.convertto_Bitmap_from_string(doc.getString("Image").toString());
                        v.religion=doc.getString("Religion").toString();
                        v.phone_number=doc.getString("Phone").toString();
                        v.name=doc.getString("Name").toString();
                        v.Job=doc.getString("Job").toString().toString();
                        v.gender= doc.getString("Gender");
                        v.Email=doc.getString("Email").toString();
                        v.marital_status=doc.getString("Marital_status").toString();
                        v.Disabled=doc.getBoolean("Disable");
                        v.qualifications=doc.getString("Qualifications").toString();
                        v.brithdate=doc.getDate("Birth_Date");
                        v.age=v.brithdate.getYear()-new Date().getYear();
                        v.address= (Address) doc.get("Address");
                        Map<String,Object>fingers= (Map<String, Object>) doc.get("Fingers");
                        v.finger_prints[0]=fingers.get("finger1").toString();
                        v.finger_prints[1]=fingers.get("finger2").toString();
                        v.finger_prints[2]=fingers.get("finger3").toString();
                        v.finger_prints[3]=fingers.get("finger4").toString();
                      v.finger_prints[4]=fingers.get("finger5").toString();
                      v.finger_prints[5]=fingers.get("finger6").toString();
                      v.finger_prints[6]=fingers.get("finger7").toString();
                      v.finger_prints[7]=fingers.get("finger8").toString();
                      v.finger_prints[8]=fingers.get("finger9").toString();
                      v.finger_prints[9]=fingers.get("finger10").toString();
                        voters.add(i,v);
                        i++;
                  }
              }
              else{System.out.println("Empty");}
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              System.out.println("failuer");
          }
      });
      return voters;
    }

    public boolean Search_by_fingerprint(String fingerprint,String[] fingers){
            for (String finger:fingers) {
                if (finger.equals(fingerprint))
                    return true;
            }
        return false;
    }

    public Voter Search_by_national_id(String national_ID, List<Voter> votersList){
        List<Voter>voters=votersList;
        for (Voter v:voters) {
                if (v.national_id.equals(national_ID))
                    return v;
        }
        return null;
    }

    public void delete_Voter(String ID){
        db.collection("Voters").document(ID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Update_voter(String ID, Voter v){
        Map<String,Object>voter_map=Map_voter(v);
        db.collection("Voters").document(ID).update(voter_map);
    }
}
