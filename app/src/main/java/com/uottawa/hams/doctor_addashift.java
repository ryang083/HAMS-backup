package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLDataException;

public class doctor_addashift extends AppCompatActivity {

    //this class is to add new doctor shift

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_addashift);


        EditText dateInput = findViewById(R.id.ds_input_date);
        EditText startTimeInput = findViewById(R.id.ds_input_starttime);
        EditText endTimeInput = findViewById(R.id.ds_input_endtime);
        Button submitButton = findViewById(R.id.b_submit_a);


        DatabaseManager dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        // haven't set up to pass the doctorID through intent
        int doctorId = getIntent().getIntExtra("doctorID", -1);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values
                String date = dateInput.getText().toString();
                String startTime = startTimeInput.getText().toString();
                String endTime = endTimeInput.getText().toString();


                if (!date.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {
                    dbManager.insertDoctorShift(doctorId, date, startTime, endTime);
                    Toast.makeText(doctor_addashift.this, "Shift added successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(doctor_addashift.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}