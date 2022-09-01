package com.example.e_voteing_system.controllers;

import androidx.annotation.NonNull;

import com.example.e_voteing_system.Models.Address;
import com.example.e_voteing_system.Models.Admin;
import com.example.e_voteing_system.Models.Machine;
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

public class Machine_controller {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public boolean Add_machine(Machine machine){
        final boolean[] re = {true};
        Map<String,Object> machine_map=map_machine(machine);
        db.collection("Machines")
                .add(machine_map)
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

    public void Retrive_machines(){
        List<Machine>machines=new ArrayList<>();
        db.collection("Machines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot doc:queryDocumentSnapshots) {
                        Machine machine=new Machine();
                        machine.FB_id=doc.getId();
                        machine.id=doc.getString("Id");
                        machine.Model=doc.getString("Model");
                        machine.location=doc.getString("Location");
                        machine.Working_statues=doc.getString("Statues");
                        Map<String,Object> d= (Map<String, Object>) doc.get("Address");
                        Address address1=new Address();
                        address1.streert_name= (String) d.get("Streert");
                        address1.area= (String) d.get("Area");
                        address1.city= (String) d.get("City");
                        machine.address=address1;
                        machines.add(machine);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failure");
            }
        });
    }

    private Map<String,Object>map_machine(Machine machine){
        Map<String,Object> machine_map=new HashMap<>();
        Map<String,Object> address_map=new HashMap<>();
        address_map.put("Streert",machine.address.streert_name);
        address_map.put("Area",machine.address.area);
        address_map.put("City",machine.address.city);
        machine_map.put("Model",machine.Model);
        machine_map.put("Id",machine.id);
        machine_map.put("Statues",machine.Working_statues);
        machine_map.put("Location",machine.location);
        machine_map.put("Address",address_map);
        return machine_map;
    }

    public void delete_machine(String FB_id){
        db.collection("Machines").document(FB_id).delete();
    }

    public void update_machine(String FB_id,Machine machine){
        Map<String,Object>maap=map_machine(machine);
        db.collection("Machines").document(FB_id).update(maap);
    }

}
