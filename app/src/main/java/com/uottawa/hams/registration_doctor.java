package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registration_doctor extends AppCompatActivity {
    private Button sub_dr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_doctor);
        sub_dr = findViewById(R.id.b_submit_DR);

        sub_dr.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (registration_doctor.this,welcome_doctor.class);
                startActivity(intent);
            }
        });


    }
}