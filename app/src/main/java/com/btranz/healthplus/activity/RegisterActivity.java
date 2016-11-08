package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 3/26/2016.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText fName, lName, regNumber, spectiality, email, hospitalAddress, other,contact;
    Spinner titleSpinner;
    String title, fNameStr, lNameStr, regNumberStr, specialityStr, emailStr, addressStr,contactStr;
    Button registerBtn;
    DatabaseHandler dbHandler;
    public static List<String> docList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        fName = (EditText) findViewById(R.id.firstname_edit_txt);
        lName = (EditText) findViewById(R.id.lasttname_edit_txt);
        titleSpinner = (Spinner) findViewById(R.id.spinner);
        regNumber = (EditText) findViewById(R.id.reg_no_edit_txt);
        spectiality = (EditText) findViewById(R.id.specialty_edit_txt);
        email = (EditText) findViewById(R.id.email_edit_txt);
        hospitalAddress = (EditText) findViewById(R.id.address_edit_txt);
        other = (EditText) findViewById(R.id.other_edit_txt);
        registerBtn = (Button) findViewById(R.id.registration_btn);
        contact= (EditText) findViewById(R.id.contact_edit_txt);

        registerBtn.setOnClickListener(this);
        dbHandler=new DatabaseHandler(RegisterActivity.this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        title = adapterView.getItemAtPosition(i).toString();

        /*if (title.equals("Other")) {
            other.setVisibility(View.VISIBLE);
        } else {
            other.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.registration_btn:
                getText();
                break;
        }
    }

    public void getText() {
        fNameStr = fName.getText().toString();
        lNameStr = lName.getText().toString();
        regNumberStr = regNumber.getText().toString();
        contactStr = contact.getText().toString();
        specialityStr = spectiality.getText().toString();
        emailStr = email.getText().toString();
        addressStr = hospitalAddress.getText().toString();

        if(fNameStr.equals("")){
            Toast.makeText(RegisterActivity.this, "First Name Required", Toast.LENGTH_SHORT).show();
        }else if(lNameStr.equals("")){
            Toast.makeText(RegisterActivity.this, "Last Name Required", Toast.LENGTH_SHORT).show();
        }
        else if(regNumberStr.equals("")){
            Toast.makeText(RegisterActivity.this, "Reg No Required", Toast.LENGTH_SHORT).show();
        }
        else if(specialityStr.equals("")){
            Toast.makeText(RegisterActivity.this, "Speciality Required", Toast.LENGTH_SHORT).show();
        }
        else if(emailStr.equals("")){
            Toast.makeText(RegisterActivity.this, "Email Required", Toast.LENGTH_SHORT).show();
        }
        else if(addressStr.equals("")){
            Toast.makeText(RegisterActivity.this, "Address Required", Toast.LENGTH_SHORT).show();
        }
        else{
            docList=dbHandler.docLogin(regNumberStr);
            if(docList.size()>0){
                Toast.makeText(RegisterActivity.this, " Reg Num already exists", Toast.LENGTH_SHORT).show();
            }
            else {
                dbHandler.docReg(fNameStr, lNameStr, title, regNumberStr, specialityStr, contactStr, emailStr, addressStr);
                Intent in=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(in);
                Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
