package com.btranz.healthplus.Handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User_Sajid on 5/2/2016.
 */
public class DataBaseHandlerEWS extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HealthApp";
    // Contacts table name
    private static final String TABLE_EWS = "ews";

    private static final String KEY_ID = "id";
    private static final String KEY_EWS="ewsrg";

    public DataBaseHandlerEWS(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_EWS_TABLE = "CREATE TABLE " + TABLE_EWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EWS + " INTEGER"
                + ")";

        sqLiteDatabase.execSQL(CREATE_EWS_TABLE);
        Log.i("BMIDatabase Created",CREATE_EWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
