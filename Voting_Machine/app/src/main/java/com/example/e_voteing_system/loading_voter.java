package com.example.e_voteing_system;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.e_voteing_system.Helper.Bitmap_helper;
import com.example.e_voteing_system.Models.Address;
import com.example.e_voteing_system.Models.Voter;
import com.example.e_voteing_system.R;
import com.example.e_voteing_system.controllers.Voter_controller;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class loading_voter extends AppCompatActivity {
    Voter voter=new Voter();
    Bitmap_helper bitmap_helper=new Bitmap_helper();
    Voter_controller voter_controller=new Voter_controller();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_voter);
        Intent intent=getIntent();
       String finger= intent.getStringExtra("Fingerprint");
        Intent intent1=new Intent(loading_voter.this,voter_data.class);

        intent1.putExtra("National_id",voter.national_id);
        startActivity(intent1);
        db.collection("Voters").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list_of_voters= queryDocumentSnapshots.getDocuments();
                    int i=0;
                    for (DocumentSnapshot doc: list_of_voters) {
                       Voter voter=set_in_voter(doc);
                       boolean found=voter_controller.Search_by_fingerprint(finger,voter.finger_prints);
                       if(found){
                           Intent intent1=new Intent(loading_voter.this,voter_data.class);
                           Bundle bundle=new Bundle();
                           bundle.putSerializable("Voter", (Serializable) voter);
                           intent1.putExtras(bundle);
                           startActivity(intent1);
                       }

                    }
                }
                else{
                    Toast.makeText(loading_voter.this, "Can`t Found you", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(loading_voter.this,MainActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("failuer");
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    Voter set_in_voter(DocumentSnapshot doc){
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
        Map<String,Object> fingers= (Map<String, Object>) doc.get("Fingers");
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
        db.collection("Vote_Records").whereEqualTo("Voter_National_id",v.national_id).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot>snapshots=queryDocumentSnapshots.getDocuments();int i=0;
                        if(!snapshots.isEmpty()){

                            Toast.makeText(getApplicationContext(), "انت بالفعل قمت بالتصويت.", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                            getApplicationContext().startActivity(intent);
                        }
                    }
                });
        return v;
    }
}