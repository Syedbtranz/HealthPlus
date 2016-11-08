package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 6/2/2016.
 */
public class PatientDetails1 extends AppCompatActivity{



    Button viewdetails;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        viewdetails=(Button)findViewById(R.id.viewdetails);
        viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PatientDetails1.this, ViewResult.class);
                startActivity(intent);
            }
        });
    }



}
