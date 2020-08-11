package com.capstoneproject.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoreDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "scores.db";
    static final int DATABASE_VERSION = 1;

    public ScoreDBHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "create table " + ScoreContract.ScoreEntry.TABLE_NAME + " (" +
                ScoreContract.ScoreEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ScoreContract.ScoreEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ScoreContract.ScoreEntry.COLUMN_LEVEL + " TEXT NOT NULL, " +
                ScoreContract.ScoreEntry.COLUMN_SCORE + " INTEGER NOT NULL, " +
                ScoreContract.ScoreEntry.COLUMN_DATE + " TEXT NOT NULL" +
                ")";
        Log.d("TABLE", "creating table " + CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScoreContract.ScoreEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}

