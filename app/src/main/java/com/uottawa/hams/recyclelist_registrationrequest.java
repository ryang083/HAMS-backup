package com.uottawa.hams;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class recyclelist_registrationrequest extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> userType, firstName, lastName;
    DatabaseManager DB;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclelist_registrationrequest);
        DB = new DatabaseManager(this);
        try {
            DB.open(); // Open the database
        } catch (Exception e) {
            Log.e("recyclelist_registrationrequest", "Error opening database", e);
            // Handle the error or finish the activity
        }
        userType = new ArrayList<>();
        firstName = new ArrayList<>();
        lastName = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview1);
        adapter = new MyAdapter(this, userType, firstName, lastName);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();



    }

    private void displaydata() {
        Cursor cursor = DB.getdata();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String userType = cursor.getString(cursor.getColumnIndexOrThrow("user_type"));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));

                this.userType.add(userType);
                this.firstName.add(firstName);
                this.lastName.add(lastName);
            } while (cursor.moveToNext());
            cursor.close(); // Close the cursor after use
        }

        adapter.notifyDataSetChanged();
    }
}