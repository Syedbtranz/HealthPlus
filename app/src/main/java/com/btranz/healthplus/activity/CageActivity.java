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
 * Created by User_Sajid on 4/21/2016.
 */
public class CageActivity extends AppCompatActivity
{
    Switch QUE1;
    String Yes="1";
    String No="0";
    Switch QUE2;
    String On="1";
    String Off="0";
    Switch QUE3;
    String onn="1";
    String off="0";
    Switch QUE4;
    String YES="1";
    String NO="0";
    /*Switch hypertension;
    String ON="45";
    String OFF="0";*/

    Button submit;
    private static String que1value,que2value,que3value,que4value;
    String finalresult;
    int quevalue1,quevalue2,quevalue3,quevalue4,result;
    String date;


    DataBaseHandlerMFS databaseHandler = new DataBaseHandlerMFS(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cage);

        QUE1=(Switch)findViewById(R.id.que1);
        QUE2=(Switch)findViewById(R.id.que2);
        QUE3=(Switch)findViewById(R.id.que3);
        QUE4=(Switch)findViewById(R.id.que4);

        submit = (Button) findViewById(R.id.Submit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();
        if (QUE1!= null) {
            QUE1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    /*Toast.makeText(CageActivity.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        QUE1.setText(On);
                        quevalue1=Integer.parseInt(On);
                    } else {
                        //do stuff when Switch if OFF
                        QUE1.setText(Off);
                        quevalue1=Integer.parseInt(Off);
                    }
                }
            });
        }
        if (QUE2!= null) {
            QUE2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   /* Toast.makeText(CageActivity.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        QUE2.setText(Yes);
                        quevalue2=Integer.parseInt(Yes);
                    } else {
                        //do stuff when Switch if OFF
                        QUE2.setText(No);
                        quevalue2=Integer.parseInt(No);
                    }
                }
            });
        }
        if (QUE3!= null) {
            QUE3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    /*Toast.makeText(CageActivity.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        QUE3.setText(onn);
                        quevalue3=Integer.parseInt(onn);
                    } else {
                        //do stuff when Switch if OFF
                        QUE3.setText(off);
                        quevalue3=Integer.parseInt(off);
                    }
                }
            });
        }
        if (QUE4!= null) {
            QUE4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    /*Toast.makeText(CageActivity.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                    if (isChecked) {
                        //do stuff when Switch is ON
                        QUE4.setText(YES);
                        quevalue4=Integer.parseInt(YES);
                    } else {
                        //do stuff when Switch if OFF
                        QUE4.setText(NO);
                        quevalue4=Integer.parseInt(NO);
                    }
                }
            });
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    que1value = QUE1.getText().toString();
                    Log.i("que1value", que1value);
                    que2value = QUE2.getText().toString();
                    Log.i("que2value", que2value);
                    que3value = QUE3.getText().toString();
                    Log.i("que3value", que3value);
                    que4value = QUE4.getText().toString();
                    Log.i("que4value", que4value);


                    result=quevalue1+quevalue2+quevalue3+quevalue4;
                    if(result==0){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CageActivity.this);

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
                                        finalresult=String.valueOf(result);
                                        Log.i("finalresult",finalresult);

                                        databaseHandler.insertcage(que1value, que2value, que3value, que4value, finalresult,Pateints_List111.patId,date);
                                        System.out.println("databaseHandler" + databaseHandler);
                                        Intent intent = new Intent(CageActivity.this,ValuesStoreCAGE.class);

                                        startActivity(intent);
                                        // Toast.makeText(CageActivity.this, "Successfully stored", Toast.LENGTH_SHORT).show();
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
                    }
                    else{
                        finalresult=String.valueOf(result);
                        Log.i("finalresult",finalresult);

                        databaseHandler.insertcage(que1value, que2value, que3value, que4value, finalresult,Pateints_List111.patId,date);
                        System.out.println("databaseHandler" + databaseHandler);
                        Intent intent = new Intent(CageActivity.this,ValuesStoreCAGE.class);

                        startActivity(intent);
                        // Toast.makeText(CageActivity.this, "Successfully stored", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
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
            Intent in = new Intent(CageActivity.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(CageActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent=new Intent(CageActivity.this,FormsActivity.class);
        startActivity(intent);
        finish();
    }
}
