package com.btranz.healthplus.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.btranz.healthplus.R;


public class Activity_Main extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
//Fragment fragment=null;
    String PATID,UNAME,HID;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pID = "patId";
    public static final String uName = "userName";
    public static final String hID = "hId";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        PatientDetails patientDetails=new PatientDetails();
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, patientDetails );
        fragmentTransaction.commit();

       // sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View header = LayoutInflater.from(this).inflate(R.layout.drawer_header, null);
        navigationView.addHeaderView(header);

       /* SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            editor.clear();
        }catch(Exception e){
            e.printStackTrace();
        }
        PATID= sharedpreferences.getString(pID,"");
        UNAME=  sharedpreferences.getString(uName,"");
        HID=   sharedpreferences.getString(hID,"");

        System.out.println(" PATID in Activity_Main :" + PATID);
        System.out.println(" UNAME in Activity_Main :" + UNAME);
*/

        TextView username = (TextView) header.findViewById(R.id.user_name);
        TextView hId = (TextView) header.findViewById(R.id.health_id);
        username.setText("Name  : "+Pateints_List111.userName);
        hId.setText("Health Id : "+Pateints_List111.hId );
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
               Fragment fragment=null;
                String title = getString(R.string.title_patient_profile);
                getSupportActionBar().setTitle(title);
                switch (menuItem.getItemId()) {

                    default:
                        fragment=new PatientDetails();
                        break;

                    case R.id.Patientdetails:
                        fragment=new PatientDetails();
                        fragment=new PatientDetails();
                        FragmentManager fragmentManager = getFragmentManager();
                        title = getString(R.string.title_patient_profile);
                        android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();

                        break;

                    case R.id.Healthdata:
                        fragment= new HealthData();
                        title = getString(R.string.title_health_data);
                        break;

                    case R.id.Clinicaldescription:
                        fragment= new ClinicalDescription();
                        title = getString(R.string.title_clinical_support);

                        break;

                    case R.id.Checkheartrate:
                        fragment= new HeartRate();
                        title = getString(R.string.title_check_hr);
                        break;
                  /*  case R.id.Other:
                        fragment= new OtherFragment();
                        title = getString(R.string.title_other);
                        break;*/
                    case R.id.manual:
                        fragment= new ManualEnter();
                        title = getString(R.string.title_manual);
                        break;

                    case R.id.Contact:
                        fragment= new DocContact();
                        title = getString(R.string.title_contact);
                        break;

                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();

                    // set the toolbar title
                    getSupportActionBar().setTitle(title);
                }
                return true;
            }
        });
    }



    private void updateDisplay(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_body, fragment)
                .commit();
      /*  FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        if (id == R.id.patient_notes) {
            Intent in=new Intent(Activity_Main.this,Notes.class);
            startActivity(in);
            return true;
        }
        if(id==R.id.logout){
            Intent i= new Intent(Activity_Main.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in=new Intent(Activity_Main.this,Pateints_List111.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();
    }
}
