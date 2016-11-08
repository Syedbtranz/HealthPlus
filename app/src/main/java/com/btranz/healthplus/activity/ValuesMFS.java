package com.btranz.healthplus.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 5/13/2016.
 */
public class ValuesMFS extends Activity {

    DatabaseHandler databaseHandler;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mfs_list);

        listView=(ListView)findViewById(R.id.mfslist);
        databaseHandler=new DatabaseHandler(getApplicationContext());



    }
}
