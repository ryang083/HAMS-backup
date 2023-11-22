package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button register1;
    private Button login1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register1 = findViewById(R.id.register1);
        login1 = findViewById(R.id.login1);
        register1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (MainActivity.this, userType_register.class);
                startActivity(intent);
            }
        });

        login1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (MainActivity.this, userType_login.class);
                startActivity(intent);
            }
        });



    }
}