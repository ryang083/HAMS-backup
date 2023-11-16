package com.uottawa.hams;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "HAMS.DB";
    static final int DATABASE_VERSION =  1;

    static final String DATABASE_TABLE = "PATIENT_INFO";
    static final String P_FN = "p_firstname";
    static final String P_LN = "p_lastname";
    static final String P_EA = "p_emailaddress";
    static final String P_PWD = "p_password";
    static final String P_PNUM = "p_phonenumber";
    static final String P_ADDRESS = "p_address";
    static final String P_HCNUM = "p_healthcardnum";

    private static final String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE + " ("
            + P_FN + " TEXT NOT NULL, "
            + P_LN + " TEXT NOT NULL, "
            + P_EA + " TEXT NOT NULL, "
            + P_PWD + " TEXT NOT NULL, "
            + P_PNUM + " TEXT NOT NULL, "
            + P_ADDRESS + " TEXT NOT NULL, "
            + P_HCNUM + " TEXT NOT NULL);";

    public DatabaseHelper( Context context) {
        super(context,DATABASE_TABLE, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DATABASE_TAG", CREATE_DB_QUERY);
        db.execSQL(CREATE_DB_QUERY) ;
    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }


}