package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userType_register extends AppCompatActivity {
    private Button patient;
    private Button doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_register);
        patient = findViewById(R.id.patient_userType);
        doctor = findViewById(R.id.doctor_userType);
        patient.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (userType_register.this,registration_patient.class);
                startActivity(intent);
            }
        });
        doctor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (userType_register.this,registration_doctor.class);
                startActivity(intent);
            }
        });



    }



}