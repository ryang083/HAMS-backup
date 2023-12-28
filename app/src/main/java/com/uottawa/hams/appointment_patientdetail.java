package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLDataException;

public class appointment_patientdetail extends AppCompatActivity {

    //this class is to display patient detail for each appointment

    private TextView firstNameTextView, lastNameTextView, emailAddressTextView, passwordTextView, phoneNumberTextView, addressTextView, healthCardNumberTextView, dateTextView, startTimeTextView, endTimeTextView;
    private int appointmentId;
    private Button approveButton, rejectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_patientdetail);


        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        emailAddressTextView = findViewById(R.id.emailAddressTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        addressTextView = findViewById(R.id.addressTextView);
        healthCardNumberTextView = findViewById(R.id.healthCardNumberTextView);
        dateTextView = findViewById(R.id.pai_date);
        startTimeTextView = findViewById(R.id.pai_starttime);
        endTimeTextView = findViewById(R.id.pai_endtime);

        // Retrieve data from the Intent - be careful when passing appointmentId from intent
        Intent intent = getIntent();
        if (intent != null) {
            appointmentId = intent.getIntExtra("appointmentId", -1); // Get the appointment ID

            if (appointmentId != -1) {
                DatabaseManager dbManager = new DatabaseManager(this);
                try {
                    dbManager.open();
                } catch (SQLDataException e) {
                    throw new RuntimeException(e);
                }

                Cursor cursor = dbManager.getAppointmentDetailsById(appointmentId);

                if (cursor != null && cursor.moveToFirst()) {
                    // Set text views with the cursor data
                    firstNameTextView.setText("First Name: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_FN)));
                    lastNameTextView.setText("Last Name: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_LN)));
                    emailAddressTextView.setText("Email: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_EA)));
                    passwordTextView.setText("Password: Not Available");
                    dateTextView.setText("Date: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.A_DATE)));
                    startTimeTextView.setText("Start Time: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.A_START_TIME)));
                    endTimeTextView.setText("End Time: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.A_END_TIME)));
                    phoneNumberTextView.setText("Phone: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_PNUM)));
                    addressTextView.setText("Address: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_ADDRESS)));
                    healthCardNumberTextView.setText("Health Card: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.P_HCNUM)));
                    cursor.close();
                }
                dbManager.close();
            }
        }

        approveButton = findViewById(R.id.b_Approve_pai);
        rejectButton = findViewById(R.id.b_Reject_pai);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appointmentId != -1) {
                    DatabaseManager dbManager = new DatabaseManager(appointment_patientdetail.this);
                    try {
                        dbManager.open();
                    } catch (SQLDataException e) {
                        throw new RuntimeException(e);
                    }
                    dbManager.updateAppointmentStatus(appointmentId, "APPROVED_STATUS");
                    dbManager.close();
                    finish();

                }
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appointmentId != -1) {
                    DatabaseManager dbManager = new DatabaseManager(appointment_patientdetail.this);
                    try {
                        dbManager.open();
                    } catch (SQLDataException e) {
                        throw new RuntimeException(e);
                    }
                    dbManager.updateAppointmentStatus(appointmentId, "REJECTED_STATUS");
                    dbManager.close();
                    finish();

                }
            }
        });


    }


}


