package com.btranz.healthplus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.parse.ParseUser;

import com.btranz.healthplus.activity.AddPatientActivity;
import com.btranz.healthplus.alive_ecg.HeartMonitorPreferences;
import com.btranz.healthplus.login.LoginActivity;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterPreferences;
import com.btranz.healthplus.wizards.WizardAliveXActivity;
import com.btranz.healthplus.wizards.WizardPulseOxiActivity;



public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener
{
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttons();

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
    }


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main_menu);

        CheckBox PO_Checkbox;
        PO_Checkbox = (CheckBox) findViewById(R.id.PO_Checkbox);
        PO_Checkbox.setClickable(false);
        CheckBox HM_Checkbox;
        HM_Checkbox = (CheckBox) findViewById(R.id.HM_Checkbox);
        HM_Checkbox.setClickable(false);



        SharedPreferences prefsHM = HeartMonitorPreferences.getSharedPreferences(this);

        String mHMAddress = prefsHM.getString(HeartMonitorPreferences.PREF_HM_BTADDRESS, "");

        if (mHMAddress.length() > 0) //if the heart monitor has been selected
            HM_Checkbox.setChecked(true);
        else
            HM_Checkbox.setChecked(false);

        SharedPreferences prefsPO = PulseOximeterPreferences.getSharedPreferences(this);

        String mPOAddress = prefsPO.getString(PulseOximeterPreferences.PREF_PO_BTADDRESS, "");

        if (mPOAddress.length() > 0) //if the pulse oximeter has been selected
            PO_Checkbox.setChecked(true);
        else
            PO_Checkbox.setChecked(false);

        buttons();
    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckBox PO_Checkbox;
        PO_Checkbox = (CheckBox) findViewById(R.id.PO_Checkbox);
        PO_Checkbox.setClickable(false);
        CheckBox HM_Checkbox;
        HM_Checkbox = (CheckBox) findViewById(R.id.HM_Checkbox);
        HM_Checkbox.setClickable(false);


        // Look up preferences to see what connections to start
        SharedPreferences prefsHM = HeartMonitorPreferences.getSharedPreferences(this);

        String mHMAddress = prefsHM.getString(HeartMonitorPreferences.PREF_HM_BTADDRESS, "");

        if (mHMAddress.length() > 0) //if the heart monitor has been selected
            HM_Checkbox.setChecked(true);
        else
            HM_Checkbox.setChecked(false);

        // Look up preferences to see what connections to start
        SharedPreferences prefsPO = PulseOximeterPreferences.getSharedPreferences(this);

        String mPOAddress = prefsPO.getString(PulseOximeterPreferences.PREF_PO_BTADDRESS, "");

        if (mPOAddress.length() > 0) //if the pulse oximeter has been selected
            PO_Checkbox.setChecked(true);
        else
            PO_Checkbox.setChecked(false);
    }

    private void buttons()
    {
        ImageButton button_ECG;
        button_ECG = (ImageButton) findViewById(R.id.button_ECG);
        button_ECG.setOnClickListener(this);

        ImageButton button_PulseOxy;
        button_PulseOxy = (ImageButton) findViewById(R.id.button_PulseOxy);
        button_PulseOxy.setOnClickListener(this);

        ImageButton button_Monitoring;
        button_Monitoring = (ImageButton) findViewById(R.id.button_monitoring);
        button_Monitoring.setOnClickListener(this);

        ImageButton button_History;
        button_History = (ImageButton) findViewById(R.id.button_history);
        button_History.setOnClickListener(this);

    }

    private void ButtonClick_ECG()
    {
        Intent wizard = new Intent(MainMenuActivity.this, WizardAliveXActivity.class);
        startActivity(wizard);
    }

    private void ButtonClick_PulseOxy()
    {
        Intent intent = new Intent(MainMenuActivity.this, WizardPulseOxiActivity.class);
        startActivity(intent);

    }


    private void ButtonClick_Monitoing()
    {
        Intent intent = new Intent(MainMenuActivity.this, MonitoringActivity.class);
        startActivity(intent);
    }

    private void ButtonClick_History()
    {
        Intent intent = new Intent(MainMenuActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    private void ActionBar_Settings()
    {
        Intent intent = new Intent(MainMenuActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    private void ActionBar_Logout()
    {
        ParseUser.logOut();

        //remove saved username and password
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.contains(LoginActivity.USERNAME))
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(LoginActivity.USERNAME);
            editor.remove(LoginActivity.PASSWORD);
            editor.apply();
        }

        Intent takeUserToMainActivity = new Intent(MainMenuActivity.this, MainActivity.class);
        startActivity(takeUserToMainActivity);
        finish();
    }

    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.button_ECG: v.setBackgroundResource(R.drawable.clicked_red); ButtonClick_ECG();  break;
            case R.id.button_PulseOxy: v.setBackgroundResource(R.drawable.clicked_blue); ButtonClick_PulseOxy(); break;
            case R.id.button_monitoring: v.setBackgroundResource(R.drawable.clicked_green); ButtonClick_Monitoing(); break;
            case R.id.button_history: v.setBackgroundResource(R.drawable.clicked_yellow); ButtonClick_History(); break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.logout)
        {
            Intent i = new Intent(MainMenuActivity.this, com.btranz.healthplus.activity.LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            ActionBar_Settings();
        }
        if (id == R.id.add_patient) {
            Intent in = new Intent(MainMenuActivity.this, AddPatientActivity.class);
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

                if (event2.getX() > event1.getX()) // right swipe
                {
                    Intent intent = new Intent(MainMenuActivity.this, HistoryActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);

                }

                else // left swipe
                {
                    Intent intent = new Intent(MainMenuActivity.this, MonitoringActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }

            return true;
        }
    }
}
