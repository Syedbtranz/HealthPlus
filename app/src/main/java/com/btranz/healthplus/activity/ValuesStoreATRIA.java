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
 * Created by User_Sajid on 5/13/2016.
 */
public class ValuesStoreATRIA extends AppCompatActivity{

    TextView aaaa,bbbb,cccc,dddd,eeee,ffff,gggg;
    String tv1,tv2,tv3,tv4,tv5,tv6;
    int fresult;

    private static final String SELECT_SQL = "SELECT * FROM atria";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuestoreatria);
        openDatabase();

        aaaa=(TextView)findViewById(R.id.aaaa);
        bbbb=(TextView)findViewById(R.id.bbbb);
        cccc=(TextView)findViewById(R.id.cccc);
        dddd=(TextView)findViewById(R.id.dddd);
        eeee=(TextView)findViewById(R.id.eeee);
        ffff=(TextView)findViewById(R.id.fscore);
        gggg=(TextView)findViewById(R.id.message);


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
        String a1 = c.getString(1);
        Log.i("aaaa",a1);
        String b1 = c.getString(2);
        Log.i("bbbb",b1 );
        String c1 = c.getString(3);
        Log.i("cccc",c1);
        String d1=c.getString(4);
        Log.i("dddd",d1);
        String e1= c.getString(5);
        Log.i("eeee",e1);
        String f1= c.getString(6);
        Log.i("eeee",f1);



        aaaa.setText(a1);
        bbbb.setText(b1);
        cccc.setText(c1);
        dddd.setText(d1);
        eeee.setText(e1);

        fresult=Integer.parseInt(f1);
String fresult1=String.valueOf(fresult);
        if(fresult<=3){
            ffff.setTextColor(getResources().getColor(R.color.colorgreen));
            ffff.setText(fresult1);
            gggg.setTextColor(getResources().getColor(R.color.colorgreen));
            gggg.setText("Low Risk (<4 Points), 0.76% Annual Risk of Hemorrhage\n" +
                    "Reasonable to start warfarin\n");
        }else if(fresult==4){
            ffff.setTextColor(getResources().getColor(R.color.coloryellow));
            ffff.setText(fresult1);
            gggg.setTextColor(getResources().getColor(R.color.coloryellow));
            gggg.setText("Intermediate Risk (4 Points), 2.6% Annual Risk of Hemorrhage\n" +
                    "Alternatives to warfarin therapy can be considered.\n");
        }else if(fresult>4){
            ffff.setTextColor(getResources().getColor(R.color.colorred));
            ffff.setText(fresult1);
            gggg.setTextColor(getResources().getColor(R.color.colorred));
            gggg.setText("High Risk (>4 Points), 5.8% Annual Risk of Hemorrhage.\n" +
                    "Alternatives to warfarin should be strongly considered.\n ");
        }
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i1= new Intent(ValuesStoreMFS.this,FormsActivity.class);
        i1.putExtra("Finalresult",fresult);
        setResult(RESULT_OK, i1);
        finish();
    }*/

    public void sendToForms(){

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
            Intent in = new Intent(ValuesStoreATRIA.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(ValuesStoreATRIA.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(ValuesStoreATRIA.this,FormsActivity.class);
        startActivity(intent);
        finish();
    }
}
