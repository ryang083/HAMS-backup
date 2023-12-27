package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_doctor extends AppCompatActivity {
    private Button logout2;
    private Button upcomingappointments1;

    private Button upcomingshift1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_doctor);
        logout2 = findViewById(R.id.logout2);
        logout2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (menu_doctor.this, MainActivity.class);
                startActivity(intent);
            }
        });

        upcomingappointments1 = findViewById(R.id.b_upcomingappointments);
        upcomingappointments1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (menu_doctor.this, recyclelist_doctorappointments.class);
                startActivity(intent);
            }
        });

        upcomingshift1 = findViewById(R.id.b_upcomingshift);
        upcomingshift1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //will have to change _addashift to something else later
                Intent intent = new Intent (menu_doctor.this, doctor_addashift.class);
                startActivity(intent);
            }
        });
    }
}