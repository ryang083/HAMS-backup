package com.uottawa.hams;

import static com.uottawa.hams.InputValidator.isInputValid;
import static com.uottawa.hams.InputValidator.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class registration_patient extends AppCompatActivity {
    // save patient registration data into database
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

        sub_pr = findViewById(R.id.p_submit_PR);
        sub_pr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Retrieve input values when the button is clicked
                String firstName = editP_FirstName.getText().toString();
                String lastName = editP_LastName.getText().toString();
                String emailAddress = editP_EmailAddress.getText().toString();
                String password = editP_Password.getText().toString();
                String phoneNumber = editP_PhoneNumber.getText().toString();
                String address = editP_Address.getText().toString();
                String healthCardNum = editP_HealthCardNum.getText().toString();


                if (InputValidator.isPatientInputValid(firstName, lastName, emailAddress, password, phoneNumber, address, healthCardNum)) {
                    dbManager.insertPatient(firstName, lastName, emailAddress, password, phoneNumber, address, healthCardNum);
                    Intent intent = new Intent(registration_patient.this, requestsubmitted_pleasewait.class);
                    startActivity(intent);
                    Toast.makeText(registration_patient.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(registration_patient.this, "Invalid input. Please check your data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }








}
