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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;


/**
 * Created by User_Sajid on 4/21/2016.
 */
public class GcsActivity extends AppCompatActivity {

    RadioGroup rg,rg1,rg2;
    Button submit;
    int selectedId,selectedId1,selectedId2 ;
    RadioButton rb3,rb1,rb2;
    String radioButtonText,radioButtonText1,radioButtonText2,FinalResult;
    int radioButtonText11,radioButtonText12,radioButtonText13,result;
    String date;
private static String rg3Str,rg1Str,rg2Str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcs);
        final DataBaseHandlerMFS databaseHandler = new DataBaseHandlerMFS(this);

        rg=(RadioGroup)findViewById(R.id.radiogroup1);
        rg1=(RadioGroup)findViewById(R.id.radiogroup2);
        rg2=(RadioGroup)findViewById(R.id.radiogroup3);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();
        submit=(Button)findViewById(R.id.Submit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButton1) {
                    String i = "4";
                    radioButtonText = String.valueOf(i);
                    radioButtonText11 = Integer.parseInt(radioButtonText);
                    Log.i("radioButtonText", radioButtonText);
                }else if(checkedId == R.id.radioButton2){
                    String j = "3";
                    radioButtonText = String.valueOf(j);
                    radioButtonText11 = Integer.parseInt(radioButtonText);
                    Log.i("radioButtonText", radioButtonText);
                }
                else if(checkedId == R.id.radioButton3){
                    String j = "2";
                    radioButtonText = String.valueOf(j);
                    radioButtonText11 = Integer.parseInt(radioButtonText);
                    Log.i("radioButtonText", radioButtonText);
                }
                else if(checkedId == R.id.radioButton4){
                    String j = "1";
                    radioButtonText = String.valueOf(j);
                    radioButtonText11 = Integer.parseInt(radioButtonText);
                    Log.i("radioButtonText", radioButtonText);
                }
                else if(checkedId == R.id.radioButton5){
                    String j = "C";
                    radioButtonText = String.valueOf(j);
                   // radioButtonText11 = Integer.parseInt(radioButtonText);
                    Log.i("radioButtonText", radioButtonText);
                }
            }
        });

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.oriented) {
                    String a = "5";
                    //int radioButtonText11=Integer.parseInt(a);
                    radioButtonText1 = String.valueOf(a);
                    radioButtonText12 = Integer.parseInt(radioButtonText1);
                    Log.i("radioButtonText1", radioButtonText1);
                }else if(checkedId == R.id.confused){
                    String j = "4";
                    radioButtonText1 = String.valueOf(j);
                    radioButtonText12 = Integer.parseInt(radioButtonText1);
                    Log.i("radioButtonText1", radioButtonText1);
                }
                else if(checkedId == R.id.inappropiate){
                    String j = "3";
                    radioButtonText1 = String.valueOf(j);
                    radioButtonText12 = Integer.parseInt(radioButtonText1);
                    Log.i("radioButtonText1", radioButtonText1);
                }
                else if(checkedId == R.id.Incomprehensible){
                    String j = "2";
                    radioButtonText1 = String.valueOf(j);
                    radioButtonText12 = Integer.parseInt(radioButtonText1);
                    Log.i("radioButtonText1", radioButtonText1);
                }
                else if(checkedId == R.id.noverbalresponse){
                    String j = "1";
                    radioButtonText1 = String.valueOf(j);
                    radioButtonText12 = Integer.parseInt(radioButtonText1);
                    Log.i("radioButtonText1", radioButtonText1);
                }
                else if(checkedId == R.id.Intubated){
                    String j = "T";
                    radioButtonText1 = String.valueOf(j);
                    //radioButtonText12 = Integer.parseInt(radioButtonText1);
                    Log.i("radioButtonText1", radioButtonText1);
                }
            }
        });


        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.obeys) {
                    String a = "6";
                    radioButtonText2 = String.valueOf(a);
                    radioButtonText13 = Integer.parseInt(radioButtonText2);
                    Log.i("radioButtonText2", radioButtonText2);
                }else if(checkedId == R.id.localizes){
                    String j = "5";
                    radioButtonText2 = String.valueOf(j);
                    radioButtonText13 = Integer.parseInt(radioButtonText2);
                    Log.i("radioButtonText2", radioButtonText2);
                }
                else if(checkedId == R.id.withdrawal){
                    String j = "4";
                    radioButtonText2 = String.valueOf(j);
                    radioButtonText13 = Integer.parseInt(radioButtonText2);
                    Log.i("radioButtonText2", radioButtonText2);
                }
                else if(checkedId == R.id.flexion){
                    String j = "3";
                    radioButtonText2 = String.valueOf(j);
                    radioButtonText13 = Integer.parseInt(radioButtonText2);
                    Log.i("radioButtonText2", radioButtonText2);
                }
                else if(checkedId == R.id.extension){
                    String j = "2";
                    radioButtonText2 = String.valueOf(j);
                    radioButtonText13 = Integer.parseInt(radioButtonText2);
                    Log.i("radioButtonText2", radioButtonText2);
                } else if(checkedId == R.id.nomotor){
                    String j = "1";
                    radioButtonText2 = String.valueOf(j);
                    radioButtonText13 = Integer.parseInt(radioButtonText2);
                    Log.i("radioButtonText2", radioButtonText2);
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedId = rg.getCheckedRadioButtonId();
                selectedId1 = rg1.getCheckedRadioButtonId();
                selectedId2 = rg2.getCheckedRadioButtonId();


                if (selectedId >= 0 && selectedId1 >= 0 && selectedId2 >= 0) {

                    try {
                        System.out.println("IN SUBMIT METHOD");
                        System.out.println("************************************************************");
                        System.out.println("************************************************************");

                        System.out.println("Respiratoryscore  : " + radioButtonText11);
                        System.out.println("Oxygenscore  : " + radioButtonText12);
                        System.out.println("Historyscore  : " + radioButtonText13);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    result = radioButtonText11 + radioButtonText12 + radioButtonText13;
                    System.out.println("result  :" + result);
                    FinalResult = String.valueOf(result);
                    System.out.println("FINAL RESULT  : " + FinalResult);

                    databaseHandler.insertgcs(radioButtonText, radioButtonText1, radioButtonText2, FinalResult, Pateints_List111.patId, date);
                    System.out.println("databaseHandler  : " + databaseHandler);


                    Intent in = new Intent(GcsActivity.this, ValuesStoreGCS.class);


                    startActivity(in);
                    // Toast.makeText(MFS.this, "Successfully stored", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(GcsActivity.this,"Incomplete Assessment.",Toast.LENGTH_SHORT).show();
                }
            }
        });

     /*   try {
            selectedId = rg.getCheckedRadioButtonId();
            System.out.println("selectedId" + selectedId);
            selectedId1 = rg1.getCheckedRadioButtonId();
            selectedId2 = rg2.getCheckedRadioButtonId();
        } catch (Exception e) {
            e.printStackTrace();
        }


        rb1 = (RadioButton) findViewById(selectedId);
        try {
            radioButtonText = rb1.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rb11 = "Eyes open spontaneously";
        try {
            if (radioButtonText.equals(rb11)) {
                String i = "+4";
                radioButtonText = String.valueOf(i);
                radioButtonText11 = Integer.parseInt(radioButtonText);
                Log.i("radioButtonText", radioButtonText);
            } else if (radioButtonText.equals("Eye opening to verbal command")) {
                String j = "+3";
                radioButtonText = String.valueOf(j);
                radioButtonText11 = Integer.parseInt(radioButtonText);
                Log.i("radioButtonText", radioButtonText);
            } else if (radioButtonText.equals("Eye opening to pain")) {
                String k = "+2";
                radioButtonText = String.valueOf(k);
                radioButtonText11 = Integer.parseInt(radioButtonText);
                Log.i("radioButtonText", radioButtonText);
            } else if (radioButtonText.equals("No eye opening")) {
                String k = "+1";
                radioButtonText = String.valueOf(k);
                radioButtonText11 = Integer.parseInt(radioButtonText);
                Log.i("radioButtonText", radioButtonText);
            } else if (radioButtonText.equals("Not assessable (Trauma, edema, etc)")) {
                String k = "C";
                radioButtonText = String.valueOf(k);
                radioButtonText11 = Integer.parseInt(radioButtonText);
                Log.i("radioButtonText", radioButtonText);
            }
        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(GcsActivity.this," Incomplete Assessment "  ,Toast.LENGTH_SHORT).show();
        }

        rb2 = (RadioButton) findViewById(selectedId1);
        try {
            radioButtonText1 = rb2.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        String rb22 = "Oriented";
        try {
            if (radioButtonText1.equals(rb22)) {
                String a = "+5";
                //int radioButtonText11=Integer.parseInt(a);
                radioButtonText1 = String.valueOf(a);
                radioButtonText12 = Integer.parseInt(radioButtonText1);
                Log.i("radioButtonText1", radioButtonText1);
            } else if (radioButtonText1.equals("Confused")) {
                String b = "+4";
                radioButtonText1 = String.valueOf(b);
                radioButtonText12 = Integer.parseInt(radioButtonText1);
                Log.i("radioButtonText1", radioButtonText1);
            } else if (radioButtonText1.equals("Inappropriate words")) {
                String c = "+3";
                radioButtonText1 = String.valueOf(c);
                radioButtonText12 = Integer.parseInt(radioButtonText1);
                Log.i("radioButtonText1", radioButtonText1);
            } else if (radioButtonText1.equals("Incomprehensible sounds")) {
                String c = "+2";
                radioButtonText1 = String.valueOf(c);
                radioButtonText12 = Integer.parseInt(radioButtonText1);
                Log.i("radioButtonText1", radioButtonText1);
            } else if (radioButtonText1.equals("No verbal response")) {
                String c = "+1";
                radioButtonText1 = String.valueOf(c);
                radioButtonText12 = Integer.parseInt(radioButtonText1);
                Log.i("radioButtonText1", radioButtonText1);
            } else if (radioButtonText1.equals("Intubated")) {
                String c = "T";
                radioButtonText1 = String.valueOf(c);
                radioButtonText12 = Integer.parseInt(radioButtonText1);
                Log.i("radioButtonText1", radioButtonText1);
            }
        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(GcsActivity.this," Incomplete Assessment’ "  ,Toast.LENGTH_SHORT).show();
        }


        rb3 = (RadioButton) findViewById(selectedId2);
        try {
            radioButtonText2 = rb3.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(MFS.this,"Please Select properly",Toast.LENGTH_SHORT).show();
        }
        String rb33 = "Obeys commands";
        try {
            if (radioButtonText2.equals(rb33)) {
                String d = "+6";
                radioButtonText2 = String.valueOf(d);
                radioButtonText13 = Integer.parseInt(radioButtonText2);
                Log.i("radioButtonText2", radioButtonText2);
            } else if (radioButtonText2.equals("Localizes pain")) {
                String e = "+5";
                radioButtonText2 = String.valueOf(e);
                radioButtonText13 = Integer.parseInt(radioButtonText2);

            } else if (radioButtonText2.equals("Withdrawal from pain")) {
                String e = "+4";
                radioButtonText2 = String.valueOf(e);
                radioButtonText13 = Integer.parseInt(radioButtonText2);

            } else if (radioButtonText2.equals("Flexion to pain")) {
                String e = "+3";
                radioButtonText2 = String.valueOf(e);
                radioButtonText13 = Integer.parseInt(radioButtonText2);

            } else if (radioButtonText2.equals("Extension to pain")) {
                String e = "+2";
                radioButtonText2 = String.valueOf(e);
                radioButtonText13 = Integer.parseInt(radioButtonText2);

            } else if (radioButtonText2.equals("No motor response")) {
                String e = "+1";
                radioButtonText2 = String.valueOf(e);
                radioButtonText13 = Integer.parseInt(radioButtonText2);

            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(GcsActivity.this," Incomplete Assessment "  ,Toast.LENGTH_SHORT).show();

        }

        if (selectedId >= 0 && selectedId1 >= 0 && selectedId2 >= 0) {


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        if(rg1Str!=null&&rg2Str!=null&&rg3Str!=null) {
                            rg1Str = rb1.getText().toString();
                            Log.i("rg1Str value", rg1Str);

                            rg2Str = rb2.getText().toString();
                            Log.i("rg2Str value", rg2Str);
                            rg3Str = rb3.getText().toString();
                            Log.i("rg2Str value", rg2Str);


                            result = radioButtonText11 + radioButtonText12 + radioButtonText13;
                            System.out.println("Finalreult" + result);
                            FinalResult = String.valueOf(result);

                            databaseHandler.insertgcs(rg1Str, rg2Str, rg3Str, FinalResult, Pateints_List111.patId, date);
                            System.out.println("databaseHandler" + databaseHandler);


                            Intent in = new Intent(GcsActivity.this, ValuesStoreGCS.class);


                            startActivity(in);
                            // Toast.makeText(MFS.this, "Successfully stored", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(GcsActivity.this,"Incomplete Assessment.",Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    //Toast.makeText(GcsActivity.this, "Please select properly", Toast.LENGTH_SHORT).show();
                }


            });
        }*/
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
            Intent in = new Intent(GcsActivity.this, AddPatientActivity.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(GcsActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent=new Intent(GcsActivity.this,FormsActivity.class);
        startActivity(intent);
        finish();
    }

}
