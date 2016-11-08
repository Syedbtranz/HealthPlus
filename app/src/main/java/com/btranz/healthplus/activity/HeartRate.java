package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.MainMenuActivity;
import com.btranz.healthplus.R;


public class HeartRate extends android.app.Fragment {

ImageView hrImgV;
    Button manualBtn;
    DataBaseHandlerMFS dataBaseHandlerMFS;
    public HeartRate() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_hr_rate, container, false);
        hrImgV=(ImageView)rootView.findViewById(R.id.heart_imgv);
        manualBtn=(Button)rootView.findViewById(R.id.manualBtn);
        dataBaseHandlerMFS=new DataBaseHandlerMFS(getActivity());
        hrImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(), MainMenuActivity.class);
                startActivity(in);
            }
        });
        manualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent in=new Intent(getActivity(),HrManualEntry.class);
                startActivity(in);
            }
        });
        return rootView;
    }
}
