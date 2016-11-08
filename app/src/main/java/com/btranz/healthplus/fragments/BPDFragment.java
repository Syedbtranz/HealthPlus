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


public class BPDFragment extends Fragment {

    public List<String> bpsList = new ArrayList<String>();
    public List<String> dateList = new ArrayList<String>();
    public List<String> bpdList = new ArrayList<String>();
    public List<String> mfsList = new ArrayList<String>();

TextView dateOne,dateTwo,dateThree,resultOne,resultTwo,resutThree;
    DataBaseHandlerMFS dataBaseHandlerMFS;

    public BPDFragment() {
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
            mfsList = dataBaseHandlerMFS.getMap(Pateints_List111.patId);
        }catch (Exception e){

        }
        try {

        //    for (int i = 0; mfsList.size() < i; i++) {

                Log.i("response map", mfsList.get(0));
                String response = mfsList.get(0);
                String resp[] = response.split(Pattern.quote("***"));

                //String bps=resp[1];
                String bpd=resp[2];
                String date=resp[4];

                Log.i("bps", resp[1]);
                Log.i("bpd", resp[2]);
                Log.i("date", resp[4]);
            dateOne.setText(date);
            int bpdResult=Integer.parseInt(bpd);
            String bpdResult1=String.valueOf(bpdResult);
            if(bpdResult<60){
                resultOne.setTextColor(Color.YELLOW);
                resultOne.setText(bpdResult1);

            }else if(bpdResult>80){
                resultOne.setTextColor(Color.RED);
                resultOne.setText(bpdResult1);

            }else if (bpdResult >= 60 && bpdResult <= 80) {
                resultOne.setTextColor(Color.GREEN);
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

            //String bps=resp[1];
            String bpd=resp[2];
            String date=resp[4];

            Log.i("bps", resp[1]);
            Log.i("bpd", resp[2]);
            Log.i("date", resp[4]);
            dateTwo.setText(date);
            int bpdResult=Integer.parseInt(bpd);
            String bpdResult1=String.valueOf(bpdResult);
            if(bpdResult<60){
                resultTwo.setTextColor(Color.YELLOW);
                resultTwo.setText(bpdResult1);

            }else if(bpdResult>80){
                resultTwo.setTextColor(Color.RED);
                resultTwo.setText(bpdResult1);

            }else if (bpdResult >= 60 && bpdResult <= 80) {
                resultTwo.setTextColor(Color.GREEN);
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

           // String bps=resp[1];
            String bpd=resp[2];
            String date=resp[4];

            Log.i("bps", resp[1]);
            Log.i("bpd", resp[2]);
            Log.i("date", resp[4]);
            dateThree.setText(date);
            int fresult=Integer.parseInt(bpd);
            String fresult1=String.valueOf(fresult);
            if(fresult<100){
                resutThree.setTextColor(Color.YELLOW);
                resutThree.setText(fresult1);

            }else if(fresult>140){
                resutThree.setTextColor(Color.RED);
                resutThree.setText(fresult1);

            }else if (fresult >= 100 && fresult <= 140) {
                resutThree.setTextColor(Color.GREEN);
                resutThree.setText(fresult1);
            }

            // }


        } catch (Exception e) {
        }

        return rootView;
    }

}
