package com.example.e_voteing_system.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class Bitmap_helper {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String convertfrom_Bitmap_to_string(Bitmap image){
        ByteArrayOutputStream arr=new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,arr);
        byte[]b=arr.toByteArray();
        return Base64.getEncoder().encodeToString(b);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap convertto_Bitmap_from_string(String image){
        byte[]decode_String=Base64.getDecoder().decode(image);
        Bitmap bit= BitmapFactory.decodeByteArray(decode_String,0,decode_String.length);
        return bit;
    }
}
