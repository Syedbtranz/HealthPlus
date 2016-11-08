package com.btranz.healthplus.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.btranz.healthplus.activity.Pateints_List111;


public class OxygenSaturationFragment extends Fragment {

    public List<String> bpsList = new ArrayList<String>();
    public List<String> dateList = new ArrayList<String>();
    public List<String> bpdList = new ArrayList<String>();
    public List<String> mfsList = new ArrayList<String>();

TextView dateOne,dateTwo,dateThree,resultOne,resultTwo,resutThree;
    DataBaseHandlerMFS dataBaseHandlerMFS;

    public OxygenSaturationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_two, container, false);
        dateOne=(TextView)rootView.findViewById(R.id.date_txt_one);
        dateTwo=(TextView)rootView.findViewById(R.id.date_txt_two);
        dateThree=(TextView)rootView.findViewById(R.id.date_txt_three);
        resultOne=(TextView)rootView.findViewById(R.id.result_txt_one);
        resultTwo=(TextView)rootView.findViewById(R.id.result_txt_two);
        resutThree=(TextView)rootView.findViewById(R.id.result_txt_three);

        dataBaseHandlerMFS = new DataBaseHandlerMFS(getActivity());
        try {
            mfsList = dataBaseHandlerMFS.getHr(Pateints_List111.patId);
        }catch (Exception e){

        }
        try {

        //    for (int i = 0; mfsList.size() < i; i++) {

                Log.i("response map", mfsList.get(0));
                String response = mfsList.get(0);
                String resp[] = response.split(Pattern.quote("***"));

            //String hr=resp[1];
            String spo=resp[2];
            //Log.i("hr",hr);
            Log.i("spo",spo);

                String date=resp[3];
            dateOne.setText(date);
            int spoResult=Integer.parseInt(spo);
            String bpdResult1=String.valueOf(spoResult);
            if(spoResult<94){
                resultOne.setTextColor(Color.YELLOW);
                resultOne.setText(bpdResult1);

            }

            else if (spoResult >= 94 && spoResult <= 100) {
                resultOne.setTextColor(Color.GREEN);
                resultOne.setText(bpdResult1);
            }
            else {
                resultOne.setBackgroundColor(Color.RED);
                resultOne.setText(bpdResult1);

            }


           // }


        } catch (Exception e) {
        }









        try {

            //    for (int i = 0; mfsList.size() < i; i++) {

            Log.i("response map", mfsList.get(1));
            String response = mfsList.get(1);
            String resp[] = response.split(Pattern.quote("***"));



            //String hr=resp[1];
            String spo=resp[2];
            //Log.i("hr",hr);
            Log.i("spo",spo);
            String date=resp[3];

            dateTwo.setText(date);
            int spoResult=Integer.parseInt(spo);
            String bpdResult1=String.valueOf(spoResult);
            if(spoResult<94){
                resultTwo.setTextColor(Color.YELLOW);
                resultTwo.setText(bpdResult1);

            }

            else if (spoResult >= 94 && spoResult <= 100) {
                resultTwo.setBackgroundColor(Color.GREEN);
                resultTwo.setText(bpdResult1);
            }
            else {
                resultTwo.setTextColor(Color.RED);
                resultTwo.setText(bpdResult1);

            }

            // }


        } catch (Exception e) {
        }


        try {

            //    for (int i = 0; mfsList.size() < i; i++) {

            Log.i("response map", mfsList.get(2));
            String response = mfsList.get(2);
            String resp[] = response.split(Pattern.quote("***"));

            //String hr=resp[1];
            String spo=resp[2];
            //Log.i("hr",hr);
            Log.i("spo",spo);
            String date=resp[3];
            dateThree.setText(date);
            int spoResult=Integer.parseInt(spo);
            String bpdResult1=String.valueOf(spoResult);
            if(spoResult<94){
                resutThree.setTextColor(Color.YELLOW);
                resutThree.setText(bpdResult1);

            }

            else if (spoResult >= 94 && spoResult <= 100) {
                resutThree.setTextColor(Color.GREEN);
                resutThree.setText(bpdResult1);
            }
            else {
                resutThree.setTextColor(Color.RED);
                resutThree.setText(bpdResult1);

            }

            // }


        } catch (Exception e) {
        }

        return rootView;
    }

}
