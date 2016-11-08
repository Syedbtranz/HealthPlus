package com.btranz.healthplus.login;

import android.app.Application;

import com.parse.Parse;

public class Initialization extends Application {

    @Override public void onCreate(){
        super.onCreate();

        Parse.initialize(this, "1erGfeHH02z6hBq7WdcTPxebD2yIVyPeCpWPDzyv", "I80oNJP2Rhy5Q7k3dauDMP46z7JxLhJh18JgAAUb");

    }
}
