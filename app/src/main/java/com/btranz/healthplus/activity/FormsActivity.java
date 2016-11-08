package com.btranz.healthplus.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 4/21/2016.
 */
public class FormsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    Button mfsBtn, ewsBtn, atriaBtn, bmiBtn, cageBtn, dashBtn, glossomBtn, mapBtn;
    String ewsscore, result;
    TextView newsScore, atriaScore, bmiScore, cageScore, dashScore, gcsScore, mapScore, mfsScore;
    List<String> mfsList = new ArrayList<String>();
    List<String> gcsList = new ArrayList<String>();
    List<String> bmiList = new ArrayList<String>();
    List<String> mapList = new ArrayList<String>();
    List<String> cageList = new ArrayList<String>();
    List<String> atriaList = new ArrayList<String>();
    List<String> newsList = new ArrayList<String>();

    String mfsfresult;
    int fresultNews, fresultMfs, fresultCage, fresultAtriya;
    float fresultBmi, fresultMap;
    private static final String SELECT_NEWS = "SELECT * FROM news WHERE pat_id =" + Pateints_List111.patId;
    private static final String SELECT_MFS = "SELECT * FROM mfs WHERE pat_id =" + Pateints_List111.patId;
    private static final String SELECT_GCS = "SELECT * FROM gcs WHERE pat_id =" + Pateints_List111.patId;
    private static final String SELECT_BMI = "SELECT * FROM bmi WHERE pat_id =" + Pateints_List111.patId;
    private static final String SELECT_MAP = "SELECT * FROM map WHERE pat_id =" + Pateints_List111.patId;
    private static final String SELECT_CAGE = "SELECT * FROM cage WHERE pat_id =" + Pateints_List111.patId;
    private static final String SELECT_ATRIA = "SELECT * FROM atria WHERE pat_id =" + Pateints_List111.patId;



/*  private static String SELECT_JOIN= "SELECT mfsresult,newsresult,atriaresult,cageresult,gcsresult,bmiresult,mapresult " +
          "FROM mfs " +
          "NATURAL JOIN news " +
          "NATURAL JOIN gcs  " +
          "NATURAL JOIN bmi  " +
          "NATURAL JOIN map  " +
          "NATURAL JOIN cage  " +
          "NATURAL JOIN atria  " +
          "WHERE news.pat_id="+Pateints_List111.patId;*/

    private SQLiteDatabase sqLiteDatabase;
    DataBaseHandlerMFS dataBaseHandlerMFS;

    private Cursor cNews;
    private Cursor cMfs;
    private Cursor cGcs;
    private Cursor cBmi;
    private Cursor cMap;
    private Cursor cCage;
    private Cursor cAtria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forms_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataBaseHandlerMFS = new DataBaseHandlerMFS(this);
       /* Intent i=getIntent();

        savedInstanceState =i.getExtras();
        mfsfresult=savedInstanceState.getString("sendtoform");*/

        mfsBtn = (Button) findViewById(R.id.mfs_button);
        ewsBtn = (Button) findViewById(R.id.ews_button);
        atriaBtn = (Button) findViewById(R.id.atria_button);
        bmiBtn = (Button) findViewById(R.id.bmi_button);
        cageBtn = (Button) findViewById(R.id.cage_button);
        dashBtn = (Button) findViewById(R.id.dash_button);
        glossomBtn = (Button) findViewById(R.id.glossom_button);
        mapBtn = (Button) findViewById(R.id.map);


        mfsScore = (TextView) findViewById(R.id.mfsScore);
        newsScore = (TextView) findViewById(R.id.newsScore);
        atriaScore = (TextView) findViewById(R.id.atriScore);
        bmiScore = (TextView) findViewById(R.id.bmiScore);
        cageScore = (TextView) findViewById(R.id.cageScore);
        dashScore = (TextView) findViewById(R.id.dashScore);
        gcsScore = (TextView) findViewById(R.id.gcScore);
        mapScore = (TextView) findViewById(R.id.mapScore);


        mfsBtn.setOnClickListener(this);
        ewsBtn.setOnClickListener(this);
        atriaBtn.setOnClickListener(this);
        bmiBtn.setOnClickListener(this);
        cageBtn.setOnClickListener(this);
        dashBtn.setOnClickListener(this);
        glossomBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);


        //   openDatabase();
        try {


        } catch (Exception e) {
            Log.i("novalues", "no values");
        }


        try {
            newsList = dataBaseHandlerMFS.getNews(Pateints_List111.patId);
            Log.i("response news", newsList.get(0));
            String response = newsList.get(0);
            String resp[] = response.split(Pattern.quote("***"));
            String fscore = resp[8];
            Log.i("fscore", fscore);
            fresultNews = Integer.parseInt(fscore);
            if (fresultNews < 4 && fresultNews > 1) {
                newsScore.setBackgroundColor(Color.GREEN);
                newsScore.setText(fscore);

            } else if (fresultNews <= 6) {
                newsScore.setBackgroundColor(Color.YELLOW);
                newsScore.setText(fscore);
            } else if (fresultNews >= 7) {
                newsScore.setBackgroundColor(Color.RED);
                newsScore.setText(fscore);

            }
            cNews.close();
        } catch (Exception e1) {
        }
        try {
            mfsList = dataBaseHandlerMFS.getMfs(Pateints_List111.patId);
            Log.i("response mfs", mfsList.get(0));
            String response = mfsList.get(0);
            String resp[] = response.split(Pattern.quote("***"));
            String result = resp[7];
            Log.i("result mfs", result);
            fresultMfs = Integer.parseInt(result);
            if (fresultMfs <= 24) {
                mfsScore.setBackgroundColor(Color.GREEN);
                mfsScore.setText(result);

            } else if (fresultMfs <= 50 && fresultMfs >= 25) {
                mfsScore.setBackgroundColor(Color.YELLOW);
                mfsScore.setText(result);

            } else if (fresultMfs >= 50) {
                mfsScore.setBackgroundColor(Color.RED);
                mfsScore.setText(result);
            }
            cMfs.close();
        } catch (Exception e2) {
        }
        try {

            gcsList = dataBaseHandlerMFS.getGcs(Pateints_List111.patId);
            Log.i("response gcs", gcsList.get(0));
            String response = gcsList.get(0);
            String resp[] = response.split(Pattern.quote("***"));
            String gcsresult = resp[4];

            Log.i("gcsresult", gcsresult);
            String gcsresult1 = String.valueOf(gcsresult);
            gcsScore.setText(gcsresult1);
            cGcs.close();

        } catch (Exception e3) {
        }
        try {

            bmiList = dataBaseHandlerMFS.getBmi(Pateints_List111.patId);
            Log.i("response bmi", bmiList.get(0));
            String response = bmiList.get(0);
            String resp[] = response.split(Pattern.quote("***"));
            String bmiresult = resp[3];
            Log.i("bmiresult", bmiresult);

            fresultBmi = Float.parseFloat(bmiresult);
            String bmiresult1 = String.valueOf(fresultBmi);
            if (fresultBmi <= 25.0) {
                bmiScore.setBackgroundColor(Color.RED);
                bmiScore.setText(bmiresult1);

            }
            if (fresultBmi > 25.0 && fresultBmi <= 30.0) {
                bmiScore.setBackgroundColor(Color.YELLOW);
                bmiScore.setText(bmiresult1);

            } else if (fresultBmi > 30.0) {
                bmiScore.setBackgroundColor(Color.GREEN);
                bmiScore.setText(bmiresult1);

            }
            cBmi.close();
        } catch (Exception e4) {
        }
        try {


            mapList = dataBaseHandlerMFS.getMap(Pateints_List111.patId);
            Log.i("response bmi", mapList.get(0));
            String response = mapList.get(0);
            String resp[] = response.split(Pattern.quote("***"));
            String mapresult = resp[3];

            Log.i("mapresult", mapresult);

            fresultMap = Float.parseFloat(mapresult);
            String mapresult1 = String.valueOf(fresultMap);
            if (fresultMap >= 60.0) {
                mapScore.setBackgroundColor(Color.GREEN);
                mapScore.setText(mapresult1);

            }
            if (fresultMap >= 65.0) {
                mapScore.setBackgroundColor(Color.YELLOW);
                mapScore.setText(mapresult1);

            } else if (fresultMap < 65.0) {
                mapScore.setBackgroundColor(Color.RED);
                mapScore.setText(mapresult1);
            }
            cMap.close();
        } catch (Exception e5) {
        }
        try {

            cageList = dataBaseHandlerMFS.getCage(Pateints_List111.patId);
            Log.i("response bmi", cageList.get(0));
            String response = cageList.get(0);
            String resp[] = response.split(Pattern.quote("***"));
            String cageresult = resp[5];

            Log.i("cageresult", cageresult);


            fresultCage = Integer.parseInt(cageresult);
            String fresult1 = String.valueOf(fresultCage);
            if (fresultCage == 1) {

                cageScore.setText(fresult1);
            } else if (fresultCage == 2) {
                cageScore.setBackgroundColor(Color.GREEN);
                cageScore.setText(fresult1);

            } else if (fresultCage == 3) {
                cageScore.setBackgroundColor(Color.GREEN);
                cageScore.setText(fresult1);

            } else if (fresultCage == 4) {
                cageScore.setBackgroundColor(Color.GREEN);
                cageScore.setText(fresult1);

            }
            cCage.close();
        } catch (Exception e6) {
        }
        try {

            atriaList = dataBaseHandlerMFS.getAtria(Pateints_List111.patId);
            Log.i("response bmi", atriaList.get(0));
            String response = atriaList.get(0);
            String resp[] = response.split(Pattern.quote("***"));
            String f1 = resp[6];

            Log.i("atria result", f1);

            fresultAtriya = Integer.parseInt(f1);
            String fresult1 = String.valueOf(fresultAtriya);
            if (fresultAtriya <= 3) {
                atriaScore.setBackgroundColor(Color.GREEN);
                atriaScore.setText(fresult1);

            } else if (fresultAtriya == 4) {
                atriaScore.setBackgroundColor(Color.YELLOW);
                atriaScore.setText(fresult1);

            } else if (fresultAtriya > 4) {
                atriaScore.setBackgroundColor(Color.RED);
                atriaScore.setText(fresult1);

            }
            cAtria.close();

        } catch (Exception e7) {
        }


    }

    protected void openDatabase() {
        sqLiteDatabase = openOrCreateDatabase("HealthApp", Context.MODE_PRIVATE, null);
    }

    protected void moveNextNews() {
        if (!cNews.isLast())
            cNews.moveToNext();
        String fscore = cNews.getString(8);
        Log.i("fscore", fscore);
    }

    protected void moveNextMfs() {
        if (!cMfs.isLast())
            cMfs.moveToNext();
    }

    protected void moveNextAtria() {
        if (!cAtria.isLast())
            cAtria.moveToNext();
    }

    protected void moveNextBmi() {
        if (!cBmi.isLast())
            cBmi.moveToNext();
    }

    protected void moveNextCage() {
        if (!cCage.isLast())
            cCage.moveToNext();
    }

    protected void moveNextGsc() {
        if (!cGcs.isLast())
            cGcs.moveToNext();
    }

    protected void moveNextMap() {
        if (!cMap.isLast())
            cMap.moveToNext();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.mfs_button:
                Intent in = new Intent(FormsActivity.this, MFS.class);
                startActivity(in);
                finish();
                break;

            case R.id.ews_button:
                Intent ewsin = new Intent(FormsActivity.this, EWSActivity1.class);
                startActivity(ewsin);
                finish();
                break;

            case R.id.atria_button:
                Intent inatria = new Intent(FormsActivity.this, ATRIA.class);
                startActivity(inatria);
                finish();
                break;

            case R.id.bmi_button:
                Intent inbmi = new Intent(FormsActivity.this, BmiActivity.class);
                startActivity(inbmi);
                finish();
                break;

            case R.id.cage_button:
                Intent incage = new Intent(FormsActivity.this, CageActivity.class);
                startActivity(incage);
                finish();
                break;

            case R.id.dash_button:
                Intent indash = new Intent(FormsActivity.this, DashActivity.class);
                startActivity(indash);
                finish();
                break;

            case R.id.glossom_button:
                Intent ingloss = new Intent(FormsActivity.this, GcsActivity.class);
                startActivity(ingloss);
                break;

            case R.id.map:
                Intent ingloss1 = new Intent(FormsActivity.this, MapActivity.class);
                startActivity(ingloss1);
                finish();
                break;

        }

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
           // onBackPressed();
            Intent i = new Intent(FormsActivity.this, Activity_Main.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.patient_notes) {
            Intent in = new Intent(FormsActivity.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(FormsActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(FormsActivity.this, Activity_Main.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
