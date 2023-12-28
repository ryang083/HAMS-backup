package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class recyclelist_rejectedrequest extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> userType, firstName, lastName, userId;
    DatabaseManager DB;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclelist_rejectedrequest); // Use an appropriate layout
        DB = new DatabaseManager(this);
        // make sure to open the database before operations
        try {
            DB.open();
        } catch (Exception e) {
            Log.e("recyclelist_rejectedrequest", "Error opening database", e);

        }
        userType = new ArrayList<>();
        firstName = new ArrayList<>();
        lastName = new ArrayList<>();
        userId = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview1); // Ensure this ID is in your layout
        adapter = new MyAdapter(this, userType, firstName, lastName, userId);

        adapter.setSelectListener(new MyAdapter.SelectListener() {
            @Override
            public void onSelect(int position) {
                Intent intent;
                String selectedUserType = userType.get(position);

                if ("Patient".equals(selectedUserType)) {
                    intent = new Intent(recyclelist_rejectedrequest.this, rejectedpatient_detailinfo.class);
                } else if ("Doctor".equals(selectedUserType)) {
                    intent = new Intent(recyclelist_rejectedrequest.this, rejecteddoctor_detailinfo.class);
                } else {
                    return; // Handle unexpected user type
                }

                intent.putExtra("userID", Integer.parseInt(userId.get(position)));
                startActivity(intent);
            }
        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    @Override
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
        Cursor cursor = DB.getRejectedData();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Load data as before
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