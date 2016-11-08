package com.btranz.healthplus.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User_Sajid on 5/2/2016.
 */
public class DataBaseHandlerBMI extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HealthApp";
    // Contacts table name
    private static final String TABLE_BMI = "bmi";
    // BMI Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EDITTEXT1= "weightedittext";
    private static final String KEY_EDITTEXT2 = "heightedittext";

    public DataBaseHandlerBMI(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_BMI_TABLE = "CREATE TABLE " + TABLE_BMI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EDITTEXT1 + " INTEGER,"
                + KEY_EDITTEXT2 + " INTEGER" + ");";

        sqLiteDatabase.execSQL(CREATE_BMI_TABLE);
        Log.i("BMIDatabase Created",CREATE_BMI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BMI);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void insert(String weightvalue,String heightvalue){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EDITTEXT1, weightvalue);
        Log.i("weightvalue",weightvalue);
        values.put(KEY_EDITTEXT2, heightvalue);
        Log.i("heightvalue",heightvalue);

        sqLiteDatabase.insert(TABLE_BMI, null, values);

        sqLiteDatabase.close(); // Closing database connection
    }
}
