package com.example.android.waitlist.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.waitlist.data.WaitlistContract.*;

public class WaitlistDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "cinema.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public WaitlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_DROP_IF_EXIST = "DROP TABLE IF EXISTS "+WaitlistEntry.TABLE_NAME+";";
        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + WaitlistEntry.TABLE_NAME + " (" +
                WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WaitlistEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                WaitlistEntry.COLUMN_SCENARIO + " INTEGER NOT NULL, " +
                WaitlistEntry.COLUMN_REALISATION + " INTEGER NOT NULL," +
                WaitlistEntry.COLUMN_MUSIQUE + " INTEGER NOT NULL," +
                WaitlistEntry.COLUMN_DESCRIPTION + " INTEGER NOT NULL," +
                WaitlistEntry.COLUMN_HORAIRE + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_DROP_IF_EXIST);
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WaitlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}