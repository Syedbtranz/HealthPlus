package com.btranz.healthplus.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;
import com.btranz.healthplus.adapter.MyNotesAdapter;
import com.btranz.healthplus.model.PatientModel;

/**
 * Created by User_Sajid on 5/24/2016.
 */
public class MyNotes extends AppCompatActivity {

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

    private static final String SELECT_SQL = "SELECT * FROM patient_registration";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;
    String firstname,lastname,phealthid;
    DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listPat = (ListView) findViewById(R.id.patient_listView);
        nodataLayout=(LinearLayout)findViewById(R.id.no_dat_layout);
        searchEdit=(EditText)findViewById(R.id.pat_search_edit);
        searchBtn=(Button)findViewById(R.id.btn_search);

        databaseHandler = new DatabaseHandler(getApplicationContext());
        getNotesList();

        MyNotesAdapter patientAdapter = new MyNotesAdapter(MyNotes.this, allValues);
        listPat.setAdapter(patientAdapter);
    }

    protected void openDatabase() {
        sqLiteDatabase = openOrCreateDatabase("HealthApp", Context.MODE_PRIVATE, null);
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
            Intent in = new Intent(MyNotes.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(MyNotes.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();

        }
        return true;
    }




    public void getNotesList() {

        try {
            p_idList.clear();
            fnameList.clear();
            lnameList.clear();

        } catch (Exception e) {
        }
        listofPat = databaseHandler.noteList(Pateints_List111.patId);
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
            lnameStr = patArr[3];
            lnameList.add(lnameStr);
        }
        for (int i = 0; i < fnameList.size(); i++) {
            PatientModel patientModel = new PatientModel();
            patientModel.setFnameStr(fnameList.get(i));
            patientModel.setLnameStr(lnameList.get(i));

            allValues.add(patientModel);
        }
    }
public void searchedNotesList(){
    String searchText=searchEdit.getText().toString();
    try {
        p_idList.clear();
        fnameList.clear();
        lnameList.clear();
        listofPat.clear();

    } catch (Exception e) {
    }

    listofPat = databaseHandler.noteSearch(searchText);

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

    }
    for (int i = 0; i < fnameList.size(); i++) {
        PatientModel patientModel = new PatientModel();
        patientModel.setFnameStr(fnameList.get(i));
        patientModel.setLnameStr(lnameList.get(i));

        allValues.add(patientModel);
    }
    MyNotesAdapter patientAdapter = new MyNotesAdapter(MyNotes.this, allValues);
    listPat.setAdapter(patientAdapter);
}
    @Override
    public void onBackPressed() {

       /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Do you want to Exit!");
        alertDialogBuilder
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }*/
        super.onBackPressed();

       /* if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        }, 2000);
*/
}

}

