package com.btranz.healthplus;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.btranz.healthplus.nonin_pulse_oxi.NoninPulseOxiPacket;
import com.btranz.healthplus.nonin_pulse_oxi.PpgView;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterListener;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterPreferences;
import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterService;

import java.util.ArrayList;


public class PulseOxiActivity extends AppCompatActivity implements PulseOximeterListener {

    private static final String TAG = "PulseOximeterActivity";

    private static final int REQUEST_SELECT_BTDEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 4;
    private PulseOximeterService pulseOximeterService = null;
    private boolean mIsBound = false;

    private TextView busyConnecting;

    private TextView mPR;
    private TextView mPRUnits;

    private TextView mSPO;
    private TextView mSPOUnits;

    private PpgView ppgView;
    private long timeOfCurrentPacket;
    private ArrayList<Long> timeStamps;
    private long timeOfLastPOPacket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timeStamps = new ArrayList<>();

        setContentView(R.layout.activity_pulse_oxi);

        getSupportActionBar().setElevation(0);

        ppgView = (PpgView) findViewById(R.id.ppgView);

        mPR = (TextView) findViewById(R.id.lblHR);
        mPRUnits = (TextView) findViewById(R.id.lblHRUnits);

        mPR.setVisibility(View.GONE);
        mPRUnits.setVisibility(View.GONE);

        mSPO = (TextView) findViewById(R.id.lblSPO);
        mSPOUnits = (TextView) findViewById(R.id.lblSPOUnits);

        mSPO.setVisibility(View.GONE);
        mSPOUnits.setVisibility(View.GONE);

        busyConnecting = (TextView) findViewById(R.id.textViewConnecting);

        busyConnecting.setVisibility(View.GONE);

        bindPOService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pulseOximeterService !=null) {
            pulseOximeterService.unregisterPulseOxiListener();
        }
        unbindPOService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alive_ecg, menu);
        MenuItem startStopMenuItem = menu.findItem(R.id.action_startstop);
        if(pulseOximeterService == null) {
            startStopMenuItem.setTitle(getResources().getText(R.string.action_start));
            startStopMenuItem.setIcon(R.drawable.ic_play_arrow_white_24dp);
            startStopMenuItem.setEnabled(false);
        } else {
            if(pulseOximeterService.isRunning()) {
                startStopMenuItem.setTitle(getResources().getText(R.string.action_stop));
                startStopMenuItem.setIcon(R.drawable.ic_stop_white_24dp);
            } else {
                startStopMenuItem.setTitle(getResources().getText(R.string.action_start));
                startStopMenuItem.setIcon(R.drawable.ic_play_arrow_white_24dp);
            }
            if(!startStopMenuItem.isEnabled()) startStopMenuItem.setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_startstop:
                if(pulseOximeterService !=null) {
                    if(pulseOximeterService.isRunning()) {
                        stop();
                    } else {
                        String btAddress = PulseOximeterPreferences.getSharedPreferences(this).getString(PulseOximeterPreferences.PREF_PO_BTADDRESS, null);
                        if(TextUtils.isEmpty(btAddress)) {
                            // If a heart monitor has not been setup, start the activity to select the BT device
                            startActivityForResult(new Intent(this, BTDeviceListActivity.class), REQUEST_SELECT_BTDEVICE);
                        }else {
                            start();
                        }
                    }
                }
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, PulseOximeterPreferences.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void start() {
        if(pulseOximeterService == null) return;

        if(!pulseOximeterService.isRunning()) {
            // Check that Bluetooth is turned on if Heart Monitor sensor is enabled
            if(!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                // Prompt user to turn on Bluetooth if it is not already on
                // TODO: Add extra check and only prompt to turn on BT if it is required
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }else {
                //pulseOximeterService.start();
                startService(new Intent(this, PulseOximeterService.class));
            }
        }
    }

    private void stop() {
        if(pulseOximeterService == null) return;
        pulseOximeterService.stop();
        invalidateOptionsMenu();
    }


    protected void  onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // Check if Bluetooth enabled rather than using resultCode because of Bug.
                // See http://code.google.com/p/android/issues/detail?id=9013
                if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    start();
                }
                break;

            case REQUEST_SELECT_BTDEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String prefPulseOximeterBTName = data.getExtras().getString(BTDeviceListActivity.EXTRA_DEVICE_NAME);
                    String  prefPulseOximeterBTAddress = data.getExtras().getString(BTDeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    if(prefPulseOximeterBTName==null || prefPulseOximeterBTName.length()==0) {
                        prefPulseOximeterBTName = prefPulseOximeterBTAddress;
                    }
                    SharedPreferences.Editor editor = PulseOximeterPreferences.getSharedPreferences(this).edit();
                    editor.putString(PulseOximeterPreferences.PREF_PO_BTNAME,prefPulseOximeterBTName);
                    editor.putString(PulseOximeterPreferences.PREF_PO_BTADDRESS,prefPulseOximeterBTAddress);
                    editor.apply();
                    start();
                }
                break;

        }
    }

    void updateStatus(int status)
    {
        invalidateOptionsMenu();
        switch(status) {
            case PulseOximeterListener.PulseOximeterStatus.PO_STATUS_CONNECTING:
                Log.d(TAG, "PO_STATUS_CONNECTING");
                if(PulseOximeterPreferences.getSharedPreferences(this).getBoolean(PulseOximeterPreferences.PREF_KEEP_SCREEN_ON, false)) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                busyConnecting.setVisibility(View.VISIBLE);
                break;

            case PulseOximeterListener.PulseOximeterStatus.PO_STATUS_CONNECTED:
                Log.d(TAG, "PO_STATUS_CONNECTED");
                if(PulseOximeterPreferences.getSharedPreferences(this).getBoolean(PulseOximeterPreferences.PREF_KEEP_SCREEN_ON, false)) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                busyConnecting.setVisibility(View.GONE);
                mPR.setText("---");
                mPR.setVisibility(View.VISIBLE);
                mPRUnits.setVisibility(View.VISIBLE);
                mPR.animate().setDuration(500).alpha(1f);
                mPRUnits.animate().setDuration(500).alpha(1f);

                mSPO.setText("---");
                mSPO.setVisibility(View.VISIBLE);
                mSPOUnits.setVisibility(View.VISIBLE);
                mSPO.animate().setDuration(500).alpha(1f);
                mSPOUnits.animate().setDuration(500).alpha(1f);
                break;

            case PulseOximeterListener.PulseOximeterStatus.PO_STATUS_RECONNECTING:
                Log.d(TAG, "PO_STATUS_RECONNECTING");
                busyConnecting.setVisibility(View.VISIBLE);
                break;

            case PulseOximeterListener.PulseOximeterStatus.PO_STATUS_NONE:
                Log.d(TAG, "PO_STATUS_NONE");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                mPR.animate().setDuration(500).alpha(0f);
                mPRUnits.animate().setDuration(500).alpha(0f);
                mPR.setVisibility(View.GONE);
                mPRUnits.setVisibility(View.GONE);

                mSPO.animate().setDuration(500).alpha(0f);
                mSPOUnits.animate().setDuration(500).alpha(0f);
                mSPO.setVisibility(View.GONE);
                mSPOUnits.setVisibility(View.GONE);
                break;
        }
    }

    private void setPulseOximeterValues(int pulseRate, int oxygenSaturation)
    {
        Toast.makeText(PulseOxiActivity.this, Integer.toString(pulseRate), Toast.LENGTH_SHORT).show();
        mPR.setText(Integer.toString(pulseRate));
        mSPO.setText(Integer.toString(oxygenSaturation));
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit service that we know is running in our own process,
            // we can cast its IBinder to a concrete class and directly access it.

            // onServiceConnected() is called in the main thread of this activity, so safe to update UI.
            pulseOximeterService = ((PulseOximeterService.HMBinder)service).getService();
            if(pulseOximeterService !=null) {
                pulseOximeterService.registerPulseOxiListener(PulseOxiActivity.this);
                invalidateOptionsMenu();
                updateStatus(pulseOximeterService.getStatus());
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
        bindService(new Intent(this, PulseOximeterService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void unbindPOService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mServiceConnection);
            mIsBound = false;
        }
    }

    @Override
    public void onPulseOxiPacket(int timeSampleCount, NoninPulseOxiPacket packet) {
        Log.d(TAG, "Packet Received");
        ppgView.onPulseOxiPacket(timeSampleCount, packet);
        Log.i("Heart Rate",String.valueOf(packet.getHeartRateDisplay()));
        Log.i("Spo",String.valueOf(packet.getSpoDisplay()));
        final int pr = packet.getHeartRateDisplay();
       final int spo = packet.getSpoDisplay();
       runOnUiThread(new Runnable() {
           public void run() {
               setPulseOximeterValues(pr, spo);
            }
        });
    }

    @Override
    public void onPulseOxiPeak(int sampleCount, final double hr, int rrSamples) {
        runOnUiThread(new Runnable() {
            public void run() {
                setPulseOximeterValues((int)(hr+0.5), 100);
            }
        });
    }

    @Override
    public void onPulseOxiStatus(final int statusID) {
        runOnUiThread(new Runnable() {
            public void run() {
                updateStatus(statusID);
            }
        });
    }
}
