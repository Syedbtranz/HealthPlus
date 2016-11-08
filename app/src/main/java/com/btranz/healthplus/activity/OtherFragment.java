package com.btranz.healthplus.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;


public class OtherFragment extends android.app.Fragment {

ImageView hrImgV;
    Button submitButton;
    EditText tempEdit,respEdit;
    DataBaseHandlerMFS dataBaseHandlerMFS;
    String date;
    public OtherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_other , container, false);
        tempEdit=(EditText)rootView.findViewById(R.id.temp_edit);
        respEdit=(EditText)rootView.findViewById(R.id.resp_edit);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();
        submitButton=(Button)rootView.findViewById(R.id.saveButton);
        dataBaseHandlerMFS=new DataBaseHandlerMFS(getActivity());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp=tempEdit.getText().toString();
                String resp=respEdit.getText().toString();

                if(temp.equals("")){
                    Toast.makeText(getActivity(), "Temperature Required", Toast.LENGTH_SHORT).show();
                }else if(resp.equals("")){
                    Toast.makeText(getActivity(), "Respiration Rate  Required", Toast.LENGTH_SHORT).show();

                }
                else{

                    dataBaseHandlerMFS.inserOther(temp,resp,Pateints_List111.patId,date);
                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                    tempEdit.setText("");
                    respEdit.setText("");
                }

            }
        });
        return rootView;
    }
}
