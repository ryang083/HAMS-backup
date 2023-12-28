package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class recyclelist_doctorappointments extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Appointment> appointments;
    DoctorAppointmentsAdapter adapter;
    DatabaseManager DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclelist_doctorappointments);

        DB = new DatabaseManager(this);
        try {
            DB.open();
        } catch (Exception e) {
            Log.e("DoctorAppointments", "Error opening database", e);
        }

        appointments = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview1);

        adapter = new DoctorAppointmentsAdapter(this, appointments, appointment -> {

            Intent intent = new Intent(recyclelist_doctorappointments.this, appointment_patientdetail.class);
            intent.putExtra("appointmentId", appointment.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAppointments();
    }


    private void loadAppointments() {
        Cursor cursor = DB.getDoctorAppointments();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment(
                        cursor.getInt(cursor.getColumnIndexOrThrow("appointment_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("first_name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("last_name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("a_date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("a_start_time")),
                        cursor.getString(cursor.getColumnIndexOrThrow("a_end_time")),
                        cursor.getString(cursor.getColumnIndexOrThrow("a_status"))
                );
                appointments.add(appointment);
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DB != null) {
            DB.close();
        }
    }



}