package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userType_login extends AppCompatActivity {
    private Button admin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_login);

        admin1 = findViewById(R.id.admin_userType);

        admin1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            Intent intent = new Intent (userType_login.this, login_admin.class);
            startActivity(intent);
        }
    });

    }




}