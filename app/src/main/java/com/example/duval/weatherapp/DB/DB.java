package com.example.duval.weatherapp.DB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    public static final String LOG_TAG = DB.class.getSimpleName();
    private static final String DATABASE_NAME = "city.db";
    private static final int DATABASE_VERSION = 1;

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CITY_TABLE = "CREATE_TABLE" + Contract.CityEntry.TABLE_NAME + "( "
                + Contract.CityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.CityEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + Contract.CityEntry.COLUMN_LATITUDE + " INTEGER NOT NULL, "
                + Contract.CityEntry.COLUMN_LONGTITUDE + "INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_CITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
