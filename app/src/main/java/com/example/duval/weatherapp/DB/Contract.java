package com.example.duval.weatherapp.DB;

import android.provider.BaseColumns;

public final class Contract {
    public Contract() {
    }

    public static final class CityEntry implements BaseColumns{
        public static final String TABLE_NAME = "City";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGTITUDE = "longtitude";


    }
}
