package com.btranz.healthplus.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;
import com.btranz.healthplus.adapter.PatientAdapter;
import com.btranz.healthplus.model.PatientModel;

/**
 * Created by all on 10/27/2016.
 */

public class Patient_SearchedList extends AppCompatActivity {

    Toolbar toolbar = null;
    ListView listPat;
    LinearLayout nodataLayout;
    boolean doubleBackToExitPressedOnce = false;
    public static String patId;
    public static String userName;
    public static String hId;
    String p_id,fnameStr, lnameStr, hidStr, addressStr, ageStr;
    ArrayList<String> p_idList = new ArrayList<String>();
    ArrayList<String> fnameList = new ArrayList<String>();
    ArrayList<String> lnameList = new ArrayList<String>();
    ArrayList<String> hidList = new ArrayList<String>();
    ArrayList<String> addressList = new ArrayList<String>();
    ArrayList<String> roomList = new ArrayList<String>();
    ArrayList<String> wardList = new ArrayList<String>();
    ArrayList<String> pcpList = new ArrayList<String>();
    ArrayList<String> fallshisList = new ArrayList<String>();
    ArrayList<String> sexList = new ArrayList<String>();
    ArrayList<String> ageList = new ArrayList<String>();
    ArrayList<PatientModel> allValues = new ArrayList<PatientModel>();

    public List<String> listofPat = new ArrayList<String>();

    private Toolbar mToolbar;
    EditText searchEdit;
    Button searchBtn;

    private static long back_pressed;

    private static final String SELECT_SQL = "SELECT * FROM patient_registration";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;
    String firstname,lastname,phealthid;
    DatabaseHandler databaseHandler;
    String SearchEdit;
    String searchText;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pID = "patId";
    public static final String uName = "userName";
    public static final String hID = "hId";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.patient_searchedlist);
        listPat = (ListView) findViewById(R.id.patient_listView);
        nodataLayout=(LinearLayout)findViewById(R.id.no_dat_layout);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Intent i=getIntent();
        savedInstanceState =i.getExtras();
        searchText= savedInstanceState.getString("SearchedText");
       // Log.i("getIntent",SearchEdit);
        System.out.println("getIntent  : " + searchText);

        databaseHandler = new DatabaseHandler(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);


        searchedPatList();
      /*  PatientAdapter patientAdapter = new PatientAdapter(Patient_SearchedList.this, allValues);
        listPat.setAdapter(patientAdapter);*/

        listPat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*c = sqLiteDatabase.rawQuery(SELECT_SQL, null);
                c.moveToLast();

                moveNext();
                showRecords();*/
               // getPatList();


                Intent in = new Intent(Patient_SearchedList.this, Activity_Main.class);

             /*   patId=p_idList.get(i);
                userName=fnameList.get(i);
                hId=hidList.get(i);

                patId= sharedpreferences.getString(pID,patId);
                userName=  sharedpreferences.getString(uName,userName);
                hId=   sharedpreferences.getString(hID,hId);
*/
                SharedPreferences.Editor editor = sharedpreferences.edit();

                try {
                    editor.clear();
                }catch(Exception e){
                    e.printStackTrace();
                }


                patId=p_idList.get(i);
                userName=fnameList.get(i);
                hId=hidList.get(i);

          //      SharedPreferences.Editor editor = sharedpreferences.edit();



                editor.putString(pID, patId);
                editor.putString(uName, userName);
                editor.putString(hID, hId);
                editor.commit();

               /* in.putExtra("patId",patId);
                in.putExtra("userName",userName);
                in.putExtra("hId",hId);
*/
                System.out.println(" patId in SearchedList :" + patId);
                System.out.println(" userName in SearchedList :" + userName);
                System.out.println(" hId in SearchedList :" + hId);

                //startActivity(in);

                Toast.makeText(Patient_SearchedList.this, patId, Toast.LENGTH_SHORT).show();
                startActivity(in);
            }
        });


    }


    protected void openDatabase() {
        sqLiteDatabase = openOrCreateDatabase("HealthApp", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {
        try {
            firstname = c.getString(1);
            Log.i("firstname", firstname);
            lastname = c.getString(2);
            Log.i("lastname", lastname);
            phealthid = c.getString(3);
            Log.i("phealthid", phealthid);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    protected void moveNext() {
        if (!c.isLast())
            c.moveToNext();
        showRecords();
    }

    public void searchedPatList(){
       // String searchText=searchEdit.getText().toString();
        try {
            p_idList.clear();
            fnameList.clear();
            lnameList.clear();
            ageList.clear();
            pcpList.clear();
            roomList.clear();
            fallshisList.clear();
            addressList.clear();
            sexList.clear();
            wardList.clear();
            hidList.clear();
            allValues.clear();
            listofPat.clear();

        } catch (Exception e) {
        }

        listofPat = databaseHandler.patSearch(searchText);

        if(listofPat.size()<=0){
            listPat.setVisibility(View.GONE);
            nodataLayout.setVisibility(View.VISIBLE);

        }
        for (int i = 0; i < listofPat.size(); i++) {
            String patListStr = listofPat.get(i);
            Log.i("patListStr", patListStr);
            String[] patArr = patListStr.split(Pattern.quote("***"));
            p_id = patArr[0];
            p_idList.add(p_id);
            Log.i("p_id", p_id);
            fnameStr = patArr[1];
            Log.i("fnameStr", fnameStr);
            fnameList.add(fnameStr);
            lnameStr = patArr[2];
            lnameList.add(lnameStr);
            hidStr = patArr[3];
            hidList.add(hidStr);
            addressStr = patArr[4];
            addressList.add(addressStr);
            ageStr = patArr[5];
            ageList.add(ageStr);
            String sexStr = patArr[6];
            sexList.add(sexStr);
            String wardStr = patArr[7];
            wardList.add(wardStr);
            String roomStr = patArr[8];
            roomList.add(roomStr);
            String fallsHisStr = patArr[9];
            fallshisList.add(fallsHisStr);
            String pcpStr = patArr[10];
            pcpList.add(pcpStr);
        }
        for (int i = 0; i < fnameList.size(); i++) {
            PatientModel patientModel = new PatientModel();
            patientModel.setFnameStr(fnameList.get(i));
            patientModel.setLnameStr(lnameList.get(i));
            patientModel.setHidStr(hidList.get(i));
            patientModel.setAddressStr(addressList.get(i));
            patientModel.setRoomStr(roomList.get(i));
            patientModel.setWardStr(wardList.get(i));
            patientModel.setSexStr(sexList.get(i));
            patientModel.setAgeStr(ageList.get(i));
            patientModel.setFallsHisStr(fallshisList.get(i));
            patientModel.setPcpStr(pcpList.get(i));
            allValues.add(patientModel);
        }
        PatientAdapter patientAdapter = new PatientAdapter(Patient_SearchedList.this, allValues);
        listPat.setAdapter(patientAdapter);
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
            Intent in = new Intent(Patient_SearchedList.this, AddPatientActivity.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(Patient_SearchedList.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

}
