package com.btranz.healthplus.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User_Sajid on 5/2/2016.
 */
public class DataBaseHandlerNEWS extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HealthApp";
    // Contacts table name
    private static final String TABLE_NEWS = "news";
    // NEWS Table Columns names
    private static final String KEY_ID = "id";

    private static final String KEY_RADIOGROUP1= "radiogroup1";
    private static final String KEY_RADIOGROUP2 = "radiogroup2";
    private static final String KEY_HISTORY="history";
    private static final String KEY_RADIOGROUP3 = "radiogroup3";
    private static final String KEY_RADIOGROUP4 = "radiogroup4";
    private static final String KEY_RADIOGROUP5 = "radiogroup5";
    private static final String KEY_RADIOGROUP6 = "radiogroup6";

    /*private static final String KEY_WEAK="weak";
    private static final String KEY_IMPAIRED="impaired";
    private static final String KEY_ORIENTED="oriented";
    private static final String KEY_FORGETS="forgets";*/

    public DataBaseHandlerNEWS(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_NEWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_RADIOGROUP1 + " TEXT,"
                + KEY_RADIOGROUP2 + " TEXT," + KEY_HISTORY + " TEXT," + KEY_RADIOGROUP3 + " TEXT," + KEY_RADIOGROUP4 + " TEXT," + KEY_RADIOGROUP5+ " TEXT," + KEY_RADIOGROUP6+ " TEXT" + ");";

        sqLiteDatabase.execSQL(CREATE_NEWS_TABLE);
        Log.i("NEWSTable Created",CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
// Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void insertnews(String radiogroup1, String radiogroup2, String history, String radiogroup3,
                           String radiogroup4, String radiogroup5, String radiogroup6){
        try {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

    values.put(KEY_RADIOGROUP1, radiogroup1);
    values.put(KEY_RADIOGROUP2, radiogroup2);
    values.put(KEY_HISTORY, history);
    values.put(KEY_RADIOGROUP3, radiogroup3);
    values.put(KEY_RADIOGROUP4, radiogroup4);
    values.put(KEY_RADIOGROUP5, radiogroup5);
    values.put(KEY_RADIOGROUP6, radiogroup6);

    System.out.println("Inserting values are:" + values);

    sqLiteDatabase.insert(TABLE_NEWS, null, values);

    sqLiteDatabase.close(); // Closing database connection
}catch (Exception e){
    e.printStackTrace();
}
}
}
