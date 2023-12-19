package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcome_patient extends AppCompatActivity {

    private Button mainmenu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_patient);
        mainmenu1 = findViewById(R.id.b_p_mainmenu);
        mainmenu1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (welcome_patient.this, menu_patient.class);
                startActivity(intent);
            }
        });
    }
}