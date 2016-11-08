package com.btranz.healthplus.activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;


public class DocContact extends android.app.Fragment implements View.OnClickListener{

ImageView callImg,mailImg,messgeImg,skypeImg;
    String callStr,mailStr,messageStr,skypeStr;
    ImageView editBtn;
    DatabaseHandler databaseHandler;
    List<String> contacList=new ArrayList<String>();
    public DocContact() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_contact, container, false);
        databaseHandler=new DatabaseHandler(getActivity());
        callImg=(ImageView)rootView.findViewById(R.id.call_imgv);
        mailImg=(ImageView)rootView.findViewById(R.id.mail_imgv);
        messgeImg=(ImageView)rootView.findViewById(R.id.message_imgv);
        skypeImg=(ImageView)rootView.findViewById(R.id.skype_imgv);
        editBtn=(ImageView)rootView.findViewById(R.id.edit_imgv);

        callImg.setOnClickListener(this);
        mailImg.setOnClickListener(this);
        messgeImg.setOnClickListener(this);
        skypeImg.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        contacList=databaseHandler.contactList();



        if(contacList.size()<=0){


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("No Communication Data");
            alertDialogBuilder.setTitle("Do you want to enter the data?");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent inten=new Intent(getActivity(),ContactEntry.class);
                    startActivity(inten);

                }
            });

           /* alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
            });*/

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }else{
            String contactStr=contacList.get(0);
            String contactArr[]=contactStr.split(Pattern.quote("***"));
            callStr=contactArr[1];
            mailStr=contactArr[2];
            messageStr=contactArr[3];
            skypeStr=contactArr[4];
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.call_imgv:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+callStr));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException e) {
                    Log.e("delight", "Call failed", e);
                }
                break;

            case R.id.mail_imgv:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ mailStr});
                email.putExtra(Intent.EXTRA_SUBJECT, "testmail");
                email.putExtra(Intent.EXTRA_TEXT, "test message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Select Email Client"));
                break;

            case R.id.message_imgv:

try {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + messageStr));
    intent.putExtra("sms_body", "test");
    startActivity(intent);
}catch(Exception e){e.printStackTrace();}

                /*try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(messageStr, null, "test", null, null);
                    System.out.println("");
                }

                catch (Exception e) {
                   Toast.makeText(getActivity(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }*/
                break;

            case R.id.skype_imgv:

                /*Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
                Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);*/
                Intent skypeVideo = new Intent("android.intent.action.VIEW");
                skypeVideo.setData(Uri.parse("skype:" + skypeStr + "?call&video=true"));
                startActivity(skypeVideo);
                break;

            case R.id.edit_imgv:


                Intent intent=new Intent(getActivity(),ContactEdit.class);
                startActivity(intent);
                break;

        }
    }
}
