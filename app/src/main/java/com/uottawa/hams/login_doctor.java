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

public class login_doctor extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (Exception e) {
            Log.e("login_doctor", "Error opening database", e);
        }

        emailEditText = findViewById(R.id.doctor_username);
        passwordEditText = findViewById(R.id.doctor_password);
        loginButton = findViewById(R.id.b_doctor_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            if (dbManager.validateDoctor(email, password)) {
                String status = dbManager.getDoctorRegistrationStatus(email);
                navigateBasedOnStatus(status, "Doctor");
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
            intent = userType.equals("Doctor") ? new Intent(this, welcome_doctor.class)
                    : new Intent(this, welcome_patient.class);
        } else if ("REJECTED_STATUS".equals(status)) {
            intent = new Intent(this, request_rejected.class);
        } else {
            return;
        }
        startActivity(intent);
        finish();
    }


}