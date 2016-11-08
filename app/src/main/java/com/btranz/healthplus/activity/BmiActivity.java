package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 4/21/2016.
 */
public class BmiActivity extends AppCompatActivity
{

    EditText et1,et2;
    Button submit;
    String date;
    Spinner KG;
    float weightvalue1,heightvalue1,res,resfinal;
    int weightvalue2,result;
    String fresult;
    float calc,height,weightheight,weightheight1,weightheight2;

    private static String weightvalue,heightvalue,kgvalue;
    final DataBaseHandlerMFS databaseHandler = new DataBaseHandlerMFS(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmibodymass);
       // final DataBaseHandlerBMI databaseHandler = new DataBaseHandlerBMI(this);

        et1=(EditText)findViewById(R.id.weightedittext);
        et2=(EditText)findViewById(R.id.heightedittext);


        submit=(Button)findViewById(R.id.Submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et1.getText().toString().equals("")){
                    Toast.makeText(BmiActivity.this, "Weight is Required", Toast.LENGTH_SHORT).show();
                }else if(et2.getText().toString().equals("")) {
                    Toast.makeText(BmiActivity.this, "Height is Required", Toast.LENGTH_SHORT).show();
                }else {
                    weightvalue=et1.getText().toString();
                    heightvalue=et2.getText().toString();
                        try {

                            weightvalue1 = Float.parseFloat(weightvalue);
                            heightvalue1 = Float.parseFloat(heightvalue);
                            System.out.println("Heightvalue1" + heightvalue1);

                            height = (float) ((heightvalue1 / 100) * (heightvalue1 / 100));
                            System.out.println("Height after calc" + height);
                            calc = weightvalue1 / height;


                            DecimalFormat df = new DecimalFormat("#.#");
                            String calcFinal = df.format(calc);
                            System.out.println("calc Value after 2nd modification" + calcFinal); //if value 2.5565314561 it will print 2.56


                            fresult = String.valueOf(calcFinal);

                            Log.i("FinalResult", fresult);
                            databaseHandler.insertbmi(weightvalue, heightvalue, fresult, Pateints_List111.patId, date);

                            Intent intent = new Intent(BmiActivity.this, ValuesStoreBMI.class);

                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
            Intent in = new Intent(BmiActivity.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(BmiActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
       // super.onBackPressed();  @Override


            Intent intent=new Intent(BmiActivity.this,FormsActivity.class);
            startActivity(intent);
            finish();

        et1.setText("");
        et2.setText("");
    }

}
