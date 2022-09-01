package com.example.e_voteing_system;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voteing_system.Helper.Bitmap_helper;
import com.example.e_voteing_system.Models.Address;
import com.example.e_voteing_system.Models.Voter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class voter_data extends AppCompatActivity {

    Bitmap_helper bitmap_helper=new Bitmap_helper();
    TextView name,id,phone;
    Button next,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_data);
        final Voter[] voters = {new Voter()};

        //ImageView image=(ImageView) findViewById(R.id.photo);
         name=findViewById(R.id.name);
         id=findViewById(R.id.nationalID);
         phone=findViewById(R.id.phone);
         next=findViewById(R.id.next_btn);
         back=findViewById(R.id.cansel_btn);
         Intent intent=getIntent();
         int finid=intent.getIntExtra("Fingerstring",0);
         System.out.println(finid);

        try{
         FirebaseFirestore db=FirebaseFirestore.getInstance();
            db.collection("Voters").whereEqualTo("Finger ID",1).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                              @RequiresApi(api = Build.VERSION_CODES.O)
                                              @Override
                                              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                  if(!queryDocumentSnapshots.isEmpty()){
                                                      for(DocumentSnapshot doc:queryDocumentSnapshots){
                                                          voters[0] =set_in_voter(doc);
                                                          name.setText(voters[0].name);
                                                          id.setText(voters[0].national_id);
                                                          phone.setText(voters[0].phone_number);

                                                          break;
                                                      }
                                                  }
                                              }
                                          }

                    ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("failuer");
                    Toast.makeText(voter_data.this, "Feeeeeeeeeeee", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception E){
            Toast.makeText(this, E.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
      //System.out.println(finid);
       // String national_iid=intent.getStringExtra("Voter_ID");
        //image.setImageBitmap(voters[0].image);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent (voter_data.this,Candidates.class);
                intent2.putExtra("Nationsl_id",voters[0].national_id);
                startActivity(intent2);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(voter_data.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    Voter set_in_voter(DocumentSnapshot doc){
        Voter v=new Voter();
//        v.FB_id=doc.getId();
        v.national_id= doc.getString("National_ID").toString();
//        v.image=bitmap_helper.convertto_Bitmap_from_string(doc.getString("Image").toString());
  //      v.religion=doc.getString("Religion").toString();
        v.phone_number=doc.getString("Phone").toString();
        v.name=doc.getString("Name").toString();
    //    v.Job=doc.getString("Job").toString().toString();
      //  v.gender= doc.getString("Gender");
       // v.Email=doc.getString("Email").toString();
        //v.marital_status=doc.getString("Marital_status").toString();
  //      v.Disabled=doc.getBoolean("Disable");
        //v.qualifications=doc.getString("Qualifications").toString();
    //    v.brithdate=doc.getDate("Birth_Date");
      //  v.age=v.brithdate.getYear()-new Date().getYear();
      //  v.address= (Address) doc.get("Address");

        //v.finger_prints[0]=doc.get("")
        /*v.finger_prints[1]=fingers.get("finger2").toString();
        v.finger_prints[2]=fingers.get("finger3").toString();
        v.finger_prints[3]=fingers.get("finger4").toString();
        v.finger_prints[4]=fingers.get("finger5").toString();
        v.finger_prints[5]=fingers.get("finger6").toString();
        v.finger_prints[6]=fingers.get("finger7").toString();
        v.finger_prints[7]=fingers.get("finger8").toString();
        v.finger_prints[8]=fingers.get("finger9").toString();
        v.finger_prints[9]=fingers.get("finger10").toString();
        */
        return v;
    }
}