package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userType_login extends AppCompatActivity {
    // choose usertype to login
    private Button admin1;
    private Button patient1;
    private Button doctor1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_login);

        patient1 = findViewById(R.id.patient_userType);
        doctor1 = findViewById(R.id.doctor_userType);
        admin1 = findViewById(R.id.admin_userType);

        patient1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (userType_login.this, login_patient.class);
                startActivity(intent);
            }
        });

        doctor1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (userType_login.this, login_doctor.class);
                startActivity(intent);
            }
        });


        admin1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            Intent intent = new Intent (userType_login.this, login_admin.class);
            startActivity(intent);
        }
    });

    }




}