package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button register1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register1 = findViewById(R.id.register1);
        register1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent (MainActivity.this,userType.class);
                startActivity(intent);
            }
        });
    }
}