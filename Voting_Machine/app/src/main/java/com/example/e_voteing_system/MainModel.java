package com.example.e_voteing_system;

import android.graphics.Bitmap;

public class MainModel {
    String solgan;
    String photos;
    String names;
    public MainModel(String solgan, String photos, String names){
        this.names=names;
        this.photos=photos;
        this.solgan=solgan; }

    public String getSolgan() {
        return solgan;
    }

    public String getPhotos() {
        return photos;
    }

    public String getNames() {
        return names;
    }
}
