package com.btranz.healthplus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.btranz.healthplus.*;
import com.btranz.healthplus.Handler.DatabaseHandler;

/**
 * Created by User_Sajid on 4/21/2016.
 */
public class AddPatientActivity extends Activity implements View.OnClickListener {
    TextView fnameTxtv, lnameTxtv, healthIdTxtv,emailTxtV, addressTxtv, ageTxtv, wardTxtv, roomTxtv, fallsHistoryTxtv, pcpTxtv,pnumberTxtv;
     private  static String fnameStr, lnameStr, healthStr, addressStr, ageStr, wardStr, roomStr, fallsStr, emailStr,pcpStr, sexStr,pnumberStr;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    DatabaseHandler dbHandler;
    Button addBtn;
    public static List<String> patList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient_activity);
        fnameTxtv = (TextView) findViewById(R.id.firstname_edit_txt);
        lnameTxtv = (TextView) findViewById(R.id.lasttname_edit_txt);
        healthIdTxtv = (TextView) findViewById(R.id.health_id_edit_txt);
        pnumberTxtv=(TextView)findViewById(R.id.phonenumber);
        addressTxtv = (TextView) findViewById(R.id.address_edit_txt);
        ageTxtv = (TextView) findViewById(R.id.age_edit_txt);
        wardTxtv = (TextView) findViewById(R.id.ward_edit_txt);
        roomTxtv = (TextView) findViewById(R.id.room_edit_txt);
        fallsHistoryTxtv = (TextView) findViewById(R.id.falls_history_edit_txt);
        pcpTxtv = (TextView) findViewById(R.id.pcp_edit_txt);
        emailTxtV = (TextView) findViewById(R.id.emailEditText);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
        dbHandler = new DatabaseHandler(this);
        addBtn=(Button)findViewById(R.id.add_btn);
        addBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                validateData();
                break;
        }
    }

    public void validateData() {

        try {
            fnameStr = fnameTxtv.getText().toString();
            lnameStr = lnameTxtv.getText().toString();
            healthStr = healthIdTxtv.getText().toString();
            pnumberStr = pnumberTxtv.getText().toString();
            addressStr = addressTxtv.getText().toString();
            ageStr = ageTxtv.getText().toString();
            wardStr = wardTxtv.getText().toString();
            roomStr = roomTxtv.getText().toString();
            fallsStr = fallsHistoryTxtv.getText().toString();
            pcpStr = pcpTxtv.getText().toString();
            emailStr=emailTxtV.getText().toString();
            int selectedId = radioSexGroup.getCheckedRadioButtonId();
            radioSexButton = (RadioButton) findViewById(selectedId);
            sexStr = radioSexButton.getText().toString();
        }catch (Exception e){
            e.printStackTrace();
        }



        if (fnameStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "First Name Required", Toast.LENGTH_SHORT).show();
        } else if (lnameStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Last Name Required", Toast.LENGTH_SHORT).show();
        }
        /*else if (addressStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Address Required", Toast.LENGTH_SHORT).show();
        }*/else if (pnumberStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Phone Number Required", Toast.LENGTH_SHORT).show();
        } else if (healthStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Health Id Required", Toast.LENGTH_SHORT).show();
        }/* else if (emailStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Email Id Required", Toast.LENGTH_SHORT).show();
        } */else if (ageStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Age Required", Toast.LENGTH_SHORT).show();
        }/* else if (wardStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Ward Required", Toast.LENGTH_SHORT).show();
        } else if (roomStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Room Required", Toast.LENGTH_SHORT).show();
        } else if (fallsStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "Falls History Required", Toast.LENGTH_SHORT).show();
        } else if (pcpStr.equals("")) {
            Toast.makeText(AddPatientActivity.this, "PCP Required", Toast.LENGTH_SHORT).show();
        } */else {

        patList=dbHandler.patExits(healthStr);
            if(patList.size()>0){
                Toast.makeText(AddPatientActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();
            }else
            {

                System.out.println("in else method in Add patientActivity : ");

                if(addressStr.equals("")){
                    addressStr="NA";
                }else{
                    addressStr = addressTxtv.getText().toString();
                }
                if(roomStr.equals("")){
                    roomStr="0";
                }else{
                    roomStr = roomTxtv.getText().toString();
                }
                if(wardStr.equals("")){
                    wardStr="0";
                }else{
                    wardStr = wardTxtv.getText().toString();
                }
                if(pcpStr.equals("")){
                    pcpStr="NA";
                }else{
                    pcpStr = pcpTxtv.getText().toString();
                }
                if(fallsStr.equals("")){
                    fallsStr="NA";
                }else{
                    fallsStr = fallsHistoryTxtv.getText().toString();
                }
                if(emailStr.equals("")){
                    emailStr="NA";
                }else{
                    emailStr=emailTxtV.getText().toString();
                }

                dbHandler.patReg(fnameStr,lnameStr,healthStr,addressStr,ageStr,roomStr,sexStr,wardStr,pcpStr,fallsStr,pnumberStr,emailStr);
                Intent in=new Intent(AddPatientActivity.this, Pateints_List111.class);
                startActivity(in);
                finish();
                Toast.makeText(AddPatientActivity.this, "Patient Added", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
