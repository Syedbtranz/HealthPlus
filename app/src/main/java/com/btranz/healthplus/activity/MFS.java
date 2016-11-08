package com.btranz.healthplus.activity;

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
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;

/**
 * Created by bTranz on 4/20/2016.
 */
public class MFS extends AppCompatActivity {

    Switch History;
    String Yes = "25";
    String No = "0";
    Switch Secondary;
    String On = "15";
    String Off = "0";
    Switch Heparinlock;
    String O = "20";
    String Of = "0";
    Button submit;


    private static final String SELECT_SQL = "SELECT * FROM mfs";

    private SQLiteDatabase db;

    private Cursor c;

    public static List<String> patList = new ArrayList<String>();
    DatabaseHandler dbHandler;
    String patid;

    int historyvalue, secondaryvalue, heparinlockvalue, selectedId, selectedId1, selectedId2;

    int radioButtonText11, radioButtonText12, radioButtonText13;
    String radioButtonText, radioButtonText1, radioButtonText2, FinalResult,sendtoform;
    int result;

    String yes;
    RadioGroup rg, rg1, rg2;
    RadioButton rb, rb1, rb2;
    String date;
    TextView textView;
    DataBaseHandlerMFS databaseHandler = new DataBaseHandlerMFS(this);
    private static String Patid, HistoryStr, SecondaryStr, HeparinlockStr, rgStr, rg1Str, rg2Str;
    String HistoryStr1, SecondaryStr1, HeparinlockStr1, rgStr1, rg1Str1, rg2Str1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mfs);


        History = (Switch) findViewById(R.id.hostory);
        Secondary = (Switch) findViewById(R.id.secondary);
        Heparinlock = (Switch) findViewById(R.id.Heparinlock);
        rg = (RadioGroup) findViewById(R.id.ambulatoryaid);
        rg1 = (RadioGroup) findViewById(R.id.gaittransfer);
        rg2 = (RadioGroup) findViewById(R.id.mental);
        /*  rb=(RadioButton)findViewById(R.id.bedrest);
        rb1=(RadioButton)findViewById(R.id.crutches);
        rb2=(RadioButton)findViewById(R.id.furnitire);

        textView=(TextView)findViewById(R.id.textView17);
*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
    date = dateFormat.format(new Date()).toString();

        submit = (Button) findViewById(R.id.Submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //databaseHandler.addMfs(new MFSModel("10","10","kskfs","sjdghf","shgd","25","sfshd","sdhvfs","sjhf","jshf","sdgf");

        //sqlite_obj=new SQLiteDB(getApplicationContext());
       /* bed=(TextView)findViewById(R.id.bed);
        bed1=(TextView)findViewById(R.id.bed1);
        bed2=(TextView)findViewById(R.id.bed2);*/
        //radioGroup = (RadioGroup) findViewById(R.id.ambulatoryaid);


        //  getSupportActionBar().setElevation(0);


        try {
            if (History != null) {
                History.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //Toast.makeText(MFS.this, "The Switch is " + (isChecked ? "on" : "off"),Toast.LENGTH_SHORT).show();
                        if (isChecked) {
                            //do stuff when Switch is ON
                            History.setText(Yes);
                            yes = Yes;
                            historyvalue = Integer.parseInt(Yes);


                        } else {
                            //do stuff when Switch if OFF
                            History.setText(No);
                            historyvalue = Integer.parseInt(No);

                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (Secondary != null) {
                Secondary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //Toast.makeText(MFS.this, "The Switch is " + (isChecked ? "on" : "off"),Toast.LENGTH_SHORT).show();
                        if (isChecked) {
                            //do stuff when Switch is ON
                            Secondary.setText(On);
                            secondaryvalue = Integer.parseInt(On);

                        } else {
                            //do stuff when Switch if OFF
                            Secondary.setText(Off);
                            secondaryvalue = Integer.parseInt(Off);

                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if (Heparinlock != null) {
                Heparinlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //Toast.makeText(MFS.this, "The Switch is " + (isChecked ? "on" : "off"),Toast.LENGTH_SHORT).show();
                        if (isChecked) {
                            //do stuff when Switch is ON
                            Heparinlock.setText(O);
                            heparinlockvalue = Integer.parseInt(O);
                        } else {
                            //do stuff when Switch if OFF
                            Heparinlock.setText(Of);
                            heparinlockvalue = Integer.parseInt(Of);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    selectedId = rg.getCheckedRadioButtonId();
                    System.out.println("selectedId" + selectedId);
                    selectedId1 = rg1.getCheckedRadioButtonId();
                    selectedId2 = rg2.getCheckedRadioButtonId();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                rb = (RadioButton) findViewById(selectedId);
                try {
                    radioButtonText = rb.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(MFS.this,"Please Select properly",Toast.LENGTH_SHORT).show();
                }
                String rb11 = "Bed rest/nurse assist";
                try {
                    if (radioButtonText.equals(rb11)) {
                        String i = "0";
                        radioButtonText = String.valueOf(i);
                        radioButtonText11 = Integer.parseInt(radioButtonText);
                        Log.i("radioButtonText", radioButtonText);
                    } else if (radioButtonText.equals("Crutches/cane/walker")) {
                        String j = "15";
                        radioButtonText = String.valueOf(j);
                        radioButtonText11 = Integer.parseInt(radioButtonText);
                        Log.i("radioButtonText", radioButtonText);
                    } else if (radioButtonText.equals("Furniture")) {
                        String k = "30";
                        radioButtonText = String.valueOf(k);
                        radioButtonText11 = Integer.parseInt(radioButtonText);
                        Log.i("radioButtonText", radioButtonText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                rb1 = (RadioButton) findViewById(selectedId1);
                try {
                    radioButtonText1 = rb1.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                   // Toast.makeText(MFS.this, "Please Select properly", Toast.LENGTH_SHORT).show();
                }
                String rb22 = "Normal/bedrest/immobile";
                try {
                    if (radioButtonText1.equals(rb22)) {
                        String a = "0";
                        //int radioButtonText11=Integer.parseInt(a);
                        radioButtonText1 = String.valueOf(a);
                        radioButtonText12 = Integer.parseInt(radioButtonText1);
                        Log.i("radioButtonText1", radioButtonText1);
                    } else if (radioButtonText1.equals("Weak")) {
                        String b = "10";
                        radioButtonText1 = String.valueOf(b);
                        radioButtonText12 = Integer.parseInt(radioButtonText1);
                        Log.i("radioButtonText1", radioButtonText1);
                    } else if (radioButtonText1.equals("Impaired")) {
                        String c = "20";
                        radioButtonText1 = String.valueOf(c);
                        radioButtonText12 = Integer.parseInt(radioButtonText1);
                        Log.i("radioButtonText1", radioButtonText1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                rb2 = (RadioButton) findViewById(selectedId2);
                try {
                    radioButtonText2 = rb2.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                  //  Toast.makeText(MFS.this, "Please Select properly", Toast.LENGTH_SHORT).show();
                }
                String rb33 = "Oriented to own ability";
                try {
                    if (radioButtonText2.equals(rb33)) {
                        String d = "0";
                        radioButtonText2 = String.valueOf(d);
                        radioButtonText13 = Integer.parseInt(radioButtonText2);
                        Log.i("radioButtonText2", radioButtonText2);
                    } else if (radioButtonText2.equals("Forgets limitations")) {
                        String e = "15";
                        radioButtonText2 = String.valueOf(e);
                        radioButtonText13 = Integer.parseInt(radioButtonText2);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (selectedId >= 0 && selectedId1 >= 0 && selectedId2 >= 0) {

                    //String radiovalue=  (RadioButton)this.findViewById(rdgrp.getCheckedRadioButtonId())).getText().toString();
      /*  int radioId = rg.indexOfChild(rb);
        RadioButton btn = (RadioButton) rg1.getChildAt(radioId);
        String selection = (String) btn.getText();*/

                    try {


                        HistoryStr = History.getText().toString();
                        Log.i("HistoryStr", HistoryStr);
                        SecondaryStr = Secondary.getText().toString();
                        Log.i("SecondaryStr", SecondaryStr);
                        rgStr = rb.getText().toString();
                        Log.i("rgStr value", rgStr);
                        HeparinlockStr = Heparinlock.getText().toString();
                        Log.i("HeparinlockStr", HeparinlockStr);
                        rg1Str = rb1.getText().toString();
                        Log.i("rg1Str value", rg1Str);
                        rg2Str = rb2.getText().toString();
                        Log.i("rg2Str value", rg2Str);

                        if(rgStr!=null&&rg1Str!=null&&rgStr!=null) {

                            try {
                                result = historyvalue + secondaryvalue + radioButtonText11 + radioButtonText12 + heparinlockvalue + radioButtonText13;
                                System.out.println("Finalreult" + result);
                                FinalResult = String.valueOf(result);

                            } catch (NumberFormatException e) {
                                System.out.println("Could not parse" + e);
                            }


                            databaseHandler.insertmfs(HistoryStr, SecondaryStr, rgStr, HeparinlockStr, rg1Str, rg2Str, FinalResult, Pateints_List111.patId, date);
                            System.out.println("databaseHandler" + databaseHandler);

                            Intent in = new Intent(MFS.this, ValuesStoreMFS.class);

                            startActivity(in);
                            // Toast.makeText(MFS.this, "Successfully stored", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(MFS.this, "Incomplete Assessment.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        // Toast.makeText(getApplicationContext(), "Please select all", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MFS.this, "Incomplete Assessment.", Toast.LENGTH_SHORT).show();
                }


            }

        });


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
            Intent in=new Intent(MFS.this,Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(MFS.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent=new Intent(MFS.this,FormsActivity.class);
        startActivity(intent);
        finish();
    }
}


