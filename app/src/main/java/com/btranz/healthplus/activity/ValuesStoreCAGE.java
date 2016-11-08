package com.btranz.healthplus.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 5/16/2016.
 */
public class ValuesStoreCAGE extends AppCompatActivity{

    TextView que1,que2,que3,que4,fresultview,message;
    String QUE1,QUE2,QUE3,QUE4,finalresult;
    int fresult;
    private static final String SELECT_SQL = "SELECT * FROM cage";

    private SQLiteDatabase sqLiteDatabase;

    private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuesstorecage);
        openDatabase();




        que1=(TextView)findViewById(R.id.que1);
        que2=(TextView)findViewById(R.id.que2);
        que3=(TextView)findViewById(R.id.que3);
        que4=(TextView)findViewById(R.id.que4);
        fresultview=(TextView)findViewById(R.id.fscore);
        message=(TextView)findViewById(R.id.message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
        c = sqLiteDatabase.rawQuery(SELECT_SQL, null);
        c.moveToLast();

        moveNext();
        showRecords();
    }

    protected void openDatabase() {
        sqLiteDatabase = openOrCreateDatabase("HealthApp", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() {
        String QUE1 = c.getString(1);
        Log.i("QUE1",QUE1);
        String QUE2 = c.getString(2);
        Log.i("QUE2",QUE2 );
        String QUE3 = c.getString(3);
        Log.i("QUE3",QUE3);
        String QUE4=c.getString(4);
        Log.i("QUE4",QUE4);
        String cageresult=c.getString(5);
        Log.i("cageresult",cageresult);


        que1.setText(QUE1);
        que2.setText(QUE2);
        que3.setText(QUE3);
        que4.setText(QUE4);


        fresult=Integer.parseInt(cageresult);
        String fresult1=String.valueOf(fresult);
        if(fresult==1){

            fresultview.setText(fresult1);
        }else if (fresult==2){
            fresultview.setTextColor(getResources().getColor(R.color.colorgreen));
            fresultview.setText(fresult1);
            message.setTextColor(getResources().getColor(R.color.colorgreen));
            message.setText("2 or higher had a 93% sensitivity /76% specificity for the identification of 'excessive drinking' and a 91% sensitivity/77% specificity for the identification of alcoholism.");

        }else if (fresult==3){
            fresultview.setTextColor(getResources().getColor(R.color.colorgreen));
            fresultview.setText(fresult1);
            message.setTextColor(getResources().getColor(R.color.colorgreen));
            message.setText("2 or higher had a 93% sensitivity /76% specificity for the identification of 'excessive drinking' and a 91% sensitivity/77% specificity for the identification of alcoholism.");

        }else if (fresult==4){
            fresultview.setTextColor(getResources().getColor(R.color.colorgreen));
            fresultview.setText(fresult1);
            message.setTextColor(getResources().getColor(R.color.colorgreen));
            message.setText("2 or higher had a 93% sensitivity /76% specificity for the identification of 'excessive drinking' and a 91% sensitivity/77% specificity for the identification of alcoholism.\n" +
                    "Some clinicians also consider the \"Eye Opener\" question as highly concerning for unhealthy drinking behavior, even if all other questions are answered negatively.\n");

        }
    }

    protected void moveNext() {
        if (!c.isLast())
            c.moveToNext();
        showRecords();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent=new Intent(ValuesStoreCAGE.this,FormsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.patient_notes) {
            Intent in = new Intent(ValuesStoreCAGE.this, Notes.class);
            startActivity(in);
            return true;
        }
        if (id == R.id.logout) {
            Intent i = new Intent(ValuesStoreCAGE.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(ValuesStoreCAGE.this,FormsActivity.class);
        startActivity(intent);
        finish();
    }




}
