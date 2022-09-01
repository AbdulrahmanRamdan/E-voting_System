package com.example.e_voteing_system.Helper;

import com.google.type.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Date_helper {
    public String convert_from_date_to_string(Date date){
        String date_string=date.toString();
        return date_string;
    }
    public Date convert_to_date_from_string(String string) throws ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mm-yyyy");
        date=simpleDateFormat.parse(string);
        return date;
    }
    public boolean allow_to_vote(Date date){
        Date today=new Date();
        if((today.getYear()-date.getYear())>=18){
            return true;
        }
        else
            return false;
    }
}
