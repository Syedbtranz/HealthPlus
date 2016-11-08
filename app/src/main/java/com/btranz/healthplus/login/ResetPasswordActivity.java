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
import com.parse.RequestPasswordResetCallback;

import com.btranz.healthplus.R;

public class ResetPasswordActivity extends Activity {

    protected EditText mEmail;
    protected Button mResetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //initialize
        mEmail= (EditText) findViewById(R.id.ResetEmailEditText);
        mResetPasswordButton = (Button) findViewById(R.id.ResetPasswordButton);

        mResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // An email was successfully sent with reset instructions.
                            Toast.makeText(ResetPasswordActivity.this, "Email Sent", Toast.LENGTH_LONG).show();

                            //Take User to Login page
                            Intent takeUserToLogin = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            startActivity(takeUserToLogin);
                            finish();

                        } else {
                            // Incorrect Username/Email
                            Toast.makeText(ResetPasswordActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reset_password, menu);
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
