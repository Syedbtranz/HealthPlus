// Alive Technologies
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



import java.util.ArrayList;

import com.btranz.healthplus.alive_ecg.AccView;
import com.btranz.healthplus.alive_ecg.AliveHeartMonitorPacket;
import com.btranz.healthplus.alive_ecg.EcgView;
import com.btranz.healthplus.alive_ecg.HeartMonitorListener;
import com.btranz.healthplus.alive_ecg.HeartMonitorPreferences;
import com.btranz.healthplus.alive_ecg.HeartMonitorService;

public class AliveECGActivity extends AppCompatActivity implements HeartMonitorListener {
	private static final String TAG = " AliveECGActivity";

    private static final int REQUEST_SELECT_BTDEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 4;
    private HeartMonitorService heartMonitorService = null;
    private boolean mIsBound = false;

    private TextView mEcgTitle;
    private TextView mHR;
    private TextView mHRUnits;
    private EcgView mEcgView;
    private AccView mAccView;

	private long timeOfCurrentPacket;
	private ArrayList<Long> hmTimeStamps;
    private long timeOfLastHMPacket;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		hmTimeStamps = new ArrayList<>();

		setContentView(R.layout.activity_alive_ecg);

        getSupportActionBar().setElevation(0);

		mEcgView = (EcgView) findViewById(R.id.ecgView);
		mAccView = (AccView) findViewById(R.id.accView);
		
		mEcgTitle = (TextView) findViewById(R.id.lblTitleECG);
		mHR = (TextView) findViewById(R.id.lblHR);
		mHRUnits = (TextView) findViewById(R.id.lblHRUnits);
		
		mHR.setVisibility(View.GONE);
		mHRUnits.setVisibility(View.GONE);
			
		bindHMService();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
    	if(heartMonitorService != null) {
    		heartMonitorService.unregisterAliveListener();
    	}
		unbindHMService();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG,"Create Options Menu");
		getMenuInflater().inflate(R.menu.menu_alive_ecg, menu);
		MenuItem startStopMenuItem = menu.findItem(R.id.action_startstop);
		if(heartMonitorService == null) {
			startStopMenuItem.setTitle(getResources().getText(R.string.action_start));
			startStopMenuItem.setIcon(R.drawable.ic_play_arrow_white_24dp);
			startStopMenuItem.setEnabled(false);
		} else {
			if(heartMonitorService.isRunning()) {
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
				if(heartMonitorService != null) {
					if(heartMonitorService.isRunning()) {
						stop();
					} else {
						String btAddress = HeartMonitorPreferences.getSharedPreferences(this).getString(HeartMonitorPreferences.PREF_HM_BTADDRESS, null);
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
				startActivity(new Intent(this, HeartMonitorPreferences.class));
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void start() {
      	if (heartMonitorService == null) return;
      	
		if (!heartMonitorService.isRunning()) {
			// Check that Bluetooth is turned on if Heart Monitor sensor is enabled
	 	   if(!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
	 		   // Prompt user to turn on Bluetooth if it is not already on
	 		   // TODO: Add extra check and only prompt to turn on BT if it is required
			   Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			   startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		   }else {
			   //heartMonitorService.start();
			   startService(new Intent(this, HeartMonitorService.class));
		   }
		}
	}
	
	private void stop() {
    	if (heartMonitorService == null) return;
		heartMonitorService.stop();
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
                    String prefHeartMonitorBTName = data.getExtras().getString(BTDeviceListActivity.EXTRA_DEVICE_NAME);
                    String  prefHeartMonitorBTAddress = data.getExtras().getString(BTDeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    if(prefHeartMonitorBTName==null || prefHeartMonitorBTName.length()==0) {
                        prefHeartMonitorBTName = prefHeartMonitorBTAddress;
                    }
                    SharedPreferences.Editor editor = HeartMonitorPreferences.getSharedPreferences(this).edit();
                    editor.putString(HeartMonitorPreferences.PREF_HM_BTNAME,prefHeartMonitorBTName);
                    editor.putString(HeartMonitorPreferences.PREF_HM_BTADDRESS,prefHeartMonitorBTAddress);
                    editor.commit();
                    start();
                }
                break;

        }
    }

    void updateStatus(int status)
    {
        Log.d(TAG,"Update Status");
    	invalidateOptionsMenu();
    	switch(status) {
    	case HeartMonitorStatus.HM_STATUS_CONNECTING:
    		Log.d(TAG, "HM_STATUS_CONNECTING");
    		if(HeartMonitorPreferences.getSharedPreferences(this).getBoolean(HeartMonitorPreferences.PREF_KEEP_SCREEN_ON, false)) {
    			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    		}
    		break;
    	case HeartMonitorStatus.HM_STATUS_CONNECTED:
    		Log.d(TAG, "HM_STATUS_CONNECTED");
    		if(HeartMonitorPreferences.getSharedPreferences(this).getBoolean(HeartMonitorPreferences.PREF_KEEP_SCREEN_ON, false)) {
    			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    		}
    		mHR.setText("---");
    		mHR.setVisibility(View.VISIBLE);
    		mHRUnits.setVisibility(View.VISIBLE);
    		mHR.animate().setDuration(500).alpha(1f);
    		mHRUnits.animate().setDuration(500).alpha(1f);
    		break;
    	case HeartMonitorStatus.HM_STATUS_RECONNECTING:
    		Log.d(TAG, "HM_STATUS_RECONNECTING");
    		break;
    	case HeartMonitorStatus.HM_STATUS_NONE:
    		Log.d(TAG, "HM_STATUS_NONE");
    		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    		mHR.animate().setDuration(500).alpha(0f);
    		mHRUnits.animate().setDuration(500).alpha(0f);
    		mHR.setVisibility(View.GONE);
    		mHRUnits.setVisibility(View.GONE);
            break;
    	}
    }
    
    void updateHeartRate(int hr) {
        Log.d(TAG,"Update Heart Rate");
    	String hrString;
		if (hr == 0) {
			hrString = "---";
		} else {
			hrString = Integer.toString(hr);
		}
    	if(mHR.getText() != hrString) mHR.setText(hrString);

    	if(mHR.getVisibility() != View.VISIBLE) {
    		mHR.setVisibility(View.VISIBLE);
    		mHRUnits.setVisibility(View.VISIBLE);
    	}
    }
 
	private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit service that we know is running in our own process,
        	// we can cast its IBinder to a concrete class and directly access it.
        	
        	// onServiceConnected() is called in the main thread of this activity, so safe to update UI.
        	heartMonitorService = ((HeartMonitorService.HMBinder)service).getService();
        	if(heartMonitorService !=null) {
        		heartMonitorService.registerAliveListener(AliveECGActivity.this);
        		invalidateOptionsMenu();
        		updateStatus(heartMonitorService.getStatus());
        	}
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never see this happen.
    		Log.w(TAG, "Unexpected onServiceDisconnected()");
    		if(heartMonitorService !=null) {
    			heartMonitorService.unregisterAliveListener();
    			heartMonitorService = null;
    		}
        }
    };
    
    void bindHMService() {
        bindService(new Intent(this, HeartMonitorService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void unbindHMService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mServiceConnection);
            mIsBound = false;
        }
    }

	@Override
	public void onAlivePacket(final int timeSampleCount, final AliveHeartMonitorPacket packet) {
		mEcgView.onAlivePacket(timeSampleCount, packet);
		mAccView.onAlivePacket(timeSampleCount, packet);
        long timeOfCurrentPacket = packet.getTimeOfArrival();
        hmTimeStamps.add(timeOfCurrentPacket - timeOfLastHMPacket);
        timeOfLastHMPacket = timeOfCurrentPacket;
	}

	@Override
	public void onAliveHeartBeat(final int timeSampleCount, final double hr, final int rrSamples) {
        runOnUiThread(new Runnable() {
            public void run() {
            	updateHeartRate((int)(hr+0.5));
            }
        });
	}

	@Override
	public void onAliveStatus(final int statusID) {
        runOnUiThread(new Runnable() {
            public void run() {
            	updateStatus(statusID);
            }
        });
	}
}
