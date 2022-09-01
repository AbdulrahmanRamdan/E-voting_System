package com.example.e_voteing_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChosenCandidate extends AppCompatActivity {

    TextView name_txt;
    Button Yes_btn;
    Button No_btn;
    String chosen_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosen_candidate);
        name_txt=findViewById(R.id.textView);
        Yes_btn=findViewById(R.id.button2);
        No_btn=findViewById(R.id.button3);
        chosen_name=getIntent().getStringExtra("Chosen_Name");
        String tmp=name_txt.getText().toString();
        name_txt.setText(tmp+chosen_name+"؟");


        Yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChosenCandidate.this, "تمت عملية التصويت بنجاح.", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ChosenCandidate.this,MainActivity.class);
                startActivity(intent);
            }
        });
        No_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ChosenCandidate.this,Candidates.class);
                startActivity(intent);
            }
        });


    }
}