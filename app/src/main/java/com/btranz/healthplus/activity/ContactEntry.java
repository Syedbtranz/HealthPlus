package com.btranz.healthplus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 8/3/2016.
 */
public class ContactEntry extends AppCompatActivity {

    EditText messageEdit,mailEdit,callEdit,skypeEdit;
    Button submitBtn;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_entry);

        messageEdit=(EditText)findViewById(R.id.message_edit_txt);
        callEdit=(EditText)findViewById(R.id.mobile_edit_txt);
        mailEdit=(EditText)findViewById(R.id.email_edit_txt);
        skypeEdit=(EditText)findViewById(R.id.skype_edit_txt);
        submitBtn=(Button)findViewById(R.id.submit_btn);

        databaseHandler=new DatabaseHandler(this);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(callEdit.getText().toString().equals("")){
                    Toast.makeText(ContactEntry.this, "Mobile NumberRequired", Toast.LENGTH_SHORT).show();
                }
                else if(mailEdit.getText().toString().equals("")){
                    Toast.makeText(ContactEntry.this, "Mail Id Required", Toast.LENGTH_SHORT).show();
                }
                else if(messageEdit.getText().toString().equals("")){
                    Toast.makeText(ContactEntry.this, "Message Number Required", Toast.LENGTH_SHORT).show();
                }
                else if(skypeEdit.getText().toString().equals("")){
                    Toast.makeText(ContactEntry.this, "Skype Id Required", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseHandler.contactInsert(callEdit.getText().toString(),mailEdit.getText().toString(),messageEdit.getText().toString(),skypeEdit.getText().toString());
                    Toast.makeText(ContactEntry.this, "Contact inserted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ContactEntry.this, Activity_Main.class);
// set the new task and clear flags
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);


                }

            }
        });


    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ContactEntry.this);
        alertDialogBuilder.setMessage("Do You want to go to Patient Details ?");
        alertDialogBuilder.setCancelable(false);
        // Setting Icon to Dialog
        alertDialogBuilder.setIcon(R.mipmap.deskicon111);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent i = new Intent(ContactEntry.this, Activity_Main.class);
// set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
            });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



    }
}
