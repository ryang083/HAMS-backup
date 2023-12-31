package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class patient_bookappointment extends AppCompatActivity {

    // page for patient to book appointments, save data into database

    private EditText dateInput, startTimeInput, endTimeInput;
    private Button submitButton;
    private DatabaseManager dbManager;
    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_bookappointment);


        TextView currentDateTextView = findViewById(R.id.currentdatetextView);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        currentDateTextView.setText("Current date: " + currentDate);


        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }


        dateInput = findViewById(R.id.a_input_date);
        startTimeInput = findViewById(R.id.a_input_starttime);
        endTimeInput = findViewById(R.id.a_input_endtime);
        submitButton = findViewById(R.id.b_submit_a);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userID")) {
            patientId = intent.getIntExtra("userID", -1);
        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAppointment();
            }
        });
    }

    private void submitAppointment() {
        String date = dateInput.getText().toString();
        String startTime = startTimeInput.getText().toString();
        String endTime = endTimeInput.getText().toString();


        if (date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        try {
            dbManager.open();
            dbManager.insertAppointment(patientId, date, startTime, endTime);
            dbManager.close();


            clearInputs();


            Toast.makeText(this, "Appointment submitted successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            Toast.makeText(this, "Failed to submit appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputs() {
        dateInput.setText("");
        startTimeInput.setText("");
        endTimeInput.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }
}