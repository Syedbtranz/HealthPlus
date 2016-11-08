package com.btranz.healthplus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.opencsv.CSVWriter;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private int fromMonitoringActivity;
    private int fromMainMenu;
    private GestureDetectorCompat gestureDetectorCompat;
    private SimpleAdapter mRecordEntriesSimpleAdapter;
    ArrayList<HashMap<String, String>> recordsList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entries_list);

        Intent intent = getIntent();
        fromMonitoringActivity = intent.getIntExtra("keyFromMonitoring", fromMonitoringActivity);
        fromMainMenu = intent.getIntExtra("keyFromMainMenu", fromMainMenu);

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

        mRecordEntriesSimpleAdapter = new SimpleAdapter(
                this,
                recordsList,
                R.layout.list_item,
                new String[]{"title", "summary", "extra"},
                new int[]{R.id.title, R.id.summary, R.id.extra});

        // Find and set up the ListView for paired devices and newly discovered devices
        ListView listViewRecordEntries = (ListView) findViewById(R.id.entries_list);
        listViewRecordEntries.setClickable(true);
        listViewRecordEntries.setAdapter(mRecordEntriesSimpleAdapter);
        listViewRecordEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final Map<String, String> data = (Map<String, String>) parent.getItemAtPosition(position);
                final String objectID = data.get("extra");
                final String objectName = data.get("summary");

                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this, AlertDialog.THEME_HOLO_DARK);
                builder.setTitle("History Options")
                        .setPositiveButton("Export", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery(objectName);
                                query.getInBackground(objectID, new GetCallback<ParseObject>() {
                                    public void done(ParseObject object, ParseException e) {
                                        if (e == null) {
                                            // object will be your object ID data
                                            List<Integer> data = new ArrayList<Integer>();
                                            data = object.getList("data");
                                            exportData(data);

                                        } else {
                                            // something went wrong
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this, AlertDialog.THEME_HOLO_DARK);
                                builder.setTitle("Are you sure you would like to delete this entry?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                ParseQuery<ParseObject> query = ParseQuery.getQuery(objectName);
                                                query.getInBackground(objectID, new GetCallback<ParseObject>() {
                                                    public void done(ParseObject object, ParseException e) {
                                                        if (e == null) {
                                                            // object will be your object ID data
                                                            object.deleteInBackground();
                                                            Toast.makeText(HistoryActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
                                                            recordsList.remove(position);
                                                            mRecordEntriesSimpleAdapter.notifyDataSetChanged();
                                                        } else {
                                                            // something went wrong
                                                        }
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });

                                AlertDialog bpDialog = builder.create();
                                bpDialog.show();

                            }
                        });

                AlertDialog bpDialog = builder.create();
                bpDialog.show();
            }
        });

        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> ppgQuery = new ParseQuery<>("PPGdata");
        ppgQuery.whereEqualTo("user", parseUser);
        ppgQuery.orderByDescending("createdAt");
        ppgQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject data : list) {
                        addItem(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(data.getCreatedAt()), "PPGdata", data.getObjectId());
                        mRecordEntriesSimpleAdapter.notifyDataSetChanged();
                    }
                } else
                    Toast.makeText(HistoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        ParseQuery<ParseObject> ecgQuery = new ParseQuery<>("ECGdata");
        ecgQuery.whereEqualTo("user", parseUser);
        ecgQuery.orderByDescending("createdAt");
        ecgQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject data : list) {

                        addItem(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(data.getCreatedAt()), "ECGdata", data.getObjectId());
                        mRecordEntriesSimpleAdapter.notifyDataSetChanged();
                    }
                } else
                    Toast.makeText(HistoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        //Toast.makeText(HistoryActivity.this, "OnCreate", Toast.LENGTH_SHORT).show();
    }

    private void addItem(String date, String objectName, String objectID) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("title", date);
        item.put("summary", objectName);
        item.put("extra", objectID);
        recordsList.add(item);
    }

    private void exportData(final List<Integer> data) {

        //String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/data.csv";
        File csv = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), "data.csv");
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));

            List<String[]> stringlar = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                String[] myStr = new String[1];
                myStr[0] = data.get(i).toString();
                stringlar.add(myStr);
            }

            writer.writeAll(stringlar);
            writer.close();

            Intent i = new Intent(Intent.ACTION_SEND);
            //i.setType("message/rfc822");
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "HeartGuardian Export");
            i.putExtra(Intent.EXTRA_TEXT, "Please find attached your vital signs recording.");
            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(csv));

            try {
                startActivityForResult(Intent.createChooser(i, "Please select an messenger client to use:"), 1);
                //csv.delete();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(HistoryActivity.this, "There are no messenger clients installed.", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_, menu);
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
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
        {
            File csv = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), "data.csv");
            if (csv.exists()) csv.delete();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            if (event2.getX() < event1.getX()) // left swipe
            {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }

            return true;
        }
    }
}
