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
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterPreferences;

public class WizardPulseOxiActivity extends AppCompatActivity {

    public static final int PULSE_OXIMETER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_pulse_oxi);
        findViewById(R.id.Next_WPO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WizardPulseOxiActivity.this, BTDeviceListActivity.class);
                intent.putExtra(BTDeviceListActivity.REQUEST_CODE, PULSE_OXIMETER);
                startActivityForResult(intent, PULSE_OXIMETER);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wizard__pulse_oxy, menu);
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
            SharedPreferences prefsPO = PulseOximeterPreferences.getSharedPreferences(this);
            SharedPreferences.Editor editor = prefsPO.edit();
            editor.putString(PulseOximeterPreferences.PREF_PO_BTNAME,data.getStringExtra(BTDeviceListActivity.EXTRA_DEVICE_NAME));
            editor.putString(PulseOximeterPreferences.PREF_PO_BTADDRESS,data.getStringExtra(BTDeviceListActivity.EXTRA_DEVICE_ADDRESS));
            editor.apply();
            finish();
        }
    }
}
