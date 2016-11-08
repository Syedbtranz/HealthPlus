package com.btranz.healthplus.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.Handler.DatabaseHandler;
import com.btranz.healthplus.R;

/**
 * Created by User_Sajid on 4/22/2016.
 */
public class PatientDetails extends android.app.Fragment implements View.OnClickListener{

    String phoneStr,mailStr,tv4,tv5,tv6,tv7;
    TextView nameTxtv,healthTxtv,phoneTxtv,ageTxtv,addressTxtv,recentEwsTxtv,myNotesTxt;
    ImageView callImgV,messageImgV,mailImgV,alertImgV,issuesImgV;
    DatabaseHandler databaseHandler;
    List<String> patList=new ArrayList<String>();
    Button viewdetails;
    DataBaseHandlerMFS dataBaseHandlerMFS;
    int fresultNews;
    List<String> newsList=new ArrayList<String>();
    String patId,userName,hId;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pID = "patId";
    public static final String uName = "userName";
    public static final String hID = "hId";
    SharedPreferences sharedpreferences;

    public PatientDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        viewdetails=(Button)rootView.findViewById(R.id.viewdetails);
        nameTxtv=(TextView)rootView.findViewById(R.id.name_txtv);
        healthTxtv=(TextView)rootView.findViewById(R.id.healthid_txtv);
        phoneTxtv=(TextView)rootView.findViewById(R.id.pnumber_txtv);
        ageTxtv=(TextView)rootView.findViewById(R.id.age_txtv);
        addressTxtv=(TextView)rootView.findViewById(R.id.address_txtv);
        recentEwsTxtv=(TextView)rootView.findViewById(R.id.ews_txtv);
        callImgV=(ImageView)rootView.findViewById(R.id.phoneImg);
        messageImgV=(ImageView)rootView.findViewById(R.id.messageImg);
        mailImgV=(ImageView)rootView.findViewById(R.id.emailImg);
       // myNotesTxt=(TextView)rootView.findViewById(R.id.my_notes_txt);
       // alertImgV=(ImageView)rootView.findViewById(R.id.alertImg);
       // issuesImgV=(ImageView)rootView.findViewById(R.id.issueImg);
        callImgV.setOnClickListener(this);
        messageImgV.setOnClickListener(this);
        mailImgV.setOnClickListener(this);
        //alertImgV.setOnClickListener(this);
       // myNotesTxt.setOnClickListener(this);
        dataBaseHandlerMFS=new DataBaseHandlerMFS(getActivity());

       /* Intent i=getActivity().getIntent();
        savedInstanceState =i.getExtras();
        patId= savedInstanceState.getString("patId");
        userName= savedInstanceState.getString("userName");
        hId= savedInstanceState.getString("hId");
        // Log.i("getIntent",SearchEdit);
        System.out.println("getIntent in PatientD : " + patId);*/


     /*   sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            editor.clear();
        }catch(Exception e){
            e.printStackTrace();
        }
        patId= sharedpreferences.getString(pID,"");
        userName=  sharedpreferences.getString(uName,"");
        hId=   sharedpreferences.getString(hID,"");


               *//* in.putExtra("patId",patId);
                in.putExtra("userName",userName);
                in.putExtra("hId",hId);
*//*
        System.out.println(" patId in SearchedList :" + patId);
        System.out.println(" userName in SearchedList :" + userName);
        System.out.println(" hId in SearchedList :" + hId);*/





        databaseHandler=new DatabaseHandler(getActivity());
       patList= databaseHandler.patExits(Pateints_List111.patId);

        String patListStr=patList.get(0);
        Log.i("patListStr",patListStr);
        String[] patArr=patListStr.split(Pattern.quote("***"));
        String fnameStr=patArr[1];
        Log.i("fnameStr",fnameStr);
        String lnameStr=patArr[2];

         nameTxtv.setText(fnameStr+" "+lnameStr);
        String hidStr=patArr[3];
        healthTxtv.setText(hidStr);
        String addressStr=patArr[4];
        addressTxtv.setText(addressStr);
        String ageStr=patArr[5];
        ageTxtv.setText(ageStr);
        String sexStr=patArr[6];
        String wardStr=patArr[7];
        String roomStr=patArr[8];
        String fallsHisStr=patArr[9];
        String pcpStr=patArr[10];
        phoneStr=patArr[11];
        mailStr=patArr[12];
        phoneTxtv.setText(phoneStr);
        try{
            newsList = dataBaseHandlerMFS.getNews(Pateints_List111.patId);
            Log.i("response news", newsList.get(0));
            String response=newsList.get(0);
            String resp[]=response.split(Pattern.quote("***"));
            String fscore = resp[8];
            Log.i("fscore", fscore);
            fresultNews = Integer.parseInt(fscore);
            if (fresultNews < 4 && fresultNews > 1) {
                recentEwsTxtv.setBackgroundColor(Color.GREEN);
                recentEwsTxtv.setText(fscore);

            } else if (fresultNews <= 6) {
                recentEwsTxtv.setBackgroundColor(Color.YELLOW);
                recentEwsTxtv.setText(fscore);
            } else if (fresultNews >= 7) {
                recentEwsTxtv.setBackgroundColor(Color.RED);
                recentEwsTxtv.setText(fscore);

            }

        }catch (Exception e1){}

/*viewdetails.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent=new Intent(getActivity(), ViewResult.class);
        startActivity(intent);


    }
});*/

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.phoneImg:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+phoneStr));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException e) {
                    Log.e("delight", "Call failed", e);
                }
                break;

            case R.id.emailImg:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ mailStr});
                email.putExtra(Intent.EXTRA_SUBJECT, "testmail");
                email.putExtra(Intent.EXTRA_TEXT, "test message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Select Email Client"));
                break;

            /*case R.id.my_notes_txt:

                break;*/
            case R.id.messageImg:

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneStr));
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

        }
    }
}

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        viewdetails=(Button)findViewById(R.id.viewdetails);

        viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(PatientDetails.this,ViewResult.class);
                startActivity(intent);
                finish();

            }
        });
    }*/





      /*  tv1=savedInstanceState.getString("fnameStr");
        tv2=savedInstanceState.getString("lnameStr");
        tv3=savedInstanceState.getString("healthStr");
        tv4=savedInstanceState.getString("ageStr");
        tv5=savedInstanceState.getString("addressStr");

        aaa=(TextView)findViewById(R.id.fname);
        bbb=(TextView)findViewById(R.id.lname);
        ccc=(TextView)findViewById(R.id.healthid);
        ddd=(TextView)findViewById(R.id.pnumber);
        eee=(TextView)findViewById(R.id.age);
        fff=(TextView)findViewById(R.id.addess);

        aaa.setText(tv1);
        bbb.setText(tv2);
        ccc.setText(tv3);
        ddd.setText(tv4);
        eee.setText(tv5);
        fff.setText(tv6);
*/




