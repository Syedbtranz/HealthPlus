package com.btranz.healthplus.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 6/2/2016.
 */
public class ValuesStoreMAP extends AppCompatActivity{

    private static final String SELECT_SQL = "SELECT * FROM map";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;

    String weightvalue,heightvalue,finalresult;
    TextView tv1,tv2,tv3,tv4,mmhg;
    float fresult;
    int FRESULT;

    TextView sys,dia,Finalresult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuesstoremap);

        openDatabase();

        sys=(TextView)findViewById(R.id.systolic);
        dia=(TextView)findViewById(R.id.diastolic);

        Finalresult=(TextView)findViewById(R.id.fscore);
        tv4=(TextView)findViewById(R.id.message);
        mmhg=(TextView)findViewById(R.id.mmhg);

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
        String systolvalue = c.getString(1);
        Log.i("systolvalue", systolvalue);
        String diastolvalue = c.getString(2);
        Log.i("diastolvalue", diastolvalue);
        String mapresult = c.getString(3);
        Log.i("mapresult", mapresult);

        sys.setText(systolvalue);
        dia.setText(diastolvalue);

        FRESULT = Integer.parseInt(mapresult);
        String mapresult1 = String.valueOf(FRESULT);
        if (FRESULT >= 60) {
            Finalresult.setTextColor(getResources().getColor(R.color.colorgreen));
            Finalresult.setText(mapresult1);
            tv4.setTextColor(getResources().getColor(R.color.colorgreen));
            tv4.setText("A MAP ≥ 60 mmHg is believed to be needed to maintain adequate tissue perfusion.");
            mmhg.setTextColor(getResources().getColor(R.color.colorgreen));
            mmhg.setText("mm Hg");
        }if(FRESULT>= 65){
            Finalresult.setTextColor(getResources().getColor(R.color.coloryellow));
            Finalresult.setText(mapresult1);
            tv4.setTextColor(getResources().getColor(R.color.coloryellow));
            tv4.setText("A MAP ≥ 65 mmHg is recommended in patients with severe sepsis and septic shock by the Surviving Sepsis Campaign Guidelines Committee.");
            mmhg.setTextColor(getResources().getColor(R.color.coloryellow));
            mmhg.setText("mm Hg");
        }else if(FRESULT<65){
            Finalresult.setTextColor(getResources().getColor(R.color.colorred));
            Finalresult.setText(mapresult1);
            tv4.setTextColor(getResources().getColor(R.color.colorred));
            tv4.setText("");
            mmhg.setTextColor(getResources().getColor(R.color.colorred));
            mmhg.setText("mm Hg");

        }
    }

    protected void moveNext() {
        if (!c.isLast())
            c.moveToNext();
        showRecords();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent in = new Intent(ValuesStoreMAP.this, FormsActivity.class);
            startActivity(in);
            finish();
            return true;
        }
        if (id == R.id.add_patient) {
            Intent in = new Intent(ValuesStoreMAP.this, AddPatientActivity.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(ValuesStoreMAP.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent in = new Intent(ValuesStoreMAP.this, FormsActivity.class);
        startActivity(in);
        finish();
    }
}
