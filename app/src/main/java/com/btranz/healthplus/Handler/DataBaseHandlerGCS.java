package com.btranz.healthplus.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User_Sajid on 5/2/2016.
 */
public class DataBaseHandlerGCS extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HealthApp";
    // Contacts table name
    private static final String TABLE_GCS = "gcs";
    // GCS Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_RADIOGROUP1= "radiogroup1";
    private static final String KEY_RADIOGROUP2 = "radiogroup2";
    private static final String KEY_RADIOGROUP3 = "radiogroup3";
    private static final String KEY_RADIOGROUP4 = "radiogroup4";
    private static final String KEY_RADIOGROUP5 = "radiogroup5";
    private static final String KEY_HISTORY="hostory";

    public DataBaseHandlerGCS(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GCS_TABLE = "CREATE TABLE " + TABLE_GCS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_RADIOGROUP1 + " TEXT,"
                + KEY_RADIOGROUP2 + " TEXT," + KEY_RADIOGROUP3 + " TEXT" + ");";

        sqLiteDatabase.execSQL(CREATE_GCS_TABLE);
        Log.i("GCSDatabase Created",CREATE_GCS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public void docReg(String radiogroup1, String radiogroup2, String radiogroup3) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
           // values.put(KEY_HISTORY, history);
            values.put(KEY_RADIOGROUP1, radiogroup1);
            values.put(KEY_RADIOGROUP2, radiogroup2);
            values.put(KEY_RADIOGROUP3, radiogroup3);
           /* values.put(KEY_GAITTRANSFER, gaittransfer);
            values.put(KEY_MENTAL, mental);*/


            sqLiteDatabase.insert(TABLE_GCS, null, values);
            System.out.println("insert gcs table" + values);

            sqLiteDatabase.close(); // Closing database connection
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
