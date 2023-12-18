package com.uottawa.hams;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// DatabaseHelper.java
public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "HAMS.DB";
    static final int DATABASE_VERSION = 2;

    // Table for patient registration
    static final String DATABASE_TABLE_PATIENT = "PATIENT_INFO";
    static final String P_FN = "p_firstname";
    static final String P_LN = "p_lastname";
    static final String P_EA = "p_emailaddress";
    static final String P_PWD = "p_password";
    static final String P_PNUM = "p_phonenumber";
    static final String P_ADDRESS = "p_address";
    static final String P_HCNUM = "p_healthcardnum";

    // Table for doctor registration
    static final String DATABASE_TABLE_DOCTOR = "DOCTOR_INFO";
    static final String D_FN = "d_firstname";
    static final String D_LN = "d_lastname";
    static final String D_EA = "d_emailaddress";
    static final String D_PWD = "d_password";
    static final String D_PNUM = "d_phonenumber";
    static final String D_ADDRESS = "d_address";
    static final String D_EMPLOYEE_NUM = "d_employeenum";
    static final String D_SPECIALTIES = "d_specialties";

    // Column for registration status
    static final String REGISTRATION_STATUS = "registration_status";
    static final String DEFAULT_STATUS = "NotProcessedYet";

    private static final String CREATE_PATIENT_TABLE_QUERY = "CREATE TABLE " + DATABASE_TABLE_PATIENT + " ("
            + P_FN + " TEXT NOT NULL, "
            + P_LN + " TEXT NOT NULL, "
            + P_EA + " TEXT NOT NULL, "
            + P_PWD + " TEXT NOT NULL, "
            + P_PNUM + " TEXT NOT NULL, "
            + P_ADDRESS + " TEXT NOT NULL, "
            + P_HCNUM + " TEXT NOT NULL, "
            + REGISTRATION_STATUS + " TEXT NOT NULL DEFAULT '" + DEFAULT_STATUS + "');";

    private static final String CREATE_DOCTOR_TABLE_QUERY = "CREATE TABLE " + DATABASE_TABLE_DOCTOR + " ("
            + D_FN + " TEXT NOT NULL, "
            + D_LN + " TEXT NOT NULL, "
            + D_EA + " TEXT NOT NULL, "
            + D_PWD + " TEXT NOT NULL, "
            + D_PNUM + " TEXT NOT NULL, "
            + D_ADDRESS + " TEXT NOT NULL, "
            + D_EMPLOYEE_NUM + " TEXT NOT NULL, "
            + D_SPECIALTIES + " TEXT NOT NULL, "
            + REGISTRATION_STATUS + " TEXT NOT NULL DEFAULT '" + DEFAULT_STATUS + "');";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PATIENT_TABLE_QUERY);
        db.execSQL(CREATE_DOCTOR_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_DOCTOR);
        onCreate(db);
    }
}
