package com.btranz.healthplus.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.btranz.healthplus.MainMenuActivity;
import com.btranz.healthplus.R;
//import andniksaj.industrialproject2015.heartguardian.R;

public class LoginActivity extends Activity {

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLogInButton;
    private CheckBox keepMeLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize
        mUsername = (EditText) findViewById(R.id.LoginUsernameEditText);
        mPassword = (EditText) findViewById(R.id.LoginPasswordEditText);
        mLogInButton = (Button) findViewById(R.id.LogInButton);
        keepMeLoggedIn = (CheckBox) findViewById(R.id.checkBoxKeepMeLoggedIn);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.contains(USERNAME) && prefs.contains(PASSWORD)) {
            String username = prefs.getString(USERNAME, "");
            String password = prefs.getString(PASSWORD, "");
            connect(username, password);
        }

        //Listen for Log In Button press
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get user inputs and convert to string
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                //we are connected to a network
                if (isOnline()) {
                    //If keep me logged in is checked save the username and password in preferences
                    if (keepMeLoggedIn.isChecked()) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(USERNAME, username);
                        editor.putString(PASSWORD, password);
                        editor.apply();
                    } else //otherwise clear the fields
                    {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove(USERNAME);
                        editor.remove(PASSWORD);
                        editor.apply();
                    }
                    connect(username, password);
		mLogInButton.setClickable(false);
		} else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, AlertDialog.THEME_HOLO_DARK);
                    builder.setTitle("No Network Connection")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Please connect to the internet to log in.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button

                                }
                            });

                    AlertDialog bpDialog = builder.create();
                    bpDialog.show();
                }
            }
        });
    }

    private void connect(String username, String password) {
        //Login user using Parse SDK
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                if (e == null) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                    final boolean emailVerified = parseUser.getBoolean("emailVerified");
                    query.getInBackground(String.valueOf(emailVerified), new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (emailVerified) {
                                //Take User to Homepage
                                Intent takeUserHome = new Intent(LoginActivity.this, MainMenuActivity.class);
                                startActivity(takeUserHome);
                                finish();
                            } else {
                                //Error advise user
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, AlertDialog.THEME_HOLO_DARK);
                                builder.setIcon(android.R.drawable.ic_dialog_alert);
                                builder.setTitle("Email Not Verified");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //close dialog
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        }
                    });

                } else {

                    //Error advise user
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, AlertDialog.THEME_HOLO_DARK);
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setTitle("Invalid Username/Password Combination");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //close dialog
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }

        });
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
