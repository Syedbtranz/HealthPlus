package com.btranz.healthplus.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;


public class HealthData extends android.app.Fragment {
TextView bpsTxtv,bpdTxtv,prTxtv,osTxtv,temTxtv,rrTxtv,historyTxtv;
    int fresult;
    List<String> mapList=new ArrayList<String>();
    List<String> hrList=new ArrayList<String>();
    List<String> otherList=new ArrayList<String>();
    DataBaseHandlerMFS dataBaseHandlerMFS;




    public HealthData() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_health_data, container, false);
        bpsTxtv=(TextView)rootView.findViewById(R.id.bps_txtv);
        bpdTxtv=(TextView)rootView.findViewById(R.id.bpd_txtv);
        prTxtv=(TextView)rootView.findViewById(R.id.pr_txtv);
        osTxtv=(TextView)rootView.findViewById(R.id.os_txtv);
        temTxtv=(TextView)rootView.findViewById(R.id.tem_txtv);
        rrTxtv=(TextView)rootView.findViewById(R.id.rr_txtv);
        historyTxtv=(TextView)rootView.findViewById(R.id.history_txt_v);
        dataBaseHandlerMFS=new DataBaseHandlerMFS(getActivity());
        historyTxtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),VitalsDataHistory.class);
                startActivity(in);
            }
        });

try{
    mapList = dataBaseHandlerMFS.getMap(Pateints_List111.patId);
    Log.i("response map", mapList.get(0));
    String response=mapList.get(0);
    String resp[]=response.split(Pattern.quote("***"));



    String bps= resp[1];
    String bpd= resp[2];

    Log.i("bps",bps);
    Log.i("bpd",bpd);





    int fresult=Integer.parseInt(bps);
    String fresult1=String.valueOf(fresult);
    if(fresult<100){
        bpsTxtv.setBackgroundColor(Color.YELLOW);
        bpsTxtv.setText(fresult1);

    }else if(fresult>140){
        bpsTxtv.setBackgroundColor(Color.RED);
        bpsTxtv.setText(fresult1);

    }else if (fresult >= 100 && fresult <= 140) {
        bpsTxtv.setBackgroundColor(Color.GREEN);
        bpsTxtv.setText(fresult1);
    }




    int bpdResult=Integer.parseInt(bpd);
    String bpdResult1=String.valueOf(bpdResult);
    if(bpdResult<60){
        bpdTxtv.setBackgroundColor(Color.YELLOW);
        bpdTxtv.setText(bpdResult1);

    }else if(bpdResult>80){
        bpdTxtv.setBackgroundColor(Color.RED);
        bpdTxtv.setText(bpdResult1);

    }else if (bpdResult >= 60 && bpdResult <= 80) {
        bpdTxtv.setBackgroundColor(Color.GREEN);
        bpdTxtv.setText(bpdResult1);
    }

}catch (Exception e){}


        try{

            hrList = dataBaseHandlerMFS.getHr(Pateints_List111.patId);
            Log.i("response hr", hrList.get(0));
            String response=hrList.get(0);
            String resp[]=response.split(Pattern.quote("***"));

            String hr=resp[1];
            String spo=resp[2];
            Log.i("hr",hr);
            Log.i("spo",spo);





            int fresult=Integer.parseInt(hr);
            String hrResult=String.valueOf(fresult);
            if(fresult<60){
                prTxtv.setBackgroundColor(Color.YELLOW);
                prTxtv.setText(hrResult);

            }else if(fresult>90){
                prTxtv.setBackgroundColor(Color.RED);
                prTxtv.setText(hrResult);

            }else if (fresult >= 60 && fresult <= 90) {
                prTxtv.setBackgroundColor(Color.GREEN);
                prTxtv.setText(hrResult);
            }




            int spoResult=Integer.parseInt(spo);
            String bpdResult1=String.valueOf(spoResult);
            if(spoResult<94){
                osTxtv.setBackgroundColor(Color.YELLOW);
                osTxtv.setText(bpdResult1);

            }

            else if (spoResult >= 94 && spoResult <= 100) {
                osTxtv.setBackgroundColor(Color.GREEN);
                osTxtv.setText(bpdResult1);
            }
            else {
                osTxtv.setBackgroundColor(Color.RED);
                osTxtv.setText(bpdResult1);

            }

        }catch (Exception e){}


        try{


            otherList = dataBaseHandlerMFS.getOther(Pateints_List111.patId);
            Log.i("response other", otherList.get(0));
            String response=otherList.get(0);
            String resp[]=response.split(Pattern.quote("***"));

            String temp= resp[1];
            String resp1= resp[2];
            Log.i("temp",temp);
            Log.i("resp",resp1);





            int fresult=Integer.parseInt(temp);
            String tempResult=String.valueOf(fresult);
            if(fresult<36){
                temTxtv.setBackgroundColor(Color.YELLOW);
                temTxtv.setText(tempResult);

            }else if(fresult>37.5){
                temTxtv.setBackgroundColor(Color.RED);
                temTxtv.setText(tempResult);

            }else if (fresult >= 36 && fresult <= 37.5) {
                temTxtv.setBackgroundColor(Color.GREEN);
                temTxtv.setText(tempResult);
            }




            int respResult=Integer.parseInt(resp1);
            String respResult1=String.valueOf(respResult);
            if(respResult<12){
                rrTxtv.setBackgroundColor(Color.YELLOW);
                rrTxtv.setText(respResult1);

            }
            else if(respResult>20){
                rrTxtv.setBackgroundColor(Color.RED);
                rrTxtv.setText(respResult1);

            }
            else if (respResult >= 12 && respResult <= 20) {
                rrTxtv.setBackgroundColor(Color.GREEN);
                rrTxtv.setText(respResult1);
            }

        }catch (Exception e){}

        return rootView;
    }

}
