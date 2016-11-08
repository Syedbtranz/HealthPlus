package com.btranz.healthplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 3/26/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button loginBtn,registerBtn;
    public static List<String> docList=new ArrayList<String>();
    EditText reg_num_edit;
    DatabaseHandler dbHandler;
    private Button mForgotPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginBtn=(Button)findViewById(R.id.login_button);
        registerBtn=(Button)findViewById(R.id.register_button);
      //  mForgotPasswordButton = (Button) findViewById(R.id.forgotpassword_button);

        reg_num_edit=(EditText)findViewById(R.id.password_edit_txt);
        dbHandler=new DatabaseHandler(LoginActivity.this);

        loginBtn.setOnClickListener(this);
        registerBtn .setOnClickListener(this);
     //   mForgotPasswordButton.setOnClickListener(this );


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.login_button:
                String regNum=reg_num_edit.getText().toString();

                if(regNum.equals("")){
                    Toast.makeText(LoginActivity.this, "Reg Number Required", Toast.LENGTH_SHORT).show();
                }else {
                    docList=dbHandler.docLogin(regNum);
                    if(docList.size()>0){
                        Intent in=new Intent(LoginActivity.this, Pateints_List111.class);
                        startActivity(in);
                        finish();
                    }
                    else{

                        Toast.makeText(LoginActivity.this, "Invalid Reg Number", Toast.LENGTH_SHORT).show();
                    }

                }



                break;
            case  R.id.register_button:
                Intent rein=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(rein);
                break;

           /* case R.id.forgotpassword_button:
                Intent takeUserToResetPassword = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(takeUserToResetPassword);
                finish();
               break;*/

        }
    }


}
