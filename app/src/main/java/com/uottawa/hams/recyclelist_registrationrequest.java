package com.uottawa.hams;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class recyclelist_registrationrequest extends AppCompatActivity {
    // works with userentry.xml and MyAdapter
    RecyclerView recyclerView;
    ArrayList<String> userType, firstName, lastName, userId;
    DatabaseManager DB;
    MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclelist_registrationrequest);
        DB = new DatabaseManager(this);
        try {
            DB.open();
        } catch (Exception e) {
            Log.e("recyclelist_registrationrequest", "Error opening database", e);
        }
        userType = new ArrayList<>();
        firstName = new ArrayList<>();
        lastName = new ArrayList<>();
        userId = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview1);
        adapter = new MyAdapter(this, userType, firstName, lastName, userId);

        adapter.setSelectListener(new MyAdapter.SelectListener() {
            @Override
            public void onSelect(int position) {
                Intent intent;
                String selectedUserType = userType.get(position);

                if ("Patient".equals(selectedUserType)) {
                    intent = new Intent(recyclelist_registrationrequest.this, patient_detailinfo.class);
                } else if ("Doctor".equals(selectedUserType)) {
                    intent = new Intent(recyclelist_registrationrequest.this, doctor_detailinfo.class);
                } else {
                    return;
                }

                intent.putExtra("userID", Integer.parseInt(userId.get(position)));
                startActivity(intent);
            }
        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();



    }

    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        userType.clear();
        firstName.clear();
        lastName.clear();
        userId.clear();
        displaydata();
    }

    private void displaydata() {
        Cursor cursor = DB.getdata();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String userType = cursor.getString(cursor.getColumnIndexOrThrow("user_type"));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
                String userId = cursor.getString(cursor.getColumnIndexOrThrow("id"));

                this.userType.add(userType);
                this.firstName.add(firstName);
                this.lastName.add(lastName);
                this.userId.add(userId);
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }

    }






