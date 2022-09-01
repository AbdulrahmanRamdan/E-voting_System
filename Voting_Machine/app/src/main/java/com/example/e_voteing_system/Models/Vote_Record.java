package com.example.e_voteing_system.Models;

import com.example.e_voteing_system.R;
import com.google.type.DateTime;

import java.util.Date;

public class Vote_Record {
    public String FB_id;
    public String voter_n_id;
    public String candidate_n_id;
    public String Machine_id;
    public String Statues;
    public Date date_of_voting;
    public Vote_Record(){
        this.Machine_id="101";
    }
}
