package com.btranz.healthplus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 6/2/2016.
 */
public class Notes extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler databaseHandler;
    EditText noteEdit;
    Button saveBtn,Close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        noteEdit = (EditText) findViewById(R.id.notes_edit);
        saveBtn = (Button) findViewById(R.id.saveButton);
        Close=(Button)findViewById(R.id.close);


            saveBtn.setOnClickListener(this);
            Close.setOnClickListener(this);
            databaseHandler = new DatabaseHandler(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.saveButton:

                if(noteEdit.getText().toString().equals("")) {
                    Toast.makeText(Notes.this, "Enter the Data or Close the Window.", Toast.LENGTH_SHORT).show();
                }else {
                    String noteStr = noteEdit.getText().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
                    String formattedDate = dateFormat.format(new Date()).toString();
                    databaseHandler.noteInsert(formattedDate, noteStr, Pateints_List111.patId);
                    Toast.makeText(Notes.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;


            case R.id.close:

                onBackPressed();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
