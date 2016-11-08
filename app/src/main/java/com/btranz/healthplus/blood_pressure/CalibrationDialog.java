package com.btranz.healthplus.blood_pressure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.btranz.healthplus.MonitoringActivity;
import com.btranz.healthplus.R;

public class CalibrationDialog extends Activity implements View.OnClickListener {

    public final static String SYSTOLIC = "SystolicValue";
    EditText systolic;
    EditText diastolic;
    Button butOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration_dialog);

        systolic = (EditText) findViewById(R.id.editTextSystolic);
        diastolic = (EditText) findViewById(R.id.editTextDiastolic);
        butOK = (Button) findViewById(R.id.buttonConfirmCalibration);
        butOK.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calibration_dialog, menu);
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonConfirmCalibration:
                if (systolic.getText().length() > 0) //If a value has been entered into the systolic field
                {
                    Intent intent = new Intent(this, MonitoringActivity.class);
                    intent.putExtra(SYSTOLIC, Float.valueOf(systolic.getText().toString()));
                    setResult(Activity.RESULT_OK, intent);
                }
                else
                    setResult(Activity.RESULT_CANCELED);

        }
        finish();
    }
}
