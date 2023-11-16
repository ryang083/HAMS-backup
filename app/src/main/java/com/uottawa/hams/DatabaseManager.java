package com.uottawa.hams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLDataException;
public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context ctx) {
        context = ctx;
    }

    public DatabaseManager open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String p_firstname, String p_lastname,
                       String p_emailaddress, String p_password, String p_phonenumber, String p_address, String p_healthcardnum) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.P_FN, p_firstname);
        contentValues.put(DatabaseHelper.P_LN, p_lastname);
        contentValues.put(DatabaseHelper.P_EA, p_emailaddress);
        contentValues.put(DatabaseHelper.P_PWD, p_password);
        contentValues.put(DatabaseHelper.P_PNUM, p_phonenumber);
        contentValues.put(DatabaseHelper.P_ADDRESS, p_address);
        contentValues.put(DatabaseHelper.P_HCNUM, p_healthcardnum);
        database.insert(DatabaseHelper.DATABASE_TABLE, null, contentValues);
    }

    public Cursor fetch() {
        String[] columns = new String[]{
                DatabaseHelper.P_FN, DatabaseHelper.P_LN, DatabaseHelper.P_EA,
                DatabaseHelper.P_PWD, DatabaseHelper.P_PNUM, DatabaseHelper.P_ADDRESS,
                DatabaseHelper.P_HCNUM};
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE, columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
