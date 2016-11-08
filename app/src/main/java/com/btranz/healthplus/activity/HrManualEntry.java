package com.btranz.healthplus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;


public class HrManualEntry extends AppCompatActivity {


    Button submitButton;
    EditText hrEdit,osEdit;
    DataBaseHandlerMFS dataBaseHandlerMFS;
    String date;
    public HrManualEntry() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_manual_entry);
        hrEdit=(EditText)findViewById(R.id.hr_edit);
        osEdit=(EditText)findViewById(R.id.os_edit);

        submitButton=(Button)findViewById(R.id.hrButton);
        dataBaseHandlerMFS=new DataBaseHandlerMFS(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hrValue=hrEdit.getText().toString();
                String spoValue=osEdit.getText().toString();

                if(hrValue.equals("")){
                    Toast.makeText(HrManualEntry.this, "Heart Rate Required", Toast.LENGTH_SHORT).show();
                }else if(spoValue.equals("")){
                    Toast.makeText(HrManualEntry.this, "Oxygen Saturation Required", Toast.LENGTH_SHORT).show();

                }
                else{

                    dataBaseHandlerMFS.inserHeartRate(hrValue,spoValue,Pateints_List111.patId,date);
                    Toast.makeText(HrManualEntry.this, "Saved",
                            Toast.LENGTH_SHORT).show();
                    hrEdit.setText("");
                    osEdit.setText("");

                }

            }
        });

    }


}
