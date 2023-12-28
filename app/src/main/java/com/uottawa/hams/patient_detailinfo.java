package com.uottawa.hams;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLDataException;

public class patient_detailinfo extends AppCompatActivity {

    //this class display patient registration info

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailAddressTextView;
    private TextView passwordTextView;
    private TextView phoneNumberTextView;
    private TextView addressTextView;
    private TextView healthCardNumberTextView;

    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detailinfo);


        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        emailAddressTextView = findViewById(R.id.emailAddressTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        addressTextView = findViewById(R.id.addressTextView);
        healthCardNumberTextView = findViewById(R.id.healthCardNumberTextView);

        // Retrieve patientId from the Intent - be careful when passing the intent
        Intent intent = getIntent();
        if (intent != null) {
            int patientId = intent.getIntExtra("userID", -1); // Get the patient ID

            if (patientId != -1) {
                DatabaseManager dbManager = new DatabaseManager(this);
                try {
                    dbManager.open();
                } catch (SQLDataException e) {
                    throw new RuntimeException(e);
                }
                Cursor cursor = dbManager.getPatientById(patientId);

                if (cursor != null && cursor.moveToFirst()) {
                    firstNameTextView.setText("First Name: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_FN)));
                    lastNameTextView.setText("Last Name: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_LN)));
                    emailAddressTextView.setText("Email Address: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_EA)));
                    passwordTextView.setText("Account Password: Not Available" );
                    phoneNumberTextView.setText("Phone Number: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_PNUM)));
                    addressTextView.setText("Address: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_ADDRESS)));
                    healthCardNumberTextView.setText("Health Card Number: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_HCNUM)));
                    cursor.close();

                }
                dbManager.close();


            }
        }

        patientId = getIntent().getIntExtra("userID", -1);
        Button approveButton = findViewById(R.id.b_Approve_p);
        Button rejectButton = findViewById(R.id.b_Reject_p);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (patientId != -1) {
                    DatabaseManager dbManager = new DatabaseManager(patient_detailinfo.this);
                    try {
                        dbManager.open();
                    } catch (SQLDataException e) {
                        throw new RuntimeException(e);
                    }
                    dbManager.updatePatientStatus(patientId, "APPROVED_STATUS");
                    dbManager.close();
                    finish();
                }
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (patientId != -1) {
                    DatabaseManager dbManager = new DatabaseManager(patient_detailinfo.this);
                    try {
                        dbManager.open();
                    } catch (SQLDataException e) {
                        throw new RuntimeException(e);
                    }
                    dbManager.updatePatientStatus(patientId, "REJECTED_STATUS");
                    dbManager.close();
                    finish();
                }
            }
        });




    }
    }
