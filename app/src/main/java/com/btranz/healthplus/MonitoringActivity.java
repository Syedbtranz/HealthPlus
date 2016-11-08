package com.btranz.healthplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import com.btranz.healthplus.Handler.DataBaseHandlerMFS;
import com.btranz.healthplus.activity.Pateints_List111;
import com.btranz.healthplus.alive_ecg.AliveHeartMonitorPacket;
import com.btranz.healthplus.alive_ecg.EcgView;
import com.btranz.healthplus.alive_ecg.HeartMonitor;
import com.btranz.healthplus.alive_ecg.HeartMonitorListener;
import com.btranz.healthplus.alive_ecg.HeartMonitorPreferences;
import com.btranz.healthplus.alive_ecg.HeartMonitorService;
import com.btranz.healthplus.blood_pressure.CalibrationDialog;
import com.btranz.healthplus.blood_pressure.ContinuousBloodPressure;
import com.btranz.healthplus.blood_pressure.PulseTransitTime;
import com.btranz.healthplus.nonin_pulse_oxi.NoninPulseOxiPacket;
import com.btranz.healthplus.nonin_pulse_oxi.PpgView;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeter;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterListener;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterPreferences;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterService;


public class MonitoringActivity extends AppCompatActivity implements HeartMonitorListener, PulseOximeterListener {

    private static final String TAG = "Collective Activity";
    private static final double PO_START_TIME = 5000.0;
    private PulseOximeterService pulseOximeterService = null;
    private HeartMonitorService heartMonitorService = null;
    private boolean poIsBound = false;
    private boolean hmIsBound = false;

    public static CountDownLatch bpSyncLatch = null;
    DataBaseHandlerMFS dataBaseHandlerMFS;

    private static final int REQUEST_SELECT_BTDEVICE = 1;
    private static final int REQUEST_CALIBRATION = 0;
    private static final int REQUEST_ENABLE_BT = 4;

    private String MPR;
    private String MSPO;

    private PpgView ppgView;
    private EcgView ecgView;
    private boolean PTTCalibrate;
    private TextView mHR;
    private TextView mHRUnits;
    private TextView busyConnecting;
    private TextView mPR;
    private TextView mSPO;
    private TextView mSPOUnits;
    private TextView mSPOlbl;
    private TextView mBPUnits;
    private TextView mBP;
    private ImageView imageViewHeartBeatIndicator;
    private AlphaAnimation heartBeatAnimation;

    private boolean pulseOxiConnected;
    private boolean heartMonitorConnected;
    private PulseTransitTime PTTcalc;
    private ContinuousBloodPressure continuousBloodPressure;
    private boolean bloodPressureMeasurement = false; //flag indicating if blood pressure measurement is active
    private float systolicCalibrate;
    String date;
    DBHandler dbHandler = new DBHandler(this);
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        ppgView = (PpgView) findViewById(R.id.collectivePpgView);
        ecgView = (EcgView) findViewById(R.id.collectiveEcgView);

        mHR = (TextView) findViewById(R.id.lblHR_Alive);
        mHRUnits = (TextView) findViewById(R.id.lblHRUnits);

        busyConnecting = (TextView) findViewById(R.id.textViewConnecting);
        mPR = (TextView) findViewById(R.id.lblHR);

        mSPO = (TextView) findViewById(R.id.lblSPO);
        mSPOUnits = (TextView) findViewById(R.id.lblSPOUnits);
        mSPOlbl = (TextView) findViewById(R.id.SpO_text);

        mBPUnits = (TextView) findViewById(R.id.blood_pressure_units);
        mBP = (TextView) findViewById(R.id.blood_pressure_value);

        imageViewHeartBeatIndicator = (ImageView) findViewById(R.id.imageView2);


        dataBaseHandlerMFS = new DataBaseHandlerMFS(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        date = dateFormat.format(new Date()).toString();
        PTTcalc = new PulseTransitTime();

        continuousBloodPressure = new ContinuousBloodPressure();
        bindHMService();
        bindPOService();

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

        busyConnecting.setVisibility(View.GONE);
        mPR.setVisibility(View.GONE);
        mSPO.setVisibility(View.GONE);
        mSPOUnits.setVisibility(View.GONE);
        mSPOlbl.setVisibility(View.GONE);
        mHR.setVisibility(View.GONE);
        mBPUnits.setVisibility(View.GONE);
        mBP.setVisibility(View.GONE);

        heartBeatAnimation = new AlphaAnimation(0f, 1f);
        heartBeatAnimation.setDuration(200);
        heartBeatAnimation.setRepeatCount(1);

        MPR = mPR.getText().toString();
        MSPO = mSPO.getText().toString();


        System.out.println("redirect to DBHandler" + dbHandler);
        dbHandler.insertNewData(MPR, MSPO);
        System.out.println("databaseHandler" + dbHandler);


    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindPOService();
        unbindHMService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindHMService();
        bindPOService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "called");

//        if (heartMonitorService != null) {
//            heartMonitorService.unregisterAliveListener();
//        }
//
//        if (pulseOximeterService != null) {
//            pulseOximeterService.unregisterPulseOxiListener();
//        }

        try {
            unbindHMService();
            unbindPOService();
        } catch (IllegalArgumentException e) {
            Log.d("onDestroy", "Service unbinding error");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "Create Options Menu");
        getMenuInflater().inflate(R.menu.menu_monitoring_, menu);
        MenuItem startStopMenuItem = menu.findItem(R.id.action_startstop);

        if (pulseOximeterService == null && heartMonitorService == null) {
            startStopMenuItem.setTitle(getResources().getText(R.string.action_start));
            startStopMenuItem.setIcon(R.drawable.ic_play_arrow_white_24dp);
            startStopMenuItem.setEnabled(false);
        } else {
            if (heartMonitorService != null) {
                if (heartMonitorService.isRunning()) {
                    startStopMenuItem.setTitle(getResources().getText(R.string.action_stop));
                    startStopMenuItem.setIcon(R.drawable.ic_stop_white_24dp);
                }
            }

            if (pulseOximeterService != null) {
                if (pulseOximeterService.isRunning()) {
                    startStopMenuItem.setTitle(getResources().getText(R.string.action_stop));
                    startStopMenuItem.setIcon(R.drawable.ic_stop_white_24dp);
                }
            }
            if (!startStopMenuItem.isEnabled()) startStopMenuItem.setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_startstop:
                bpSyncLatch = null;
                if (pulseOximeterService != null && heartMonitorService != null) {
                    if (pulseOximeterService.isRunning() || heartMonitorService.isRunning()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MonitoringActivity.this, AlertDialog.THEME_HOLO_DARK);
                        builder.setTitle("Save recording")
                                .setMessage("Would you like to save the recording?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dataBaseHandlerMFS.inserHeartRate(MPR, MSPO, Pateints_List111.patId,date);
                                        Toast.makeText(MonitoringActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                                        stop();
                                        //Save the recorded data to parse
                                     /*   ParseUser parseUser = ParseUser.getCurrentUser();
                                        //Get array lists from services
                                        ArrayList<Double> ecgData = heartMonitorService.getECGdata();
                                        ArrayList<Double> ppgData = pulseOximeterService.getPpgData();
                                        Toast.makeText(MonitoringActivity.this, String.valueOf(ecgData.get(0)), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(MonitoringActivity.this, String.valueOf(ppgData.get(0)), Toast.LENGTH_SHORT).show();
                                        //Stop services
                                        stop();

                                        if (ecgData.size() > 0) {
                                            ParseObject parseObjectECG = new ParseObject("ECGdata");
                                            parseObjectECG.setACL(new ParseACL(parseUser));
                                            parseObjectECG.put("data", ecgData);
                                            parseObjectECG.put("user", parseUser);
                                            parseObjectECG.saveInBackground();
                                        }


                                        if (ppgData.size() > 0) {
                                            ParseObject parseObjectPPG = new ParseObject("PPGdata");
                                            parseObjectPPG.setACL(new ParseACL(parseUser));
                                            parseObjectPPG.put("data", ppgData);
                                            parseObjectPPG.put("user", parseUser);
                                            parseObjectPPG.saveInBackground();
                                        }*/
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //clear local data
                                        stop();
                                    }
                                });
                        AlertDialog saveDialog = builder.create();
                        saveDialog.show();

                    } else {
                        start();
                    }
                }
                return true;

            case R.id.action_bp:
                AlertDialog.Builder builder = new AlertDialog.Builder(MonitoringActivity.this, AlertDialog.THEME_HOLO_DARK);
                builder.setTitle("Blood Pressure Measurement")
                        .setMessage("To calibrate please take blood pressure measurement while holding the play button on the Alive Heart Monitor, then click calibrate to enter results.")
                        .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Log.d("BP alert", "OK");
                                if (pulseOximeterService != null && heartMonitorService != null) {
                                    //stop();
                                    bpSyncLatch = new CountDownLatch(2);
                                    //start();

                                    //initialise the variables needed for bp measurement
                                    PTTcalc = new PulseTransitTime(); //Clear the PTTcalculator
                                    bloodPressureMeasurement = true;
                                    mBPUnits.setVisibility(View.VISIBLE);
                                    mBP.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .setNegativeButton("Stop", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                // Return to monitoring and disable blood pressure monitoring
                                bloodPressureMeasurement = false;
                                mBPUnits.setVisibility(View.GONE);
                                mBP.setVisibility(View.GONE);
                            }
                        })
                        .setNeutralButton("Calibrate", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked calibrate
                                bloodPressureMeasurement = false;
                                startActivityForResult(new Intent(MonitoringActivity.this, CalibrationDialog.class), REQUEST_CALIBRATION);
                            }
                        });

                AlertDialog bpDialog = builder.create();
                bpDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void start() {
        if (heartMonitorService == null || pulseOximeterService == null) return;

        if (!pulseOximeterService.isRunning()) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                // Prompt user to turn on Bluetooth if it is not already on
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            } else //start pulse oximeter service
                startService(new Intent(this, PulseOximeterService.class));
        }

        if (!heartMonitorService.isRunning()) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                // Prompt user to turn on Bluetooth if it is not already on
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            } else //start heart monitor service
                startService(new Intent(this, HeartMonitorService.class));
        }

    }

    private void stop() {
        if (pulseOximeterService != null)
            pulseOximeterService.stop();

        if (heartMonitorService != null)
            heartMonitorService.stop();

        invalidateOptionsMenu();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // Check if Bluetooth enabled rather than using resultCode because of Bug.
                // See http://code.google.com/p/android/issues/detail?id=9013
                if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    start();
                }
                break;
            case REQUEST_CALIBRATION:
                if (resultCode == Activity.RESULT_OK) {
                    ParseUser parseUser = ParseUser.getCurrentUser();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                    String height = parseUser.getString("Height"); //height of person in cm
                    query.getInBackground(height, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                // object will be your height
                            } else {
                                // something went wrong
                            }
                        }
                    });
                    Toast.makeText(MonitoringActivity.this, "Height: " + height, Toast.LENGTH_SHORT).show();
                    systolicCalibrate = data.getFloatExtra(CalibrationDialog.SYSTOLIC, 0);
                    continuousBloodPressure.setBPcal(systolicCalibrate);
                    continuousBloodPressure.calibrate(Integer.valueOf(height));
                    Toast.makeText(this, "Systolic value = " + String.valueOf(systolicCalibrate), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Please enter values to calibrate", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private ServiceConnection hmServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit service that we know is running in our own process,
            // we can cast its IBinder to a concrete class and directly access it.

            // onServiceConnected() is called in the main thread of this activity, so safe to update UI.
            heartMonitorService = ((HeartMonitorService.HMBinder) service).getService();
            if (heartMonitorService != null) {
                heartMonitorService.registerAliveListener(MonitoringActivity.this);
                invalidateOptionsMenu();
                updateHMstatus(heartMonitorService.getStatus());
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never see this happen.
            Log.w(TAG, "Unexpected onServiceDisconnected()");
            if (heartMonitorService != null) {
                heartMonitorService.unregisterAliveListener();
                heartMonitorService = null;
            }
        }
    };


    void bindHMService() {
        getApplicationContext().bindService(new Intent(this, HeartMonitorService.class), hmServiceConnection, Context.BIND_AUTO_CREATE);
        hmIsBound = true;
    }

    void unbindHMService() {
        //Log.d("unbindHMService", "hmIsBound = " + hmIsBound);
        if (hmIsBound) {
            // Detach our existing connection.
            hmIsBound = false;
            getApplicationContext().unbindService(hmServiceConnection);
        }
    }

    private ServiceConnection poServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit service that we know is running in our own process,
            // we can cast its IBinder to a concrete class and directly access it.

            // onServiceConnected() is called in the main thread of this activity, so safe to update UI.
            pulseOximeterService = ((PulseOximeterService.HMBinder) service).getService();
            if (pulseOximeterService != null) {
                pulseOximeterService.registerPulseOxiListener(MonitoringActivity.this);
                invalidateOptionsMenu();
                updatePOStatus(pulseOximeterService.getStatus());
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never see this happen.
            Log.w(TAG, "Unexpected onServiceDisconnected()");
            if (pulseOximeterService != null) {
                pulseOximeterService.unregisterPulseOxiListener();
                pulseOximeterService = null;
            }
        }
    };


    void bindPOService() {
        getApplicationContext().bindService(new Intent(this, PulseOximeterService.class), poServiceConnection, Context.BIND_AUTO_CREATE);
        poIsBound = true;
    }

    void unbindPOService() {
        //Log.d("unbindPOService", "poIsBound = " + poIsBound);
        if (poIsBound) {
            // Detach our existing connection.
            getApplicationContext().unbindService(poServiceConnection);
            poIsBound = false;
        }
    }

    void updateHeartRate(int hr) {
        String hrString;
        if (hr == 0) {
            hrString = "---";
        } else {
            hrString = Integer.toString(hr);
            System.out.println(" Heart Rating  " + hr);
        }
        if (mHR.getText() != hrString) mHR.setText(hrString);

//        if(mHR.getVisibility() != View.VISIBLE) {
//            mHR.setVisibility(View.VISIBLE);
//            mHRUnits.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onAliveHeartBeat(int timeSampleCount, final double hr, int rrSamples) {
        PTTcalc.addHMPeakTime(timeSampleCount * (1000.0 / HeartMonitor.SAMPLING_RATE)); //Add the time of the R wave to the PTT calculator

        runOnUiThread(new Runnable() {
            public void run() {
                updateHeartRate((int) (hr + 0.5));
                imageViewHeartBeatIndicator.startAnimation(heartBeatAnimation);
            }
        });
    }

    @Override
    public void onAlivePacket(int timeSampleCount, AliveHeartMonitorPacket packet) {
        ecgView.onAlivePacket(timeSampleCount, packet); //Display the received data

        // Process the ECG data
        final int len = packet.getECGLength();
        final int startIndex = packet.getECGDataIndex();
        final byte[] buffer = packet.getPacketData();
        for (int i = 0; i < len; i++) {
            int nDatum = (buffer[startIndex + i] & 0xFF);
            heartMonitorService.addECGdata(nDatum);
        }

        if (packet.getInfo() == 1) //If the button on the Alive Heart Monitor is being pressed toggle calibration mode
        {
            PTTCalibrate = !PTTCalibrate;
            if (PTTCalibrate) //if PTTcalibrate has been toggled on
            {
                continuousBloodPressure.clearCalibrationPTT(); //Clear the calibration PTT array list
            }
        }

        Log.d("onAlivePacket", "PTTCalibrate: " + String.valueOf(PTTCalibrate));
    }

    void updateHMstatus(int status) {
        Log.d(TAG, "Update HM Status");
        invalidateOptionsMenu();
        switch (status) {
            case HeartMonitorStatus.HM_STATUS_CONNECTING:
                Log.d(TAG, "HM_STATUS_CONNECTING");
                if (HeartMonitorPreferences.getSharedPreferences(this).getBoolean(HeartMonitorPreferences.PREF_KEEP_SCREEN_ON, false)) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                heartMonitorConnected = false;
                if (!pulseOxiConnected)
                    busyConnecting.setVisibility(View.VISIBLE);
                break;
            case HeartMonitorStatus.HM_STATUS_CONNECTED:
                Log.d(TAG, "HM_STATUS_CONNECTED");
                if (HeartMonitorPreferences.getSharedPreferences(this).getBoolean(HeartMonitorPreferences.PREF_KEEP_SCREEN_ON, false)) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                heartMonitorConnected = true;
                busyConnecting.setVisibility(View.GONE);
                mHR.setText("---");

                if (!pulseOxiConnected) {
                    mHR.setVisibility(View.VISIBLE);
                }
                break;
            case HeartMonitorStatus.HM_STATUS_RECONNECTING:
                Log.d(TAG, "HM_STATUS_RECONNECTING");
                heartMonitorConnected = false;
                if (!pulseOxiConnected)
                    busyConnecting.setVisibility(View.VISIBLE);
                break;
            case HeartMonitorStatus.HM_STATUS_NONE:
                Log.d(TAG, "HM_STATUS_NONE");
                heartMonitorConnected = false;
                busyConnecting.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                mHR.setVisibility(View.GONE);
                break;
        }
    }

    void updatePOStatus(int status) {
        invalidateOptionsMenu();
        switch (status) {
            case PulseOximeterListener.PulseOximeterStatus.PO_STATUS_CONNECTING:
                Log.d(TAG, "PO_STATUS_CONNECTING");
                pulseOxiConnected = false;
                if (PulseOximeterPreferences.getSharedPreferences(this).getBoolean(PulseOximeterPreferences.PREF_KEEP_SCREEN_ON, false)) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                if (!heartMonitorConnected)
                    busyConnecting.setVisibility(View.VISIBLE);
                break;

            case PulseOximeterListener.PulseOximeterStatus.PO_STATUS_CONNECTED:
                Log.d(TAG, "PO_STATUS_CONNECTED");
                pulseOxiConnected = true;
                if (PulseOximeterPreferences.getSharedPreferences(this).getBoolean(PulseOximeterPreferences.PREF_KEEP_SCREEN_ON, false)) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                busyConnecting.setVisibility(View.GONE);
                mPR.setText("---");
                mPR.setVisibility(View.VISIBLE);
                mHR.setVisibility(View.GONE); //This should never be visible while mPR is visible

                mSPO.setText("---");
                mSPO.setVisibility(View.VISIBLE);
                mSPOUnits.setVisibility(View.VISIBLE);
                mSPOlbl.setVisibility(View.VISIBLE);
                break;

            case PulseOximeterListener.PulseOximeterStatus.PO_STATUS_RECONNECTING:
                Log.d(TAG, "PO_STATUS_RECONNECTING");
                pulseOxiConnected = false;
                if (!heartMonitorConnected)
                    busyConnecting.setVisibility(View.VISIBLE);
                break;

            case PulseOximeterListener.PulseOximeterStatus.PO_STATUS_NONE:
                Log.d(TAG, "PO_STATUS_NONE");
                pulseOxiConnected = false;

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                busyConnecting.setVisibility(View.GONE);
                mPR.setVisibility(View.GONE);
                mSPO.setVisibility(View.GONE);
                mSPOUnits.setVisibility(View.GONE);
                mSPOlbl.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onAliveStatus(final int statusID) {
        runOnUiThread(new Runnable() {
            public void run() {
                updateHMstatus(statusID);
            }
        });
    }

    @Override
    public void onPulseOxiStatus(final int statusID) {
        runOnUiThread(new Runnable() {
            public void run() {
                updatePOStatus(statusID);
            }
        });
    }

    @Override
    public void onPulseOxiPacket(int timeSampleCount, final NoninPulseOxiPacket packet) {
        ppgView.onPulseOxiPacket(timeSampleCount, packet);
        final int[] ppgArray = packet.getPlethData();
        final int pr = packet.getHeartRateDisplay();
        final int spo = packet.getSpoDisplay();
        for (int reading : ppgArray) pulseOximeterService.addPPGdata(reading);
        runOnUiThread(new Runnable() {
            public void run() {
                setPulseOximeterValues(pr, spo);
            }
        });
    }

    private void setPulseOximeterValues(int pulseRate, int oxygenSaturation) {
        mPR.setText(Integer.toString(pulseRate));// here we getting pulse rate
        mSPO.setText(Integer.toString(oxygenSaturation));//here we getting spo rate
        //System.out.println("mPR"+ mPR);
        //System.out.println("mSPO"+mSPO);
        MPR = Integer.toString(pulseRate);
        MSPO = Integer.toString(oxygenSaturation);
        Log.i("mPR", MPR);
        Log.i("mSPO", MSPO);

        //Toast.makeText(MonitoringActivity.this, Integer.toString(oxygenSaturation), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPulseOxiPeak(int sampleCount, double hr, int rrSamples) {
        Log.d("onPulseOxiPeak", "Entered");
        double currentPTT;
        double currentPOPeak = PO_START_TIME + sampleCount * 1000.0 / PulseOximeter.SAMPLING_RATE; //The time of the current pulse oximeter peak in milliseconds since start
        double rrTime = rrSamples * (1000.0 / PulseOximeter.SAMPLING_RATE); //The most recent RR time in milliseconds
        if (PTTcalc != null) {
            currentPTT = PTTcalc.calculatePTT(currentPOPeak, rrTime);
            if (currentPTT != 0) {
                if (PTTCalibrate) {
                    continuousBloodPressure.addCalibrationPTT(currentPTT);
                } else if (bloodPressureMeasurement) {
                    final double BP = continuousBloodPressure.calculateBP(currentPTT);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBP.setText(String.format("%.0f", BP));

                        }
                    });
                }
            }
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

            if (event2.getX() > event1.getX()) // right swipe
            {
                Intent intent = new Intent(MonitoringActivity.this, MainMenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }

            return true;
        }
    }
}
