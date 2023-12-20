package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.SQLDataException;

public class appointment_patientdetail extends AppCompatActivity {

    private TextView firstNameTextView, lastNameTextView, emailAddressTextView, dateTextView, startTimeTextView, endTimeTextView;
    private int appointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_patientdetail);

        // Initialize TextViews
        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        emailAddressTextView = findViewById(R.id.emailAddressTextView);
        dateTextView = findViewById(R.id.pai_date);
        startTimeTextView = findViewById(R.id.pai_starttime);
        endTimeTextView = findViewById(R.id.pai_endtime);

        // Retrieve data from the Intent
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
                    dateTextView.setText("Date: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.A_DATE)));
                    startTimeTextView.setText("Start Time: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.A_START_TIME)));
                    endTimeTextView.setText("End Time: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.A_END_TIME)));
                    cursor.close();
                }
                dbManager.close();
            }
        }
    }
}