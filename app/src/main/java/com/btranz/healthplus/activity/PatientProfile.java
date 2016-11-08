package com.btranz.healthplus.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;
import com.btranz.healthplus.adapter.PatientAdapter;
import com.btranz.healthplus.model.PatientModel;


public class PatientProfile extends android.app.Fragment {
ListView listPat;


    String Patient1;

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
    public PatientProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_list, container, false);
        listPat=(ListView)rootView.findViewById(R.id.patient_listView);



        databaseHandler=new DatabaseHandler(getActivity());
        try {
            listPat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent in = new Intent(getActivity(), PatientDetails1.class);
try {
    startActivity(in);
}catch (Exception e){
    e.printStackTrace();
}
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        getPatList();

        PatientAdapter patientAdapter=new PatientAdapter(getActivity(),allValues);
         listPat.setAdapter(patientAdapter);




        return rootView;
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
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
