package com.btranz.healthplus.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.R;

/**
 * Created by all on 10/27/2016.
 */

public class ManualEnter extends android.app.Fragment{

    EditText hrEdit,osEdit;
    EditText tempEdit,respEdit;
    EditText sys,dia;
    Button submitButton;
    String systolic,diastolic,Finalresult;
    String date;
    Float systolic1,diastolic1,Fresult;



    DataBaseHandlerMFS dataBaseHandlerMFS;
    final DataBaseHandlerMFS databaseHandler = new DataBaseHandlerMFS(getActivity());

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.manualdata , container, false);

        dataBaseHandlerMFS=new DataBaseHandlerMFS(getActivity());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();

        hrEdit=(EditText)rootView.findViewById(R.id.hr_edit);
        osEdit=(EditText)rootView.findViewById(R.id.os_edit);

        tempEdit=(EditText)rootView.findViewById(R.id.temp_edit);
        respEdit=(EditText)rootView.findViewById(R.id.resp_edit);

        sys=(EditText)rootView.findViewById(R.id.systolic_edittext);
        dia=(EditText)rootView.findViewById(R.id.diastolic_edittext);

        submitButton=(Button)rootView.findViewById(R.id.saveButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hrValue=hrEdit.getText().toString();
                String spoValue=osEdit.getText().toString();

                String temp=tempEdit.getText().toString();
                String resp=respEdit.getText().toString();

                systolic = sys.getText().toString();
                diastolic = dia.getText().toString();

                if(hrValue.equals("")){
                    Toast.makeText(getActivity(), "Heart Rate Required", Toast.LENGTH_SHORT).show();
                }else if(spoValue.equals("")){
                    Toast.makeText(getActivity(), "Oxygen Saturation Required", Toast.LENGTH_SHORT).show();
                }else if(temp.equals("")){
                    Toast.makeText(getActivity(), "Temperature Required", Toast.LENGTH_SHORT).show();
                }else if(resp.equals("")){
                    Toast.makeText(getActivity(), "Respiration Rate  Required", Toast.LENGTH_SHORT).show();
                }else if(systolic.equals("")){
                    Toast.makeText(getActivity(), "Systolic Required", Toast.LENGTH_SHORT).show();
                }else if(diastolic.equals("")){
                    Toast.makeText(getActivity(), "Diastolic  Required", Toast.LENGTH_SHORT).show();
                }
                else{

                    dataBaseHandlerMFS.inserHeartRate(hrValue,spoValue,Pateints_List111.patId,date);
                    Toast.makeText(getActivity(), "Saved",
                            Toast.LENGTH_SHORT).show();
                    hrEdit.setText("");
                    osEdit.setText("");

                    dataBaseHandlerMFS.inserOther(temp,resp,Pateints_List111.patId,date);
                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                    tempEdit.setText("");
                    respEdit.setText("");

                    try {
                        systolic1 = Float.parseFloat(systolic);
                        diastolic1 = Float.parseFloat(diastolic);

                        Fresult = ((2 * diastolic1) + systolic1) / 3;

                        DecimalFormat df = new DecimalFormat("#");
                        String calcFinal = df.format(Fresult);
                        System.out.println("calc Value after 2nd modification" + calcFinal); //if value 2.5565314561 it will print 2.56

                        Finalresult = String.valueOf(calcFinal);
                        System.out.println("Finalresult" + Finalresult);


                        dataBaseHandlerMFS.insertmap(systolic, diastolic, Finalresult, Pateints_List111.patId, date);

                        sys.setText("");
                        dia.setText("");

                }catch (Exception e){
                    e.printStackTrace();}
                }

            }
        });
        return rootView;
    }
}
