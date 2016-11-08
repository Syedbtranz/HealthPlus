package com.btranz.healthplus.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import com.btranz.healthplus.R;

public class RegisterActivity extends Activity {

    protected EditText mUserUsername;
    protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected EditText mUserAge;
    protected EditText mUserHeight;
    protected Button mRegisterButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize
        mUserUsername = (EditText)findViewById(R.id.UsernameRegisterEditText);
        mUserEmail = (EditText) findViewById(R.id.EmailRegisterEditText);
        mUserPassword = (EditText) findViewById(R.id.PasswordRegisterEditText);
        mUserAge = (EditText) findViewById(R.id.AgeRegisterEditText);
        mUserHeight = (EditText) findViewById(R.id.HeightRegisterEditText);
        mRegisterButton = (Button) findViewById(R.id.SignUpRegisterButton);


        //Listen to register button click
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get Username, Password, Email, Age, Height and convert to string
                String username = mUserUsername.getText().toString().trim();
                String email = mUserEmail.getText().toString().trim();
                String password = mUserPassword.getText().toString().trim();
                String age = mUserAge.getText().toString();
                String height = mUserHeight.getText().toString();

                //Store User in Parse
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.put("Age", age);
                user.put("Height", height);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            //Sign up Successful
                            Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();

                            //Take user to Login Page
                            Intent takeUserToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(takeUserToLogin);
                            finish();

                        } else {
                            //Error, advise user
                            Toast.makeText(RegisterActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
