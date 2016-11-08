package com.btranz.healthplus.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 5/30/2016.
 */


public class ViewResult extends AppCompatActivity {

    TextView ews,mfs,atria,bmi,cage,dash,gcs;

   /* private static final String SELECT_SQL = "SELECT * FROM cage";
    private static final String SELECT_SQL1 = "SELECT * FROM mfs";*/
    private static final String SELECT_SQL = "SELECT * FROM news Where;";
   /* private static final String SELECT_SQL3 = "SELECT * FROM gcs";
    private static final String SELECT_SQL4 = "SELECT * FROM bmi";
    private static final String SELECT_SQL5 = "SELECT * FROM atria";*/
    int fresult;
    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewresults);
        //openDatabase();

        ews=(TextView)findViewById(R.id.ewsresult);
        mfs=(TextView)findViewById(R.id.mfsresult);
        atria=(TextView)findViewById(R.id.atriaresult);
        bmi=(TextView)findViewById(R.id.bmiresult);
        cage=(TextView)findViewById(R.id.cageresult);
        dash=(TextView)findViewById(R.id.dashresult);
        gcs=(TextView)findViewById(R.id.gcsresult);

       /* c = sqLiteDatabase.rawQuery(SELECT_SQL, null);
        c.moveToLast();


        //cursor.moveToLast();
        showRecords();*/
/*
        c = sqLiteDatabase.rawQuery(SELECT_SQL1, null);
        c.moveToLast();
        moveNext();
        showRecords1();

        c = sqLiteDatabase.rawQuery(SELECT_SQL, null);
        c.moveToLast();
        moveNext();
        showRecords2();

        c = sqLiteDatabase.rawQuery(SELECT_SQL3, null);
        c.moveToLast();
        moveNext();
        showRecords3();

        c = sqLiteDatabase.rawQuery(SELECT_SQL4, null);
        c.moveToLast();
        moveNext();
        showRecords4();

        c = sqLiteDatabase.rawQuery(SELECT_SQL5, null);
        c.moveToLast();
        moveNext();
        showRecords5();*/
    }
    protected void openDatabase() {
        sqLiteDatabase = openOrCreateDatabase("HealthApp", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {
        String respiratory = c.getString(1);
        Log.i("respiratory", respiratory);
        String oxygen = c.getString(2);
        Log.i("oxygen", oxygen);
        String supplementaloxygen = c.getString(3);
        Log.i("supplementaloxygen", supplementaloxygen);
        String systolicbp = c.getString(4);
        Log.i("systolicbp", systolicbp);
        String temp = c.getString(5);
        Log.i("temp", temp);
        String heartrate = c.getString(6);
        Log.i("heartrate", heartrate);
        String avpuscore = c.getString(7);
        Log.i("avpuscore", avpuscore);
        String ewsresult = c.getString(8);
        Log.i("ewsresult",ewsresult);

        fresult = Integer.parseInt(ewsresult);
        if (fresult < 4 && fresult > 1) {
            ews.setTextColor(getResources().getColor(R.color.colorgreen));
            ews.setText(ewsresult);
           /* iii.setTextColor(getResources().getColor(R.color.colorgreen));
            iii.setText("Assessment by a competent registered nurse who should decide if a change to frequency of clinical monitoring or an escalation of clinical care is required.");*/
        } else if (fresult <= 6) {
            ews.setTextColor(getResources().getColor(R.color.coloryellow));
            ews.setText(ewsresult);
           /* iii.setTextColor(getResources().getColor(R.color.coloryellow));
            iii.setText("An urgent review by a clinician skilled with competencies in the assessment of acute illness – usually a ward-based doctor or acute team nurse, who should consider whether escalation of care to a team with critical-care skills is required (ie critical care outreach team).");*/
        } else if (fresult >= 7) {
            ews.setTextColor(getResources().getColor(R.color.colorred));
            ews.setText(ewsresult);
/*            iii.setTextColor(getResources().getColor(R.color.colorred));
            iii.setText("Emergency assessment by a clinical team/critical care outreach team with critical-care competencies and usually transfer of the patient to a higher dependency care area.");*/

        }
    }
   /* protected void showRecords1() {
        String mfsresult = c.getString(7);
        Log.i("mfsresult",mfsresult);

        mfs.setText(mfsresult);
    }
    protected void showRecords2() {
        String cageresult = c.getString(5);
        Log.i("cageresult",cageresult);

        cage.setText(cageresult);
    }
    protected void showRecords3() {
        String gcsresult = c.getString(4);
        Log.i("gcsresult ",gcsresult );

        gcs.setText(gcsresult );
    }
    protected void showRecords4() {
        String bmiresult = c.getString(3);
        Log.i("bmiresult ",bmiresult);

        bmi.setText(bmiresult);
    }
    protected void showRecords5() {
        String atriaresult = c.getString(3);
        Log.i("atriaresult",atriaresult);

        atria.setText(atriaresult);
    }*/


    protected void moveNext() {
        if (!c.isLast())
            c.moveToNext();
        showRecords();
    }
    protected void movePrev() {
        if (!c.isFirst())
            c.moveToPrevious();

        showRecords();
    }
}
