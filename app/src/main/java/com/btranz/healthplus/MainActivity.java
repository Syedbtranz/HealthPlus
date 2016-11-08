package com.btranz.healthplus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.ParseUser;

import com.btranz.healthplus.activity.AddPatientActivity;
import com.btranz.healthplus.login.LoginActivity;
import com.btranz.healthplus.login.RegisterActivity;
import com.btranz.healthplus.login.ResetPasswordActivity;

public class MainActivity extends Activity {

    private Button mLogInButton;
    private Button mRegisterButton;
    private Button mForgotPasswordButton;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        //initialize buttons
        mLogInButton = (Button) findViewById(R.id.OpeningLogInButton);
        mRegisterButton = (Button) findViewById(R.id.OpeningRegisterButton);
        mForgotPasswordButton = (Button) findViewById(R.id.OpeningForgotPasswordButton);

        logo = (ImageView) findViewById(R.id.logo);
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation rotate_clock = AnimationUtils.loadAnimation(this, R.anim.rotation_clock);
        Animation zoom_in = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        AnimationSet animations = new AnimationSet(false);
        animations.addAnimation(fade_in);
        animations.addAnimation(rotate_clock);
        animations.addAnimation(zoom_in);
        animations.setFillAfter(true);
        animations.setFillEnabled(true);
        animations.setFillBefore(true);
        logo.startAnimation(animations);

        //listen for Log In Button press
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    ParseUser currentUser = ParseUser.getCurrentUser();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (currentUser != null && prefs.contains(LoginActivity.USERNAME) && prefs.contains(LoginActivity.PASSWORD)) {

                    //Take user to main menu activity
                    Intent takeUserToMainMenu = new Intent(MainActivity.this, MainMenuActivity.class);
                    startActivity(takeUserToMainMenu);
                    finish();

                } else {
                    //Take user to Log In page*/
                    Intent takeUserToLogIn = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(takeUserToLogIn);
                    finish();
                }
            }
        });

        //listen for Register Button press
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Take user to Register page
                Intent takeUserToSignUp = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(takeUserToSignUp);
                finish();
            }
        });

        //listen for Forgot Password Button press
        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Take user to Reset Password page
                Intent takeUserToResetPassword = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(takeUserToResetPassword);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opening, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_patient) {
            Intent in =new Intent(MainActivity.this, AddPatientActivity.class);
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
