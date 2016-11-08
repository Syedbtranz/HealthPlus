package com.btranz.healthplus.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class  DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "health_check";

    // Exhibitors table name
    private static final String TABLE_DOC_REG = "doc_registration";
    private static final String TABLE_PATIENT_REG = "patient_registration";
    private static final String TABLE_CONTACT = "contact";


    private static final String ID = "id";
    private static final String DOC_F_NAME = "f_name";
    private static final String DOC_L_NAME = "l_name";
    private static final String DOC_TITLE = "title";
    private static final String DOC_REG_NUMBER = "reg_number";
    private static final String DOC_SPECIALITY = "speciality";
    private static final String DOC_CONTACT = "contact";
    private static final String DOC_EMAIL = "email";
    private static final String DOC_HOSPITAL_ADDRESS = "hosp_address";

    private static final String P_ID = "id";
    private static final String PAT_F_NAME = "f_name";
    private static final String PAT_L_NAME = "l_name";
    private static final String PAT_HID = "h_id";
    private static final String PAT_ADDRESS = "address";
    private static final String PAT_AGE = "age";
    private static final String PAT_SEX = "sex";
    private static final String PAT_WARD = "ward";
    private static final String PAT_ROOM = "room";
    private static final String PAT_FALLS_HIS = "falls_his";
    private static final String PAT_PCP = "pcp";
    private static final String PAT_PHONE = "phone";
    private static final String PAT_EMAIL = "email";

    private static final String D_ID = "id";
    private static final String D_CALL = "call";
    private static final String D_MAIL = "mail";
    private static final String D_MESSAGE = "message";
    private static final String D_SKYPE = "skype";


    private static final String TABLE_NOTES = "tbl_notes";
    private static final String N_ID = "id";
    private static final String N_DATE = "date";
    private static final String N_PAT_ID = "pat_id";
    private static final String N_NOTE_TEXT = "note";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        String CREATE_DOC_REG_TABLE = "CREATE TABLE " + TABLE_DOC_REG + "("
                + ID + " INTEGER PRIMARY KEY," + DOC_F_NAME + " TEXT," + DOC_L_NAME + " TEXT," + DOC_TITLE + " TEXT," +
                DOC_REG_NUMBER + " TEXT," + DOC_SPECIALITY + " TEXT," + DOC_CONTACT + " TEXT," + DOC_EMAIL + " TEXT," + DOC_HOSPITAL_ADDRESS + " TEXT)";

        String CREATE_PAT_REG_TABLE = "CREATE TABLE " + TABLE_PATIENT_REG + "("
                + P_ID + " INTEGER PRIMARY KEY,"
                + PAT_F_NAME + " TEXT,"
                + PAT_L_NAME + " TEXT,"
                + PAT_HID + " TEXT,"
                + PAT_ADDRESS + " TEXT,"
                + PAT_AGE + " TEXT,"
                + PAT_SEX + " TEXT,"
                + PAT_WARD + " TEXT,"
                + PAT_ROOM + " TEXT,"
                + PAT_FALLS_HIS + " TEXT,"
                + PAT_PCP + " TEXT,"
                + PAT_PHONE + " TEXT,"
                + PAT_EMAIL + " TEXT)";

        String CREATE_DOC_CONTACT_TABLE = "CREATE TABLE " + TABLE_CONTACT + "("
                + D_ID + " INTEGER PRIMARY KEY," + D_CALL + " TEXT," + D_MAIL + " TEXT," + D_MESSAGE + " TEXT," +
                D_SKYPE + " TEXT)";

        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + N_ID + " INTEGER PRIMARY KEY,"
                + N_DATE + " TEXT,"
                + N_PAT_ID + " TEXT,"
                + N_NOTE_TEXT + " TEXT)";

        db.execSQL(CREATE_DOC_REG_TABLE);
        db.execSQL(CREATE_PAT_REG_TABLE);
        db.execSQL(CREATE_DOC_CONTACT_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOC_REG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT_REG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    //**************************** INTETRTING VALUES IN TO AIRPORT TABLE******************************//

    public void docReg(String f_name, String l_name, String title, String reg_num, String speciality, String contact, String email, String hos_address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DOC_F_NAME, f_name);
        values.put(DOC_L_NAME, l_name);
        values.put(DOC_TITLE, title);
        values.put(DOC_REG_NUMBER, reg_num);
        values.put(DOC_SPECIALITY, speciality);
        values.put(DOC_CONTACT, contact);
        values.put(DOC_EMAIL, email);
        values.put(DOC_HOSPITAL_ADDRESS, hos_address);


        // Inserting Row
        db.insert(TABLE_DOC_REG, null, values);
        db.close(); // Closing database connection
    }

    public void patReg(String f_name, String l_name, String hid, String address, String age, String room, String sex, String ward, String pcp, String falls_his, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PAT_F_NAME, f_name);
        values.put(PAT_L_NAME, l_name);
        values.put(PAT_HID, hid);
        values.put(PAT_ADDRESS, address);
        values.put(PAT_AGE, age);
        values.put(PAT_ROOM, room);
        values.put(PAT_SEX, sex);
        values.put(PAT_WARD, ward);
        values.put(PAT_PCP, pcp);
        values.put(PAT_FALLS_HIS, falls_his);
        values.put(PAT_PHONE, phone);
        values.put(PAT_EMAIL, email);


        // Inserting Row
        db.insert(TABLE_PATIENT_REG, null, values);
        db.close(); // Closing database connection
    }

    public void update_contactList(int id, String call, String mail,String message,String skype){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(D_CALL, call);
        values.put(D_MAIL, mail);
        values.put(D_MESSAGE, message);
        values.put(D_SKYPE, skype);
        db.update(TABLE_CONTACT, values, D_ID+"="+id, null);
    }


    public List<String> contactList() {

        List<String> flights = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                flights.add(cursor.getString(0) + "***" +cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4));

            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return flights;

    }

    public void contactInsert(String mobile, String mail, String message, String skype) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(D_CALL, mobile);
        values.put(D_MAIL, mail);
        values.put(D_MESSAGE, message);
        values.put(D_SKYPE, skype);
        database.insert(TABLE_CONTACT, null, values);
        database.close();

    }

    public List<String> docLogin(String reg_num) {

        List<String> docs = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_DOC_REG + " WHERE reg_number =" + reg_num;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                docs.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return docs;

    }

    public List<String> patExits(String reg_num) {

        List<String> patients = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_PATIENT_REG + " WHERE id =" + reg_num;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                patients.add(cursor.getString(0) + "***" + cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6) + "***" + cursor.getString(7) + "***" + cursor.getString(8) + "***" + cursor.getString(9)+ "***" + cursor.getString(10)+ "***" + cursor.getString(11)+ "***" + cursor.getString(12));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();


        return patients;

    }

    public List<String> patList() {

        List<String> flights = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_PATIENT_REG + " ORDER BY id ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                flights.add(cursor.getString(0) + "***" +cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6) + "***" + cursor.getString(7) + "***" + cursor.getString(8) + "***" + cursor.getString(9) + "***" + cursor.getString(10));

            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return flights;

    }


    public List<String> patSearch(String fname) {

        List<String> patList = new ArrayList<String>();
        String selectQuery = "select * from "+TABLE_PATIENT_REG+" where "+PAT_F_NAME+" like '%"+fname+"%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                patList.add(cursor.getString(0) + "***" +cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6) + "***" + cursor.getString(7) + "***" + cursor.getString(8) + "***" + cursor.getString(9) + "***" + cursor.getString(10));
                System.out.println(cursor.getString(0) + "***" +cursor.getString(1) + "***" + cursor.getString(2) + "***" + cursor.getString(3) + "***" + cursor.getString(4) + "***" + cursor.getString(5) + "***" + cursor.getString(6) + "***" + cursor.getString(7) + "***" + cursor.getString(8) + "***" + cursor.getString(9) + "***" + cursor.getString(10));

            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return patList;

    }

    public void noteInsert(String date, String note,String pat_id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(N_DATE, date);
        values.put(N_NOTE_TEXT, note);
        values.put(N_PAT_ID, pat_id);

        database.insert(TABLE_NOTES, null, values);
        database.close();

    }

    public List<String> noteList(String id) {

        List<String> notes = new ArrayList<String>();
        String selectQuery ="SELECT * FROM " + TABLE_NOTES + "  WHERE pat_id ="+id + " ORDER BY id DESC";
        //String selectQuery = "SELECT * FROM " + TABLE_NOTES + " ORDER BY id ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                notes.add(cursor.getString(0) + "***" +cursor.getString(1) + "***" + cursor.getString(2)+ "***" + cursor.getString(3));

            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return notes;

    }
    public List<String> noteSearch(String text) {

        List<String> noteList = new ArrayList<String>();
        String selectQuery = "select * from "+TABLE_NOTES+" where "+N_NOTE_TEXT+" like '%"+text+"%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                noteList.add(cursor.getString(0) + "***" +cursor.getString(1) + "***" + cursor.getString(2) );
                System.out.println(cursor.getString(0) + "***" +cursor.getString(1) + "***" + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return noteList;

    }

}
