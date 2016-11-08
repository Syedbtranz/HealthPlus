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


public class TemperatureFragment extends Fragment {

    public List<String> bpsList = new ArrayList<String>();
    public List<String> dateList = new ArrayList<String>();
    public List<String> bpdList = new ArrayList<String>();
    public List<String> mfsList = new ArrayList<String>();

TextView dateOne,dateTwo,dateThree,resultOne,resultTwo,resutThree;
    DataBaseHandlerMFS dataBaseHandlerMFS;

    public TemperatureFragment() {
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




            int fresult=Integer.parseInt(temp);
            String tempResult=String.valueOf(fresult);
            if(fresult<36){
                resultOne.setTextColor(Color.YELLOW);
                resultOne.setText(tempResult);

            }else if(fresult>37.5){
                resultOne.setTextColor(Color.RED);
                resultOne.setText(tempResult);

            }else if (fresult >= 36 && fresult <= 37.5) {
                resultOne.setTextColor(Color.GREEN);
                resultOne.setText(tempResult);
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





            int fresult=Integer.parseInt(temp);
            String tempResult=String.valueOf(fresult);
            if(fresult<36){
                resultTwo.setTextColor(Color.YELLOW);
                resultTwo.setText(tempResult);

            }else if(fresult>37.5){
                resultTwo.setTextColor(Color.RED);
                resultTwo.setText(tempResult);

            }else if (fresult >= 36 && fresult <= 37.5) {
                resultTwo.setTextColor(Color.GREEN);
                resultTwo.setText(tempResult);
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





            int fresult=Integer.parseInt(temp);
            String tempResult=String.valueOf(fresult);
            if(fresult<36){
                resutThree.setTextColor(Color.YELLOW);
                resutThree.setText(tempResult);

            }else if(fresult>37.5){
                resutThree.setTextColor(Color.RED);
                resutThree.setText(tempResult);

            }else if (fresult >= 36 && fresult <= 37.5) {
                resutThree.setTextColor(Color.GREEN);
                resutThree.setText(tempResult);
            }


            // }


        } catch (Exception e) {
        }

        return rootView;
    }

}
