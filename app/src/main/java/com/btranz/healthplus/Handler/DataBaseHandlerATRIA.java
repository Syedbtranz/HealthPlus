package com.btranz.healthplus.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User_Sajid on 5/2/2016.
 */
public class DataBaseHandlerATRIA extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HealthApp";
    // Contacts table name
    private static final String TABLE_ATRIA = "atria";
    // ATRIA Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ANEMIA= "anemiaa";
    private static final String KEY_SEVERE = "severerenaldisease";
    private static final String KEY_AGEYEARS= "ageyears";
    private static final String KEY_ANYPRIOR= "anyprior";
    private static final String KEY_HYPERTENSION= "Hypertensionhistory";


    public DataBaseHandlerATRIA(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_ATRIA_TABLE = "CREATE TABLE " + TABLE_ATRIA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ANEMIA + " TEXT,"
                + KEY_SEVERE + " TEXT," + KEY_AGEYEARS + " TEXT," +KEY_ANYPRIOR + " TEXT," + KEY_HYPERTENSION + " TEXT" + ");";

        sqLiteDatabase.execSQL(CREATE_ATRIA_TABLE);
        Log.i("ATRIA table Created",CREATE_ATRIA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ATRIA);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void insert(String anemiaa, String severerenaldisease, String ageyears, String anyprior, String Hypertensionhistory) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ANEMIA, anemiaa);
        Log.i("anemiaa",anemiaa);
        values.put(KEY_SEVERE, severerenaldisease);
        Log.i("severerenaldisease",severerenaldisease);
        values.put(KEY_AGEYEARS, ageyears);
        Log.i("ageyears",ageyears);
        values.put(KEY_ANYPRIOR, anyprior);
        Log.i("anyprior",anyprior);
        values.put(KEY_HYPERTENSION, Hypertensionhistory);
        Log.i("Hypertensionhistory",Hypertensionhistory);


        sqLiteDatabase.insert(TABLE_ATRIA, null, values);

        sqLiteDatabase.close(); // Closing database connection
    }

}
