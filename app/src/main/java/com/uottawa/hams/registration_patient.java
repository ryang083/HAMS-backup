package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class registration_patient extends AppCompatActivity {
    private Button sub_pr;
    EditText editP_FirstName;
    EditText editP_LastName;
    EditText editP_EmailAddress;
    EditText editP_Password;
    EditText editP_PhoneNumber;
    EditText editP_Address;
    EditText editP_HealthCardNum;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_patient);

        editP_FirstName = findViewById(R.id.p_input_firstname);
        editP_LastName = findViewById(R.id.p_input_lastname);
        editP_EmailAddress = findViewById(R.id.p_input_email);
        editP_Password = findViewById(R.id.p_input_password);
        editP_PhoneNumber = findViewById(R.id.p_input_phonenum);
        editP_Address = findViewById(R.id.p_input_address);
        editP_HealthCardNum = findViewById(R.id.p_input_healthcardnum);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sub_pr = findViewById(R.id.b_submit_PR);
        sub_pr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Insert data into the database
                dbManager.insert(
                        editP_FirstName.getText().toString(),
                        editP_LastName.getText().toString(),
                        editP_EmailAddress.getText().toString(),
                        editP_Password.getText().toString(),
                        editP_PhoneNumber.getText().toString(),
                        editP_Address.getText().toString(),
                        editP_HealthCardNum.getText().toString()
                );

                // Start the welcome_patient activity
                Intent intent = new Intent(registration_patient.this, welcome_patient.class);
                startActivity(intent);
            }
        });
    }


    }



