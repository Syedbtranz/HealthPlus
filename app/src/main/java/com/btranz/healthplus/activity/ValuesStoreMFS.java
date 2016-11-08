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

import java.util.ArrayList;
import java.util.List;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 5/12/2016.
 */
public class ValuesStoreMFS extends AppCompatActivity {

    TextView aaa,bbb,ccc,ddd,eee,fff,ggg,hhh;
int fresult;
    String tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    public static List<String> patList=new ArrayList<String>();
    DatabaseHandler dbHandler;
    String TV1,TV2,TV3;
    String HistoryStr1,SecondaryStr1,HeparinlockStr1,rgStr1,rg1Str1,rg2Str1;

    private static final String SELECT_SQL = "SELECT * FROM mfs";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuesstoremfs);
        openDatabase();
      //  validatedata(patid);
       /* Intent i=getIntent();

        savedInstanceState =i.getExtras();
        *//*tv1=savedInstanceState.getString("HistoryStr");
        tv2=savedInstanceState.getString("SecondaryStr");
        tv3=savedInstanceState.getString("rgStr");
        tv4=savedInstanceState.getString("HeparinlockStr");
        tv5=savedInstanceState.getString("rg1Str");
        tv6=savedInstanceState.getString("rg2Str");*//*
        tv7=savedInstanceState.getString("Finalresult");*/



       // int tv=Integer.parseInt(tv7);

        aaa=(TextView)findViewById(R.id.aaa);
        bbb=(TextView)findViewById(R.id.bbb);
        ccc=(TextView)findViewById(R.id.ccc);
        ddd=(TextView)findViewById(R.id.ddd);
        eee=(TextView)findViewById(R.id.eee);
        fff=(TextView)findViewById(R.id.fff);
        ggg=(TextView)findViewById(R.id.fresult);
        hhh=(TextView)findViewById(R.id.message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);


        c = sqLiteDatabase.rawQuery(SELECT_SQL, null);
        c.moveToLast();

        moveNext();
        showRecords();
        //movePrev();
        //showRecords();
       /* aaa.setText(tv1);
        bbb.setText(tv2);
        ccc.setText(tv3);
        ddd.setText(tv4);
        eee.setText(tv5);
        fff.setText(tv6);*/




    }
    protected void openDatabase() {
        sqLiteDatabase = openOrCreateDatabase("HealthApp", Context.MODE_PRIVATE, null);
    }
    protected void showRecords() {
        String HistoryStr1 = c.getString(1);
        Log.i("HistoryStr1",HistoryStr1);
        String SecondaryStr1 = c.getString(2);
        Log.i("SecondaryStr1",SecondaryStr1 );
        String rgStr1 = c.getString(3);
        Log.i("rgStr1",rgStr1);
        String HeparinlockStr1=c.getString(4);
        Log.i("HeparinlockStr1",HeparinlockStr1);
        String rg1Str1= c.getString(5);
        Log.i("rgS1tr1",rg1Str1);
        String rg2Str1= c.getString(6);
        Log.i("rg2Str1",rg2Str1);
        String result= c.getString(7);
        Log.i("result",result);


        aaa.setText(HistoryStr1);
        bbb.setText(SecondaryStr1);
        ccc.setText(rgStr1);
        ddd.setText(HeparinlockStr1);
        eee.setText(rg1Str1);
        fff.setText(rg2Str1);

        fresult=Integer.parseInt(result);
        if(fresult<=24){
            ggg.setTextColor(getResources().getColor(R.color.colorgreen));
            ggg.setText(result);
            hhh.setTextColor(getResources().getColor(R.color.colorgreen));
            hhh.setText("Good Basic Nursing Care");
        }else if(fresult<=50&&fresult>=25){
            ggg.setTextColor(getResources().getColor(R.color.coloryellow));
            ggg.setText(result);
            hhh.setTextColor(getResources().getColor(R.color.coloryellow));
            hhh.setText("Implement Standard Fall Prevention Interventions");
        }else if(fresult>=50){
            ggg.setTextColor(getResources().getColor(R.color.colorred));
            ggg.setText(result);
            hhh.setTextColor(getResources().getColor(R.color.colorred));
            hhh.setText("Implement High Risk Fall Prevention Interventions");

        }
    }



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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent in = new Intent(ValuesStoreMFS.this, FormsActivity.class);
            startActivity(in);
            finish();
            return true;
        }
        if (id == R.id.patient_notes) {
            Intent in=new Intent(ValuesStoreMFS.this,Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(ValuesStoreMFS.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent in = new Intent(ValuesStoreMFS.this, FormsActivity.class);
        startActivity(in);
        finish();
    }

}
