package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btranz.healthplus.R;


public class ClinicalDescription extends android.app.Fragment implements View.OnClickListener{

ImageView searchImgv,formImgv,calculatorImgv,notesImgv;
    public ClinicalDescription() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.clinical_decision, container, false);
        searchImgv=(ImageView)rootview.findViewById(R.id.search_imgv);
        formImgv=(ImageView)rootview.findViewById(R.id.forms_imgv);
        //calculatorImgv=(ImageView)rootview.findViewById(R.id.calculator_imgv);
        notesImgv=(ImageView)rootview.findViewById(R.id.notes_imgv);

        searchImgv.setOnClickListener(this);
        formImgv.setOnClickListener(this);
       // calculatorImgv.setOnClickListener(this);
        notesImgv.setOnClickListener(this);



        // Inflate the layout for this fragment
        return rootview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.search_imgv:
                Intent intent2=new Intent(getActivity(),Search_Activity.class);
                startActivity(intent2);

                break;
            case  R.id.forms_imgv:
                Intent intent=new Intent(getActivity(),FormsActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            /*case  R.id.calculator_imgv:
                Intent in =new Intent(getActivity(),CalculatorActivity.class);
                startActivity(in);
                break;
            */case  R.id.notes_imgv:

                Intent intent1=new Intent(getActivity(),MyNotes.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }
}
