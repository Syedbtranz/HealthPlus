package com.btranz.healthplus.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User_Sajid on 4/26/2016.
 */
public class DataBaseHandlerMFS extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HealthApp";
    // Contacts table name

    private static final String TABLE_MFS = "mfs";
    private static final String KEY_ID = "id";
    private static final String KEY_HISTORY = "history";
    private static final String KEY_SECONDARY = "secondary";
    private static final String KEY_AMBULATORY = "ambulatoryid";
    private static final String KEY_HEPARINLOCK = "Heparinlock";
    private static final String KEY_GAITTRANSFER = "gaittransfer";
    private static final String KEY_MENTAL = "mental";
    private static final String KEY_MFSRESULT = "mfsresult";
    private static final String KEY_MFS_PAT_ID = "pat_id";
    private static final String KEY_MFS_DATE = "mfs_date";



    private static final String TABLE_NEWS = "news";
    private static final String KEY_NEWSID = "id";
    private static final String KEY_RADIOGROUP1 = "radiogroup1";
    private static final String KEY_RADIOGROUP2 = "radiogroup2";
    private static final String KEY_HOSTORY = "hostory";
    private static final String KEY_RADIOGROUP3 = "radiogroup3";
    private static final String KEY_RADIOGROUP4 = "radiogroup4";
    private static final String KEY_RADIOGROUP5 = "radiogroup5";
    private static final String KEY_RADIOGROUP6 = "radiogroup6";
    private static final String KEY_NEWSRESULT = "newsresult";
    private static final String KEY_NEWS_PAT_ID = "pat_id";
    private static final String KEY_NEWS_DATE = "news_date";

    private static final String TABLE_ATRIA = "atria";
    private static final String KEY_ATRIAID = "id";
    private static final String KEY_ANEMIA= "anemiaa";
    private static final String KEY_SEVERE = "severerenaldisease";
    private static final String KEY_AGEYEARS= "ageyears";
    private static final String KEY_ANYPRIOR= "anyprior";
    private static final String KEY_HYPERTENSION= "Hypertensionhistory";
    private static final String KEY_ATRIARESULT= "atriaresult";
    private static final String KEY_ATRIA_PAT_ID = "pat_id";
    private static final String KEY_ATRIA_DATE = "atrria_date";

    private static final String TABLE_CAGE = "cage";
    private static final String KEYCAGE_ID = "id";
    private static final String KEY_QUE1= "que1";
    private static final String KEY_QUE2 = "que2";
    private static final String KEY_QUE3= "que3";
    private static final String KEY_QUE4= "que4";
    private static final String KEY_CAGERESULT= "cageresult";
    private static final String KEY_CAGE_PAT_ID = "pat_id";
    private static final String KEY_CAGE_DATE = "cage_date";


    private static final String TABLE_GCS = "gcs";
    private static final String KEY_GCSID = "id";
    private static final String KEY_RADIOGROUP11= "radiogroup11";
    private static final String KEY_RADIOGROUP22 = "radiogroup22";
    private static final String KEY_RADIOGROUP33 = "radiogroup33";
    private static final String KEY_GCSRESULT = "gcsresult";
    private static final String KEY_GCS_PAT_ID = "pat_id";
    private static final String KEY_GCS_DATE = "gcs_date";

    private static final String TABLE_BMI = "bmi";
    private static final String KEYBMI_ID = "id";
    private static final String KEY_EDITTEXT1= "weightedittext";
    private static final String KEY_EDITTEXT2 = "heightedittext";
    private static final String KEY_BMIRESULT = "bmiresult";
    private static final String KEY_BMI_PAT_ID = "pat_id";
    private static final String KEY_BMI_DATE = "bmi_date";

    private static final String TABLE_MAP = "map";
    private static final String KEYMAP_ID = "id";
    private static final String KEY_SYSTOLIC= "systolicedittext";
    private static final String KEY_DIASTOLIC = "diastolicedittext";
    private static final String KEY_MAPRESULT = "mapresult";
    private static final String KEY_MAP_PAT_ID = "pat_id";
    private static final String KEY_MAP_DATE = "map_date";

    private static final String TABLE_HEART = "heart";
    private static final String KEYH_ID = "id";
    private static final String KEY_HRATE= "heart_rate";
    private static final String KEY_SPO2 = "spo";
    private static final String KEY_HEART_PAT_ID = "pat_id";
    private static final String KEY_HEART_DATE = "heart_date";

    private static final String TABLE_OTHER = "other";
    private static final String KEYO_ID = "id";
    private static final String KEY_TEMP= "temp";
    private static final String KEY_RESP = "resp";
    private static final String KEY_OTHER_PAT_ID = "pat_id";
    private static final String KEY_OTHER_DATE = "other_date";


    public DataBaseHandlerMFS(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_MFS_TABLE = "CREATE TABLE " + TABLE_MFS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_HISTORY + " TEXT,"
                + KEY_SECONDARY + " TEXT,"
                + KEY_AMBULATORY + " TEXT,"
                + KEY_HEPARINLOCK + " TEXT,"
                + KEY_GAITTRANSFER + " TEXT,"
                + KEY_MENTAL + " TEXT,"
                + KEY_MFSRESULT + " TEXT,"
                + KEY_MFS_DATE + " TEXT,"
                + KEY_MFS_PAT_ID + " TEXT" + ");";

        String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_NEWS + "("
                + KEY_NEWSID + " INTEGER PRIMARY KEY,"
                + KEY_RADIOGROUP1 + " TEXT,"
                + KEY_RADIOGROUP2 + " TEXT,"
                + KEY_HOSTORY + " TEXT,"
                + KEY_RADIOGROUP3 + " TEXT,"
                + KEY_RADIOGROUP4 + " TEXT,"
                + KEY_RADIOGROUP5 + " TEXT,"
                + KEY_RADIOGROUP6 + " TEXT,"
                + KEY_NEWSRESULT + " TEXT,"
                + KEY_NEWS_DATE + " TEXT,"
                + KEY_NEWS_PAT_ID + " TEXT" + ");";

        String CREATE_ATRIA_TABLE = "CREATE TABLE " + TABLE_ATRIA + "("
                + KEY_ATRIAID + " INTEGER PRIMARY KEY,"
                + KEY_ANEMIA + " TEXT,"
                + KEY_SEVERE + " TEXT,"
                + KEY_AGEYEARS + " TEXT,"
                + KEY_ANYPRIOR + " TEXT,"
                + KEY_HYPERTENSION + " TEXT,"
                + KEY_ATRIARESULT + " TEXT,"
                + KEY_ATRIA_DATE + " TEXT,"
                + KEY_ATRIA_PAT_ID + " TEXT" + ");";

        String CREATE_CAGE_TABLE = "CREATE TABLE " + TABLE_CAGE + "("
                + KEYCAGE_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUE1 + " INTEGER,"
                + KEY_QUE2 + " INTEGER,"
                + KEY_QUE3 + " INTEGER,"
                + KEY_QUE4 + " INTEGER,"
                + KEY_CAGERESULT + " INTEGER,"
                + KEY_CAGE_DATE + " TEXT,"
                + KEY_CAGE_PAT_ID + " INTEGER" + ");";

        String CREATE_GCS_TABLE = "CREATE TABLE " + TABLE_GCS + "("
                + KEY_GCSID + " INTEGER PRIMARY KEY,"
                + KEY_RADIOGROUP11 + " TEXT,"
                + KEY_RADIOGROUP22 + " TEXT,"
                + KEY_RADIOGROUP33 + " TEXT,"
                + KEY_GCSRESULT + " TEXT,"
                + KEY_GCS_DATE + " TEXT,"
                + KEY_GCS_PAT_ID + " TEXT" + ");";

        String CREATE_BMI_TABLE = "CREATE TABLE " + TABLE_BMI + "("
                + KEYBMI_ID + " INTEGER PRIMARY KEY,"
                + KEY_EDITTEXT1 + " INTEGER,"
                + KEY_EDITTEXT2 + " INTEGER,"
                + KEY_BMIRESULT + " INTEGER,"
                + KEY_BMI_DATE + " TEXT,"
                + KEY_BMI_PAT_ID + " INTEGER" + ");";

        String CREATE_MAP_TABLE = "CREATE TABLE " + TABLE_MAP + "("
                + KEYMAP_ID + " INTEGER PRIMARY KEY,"
                + KEY_SYSTOLIC + " INTEGER,"
                + KEY_DIASTOLIC + " INTEGER,"
                + KEY_MAPRESULT + " INTEGER,"
                + KEY_MAP_DATE + " TEXT,"
                + KEY_MAP_PAT_ID + " INTEGER" + ");";

        String CREATE_HEART_TABLE = "CREATE TABLE " + TABLE_HEART + "("
                + KEYH_ID + " INTEGER PRIMARY KEY,"
                + KEY_HRATE + " INTEGER,"
                + KEY_SPO2 + " INTEGER,"
                + KEY_HEART_DATE + " TEXT,"
                + KEY_HEART_PAT_ID + " INTEGER" + ");";
        String CREATE_OTHER_TABLE = "CREATE TABLE " + TABLE_OTHER + "("
                + KEYO_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEMP + " INTEGER,"
                + KEY_RESP + " INTEGER,"
                + KEY_OTHER_DATE + " TEXT,"
                + KEY_OTHER_PAT_ID+ " INTEGER" + ");";

        sqLiteDatabase.execSQL(CREATE_MAP_TABLE);
        Log.i("MAPTable Created", CREATE_MAP_TABLE);

        sqLiteDatabase.execSQL(CREATE_BMI_TABLE);
        Log.i("BMITable Created",CREATE_BMI_TABLE);

        sqLiteDatabase.execSQL(CREATE_GCS_TABLE);
        Log.i("GCSTable Created",CREATE_GCS_TABLE);

        sqLiteDatabase.execSQL(CREATE_CAGE_TABLE);
        Log.i("CAGE table Created",CREATE_CAGE_TABLE);

        sqLiteDatabase.execSQL(CREATE_ATRIA_TABLE);
        Log.i("ATRIA table Created",CREATE_ATRIA_TABLE);

        sqLiteDatabase.execSQL(CREATE_NEWS_TABLE);
        Log.i("NEWSTable Created", CREATE_NEWS_TABLE);

        sqLiteDatabase.execSQL(CREATE_MFS_TABLE);
        Log.i("MFSTable Created", CREATE_MFS_TABLE);

        sqLiteDatabase.execSQL(CREATE_HEART_TABLE);
        Log.i("heart table Created",CREATE_HEART_TABLE);

        sqLiteDatabase.execSQL(CREATE_OTHER_TABLE);
        Log.i("OTHER table Created",CREATE_OTHER_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
// Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MFS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ATRIA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CAGE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GCS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BMI);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HEART);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_OTHER);

        onCreate(sqLiteDatabase);
    }

   //*************************mfs*************//

    public void insertmfs(String history, String secondary, String ambulatoryid, String Heparinlock, String gaittransfer,
                          String mental, String mfsresult,String pat_id,String date) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();


            values.put(KEY_HISTORY, history);
            values.put(KEY_SECONDARY, secondary);
            values.put(KEY_AMBULATORY, ambulatoryid);
            values.put(KEY_HEPARINLOCK, Heparinlock);
            values.put(KEY_GAITTRANSFER, gaittransfer);
            values.put(KEY_MENTAL, mental);
            values.put(KEY_MFSRESULT, mfsresult);
            values.put(KEY_MFS_PAT_ID, pat_id);
            values.put(KEY_MFS_DATE, date);


            sqLiteDatabase.insert(TABLE_MFS, null, values);
            System.out.println("insert mfs table" + values);

            sqLiteDatabase.close(); // Closing database connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getMfs(String id) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT * FROM mfs WHERE pat_id ="+id + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6) + "***" + cursor.getString(7) + "***" + cursor.getString(8));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }
    //**********************news*************************//
    public void insertnews(String radiogroup1, String radiogroup2, String hostory, String radiogroup3,
                           String radiogroup4, String radiogroup5, String radiogroup6, String newsresult,String pat_id,String date) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(KEY_RADIOGROUP1, radiogroup1);
            values.put(KEY_RADIOGROUP2, radiogroup2);
            values.put(KEY_HOSTORY, hostory);
            values.put(KEY_RADIOGROUP3, radiogroup3);
            values.put(KEY_RADIOGROUP4, radiogroup4);
            values.put(KEY_RADIOGROUP5, radiogroup5);
            values.put(KEY_RADIOGROUP6, radiogroup6);
            values.put(KEY_NEWSRESULT, newsresult);
            values.put(KEY_NEWS_PAT_ID, pat_id);
            values.put(KEY_NEWS_DATE, date);

            System.out.println("Inserting values are:" + values);

            sqLiteDatabase.insert(TABLE_NEWS, null, values);

            sqLiteDatabase.close(); // Closing database connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getNews(String id) {

        List<String> patients = new ArrayList<String>();
       // String selectQuery = "SELECT * FROM " + TABLE_NEWS + " WHERE pat_id =" + id;
        String selectQuery ="SELECT * FROM news WHERE pat_id ="+id + " ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6) + "***" + cursor.getString(7) + "***" + cursor.getString(8) + "***" + cursor.getString(9));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }

    //********************************atria****************//


    public void insertatria(String anemiaa, String severerenaldisease, String ageyears, String anyprior, String Hypertensionhistory, String result,String pat_id,String date) {

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
        values.put(KEY_ATRIARESULT, result);
        Log.i("result",result);
        values.put(KEY_ATRIA_PAT_ID, pat_id);
        values.put(KEY_ATRIA_DATE, date);

        sqLiteDatabase.insert(TABLE_ATRIA, null, values);
        sqLiteDatabase.close();
    }
    public List<String> getAtria(String id) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT * FROM atria WHERE pat_id ="+id + " ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6) + "***" + cursor.getString(7));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }
    //**********************************cage*************************//
    public void insertcage(String que1, String que2, String que3, String que4, String cageresult,String pat_id,String date){

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
        values.put(KEY_CAGERESULT, cageresult);
        Log.i("cageresult",cageresult);
        values.put(KEY_CAGE_PAT_ID, pat_id);
        Log.i("pat_id",pat_id);
        values.put(KEY_CAGE_DATE, date);

        sqLiteDatabase.insert(TABLE_CAGE , null, values);

        sqLiteDatabase.close(); // Closing database connection
    }
    public List<String> getCage(String id) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT * FROM cage WHERE pat_id ="+id + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }
    //**************************************gcs*******************//
    public void insertgcs(String radiogroup11, String radiogroup22, String radiogroup33, String gcsresult, String pat_id,String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            // values.put(KEY_HISTORY, history);
            values.put(KEY_RADIOGROUP11, radiogroup11);
            values.put(KEY_RADIOGROUP22, radiogroup22);
            values.put(KEY_RADIOGROUP33, radiogroup33);
          values.put(KEY_GCSRESULT, gcsresult);
            values.put(KEY_GCS_PAT_ID, pat_id);
            values.put(KEY_GCS_DATE, date);


            sqLiteDatabase.insert(TABLE_GCS, null, values);
            System.out.println("insert gcs table" + values);

            sqLiteDatabase.close(); // Closing database connection
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<String> getGcs(String id) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT * FROM gcs WHERE pat_id ="+id + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }
    //******************************bmi*******************//

    public void insertbmi(String weightvalue,String heightvalue, String bmiresult, String pat_id,String date){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EDITTEXT1, weightvalue);
        Log.i("weightvalue",weightvalue);
        values.put(KEY_EDITTEXT2, heightvalue);
        Log.i("heightvalue",heightvalue);
        values.put(KEY_BMIRESULT, bmiresult);
        Log.i("bmiresult",bmiresult);
        values.put(KEY_BMI_PAT_ID, pat_id);
        Log.i("pat_id",pat_id);
        values.put(KEY_BMI_DATE, date);
        sqLiteDatabase.insert(TABLE_BMI, null, values);

        sqLiteDatabase.close(); // Closing database connection
    }
    public List<String> getBmi(String id) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT * FROM bmi WHERE pat_id ="+id + " ORDER BY id DESC";
       // String selectQuery ="SELECT  * FROM bmi WHERE pat_id ="+id + " ORDER BY id DESC LIMIT 3";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4));
                System.out.println("**************BMI************"+cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        return patients;
    }
    //************************************map**************//
    public void insertmap(String systolic,String diastolic, String mapresult, String pat_id,String date){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SYSTOLIC, systolic);
        Log.i("systolicvalue",systolic);
        values.put(KEY_DIASTOLIC, diastolic);
        Log.i("diastolicvalue",diastolic);
        values.put(KEY_MAPRESULT, mapresult);
        Log.i("mapresult",mapresult);
        values.put(KEY_MAP_PAT_ID, pat_id);
        Log.i("pat_id",pat_id);
        values.put(KEY_MAP_DATE, date);
        sqLiteDatabase.insert(TABLE_MAP, null, values);

        sqLiteDatabase.close(); // Closing database connection
    }

    public List<String> getMap(String id) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT * FROM map WHERE pat_id ="+id + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4)+ "***" + cursor.getString(5));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }

    public void inserHeartRate(String hrate, String spo, String pat_id,String date) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_HRATE, hrate);
            values.put(KEY_SPO2, spo);
            values.put(KEY_HEART_PAT_ID, pat_id);
            values.put(KEY_HEART_DATE, date);

            sqLiteDatabase.insert(TABLE_HEART, null, values);
            System.out.println("insert mfs table" + values);

            sqLiteDatabase.close(); // Closing database connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<String> getHr(String id) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT * FROM heart WHERE pat_id ="+id + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }
    public void inserOther(String temp, String resp, String pat_id,String date) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(KEY_TEMP, temp);
            values.put(KEY_RESP, resp);
            values.put(KEY_OTHER_PAT_ID, pat_id);
            values.put(KEY_OTHER_DATE, date);

            sqLiteDatabase.insert(TABLE_OTHER, null, values);
            System.out.println("insert other table" + values);

            sqLiteDatabase.close(); // Closing database connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<String> getOther(String id) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT * FROM other WHERE pat_id ="+id + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }

    /**************************** PATIENT SEARCH **************/
    public List<String> patientSearch(String name) {

        List<String> patients = new ArrayList<String>();
        String selectQuery ="SELECT FROM table_name" +
                "WHERE column LIKE '%XXXX%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }
}


