package com.example.e_voteing_system;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voteing_system.Models.Candidate;

public class popup_Window extends AppCompatActivity {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    TextView txt;
    Button done_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        Intent newint=getIntent();
        //String finger=newint.getStringExtra(MainActivity.Fingerstring);
        int finid=newint.getIntExtra("Fingerstring",0);
        txt=findViewById(R.id.tvb);
        done_btn=findViewById(R.id.but);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText(String.valueOf(finid));}
        });
/*
            builder = new AlertDialog.Builder(this);
            final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);
            txt = (TextView) findViewById(R.id.popup_txt);
            txt.setText("kkkkk");
            builder.setView(contactPopupView);


            builder.setNegativeButton("back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(popup_Window.this, Candidate.class);
                    startActivity(intent);
                    finish();
                }
            }).create();
        dialog = builder.create();
        dialog.show();

            /*done_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(popup_Window.this, Candidate.class);
                     startActivity(intent);
                }
            });

             */

    }
}