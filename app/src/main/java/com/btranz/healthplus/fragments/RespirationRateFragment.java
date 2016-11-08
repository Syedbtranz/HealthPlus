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


public class RespirationRateFragment extends Fragment {

    public List<String> bpsList = new ArrayList<String>();
    public List<String> dateList = new ArrayList<String>();
    public List<String> bpdList = new ArrayList<String>();
    public List<String> mfsList = new ArrayList<String>();

TextView dateOne,dateTwo,dateThree,resultOne,resultTwo,resutThree;
    DataBaseHandlerMFS dataBaseHandlerMFS;

    public RespirationRateFragment() {
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
            mfsList = dataBaseHandlerMFS.getOther(Pateints_List111.patId);
        }catch (Exception e){

        }
        try {

        //    for (int i = 0; mfsList.size() < i; i++) {

                Log.i("response map", mfsList.get(0));
                String response = mfsList.get(0);
                String resp[] = response.split(Pattern.quote("***"));


            String temp= resp[1];
            String resp1= resp[2];
            String date= resp[3];
            Log.i("temp",temp);
            Log.i("resp",resp1);
dateOne.setText(date);




            int respResult=Integer.parseInt(resp1);
            String respResult1=String.valueOf(respResult);
            if(respResult<12){
                resultOne.setTextColor(Color.YELLOW);
                resultOne.setText(respResult1);

            }
            else if(respResult>20){
                resultOne.setTextColor(Color.RED);
                resultOne.setText(respResult1);

            }
            else if (respResult >= 12 && respResult <= 20) {
                resultOne.setTextColor(Color.GREEN);
                resultOne.setText(respResult1);
            }

           // }


        } catch (Exception e) {
        }









        try {

            //    for (int i = 0; mfsList.size() < i; i++) {

            Log.i("response map", mfsList.get(1));
            String response = mfsList.get(1);
            String resp[] = response.split(Pattern.quote("***"));



            String temp= resp[1];
            String resp1= resp[2];
            String date= resp[3];
            Log.i("temp",temp);
            Log.i("resp",resp1);
            dateTwo.setText(date);





            int respResult=Integer.parseInt(resp1);
            String respResult1=String.valueOf(respResult);
            if(respResult<12){
                resultTwo.setTextColor(Color.YELLOW);
                resultTwo.setText(respResult1);

            }
            else if(respResult>20){
                resultTwo.setTextColor(Color.RED);
                resultTwo.setText(respResult1);

            }
            else if (respResult >= 12 && respResult <= 20) {
                resultTwo.setTextColor(Color.GREEN);
                resultTwo.setText(respResult1);
            }


            // }


        } catch (Exception e) {
        }


        try {

            //    for (int i = 0; mfsList.size() < i; i++) {

            Log.i("response map", mfsList.get(2));
            String response = mfsList.get(2);
            String resp[] = response.split(Pattern.quote("***"));

            String temp= resp[1];
            String resp1= resp[2];
            String date= resp[3];
            Log.i("temp",temp);
            Log.i("resp",resp1);
            dateThree.setText(date);





            int respResult=Integer.parseInt(resp1);
            String respResult1=String.valueOf(respResult);
            if(respResult<12){
                resutThree.setTextColor(Color.YELLOW);
                resutThree.setText(respResult1);

            }
            else if(respResult>20){
                resutThree.setTextColor(Color.RED);
                resutThree.setText(respResult1);

            }
            else if (respResult >= 12 && respResult <= 20) {
                resutThree.setTextColor(Color.GREEN);
                resutThree.setText(respResult1);
            }

            // }


        } catch (Exception e) {
        }

        return rootView;
    }

}
