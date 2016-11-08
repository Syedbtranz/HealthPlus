package com.btranz.healthplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.btranz.healthplus.alive_ecg.HeartMonitorPreferences;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterPreferences;

//TODO Andries
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case (R.id.buttonHeartMonitorPrefs) : startActivity(new Intent(SettingActivity.this, HeartMonitorPreferences.class)); break;
            case (R.id.buttonPulseOximeterPrefs) : startActivity(new Intent(SettingActivity.this, PulseOximeterPreferences.class)); break;
        }

    }
}
