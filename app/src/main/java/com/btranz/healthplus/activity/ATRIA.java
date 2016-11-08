package com.btranz.healthplus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 5/13/2016.
 */
public class ATRIA extends AppCompatActivity {

    Switch Anemia;
    String Yes="3";
    String No="";
    Switch Severe;
    String On="3";
    String Off="";
    Switch Age;
    String onn="2";
    String off="";
    Switch anyprior;
    String YES="1";
    String NO="";
    Switch hypertension;
    String ON="1";
    String OFF="";
String date;
    int Anemiavalue1,Severevalue1,Agevalue1,anypriorvalue1,hypertensionvalue1;

    Button submit;
    private static String Anemiavalue,Severevalue,Agevalue,anypriorvalue,hypertensionvalue;

    DataBaseHandlerMFS databaseHandler = new DataBaseHandlerMFS(this);
    int result;

    String finalresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atria);

        Anemia = (Switch) findViewById(R.id.anemiaa);
        Severe = (Switch) findViewById(R.id.severerenaldisease);
        Age = (Switch) findViewById(R.id.ageyears);
        anyprior = (Switch) findViewById(R.id.anyprior);
        hypertension = (Switch) findViewById(R.id.Hypertensionhistory);


        submit = (Button) findViewById(R.id.Submit);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();
        if (Anemia != null) {
            Anemia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   /* Toast.makeText(ATRIA.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        Anemia.setText(Yes);
                        // yes=Yes;
                        Anemiavalue1=Integer.parseInt(Yes);


                    } else {
                        //do stuff when Switch if OFF
                        Anemia.setText(No);
                        Anemiavalue1=Integer.parseInt(No);
                    }
                }
            });
        }
        if (Severe != null) {
            Severe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   /* Toast.makeText(ATRIA.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        Severe.setText(On);
                        Severevalue1=Integer.parseInt(On);
                    } else {
                        //do stuff when Switch if OFF
                        Severe.setText(Off);
                        Severevalue1=Integer.parseInt(Off);
                    }
                }
            });
        }
        if (Age != null) {
            Age.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  /*  Toast.makeText(ATRIA.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        Age.setText(onn);
                        Agevalue1=Integer.parseInt(onn);
                    } else {
                        //do stuff when Switch if OFF
                        Age.setText(off);
                        Agevalue1=Integer.parseInt(off);
                    }
                }
            });
        }
        if (anyprior != null) {
            anyprior.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  /*  Toast.makeText(ATRIA.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        anyprior.setText(YES);
                        anypriorvalue1=Integer.parseInt(YES);

                    } else {
                        //do stuff when Switch if OFF
                        anyprior.setText(NO);
                        anypriorvalue1=Integer.parseInt(NO);
                    }
                }
            });
        }
        if (hypertension != null) {
            hypertension.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  /*  Toast.makeText(ATRIA.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        hypertension.setText(ON);
                        hypertensionvalue1=Integer.parseInt(ON);

                    } else {
                        //do stuff when Switch if OFF
                        hypertension.setText(OFF);
                        hypertensionvalue1=Integer.parseInt(OFF);
                    }
                }
            });
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Anemiavalue = Anemia.getText().toString();
                    Log.i("Anemiavalue", Anemiavalue);
                    Severevalue = Severe.getText().toString();
                    Log.i("Severevalue", Severevalue);
                    Agevalue = Age.getText().toString();
                    Log.i("agevalue", Agevalue);
                    anypriorvalue = anyprior.getText().toString();
                    Log.i("anypriorvalue", anypriorvalue);
                    hypertensionvalue = hypertension.getText().toString();
                    Log.i("hypertensionvalue", hypertensionvalue);


                    result=Anemiavalue1+Severevalue1+Agevalue1+anypriorvalue1+hypertensionvalue1;
                    finalresult=String.valueOf(result);


                }catch (Exception e){
                    e.printStackTrace();
                }


                if(result<=0){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ATRIA.this);

                    // Setting Dialog Title
                    alertDialog.setTitle("Do you Want to go with zero?");

                    // Setting Dialog Message
                    //   alertDialog.setMessage("Are You Sure");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.mipmap.deskicon111);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int which) {

                                    databaseHandler.insertatria(Anemiavalue, Severevalue, Agevalue, anypriorvalue, hypertensionvalue, finalresult,Pateints_List111.patId,date);
                                    System.out.println("databaseHandler" + databaseHandler);
                                    Intent intent = new Intent(ATRIA.this, ValuesStoreATRIA.class);

                                    startActivity(intent);
                                    //  Toast.makeText(ATRIA.this, "Successfully stored", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                   // finish();
                                   // afterDialog_Drop();


                                    dialog.cancel();
                                }
                            });

                    // Showing Alert Message

                    alertDialog.show();



                    //Toast.makeText(ATRIA.this,"Please select atleast one value",Toast.LENGTH_SHORT).show();
                }else {

                    databaseHandler.insertatria(Anemiavalue, Severevalue, Agevalue, anypriorvalue, hypertensionvalue, finalresult,Pateints_List111.patId,date);
                    System.out.println("databaseHandler" + databaseHandler);
                    Intent intent = new Intent(ATRIA.this, ValuesStoreATRIA.class);

                    startActivity(intent);
                    //  Toast.makeText(ATRIA.this, "Successfully stored", Toast.LENGTH_SHORT).show();
                    finish();


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
            Intent in = new Intent(ATRIA.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(ATRIA.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent=new Intent(ATRIA.this,FormsActivity.class);
        startActivity(intent);
        finish();
    }
}
