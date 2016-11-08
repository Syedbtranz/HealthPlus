package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;


/**
 * Created by all on 10/21/2016.
 */

public class EWSActivity1 extends AppCompatActivity {

    RadioGroup Respiratory, Oxygen, Systolic, Temparature, HeartRate, AvpuScore;
    RadioButton rb1, rb2, rb3, rb4, rb5, rb6;
    String rg1Str, rg2Str, HistoryStr, rg3Str, rg4Str, rg5Str, rg6Str;
    Switch History;
    String Yes = "2";
    String No = "0";

    int radioButtonText11, radioButtonText12, radioButtonText13, radioButtonText14, radioButtonText15, radioButtonText16;
    String radioButtonText, radioButtonText1, radioButtonText2, radioButtonText3, radioButtonText4, radioButtonText5, FinalResult;
    int selectedId1, selectedId2, selectedId3, selectedId4, selectedId5, selectedId6, result, historyvalue;

    int respiratoryscore,oxygenscore,systolicscore,temparaturescore,heartratescore,avpuscore;
    int RespitoryScore,OxygenScore,SystolicScore,TemaparatureScore,HeartRateScore,AvpScore;
    RadioButton Respiratory1,Respiratory2,Respiratory3,Respiratory4,Respiratory5;
    String respiratoryvalue,oxygenvalue,systolicvalue,temparaturevalue,heartratevalue,avpuvalue,historyValue;

    DataBaseHandlerMFS databaseHandler = new DataBaseHandlerMFS(this);

    Button submit;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        Respiratory = (RadioGroup) findViewById(R.id.respiratory);
        Oxygen = (RadioGroup) findViewById(R.id.oxygen);
        History = (Switch) findViewById(R.id.history);
        Systolic = (RadioGroup) findViewById(R.id.systolic);
        Temparature = (RadioGroup) findViewById(R.id.temparature);
        HeartRate = (RadioGroup) findViewById(R.id.heartrate);
        AvpuScore = (RadioGroup) findViewById(R.id.avpuscore);

        submit = (Button) findViewById(R.id.Submitnews);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
        try {
            if (History != null) {
                History.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   /* Toast.makeText(EwsActivity.this, "The Switch is " + (isChecked ? "on" : "off"),
                            Toast.LENGTH_SHORT).show();*/
                        if (isChecked) {
                            //do stuff when Switch is ON
                            History.setText(Yes);
                            historyvalue = Integer.parseInt(Yes);
                        //    historyValue=String.valueOf(heartratevalue);

                        } else {
                            //do stuff when Switch if OFF
                            History.setText(No);
                            historyvalue = Integer.parseInt(No);
                       //     historyValue=String.valueOf(heartratevalue);

                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*try {
            selectedId1 = Respiratory.getCheckedRadioButtonId();
            System.out.println("selectedId1" + selectedId1);
            selectedId2 = Oxygen.getCheckedRadioButtonId();
            System.out.println("selectedId2" + selectedId2);
            selectedId3 = Systolic.getCheckedRadioButtonId();
            System.out.println("selectedId3" + selectedId3);
            selectedId4 = Temparature.getCheckedRadioButtonId();
            selectedId5 = HeartRate.getCheckedRadioButtonId();
            selectedId6 = AvpuScore.getCheckedRadioButtonId();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        Respiratory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.respiratory1) {
                    String i = "3";
                    respiratoryscore = Integer.parseInt(i);
                    respiratoryvalue=String.valueOf(respiratoryscore);
                    System.out.println("respiratoryscore  : " + respiratoryscore);
                } else if (checkedId == R.id.respiratory2) {
                    String i = "1";
                    respiratoryscore = Integer.parseInt(i);
                    respiratoryvalue=String.valueOf(respiratoryscore);
                    System.out.println("respiratoryscore  : " + respiratoryscore);
                } else if (checkedId == R.id.respiratory3) {
                    String i = "0";
                    respiratoryscore = Integer.parseInt(i);
                    respiratoryvalue=String.valueOf(respiratoryscore);
                    System.out.println("respiratoryscore  : " + respiratoryscore);
                } else if (checkedId == R.id.respiratory4) {
                    String i = "2";
                    respiratoryscore = Integer.parseInt(i);
                    respiratoryvalue=String.valueOf(respiratoryscore);
                    System.out.println("respiratoryscore  : " + respiratoryscore);
                } else if (checkedId == R.id.respiratory5) {
                    String i = "3";
                    respiratoryscore = Integer.parseInt(i);
                    respiratoryvalue=String.valueOf(respiratoryscore);
                    System.out.println("respiratoryscore  : " + respiratoryscore);
                }
            }
        });

        Oxygen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.oxygen1) {
                    String i = "3";
                    oxygenscore = Integer.parseInt(i);
                    oxygenvalue=String.valueOf(oxygenscore);
                    System.out.println("oxygenscore  : " + oxygenscore);
                } else if (checkedId == R.id.oxygen2) {
                    String i = "2";
                    oxygenscore = Integer.parseInt(i);
                    oxygenvalue=String.valueOf(oxygenscore);
                    System.out.println("oxygenscore  : " + oxygenscore);
                } else if (checkedId == R.id.oxygen3) {
                    String i = "1";
                    oxygenscore = Integer.parseInt(i);
                    oxygenvalue=String.valueOf(oxygenscore);
                    System.out.println("oxygenscore  : " + oxygenscore);
                } else if (checkedId == R.id.oxygen4) {
                    String i = "0";
                    oxygenscore = Integer.parseInt(i);
                    oxygenvalue=String.valueOf(oxygenscore);
                    System.out.println("oxygenscore  : " + oxygenscore);
                }
            }
        });

        Systolic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.systolic1) {
                    String i = "3";
                    systolicscore = Integer.parseInt(i);
                    systolicvalue=String.valueOf(systolicscore);
                    System.out.println("systolicscore  : " + systolicscore);
                } else if (checkedId == R.id.systolic2) {
                    String i = "2";
                    systolicscore = Integer.parseInt(i);
                    systolicvalue=String.valueOf(systolicscore);
                    System.out.println("systolicscore  : " + systolicscore);
                } else if (checkedId == R.id.systolic3) {
                    String i = "1";
                    systolicscore = Integer.parseInt(i);
                    systolicvalue=String.valueOf(systolicscore);
                    System.out.println("systolicscore  : " + systolicscore);
                } else if (checkedId == R.id.systolic4) {
                    String i = "0";
                    systolicscore = Integer.parseInt(i);
                    systolicvalue=String.valueOf(systolicscore);
                    System.out.println("systolicscore  : " + systolicscore);
                } else if (checkedId == R.id.systolic5) {
                    String i = "3";
                    systolicscore = Integer.parseInt(i);
                    systolicvalue=String.valueOf(systolicscore);
                    System.out.println("systolicscore  : " + systolicscore);
                }
            }
        });


        Temparature.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.temparature1) {
                    String i = "3";
                    temparaturescore = Integer.parseInt(i);
                    temparaturevalue=String.valueOf(temparaturescore);
                    System.out.println("temparaturescore  : " + temparaturescore);
                } else if (checkedId == R.id.temparature2) {
                    String i = "1";
                    temparaturescore = Integer.parseInt(i);
                    temparaturevalue=String.valueOf(temparaturescore);
                    System.out.println("temparaturescore  : " + temparaturescore);
                } else if (checkedId == R.id.temparature3) {
                    String i = "0";
                    temparaturescore = Integer.parseInt(i);
                    temparaturevalue=String.valueOf(temparaturescore);
                    System.out.println("temparaturescore  : " + temparaturescore);
                } else if (checkedId == R.id.temparature4) {
                    String i = "1";
                    temparaturescore = Integer.parseInt(i);
                    temparaturevalue=String.valueOf(temparaturescore);
                    System.out.println("temparaturescore  : " + temparaturescore);
                } else if (checkedId == R.id.temparature5) {
                    String i = "2";
                    temparaturescore = Integer.parseInt(i);
                    temparaturevalue=String.valueOf(temparaturescore);
                    System.out.println("systolicscore  : " + systolicscore);
                }
            }
        });


        HeartRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.heartrate1) {
                    String i = "3";
                    heartratescore = Integer.parseInt(i);
                    heartratevalue=String.valueOf(heartratescore);
                    System.out.println("heartratescore  : " + heartratescore);
                } else if (checkedId == R.id.heartrate2) {
                    String i = "1";
                    heartratescore = Integer.parseInt(i);
                    heartratevalue=String.valueOf(heartratescore);
                    System.out.println("heartratescore  : " + heartratescore);
                } else if (checkedId == R.id.heartrate3) {
                    String i = "0";
                    heartratescore = Integer.parseInt(i);
                    heartratevalue=String.valueOf(heartratescore);
                    System.out.println("heartratescore  : " + heartratescore);
                } else if (checkedId == R.id.heartrate4) {
                    String i = "1";
                    heartratescore = Integer.parseInt(i);
                    heartratevalue=String.valueOf(heartratescore);
                    System.out.println("heartratescore  : " + heartratescore);
                } else if (checkedId == R.id.heartrate5) {
                    String i = "2";
                    heartratescore = Integer.parseInt(i);
                    heartratevalue=String.valueOf(heartratescore);
                    System.out.println("heartratescore  : " + heartratescore);
                }else if (checkedId == R.id.heartrate6) {
                    String i = "3";
                    heartratescore = Integer.parseInt(i);
                    heartratevalue=String.valueOf(heartratescore);
                    System.out.println("heartratescore  : " + heartratescore);
                }
            }
        });


        AvpuScore.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.avpuscore1) {
                    String i = "0";
                    avpuscore = Integer.parseInt(i);
                    avpuvalue=String.valueOf(avpuscore);
                    System.out.println("avpuscore  : " + avpuscore);
                } else if (checkedId == R.id.avpuscore2) {
                    String i = "3";
                    avpuscore = Integer.parseInt(i);
                    avpuvalue=String.valueOf(avpuscore);
                    System.out.println("avpuscore  : " + avpuscore);
                }
            }
        });

           /*RESPIRATORY RATE*/

        Respiratory1 = (RadioButton) findViewById(R.id.respiratory1);
        Respiratory2 = (RadioButton) findViewById(R.id.respiratory2);
        Respiratory3 = (RadioButton) findViewById(R.id.respiratory3);
        Respiratory4 = (RadioButton) findViewById(R.id.respiratory4);
        Respiratory5 = (RadioButton) findViewById(R.id.respiratory5);

        //Respiratory1=(RadioButton)findViewById(selectedId1);


        // selectedId1 = Respiratory.getCheckedRadioButtonId();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                selectedId1 = Respiratory.getCheckedRadioButtonId();
                selectedId2 = Oxygen.getCheckedRadioButtonId();
                selectedId3 = Systolic.getCheckedRadioButtonId();
                selectedId4 = Temparature.getCheckedRadioButtonId();
                selectedId5 = HeartRate.getCheckedRadioButtonId();
                selectedId5 = AvpuScore.getCheckedRadioButtonId();

                if (selectedId1 >= 0 && selectedId2 >= 0 && selectedId3 >= 0 && selectedId4 >= 0 && selectedId5 >= 0 && selectedId6 >= 0) {
                    try {
                        System.out.println("IN SUBMIT METHOD");
                        System.out.println("************************************************************");
                        System.out.println("************************************************************");

                        System.out.println("Respiratoryscore  : " + respiratoryscore);
                        System.out.println("Oxygenscore  : " + oxygenscore);
                        System.out.println("Historyscore  : " + historyValue);
                        System.out.println("Temparaturescore  : " + temparaturescore);
                        System.out.println("Systolicscore  : " + systolicscore);
                        System.out.println("Heartratescore  : " + heartratescore);
                        System.out.println("Avpuscore  : " + avpuscore);


                        result = respiratoryscore + oxygenscore + historyvalue + temparaturescore + systolicscore + heartratescore + avpuscore;
                        System.out.println("result in integer format" + result);
                        FinalResult = String.valueOf(result);
                        System.out.println("Finalresult in String format" + FinalResult);
                        historyValue=String.valueOf(historyvalue);

                        databaseHandler.insertnews(respiratoryvalue, oxygenvalue, historyValue, temparaturevalue, systolicvalue, heartratevalue, avpuvalue, FinalResult,Pateints_List111.patId,date);
                        System.out.println("databaseHandler" + databaseHandler);
                        Intent in = new Intent(EWSActivity1.this, ValuesStoreNEWS.class);
                        startActivity(in);

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }else{
                    Toast.makeText(EWSActivity1.this,"Incomplete Assessment",Toast.LENGTH_SHORT).show();
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
            Intent in = new Intent(EWSActivity1.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(EWSActivity1.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent=new Intent(EWSActivity1.this,FormsActivity.class);
        startActivity(intent);
        finish();
    }
}