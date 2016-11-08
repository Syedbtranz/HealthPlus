package com.btranz.healthplus.activity;

import android.app.Activity;
import android.os.Bundle;

import com.btranz.healthplus.Handler.DataBaseHandlerATRIA;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 4/21/2016.
 */
public class AtriaActivity extends Activity {

    DataBaseHandlerATRIA dataBaseHandlerATRIA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atria);

        dataBaseHandlerATRIA= new DataBaseHandlerATRIA(this);
    }
}
