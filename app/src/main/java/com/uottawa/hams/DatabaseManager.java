package com.uottawa.hams;

import static com.uottawa.hams.DatabaseHelper.DATABASE_TABLE_DOCTOR;
import static com.uottawa.hams.DatabaseHelper.DATABASE_TABLE_PATIENT;
import static com.uottawa.hams.DatabaseHelper.REGISTRATION_STATUS;
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
        dbHelper = new DatabaseHelper(context);
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
            contentValues.put(REGISTRATION_STATUS, DatabaseHelper.DEFAULT_STATUS);
            database.insert(DATABASE_TABLE_PATIENT, null, contentValues);
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

    public void insertAppointment(int patientId, String date, String startTime, String endTime) {
        if (database == null) {
            Log.e("DatabaseManager", "Database is not opened");
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.A_PATIENT_ID, patientId);
        contentValues.put(DatabaseHelper.A_DATE, date);
        contentValues.put(DatabaseHelper.A_START_TIME, startTime);
        contentValues.put(DatabaseHelper.A_END_TIME, endTime);
        contentValues.put(DatabaseHelper.A_STATUS, DatabaseHelper.DEFAULT_STATUS);
        database.insert(DatabaseHelper.DATABASE_TABLE_APPOINTMENT, null, contentValues);
    }

    // Fetch all appointments for a patient
    public Cursor getPatientAppointments(int patientId) {
        if (database == null) {
            Log.e("DatabaseManager", "Database is not opened");
            return null;
        }
        String[] columns = { DatabaseHelper.A_ID, DatabaseHelper.A_DATE, DatabaseHelper.A_START_TIME, DatabaseHelper.A_END_TIME, DatabaseHelper.A_STATUS };
        String selection = DatabaseHelper.A_PATIENT_ID + "=?";
        String[] selectionArgs = { String.valueOf(patientId) };
        return database.query(DatabaseHelper.DATABASE_TABLE_APPOINTMENT, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor getPatientById(int patientId) {
        if (database == null) {
            Log.e("DatabaseManager", "Database is not opened");
            return null;
        }

        String[] columns = new String[] {

                "id", DatabaseHelper.P_FN, DatabaseHelper.P_LN, DatabaseHelper.P_EA, DatabaseHelper.P_PWD,
                DatabaseHelper.P_PNUM, DatabaseHelper.P_ADDRESS, DatabaseHelper.P_HCNUM,
                DatabaseHelper.REGISTRATION_STATUS
        };

        String selection = "id=?";
        String[] selectionArgs = { String.valueOf(patientId) };

        return database.query(DatabaseHelper.DATABASE_TABLE_PATIENT, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor getDoctorById(int doctorId) {
        if (database == null) {
            Log.e("DatabaseManager", "Database is not opened");
            return null;
        }

        String[] columns = new String[] {
                "id", DatabaseHelper.D_FN, DatabaseHelper.D_LN, DatabaseHelper.D_EA, DatabaseHelper.D_PWD,
                DatabaseHelper.D_PNUM, DatabaseHelper.D_ADDRESS, DatabaseHelper.D_EMPLOYEE_NUM, DatabaseHelper.D_SPECIALTIES,
                DatabaseHelper.REGISTRATION_STATUS

        };

        String selection = "id=?";
        String[] selectionArgs = { String.valueOf(doctorId) };

        return database.query(DatabaseHelper.DATABASE_TABLE_DOCTOR, columns, selection, selectionArgs, null, null, null);
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

        String unionQuery = "SELECT 'Patient' AS user_type, id, "
                + DatabaseHelper.P_FN + " AS first_name, " + DatabaseHelper.P_LN + " AS last_name, "
                + DatabaseHelper.REGISTRATION_STATUS
                + " FROM " + DatabaseHelper.DATABASE_TABLE_PATIENT
                + " WHERE " + DatabaseHelper.REGISTRATION_STATUS + " = '" + DatabaseHelper.DEFAULT_STATUS + "'"
                + " UNION "
                + "SELECT 'Doctor', id, "
                + DatabaseHelper.D_FN + " AS first_name, " + DatabaseHelper.D_LN + " AS last_name, "
                + DatabaseHelper.REGISTRATION_STATUS
                + " FROM " + DatabaseHelper.DATABASE_TABLE_DOCTOR
                + " WHERE " + DatabaseHelper.REGISTRATION_STATUS + " = '" + DatabaseHelper.DEFAULT_STATUS + "'";
        return database.rawQuery(unionQuery, null);
    }

    public void updatePatientStatus(int patientId, String newStatus) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.REGISTRATION_STATUS, newStatus);
        String selection = "id=?";
        String[] selectionArgs = { String.valueOf(patientId) };
        database.update(DatabaseHelper.DATABASE_TABLE_PATIENT, contentValues, selection, selectionArgs);
    }

    public void updateDoctorStatus(int doctorId, String newStatus) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.REGISTRATION_STATUS, newStatus);
        String selection = "id=?";
        String[] selectionArgs = { String.valueOf(doctorId) };
        database.update(DatabaseHelper.DATABASE_TABLE_DOCTOR, contentValues, selection, selectionArgs);
    }

    public Cursor getRejectedData() {
        String unionQuery = "SELECT 'Patient' AS user_type, id, "
                + DatabaseHelper.P_FN + " AS first_name, " + DatabaseHelper.P_LN + " AS last_name, "
                + DatabaseHelper.REGISTRATION_STATUS
                + " FROM " + DatabaseHelper.DATABASE_TABLE_PATIENT
                + " WHERE " + DatabaseHelper.REGISTRATION_STATUS + " = 'REJECTED_STATUS'"
                + " UNION "
                + "SELECT 'Doctor', id, "
                + DatabaseHelper.D_FN + " AS first_name, " + DatabaseHelper.D_LN + " AS last_name, "
                + DatabaseHelper.REGISTRATION_STATUS
                + " FROM " + DatabaseHelper.DATABASE_TABLE_DOCTOR
                + " WHERE " + DatabaseHelper.REGISTRATION_STATUS + " = 'REJECTED_STATUS'";
        return database.rawQuery(unionQuery, null);
    }

    public boolean validateDoctor(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = { DatabaseHelper.D_EA, DatabaseHelper.D_PWD };
        String selection = DatabaseHelper.D_EA + " =? AND " + DatabaseHelper.D_PWD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(DatabaseHelper.DATABASE_TABLE_DOCTOR, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public boolean validatePatient(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = { DatabaseHelper.P_EA, DatabaseHelper.P_PWD };
        String selection = DatabaseHelper.P_EA + " =? AND " + DatabaseHelper.P_PWD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(DatabaseHelper.DATABASE_TABLE_PATIENT, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public String getDoctorRegistrationStatus(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = { DatabaseHelper.REGISTRATION_STATUS };
        String selection = DatabaseHelper.D_EA + " =?";
        String[] selectionArgs = { email };
        Cursor cursor = db.query(DatabaseHelper.DATABASE_TABLE_DOCTOR, columns, selection, selectionArgs, null, null, null);

        String status = null;
        if (cursor != null && cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.REGISTRATION_STATUS));
            cursor.close();
        }
        db.close();
        return status;
    }

    public String getPatientRegistrationStatus(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = { DatabaseHelper.REGISTRATION_STATUS };
        String selection = DatabaseHelper.P_EA + " =?";
        String[] selectionArgs = { email };
        Cursor cursor = db.query(DatabaseHelper.DATABASE_TABLE_PATIENT, columns, selection, selectionArgs, null, null, null);

        String status = null;
        if (cursor != null && cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.REGISTRATION_STATUS));
            cursor.close();
        }
        db.close();
        return status;
    }

    public int getPatientIdByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"id"};
        String selection = DatabaseHelper.P_EA + " =?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(DatabaseHelper.DATABASE_TABLE_PATIENT, columns, selection, selectionArgs, null, null, null);

        int id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
        }
        db.close();
        return id;
    }


    public Cursor getDoctorAppointments() {
        if (database == null) {
            Log.e("DatabaseManager", "Database is not opened");
            return null;
        }

        String query = "SELECT APPOINTMENT_INFO.a_id AS appointment_id, " +
                "PATIENT_INFO.p_firstname AS first_name, PATIENT_INFO.p_lastname AS last_name, " +
                "APPOINTMENT_INFO.a_date, APPOINTMENT_INFO.a_start_time, APPOINTMENT_INFO.a_end_time, APPOINTMENT_INFO.a_status " +
                "FROM APPOINTMENT_INFO INNER JOIN PATIENT_INFO ON APPOINTMENT_INFO.a_patient_id = PATIENT_INFO.id";
        return database.rawQuery(query, null);
    }

    public Cursor getAppointmentDetailsById(int appointmentId) {
        if (database == null) {
            Log.e("DatabaseManager", "Database is not opened");
            return null;
        }

        String query = "SELECT a.*, p." + DatabaseHelper.P_FN + ", p." + DatabaseHelper.P_LN + ", p." + DatabaseHelper.P_EA +
                ", p." + DatabaseHelper.P_PNUM + ", p." + DatabaseHelper.P_ADDRESS + ", p." + DatabaseHelper.P_HCNUM +
                " FROM " + DatabaseHelper.DATABASE_TABLE_APPOINTMENT + " a" +
                " JOIN " + DatabaseHelper.DATABASE_TABLE_PATIENT + " p" +
                " ON a." + DatabaseHelper.A_PATIENT_ID + " = p.id" +
                " WHERE a." + DatabaseHelper.A_ID + "=?";

        return database.rawQuery(query, new String[]{String.valueOf(appointmentId)});
    }






    }


