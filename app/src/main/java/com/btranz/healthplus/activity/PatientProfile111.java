package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;
import com.btranz.healthplus.adapter.PatientAdapter;
import com.btranz.healthplus.model.PatientModel;

/**
 * Created by User_Sajid on 5/17/2016.
 */
public class PatientProfile111 extends AppCompatActivity {


    ListView listPat;
    String fnameStr,lnameStr,hidStr,addressStr,ageStr;
    ArrayList<String> fnameList=new ArrayList<String>();
    ArrayList<String> lnameList=new ArrayList<String>();
    ArrayList<String> hidList=new ArrayList<String>();
    ArrayList<String> addressList=new ArrayList<String>();
    ArrayList<String> roomList=new ArrayList<String>();
    ArrayList<String> wardList=new ArrayList<String>();
    ArrayList<String> pcpList=new ArrayList<String>();
    ArrayList<String> fallshisList=new ArrayList<String>();
    ArrayList<String> sexList=new ArrayList<String>();
    ArrayList<String> ageList=new ArrayList<String>();
    ArrayList<PatientModel> allValues=new ArrayList<PatientModel>();

    public List<String> listofPat=new ArrayList<String>();
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.patient_list);
        databaseHandler=new DatabaseHandler(PatientProfile111.this);

        listPat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(PatientProfile111.this,Activity_Main.class);
                startActivity(in);
            }
        });


        getPatList();

        PatientAdapter patientAdapter=new PatientAdapter(PatientProfile111.this,allValues);
        listPat.setAdapter(patientAdapter);
    }
    public void getPatList(){

        try{
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

        }catch (Exception e){}
        listofPat=databaseHandler.patList();

        for(int i=0;i<listofPat.size();i++){
            String patListStr=listofPat.get(i);
            Log.i("patListStr",patListStr);
            String[] patArr=patListStr.split(Pattern.quote("***"));

            fnameStr=patArr[0];
            Log.i("fnameStr",fnameStr);
            fnameList.add(fnameStr);
            lnameStr=patArr[1];
            lnameList.add(lnameStr);
            hidStr=patArr[2];
            hidList.add(hidStr);
            addressStr=patArr[3];
            addressList.add(addressStr);
            ageStr=patArr[4];
            ageList.add(ageStr);
            String sexStr=patArr[5];
            sexList.add(sexStr);
            String wardStr=patArr[6];
            wardList.add(wardStr);
            String roomStr=patArr[7];
            roomList.add(roomStr);
            String fallsHisStr=patArr[8];
            fallshisList.add(fallsHisStr);
            String pcpStr=patArr[9];
            pcpList.add(pcpStr);
        }
        for(int i=0;i<fnameList.size();i++){
            PatientModel patientModel=new PatientModel();
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
}
}
