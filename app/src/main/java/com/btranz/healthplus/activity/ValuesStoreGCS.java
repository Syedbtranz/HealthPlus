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
 * Created by User_Sajid on 5/24/2016.
 */
public class ValuesStoreGCS extends AppCompatActivity {

    TextView aaa,bbb,ccc,result;
    String tv1,tv2,tv3,fresult;
    private static final String SELECT_SQL = "SELECT * FROM gcs";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuesstoregcs);
        openDatabase();


        aaa=(TextView)findViewById(R.id.aaa);
        bbb=(TextView)findViewById(R.id.bbb);
        ccc=(TextView)findViewById(R.id.ccc);
        result=(TextView)findViewById(R.id.fresult);

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
        String tv1 = c.getString(1);
        Log.i("tv1",tv1);
        String tv2 = c.getString(2);
        Log.i("tv2",tv2 );
        String tv3 = c.getString(3);
        Log.i("tv3",tv3);
        String gcsresult = c.getString(4);
        Log.i("gcsresult",gcsresult);
        String gcsresult1=String.valueOf(gcsresult);

        aaa.setText(tv1);
        bbb.setText(tv2);
        ccc.setText(tv3);

        result.setText(gcsresult1);
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
            Intent in = new Intent(ValuesStoreGCS.this, FormsActivity.class);
            startActivity(in);
            finish();
            return true;
        }
        if (id == R.id.add_patient) {
            Intent in = new Intent(ValuesStoreGCS.this, AddPatientActivity.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(ValuesStoreGCS.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent in = new Intent(ValuesStoreGCS.this, FormsActivity.class);
        startActivity(in);
        finish();
    }

}
