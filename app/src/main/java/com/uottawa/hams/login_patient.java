package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_patient extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private DatabaseManager dbManager;
    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        dbManager = new DatabaseManager(this);
        try {
            dbManager.open(); // Open the database
        } catch (Exception e) {
            Log.e("login_patient", "Error opening database", e);
        }


        emailEditText = findViewById(R.id.patient_username);
        passwordEditText = findViewById(R.id.patient_password);
        loginButton = findViewById(R.id.patient_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            if (dbManager.validatePatient(email, password)) {
                // Fetching patient ID based on the email
                patientId = dbManager.getPatientIdByEmail(email); // Implement this method in DatabaseManager

                // Fetch registration status and navigate accordingly
                String status = dbManager.getPatientRegistrationStatus(email);
                navigateBasedOnStatus(status, "Patient");
            } else {
                Toast.makeText(this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateBasedOnStatus(String status, String userType) {
        Intent intent;
        if (DatabaseHelper.DEFAULT_STATUS.equals(status)) {
            intent = new Intent(this, request_notprocesssedyet.class);
        } else if ("APPROVED_STATUS".equals(status)) {
            if (userType.equals("Doctor")) {
                intent = new Intent(this, welcome_doctor.class);
            } else {
                intent = new Intent(this, welcome_patient.class);
            }
            intent.putExtra("userID", patientId); // Ensure you have the correct patientId here
        } else if ("REJECTED_STATUS".equals(status)) {
            intent = new Intent(this, request_rejected.class);
        } else {
            return; // Handle unexpected status
        }
        startActivity(intent);
        finish();
    }
}
