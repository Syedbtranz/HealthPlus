package com.btranz.healthplus.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.btranz.healthplus.R;


/**
 * Created by User_Sajid on 5/17/2016.
 */
public class ValuesStoreNEWS extends AppCompatActivity {

    String tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
    TextView aaa, bbb, ccc, ddd, eee, fff, ggg, hhh, iii;
    int fresult;
    private static final String SELECT_SQL = "SELECT * FROM news";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuesstorenews);
        openDatabase();

        aaa = (TextView) findViewById(R.id.respiratory);
        bbb = (TextView) findViewById(R.id.oxygen);
        ccc = (TextView) findViewById(R.id.supplementaloxygen);
        ddd = (TextView) findViewById(R.id.systolicbp);
        eee = (TextView) findViewById(R.id.temp);
        fff = (TextView) findViewById(R.id.heartrate);
        ggg = (TextView) findViewById(R.id.avpuscore);
        hhh = (TextView) findViewById(R.id.fscore);
        iii = (TextView) findViewById(R.id.message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);


        c = sqLiteDatabase.rawQuery(SELECT_SQL, null);
        c.moveToLast();

        moveNext();
        showRecords();


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
        String fscore = c.getString(8);
        Log.i("fscore", fscore);
        /*  String message = c.getString(6);
        Log.i("message", message);*/


        aaa.setText(respiratory);
        bbb.setText(oxygen);
        ccc.setText(supplementaloxygen);
        ddd.setText(systolicbp);
        eee.setText(temp);
        fff.setText(heartrate);
        ggg.setText(avpuscore);



        fresult = Integer.parseInt(fscore);
        if (fresult < 4 && fresult > 1) {
            hhh.setTextColor(getResources().getColor(R.color.colorgreen));
            hhh.setText(fscore);
            ;
            iii.setTextColor(getResources().getColor(R.color.colorgreen));
            iii.setText("Assessment by a competent registered nurse who should decide if a change to frequency of clinical monitoring or an escalation of clinical care is required.");
        } else if (fresult <= 6) {
            hhh.setTextColor(getResources().getColor(R.color.coloryellow));
            hhh.setText(fscore);
            iii.setTextColor(getResources().getColor(R.color.coloryellow));
            iii.setText("An urgent review by a clinician skilled with competencies in the assessment of acute illness – usually a ward-based doctor or acute team nurse, who should consider whether escalation of care to a team with critical-care skills is required (ie critical care outreach team).");
        } else if (fresult >= 7) {
            hhh.setTextColor(getResources().getColor(R.color.colorred));
            hhh.setText(fscore);
            iii.setTextColor(getResources().getColor(R.color.colorred));
            iii.setText("Emergency assessment by a clinical team/critical care outreach team with critical-care competencies and usually transfer of the patient to a higher dependency care area.");

        }
    }

    protected void moveNext() {
        if (!c.isLast())
            c.moveToNext();

        showRecords();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent in = new Intent(ValuesStoreNEWS.this, FormsActivity.class);
            startActivity(in);
            finish();
            return true;
        }
        if (id == R.id.patient_notes) {
            Intent in = new Intent(ValuesStoreNEWS.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(ValuesStoreNEWS.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent in = new Intent(ValuesStoreNEWS.this, FormsActivity.class);
        startActivity(in);
        finish();
    }

}
