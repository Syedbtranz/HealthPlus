package com.btranz.healthplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User_Sajid on 4/26/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HEARTRATE";
    // Contacts table name
    private static final String TABLE_HEARTMONITOR = "heartmonitor";

    private static final String KEY_ID="id";
    private static final String KEY_MPR="mpr";
    private static final String KEY_MSPO="mspo";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HEARTMONITOR_TABLE = "CREATE TABLE " + TABLE_HEARTMONITOR + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MPR + " INTEGER,"
                + KEY_MSPO + " INTEGER" + ")";
        db.execSQL(CREATE_HEARTMONITOR_TABLE);
        Log.i("Database Created",CREATE_HEARTMONITOR_TABLE);

        if(CREATE_HEARTMONITOR_TABLE!=null){
            System.out.println("Db Created"+ CREATE_HEARTMONITOR_TABLE);
        }else
        {
            System.out.println("Database not created");

        }
       // insertNewData(KEY_MPR,KEY_MSPO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HEARTMONITOR);
    }

    public final void insertNewData(String mpr, String mspo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataRowValues = new ContentValues();

        dataRowValues.put(KEY_MPR,mpr);
        dataRowValues.put(KEY_MSPO,mspo);

        db.insert(TABLE_HEARTMONITOR,null,dataRowValues);

        if(dataRowValues!=null){
            System.out.println("Data Successfully stored in sqlite Database");
        }
        else {
            System.out.println("Not inserted in Sqlite Database");
        }
        db.close();
    }
  }
