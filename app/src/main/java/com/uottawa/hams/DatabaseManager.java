package com.uottawa.hams;

import static com.uottawa.hams.InputValidator.isInputValid;
import static com.uottawa.hams.InputValidator.isValidEmail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import java.sql.SQLDataException;
// DatabaseManager.java
public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context ctx) {
        context = ctx;
    }

    public DatabaseManager open() throws SQLDataException {
        try {
            dbHelper = new DatabaseHelper(context);
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.e("DatabaseManager", "Error opening database: " + e.getMessage());
            // Optionally, you can rethrow a RuntimeException
            // throw new RuntimeException("Error opening database", e);
        }
        return this;
    }

    public void close() {
        if (database != null) {
            database.close();
        };
    }

    // Insert patient data
    public void insertPatient(String p_firstname, String p_lastname,
                              String p_emailaddress, String p_password,
                              String p_phonenumber, String p_address,
                              String p_healthcardnum) {
        if (database == null) {
            Log.e("DatabaseManager", "Database is not opened");
            Toast.makeText(context, "Database is not available", Toast.LENGTH_SHORT).show();
            return;}
        if (InputValidator.isPatientInputValid(p_firstname, p_lastname, p_emailaddress,
                p_password, p_phonenumber, p_address, p_healthcardnum)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.P_FN, p_firstname);
            contentValues.put(DatabaseHelper.P_LN, p_lastname);
            contentValues.put(DatabaseHelper.P_EA, p_emailaddress);
            contentValues.put(DatabaseHelper.P_PWD, p_password);
            contentValues.put(DatabaseHelper.P_PNUM, p_phonenumber);
            contentValues.put(DatabaseHelper.P_ADDRESS, p_address);
            contentValues.put(DatabaseHelper.P_HCNUM, p_healthcardnum);
            contentValues.put(DatabaseHelper.REGISTRATION_STATUS, DatabaseHelper.DEFAULT_STATUS);
            database.insert(DatabaseHelper.DATABASE_TABLE_PATIENT, null, contentValues);
        } else {
            Toast.makeText(context, "Invalid input. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertDoctor(String d_firstname, String d_lastname,
                             String d_emailaddress, String d_password,
                             String d_phonenumber, String d_address,
                             String d_employeenum, String d_specialties) {
        if (database == null) {
            Log.e("DatabaseManager", "Database is not opened");
            Toast.makeText(context, "Database is not available", Toast.LENGTH_SHORT).show();
            return;}
        if (InputValidator.isDoctorInputValid(d_firstname, d_lastname, d_emailaddress, d_password,
                d_phonenumber, d_address, d_employeenum, d_specialties)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.D_FN, d_firstname);
            contentValues.put(DatabaseHelper.D_LN, d_lastname);
            contentValues.put(DatabaseHelper.D_EA, d_emailaddress);
            contentValues.put(DatabaseHelper.D_PWD, d_password);
            contentValues.put(DatabaseHelper.D_PNUM, d_phonenumber);
            contentValues.put(DatabaseHelper.D_ADDRESS, d_address);
            contentValues.put(DatabaseHelper.D_EMPLOYEE_NUM, d_employeenum);
            contentValues.put(DatabaseHelper.D_SPECIALTIES, d_specialties);
            contentValues.put(DatabaseHelper.REGISTRATION_STATUS, DatabaseHelper.DEFAULT_STATUS);
            database.insert(DatabaseHelper.DATABASE_TABLE_DOCTOR, null, contentValues);
        } else {
            Toast.makeText(context, "Invalid input. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor fetch() {
        String[] columns = new String[]{
                DatabaseHelper.P_FN, DatabaseHelper.P_LN, DatabaseHelper.P_EA,
                DatabaseHelper.P_PWD, DatabaseHelper.P_PNUM, DatabaseHelper.P_ADDRESS,
                DatabaseHelper.P_HCNUM};
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE_PATIENT, columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getdata() {
        String unionQuery = "SELECT 'Patient' AS user_type, " // Change made here
                + DatabaseHelper.P_FN + " AS first_name, " + DatabaseHelper.P_LN + " AS last_name, "
                + DatabaseHelper.REGISTRATION_STATUS
                + " FROM " + DatabaseHelper.DATABASE_TABLE_PATIENT
                + " UNION "
                + "SELECT 'Doctor', " // And here
                + DatabaseHelper.D_FN + " AS first_name, " + DatabaseHelper.D_LN + " AS last_name, "
                + DatabaseHelper.REGISTRATION_STATUS
                + " FROM " + DatabaseHelper.DATABASE_TABLE_DOCTOR;
        return database.rawQuery(unionQuery, null);
    }
    }


