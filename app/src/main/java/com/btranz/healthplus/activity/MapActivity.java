package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;


/**
 * Created by User_Sajid on 6/2/2016.
 */
public class MapActivity extends AppCompatActivity {

EditText sys,dia;
    Button submit;
    String date;
    float sysi,diai,sysiresult,diairesult,systolic1,diastolic1,Fresult;
    String systolic,diastolic,Finalresult;

    final DataBaseHandlerMFS databaseHandler = new DataBaseHandlerMFS(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);

        sys=(EditText)findViewById(R.id.systolic_edittext);
        dia=(EditText)findViewById(R.id.diastolic_edittext);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();
        submit=(Button)findViewById(R.id.Submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
try {
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

finish();
            systolic = sys.getText().toString();
            diastolic = dia.getText().toString();

        //    if(systolic!=null&&diastolic!=null) {

try {
    systolic1 = Float.parseFloat(systolic);
    diastolic1 = Float.parseFloat(diastolic);

           /* sysi = 1 / 3;
            System.out.println("Sysi" + sysi);*/
          /*  sysiresult = (1/3) * systolic1;
            System.out.println("sysiresult" + sysiresult);

           *//* diai = 2 / 3;
            System.out.println("diai" + diai);*//*
            diairesult = (2/3) * diastolic1;
            System.out.println("diairesult" + diairesult);
*/
    Fresult = ((2 * diastolic1) + systolic1) / 3;

    DecimalFormat df = new DecimalFormat("#");
    String calcFinal = df.format(Fresult);
    System.out.println("calc Value after 2nd modification" + calcFinal); //if value 2.5565314561 it will print 2.56

    Finalresult = String.valueOf(calcFinal);
    System.out.println("Finalresult" + Finalresult);


    databaseHandler.insertmap(systolic, diastolic, Finalresult, Pateints_List111.patId, date);

    Intent intent = new Intent(MapActivity.this, ValuesStoreMAP.class);

    startActivity(intent);
}catch (Exception e){
    e.printStackTrace();
}
        }
    });
}catch (Exception e){
    e.printStackTrace();
}
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
            onBackPressed();
            return true;
        }
        if (id == R.id.add_patient) {
            Intent in = new Intent(MapActivity.this, AddPatientActivity.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(MapActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }




    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent=new Intent(MapActivity.this,FormsActivity.class);
        startActivity(intent);
        finish();
        sys.getText().clear();
        dia.getText().clear();
    }
}
