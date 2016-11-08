package com.btranz.healthplus.wizards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.btranz.healthplus.BTDeviceListActivity;
import com.btranz.healthplus.R;
import com.btranz.healthplus.alive_ecg.HeartMonitorPreferences;

public class WizardAliveX2Activity extends AppCompatActivity {

    public static final int HEART_MONITOR = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard__alive_x_2);
        findViewById(R.id.Next_WHM2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WizardAliveX2Activity.this, BTDeviceListActivity.class);
                intent.putExtra(BTDeviceListActivity.REQUEST_CODE, HEART_MONITOR);
                startActivityForResult(intent, HEART_MONITOR);
               
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wizard__alive_x_2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            SharedPreferences prefsHM = HeartMonitorPreferences.getSharedPreferences(this);
            SharedPreferences.Editor editor = prefsHM.edit();
            editor.putString(HeartMonitorPreferences.PREF_HM_BTNAME,data.getStringExtra(BTDeviceListActivity.EXTRA_DEVICE_NAME));
            editor.putString(HeartMonitorPreferences.PREF_HM_BTADDRESS,data.getStringExtra(BTDeviceListActivity.EXTRA_DEVICE_ADDRESS));
            editor.apply();
            finish();
        }
    }
}
