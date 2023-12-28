package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class menu_patient extends AppCompatActivity {
    private Button logout2;
    private Button bookappointment1;
    private int patientId; // Variable to store patient ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_patient);

        // Retrieve patient ID from the intent
        Intent incomingIntent = getIntent();
        patientId = incomingIntent.getIntExtra("userID", -1); // Default to -1 if not found

        logout2 = findViewById(R.id.logout2);
        logout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(menu_patient.this, MainActivity.class);
                startActivity(intent);
            }
        });

        bookappointment1 = findViewById(R.id.b_bookappointment);
        bookappointment1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (patientId != -1) {
                    Intent intent = new Intent(menu_patient.this, patient_bookappointment.class);
                    intent.putExtra("userID", patientId);
                    startActivity(intent);
                } else {
                    Toast.makeText(menu_patient.this, "Error: Patient ID not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}