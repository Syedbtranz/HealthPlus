package com.btranz.healthplus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 4/21/2016.
 */
public class CalculatorActivity extends Activity implements View.OnClickListener{
    Button atriaBtn,bmiBtn,cageBtn,dashBtn,glossomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);
        atriaBtn=(Button)findViewById(R.id.atria_button);
        bmiBtn=(Button)findViewById(R.id.bmi_button);
        cageBtn=(Button)findViewById(R.id.cage_button);
        dashBtn=(Button)findViewById(R.id.dash_button);
        glossomBtn=(Button)findViewById(R.id.glossom_button);

        atriaBtn.setOnClickListener(this);
        bmiBtn.setOnClickListener(this);
        cageBtn.setOnClickListener(this);
        dashBtn.setOnClickListener(this);
        glossomBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.atria_button:
                Intent in=new Intent(CalculatorActivity.this,ATRIA.class);
                startActivity(in);
                break;
            case R.id.bmi_button:
                Intent inbmi=new Intent(CalculatorActivity.this,BmiActivity.class);
                startActivity(inbmi);
                break;
            case R.id.cage_button:
                Intent incage=new Intent(CalculatorActivity.this,CageActivity.class);
                startActivity(incage);
                break;

            case R.id.dash_button:
                Intent indash=new Intent(CalculatorActivity.this,DashActivity.class);
                startActivity(indash);
                break;
            case R.id.glossom_button:
                Intent ingloss=new Intent(CalculatorActivity.this,GcsActivity.class);
                startActivity(ingloss);
                break;
        }
    }
}
