package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLDataException;

public class doctor_detailinfo extends AppCompatActivity {
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailAddressTextView;
    private TextView passwordTextView;
    private TextView phoneNumberTextView;
    private TextView addressTextView;
    private TextView employeeNumberTextView;
    private TextView specialtiesTextView;

    private int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detailinfo);

        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        emailAddressTextView = findViewById(R.id.emailAddressTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        addressTextView = findViewById(R.id.addressTextView);
        employeeNumberTextView = findViewById(R.id.employeeNumberTextView);
        specialtiesTextView = findViewById(R.id.specialtiesTextView);
        Intent intent = getIntent();
        if (intent != null) {
            int doctorId = intent.getIntExtra("userID", -1);

            if (doctorId != -1) {
                DatabaseManager dbManager = new DatabaseManager(this);
                try {
                    dbManager.open();
                } catch (SQLDataException e) {
                    throw new RuntimeException(e);
                }
                Cursor cursor = dbManager.getDoctorById(doctorId);

                if (cursor != null && cursor.moveToFirst()) {
                    firstNameTextView.setText("First Name: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.D_FN)));
                    lastNameTextView.setText("Last Name: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.D_LN)));
                    emailAddressTextView.setText("Email Address: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.D_EA)));
                    passwordTextView.setText("Account Password: Not Available" );
                    phoneNumberTextView.setText("Phone Number: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.D_PNUM)));
                    addressTextView.setText("Address: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.D_ADDRESS)));
                    employeeNumberTextView.setText("Employee Number: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.D_EMPLOYEE_NUM)));
                    specialtiesTextView.setText("Specialties: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.D_SPECIALTIES)));
                    cursor.close();

                }
                dbManager.close();


            }
        }
        doctorId = getIntent().getIntExtra("userID", -1);
        Button approveButton = findViewById(R.id.b_Approve_d);
        Button rejectButton = findViewById(R.id.b_Reject_d);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doctorId != -1) {
                    DatabaseManager dbManager = new DatabaseManager(doctor_detailinfo.this);
                    try {
                        dbManager.open();
                    } catch (SQLDataException e) {
                        throw new RuntimeException(e);
                    }
                    dbManager.updateDoctorStatus(doctorId, "APPROVED_STATUS");
                    dbManager.close();
                    finish();
                }
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the database first before call a method from the database
                if (doctorId != -1) {
                    DatabaseManager dbManager = new DatabaseManager(doctor_detailinfo.this);
                    try {
                        dbManager.open();
                    } catch (SQLDataException e) {
                        throw new RuntimeException(e);
                    }
                    dbManager.updateDoctorStatus(doctorId, "REJECTED_STATUS");
                    dbManager.close();
                    finish();
                }
            }
        });



    }
}


