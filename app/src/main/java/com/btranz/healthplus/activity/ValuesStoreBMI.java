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
 * Created by User_Sajid on 5/16/2016.
 */
public class ValuesStoreBMI extends AppCompatActivity {

   String weightvalue,heightvalue,finalresult;
    TextView tv1,tv2,tv3,tv4;
    float fresult;

    private static final String SELECT_SQL = "SELECT * FROM bmi";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuesstorebmi);

        openDatabase();



        tv1=(TextView)findViewById(R.id.weight);
        tv2=(TextView)findViewById(R.id.height);
        tv3=(TextView)findViewById(R.id.fscore);
        tv4=(TextView)findViewById(R.id.message);


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
        String weightvalue = c.getString(1);
        Log.i("weightvalue", weightvalue);
        String heightvalue = c.getString(2);
        Log.i("heightvalue", heightvalue);
        String bmiresult = c.getString(3);
        Log.i("bmiresult", bmiresult);

        tv1.setText(weightvalue);
        tv2.setText(heightvalue);

        fresult = Float.parseFloat(bmiresult);
        String bmiresult1 = String.valueOf(fresult);
        if (fresult <= 25.0) {
            tv3.setTextColor(getResources().getColor(R.color.colorgreen));
            tv3.setText(bmiresult1);
            tv4.setTextColor(getResources().getColor(R.color.colorgreen));
            tv4.setText("Healthy");
        }if(fresult>25.0 && fresult<=30.0){
            tv3.setTextColor(getResources().getColor(R.color.coloryellow));
            tv3.setText(bmiresult1);
            tv4.setTextColor(getResources().getColor(R.color.coloryellow));
            tv4.setText("Overweight");
        }else if(fresult>30.0){
            tv3.setTextColor(getResources().getColor(R.color.colorred));
            tv3.setText(bmiresult1);
            tv4.setTextColor(getResources().getColor(R.color.colorred));
            tv4.setText(" obese patients, and must take diet");

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
            onBackPressed();
            return true;
        }
        if (id == R.id.patient_notes) {
            Intent in = new Intent(ValuesStoreBMI.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(ValuesStoreBMI.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(ValuesStoreBMI.this,FormsActivity.class);
        startActivity(intent);
        finish();
    }


}
