package com.example.e_voteing_system.Models;

import android.graphics.Bitmap;


import com.google.type.DateTime;

import java.util.Date;

public class Voter {
    public  String FB_id;
    public String national_id;
    public String name;
    public String gender;
    public String Email;
    public String phone_number;
    public boolean Disabled;
    public String marital_status;
    public String[] finger_prints = new String[10];
    public Date brithdate;
    public String religion;
    public int age;
    public String Job;
    public Bitmap image;
    public String qualifications;
    public Address address;

    public Voter(){
       this.address=new Address();
    }
}
