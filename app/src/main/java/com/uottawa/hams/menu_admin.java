package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_admin extends AppCompatActivity {
    private Button logout1;
    private Button registrationrequest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        logout1 = findViewById(R.id.logout2);
        logout1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (menu_admin.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registrationrequest1 = findViewById(R.id.b_registrationrequest);
        registrationrequest1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (menu_admin.this, recyclelist_registrationrequest.class);
                startActivity(intent);
            }
        });



    }
}