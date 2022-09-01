package com.example.e_voteing_system.Models;

import android.graphics.Bitmap;

public class Candidate {
    public String Fb_id;
    public String name;
    public String  National_id;
    public String slogan;
    public String party;
    public String program;
    public int Number_of_votes;
    public String link_requied_paper;
    public String image;
    public Bitmap Slogan_image;

    public Candidate(){
        this.Number_of_votes=0;
    }
}
