package com.uottawa.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// registration_doctor.java
public class registration_doctor extends AppCompatActivity {
    private Button sub_dr;
    EditText editD_FirstName;
    EditText editD_LastName;
    EditText editD_EmailAddress;
    EditText editD_Password;
    EditText editD_PhoneNumber;
    EditText editD_Address;
    EditText editD_EmployeeNum;
    EditText editD_Specialties;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_doctor);

        editD_FirstName = findViewById(R.id.d_input_firstname);
        editD_LastName = findViewById(R.id.d_input_lastname);
        editD_EmailAddress = findViewById(R.id.d_input_email);
        editD_Password = findViewById(R.id.d_input_password);
        editD_PhoneNumber = findViewById(R.id.d_input_phonenum);
        editD_Address = findViewById(R.id.d_input_address);
        editD_EmployeeNum = findViewById(R.id.d_input_healthcardnum);
        editD_Specialties = findViewById(R.id.d_input_specialties);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sub_dr = findViewById(R.id.b_submit_DR);
        sub_dr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Insert data into the database
                String firstName = editD_FirstName.getText().toString();
                String lastName = editD_LastName.getText().toString();
                String emailAddress = editD_EmailAddress.getText().toString();
                String password = editD_Password.getText().toString();
                String phoneNumber = editD_PhoneNumber.getText().toString();
                String address = editD_Address.getText().toString();
                String employeeNum = editD_EmployeeNum.getText().toString();
                String specialties = editD_Specialties.getText().toString();

                // Validate and insert data into the database
                if (InputValidator.isDoctorInputValid(firstName, lastName, emailAddress, password,
                        phoneNumber, address, employeeNum, specialties)) {
                    dbManager.insertDoctor(firstName, lastName, emailAddress, password,
                            phoneNumber, address, employeeNum, specialties);
                    Intent intent = new Intent(registration_doctor.this, welcome_doctor.class);
                    startActivity(intent);
                    Toast.makeText(registration_doctor.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle invalid input, show a message, or log an error
                    Toast.makeText(registration_doctor.this, "Invalid input. Please check your data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
