package com.btranz.healthplus.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User_Sajid on 5/2/2016.
 */
public class DataBaseHandlerCAGE extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HealthApp";
    // Contacts table name
    private static final String TABLE_CAGE = "cage";
    // CAGE Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUE1= "que1";
    private static final String KEY_QUE2 = "que2";
    private static final String KEY_QUE3= "que3";
    private static final String KEY_QUE4= "que4";

    public DataBaseHandlerCAGE(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CAGE_TABLE = "CREATE TABLE " + TABLE_CAGE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_QUE1 + " INTEGER,"
                + KEY_QUE2 + " INTEGER," + KEY_QUE3 + " INTEGER," + KEY_QUE4 + " INTEGER" + ");";

        sqLiteDatabase.execSQL(CREATE_CAGE_TABLE);
        Log.i("CAGE table Created",CREATE_CAGE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CAGE);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void insert(String que1,String que2,String que3,String que4){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_QUE1, que1);
        Log.i("que1",que1);
        values.put(KEY_QUE2, que2);
        Log.i("que2",que2);
        values.put(KEY_QUE3, que3);
        Log.i("que3",que3);
        values.put(KEY_QUE4, que4);
        Log.i("que4",que4);

        sqLiteDatabase.insert(TABLE_CAGE , null, values);

        sqLiteDatabase.close(); // Closing database connection
    }

}
