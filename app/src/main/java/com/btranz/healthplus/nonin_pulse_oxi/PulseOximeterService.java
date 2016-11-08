// AliveService.java
// Alive Technologies
package com.btranz.healthplus.nonin_pulse_oxi;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import com.btranz.healthplus.MonitoringActivity;
import com.btranz.healthplus.R;

public class PulseOximeterService extends Service implements PulseOximeterListener {
	private static final String TAG = "PulseOxiService";

	private boolean mRunning = false;
	private boolean mPause = false;
	private long mPauseTime;
    private String mBTAddress;
    private PulseOximeterListener mPulseOxiListener = null;
    private int mStatus;
    private PulseOximeter pulseOximeter = null;
    private long mStartTime=0; // Used to calculate duration

	private ArrayList<Double> ppgData;

    @Override
    public void onCreate() {
    	Log.d(TAG, "onCreate() called");
        ppgData = new ArrayList<>();
    	mStatus = PulseOximeterStatus.PO_STATUS_NONE;
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	Log.d(TAG, "onDestroy() called");
    	if(pulseOximeter !=null) {
    		pulseOximeter.stopConnection();
    		pulseOximeter = null;
    	}
        stopForeground(true);
    }
    public synchronized void registerPulseOxiListener(PulseOximeterListener listener)
    {
   		mPulseOxiListener = listener;
    }
    public synchronized void unregisterPulseOxiListener()
    {
   		mPulseOxiListener = null;
    }

    public int getStatus() {
    	return(mStatus);
    }

    public long getElapsedTime() {
    	long elapsedTime; 
    	if(mPause) {
    		elapsedTime = mPauseTime - mStartTime;
    	}else {
    		elapsedTime = SystemClock.elapsedRealtime() - mStartTime;
    	}
    	return(elapsedTime);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	Log.d(TAG, "onStartCommand() id " + startId + ": " + intent);
    	if ((flags & START_FLAG_RETRY) == 0) {
    		// TODO If it's a restart, do something.
    		Log.i(TAG, "Restarting");
    	}else {
    		Log.i(TAG, "Starting");
    		// TODO Alternative background process.
    	}
    	mStartTime = SystemClock.elapsedRealtime();
    	
    	// Look up preferences to see what connections to start
    	SharedPreferences prefs = PulseOximeterPreferences.getSharedPreferences(this);

        mBTAddress = prefs.getString(PulseOximeterPreferences.PREF_PO_BTADDRESS, "");
    	
    	if(mBTAddress.length()>0 && pulseOximeter ==null) {
			mStatus = PulseOximeterStatus.PO_STATUS_CONNECTING;
    		pulseOximeter = new PulseOximeter(this,mBTAddress);
    		pulseOximeter.startConnection();
    		mRunning = true;
			// We want this service to continue running until it is explicitly stopped, so return sticky.
			startForeground();
    	}else {
    		Log.i(TAG, "Not started, BT device not selected");
    	}
    	
    	// START_NOT_STICKY:  If this service's process is killed, the system will not try to re-created the service.
    	// START_STICKY: If this service's process is killed, later the system will try to re-created the service.
        return START_STICKY;
    }

    public boolean isRunning() {
    	return mRunning;
    }
  
    public void pause() {
    	mPause = true;
    	mPauseTime = SystemClock.elapsedRealtime();
    }
    public void resume() {
    	if(mPause) {
    		mStartTime += SystemClock.elapsedRealtime() - mPauseTime;
    	}
    	mPause = false;
    	
    }
    public boolean isPaused() {
    	return(mPause);
    }
    
    public void stop() {
    	Log.d(TAG, "stop()");
    	if(mRunning) {
	    	mRunning = false;
	    	mPause = false;
	
	    	if(pulseOximeter !=null) {
	    		pulseOximeter.stopConnection();
	    		pulseOximeter = null;
	    	} 	
	    	stopForeground(true);
    	}
    	stopSelf();
    }
    
    private void startForeground() {
    	Log.d(TAG, "startForeground");
        
        Intent intent = new Intent(this, MonitoringActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        		.setOngoing(true)
                .setSmallIcon(R.drawable.ic_action_bluetooth_pulseoxi)
                .setContentIntent(contentIntent)
                .setContentTitle(getText(R.string.app_name))
                .setContentText(getText(R.string.poservice_running));

        // Using string id as a unique number.
        startForeground(R.string.poservice_running, mBuilder.build());


    }

    // Class for clients to access.  Because we know this service always
    // runs in the same process as its clients, we don't need to deal with IPC.
    public class HMBinder extends Binder {
    	public PulseOximeterService getService() {
            return PulseOximeterService.this;
        }
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.
    private final IBinder mBinder = new HMBinder();
    
    
    // HeartMonitorListener callbacks
    public void onPulseOxiPacket(int timeSampleCount, NoninPulseOxiPacket packet) {
    	if(mPause) return;
    	synchronized(this) {
    		if(mPulseOxiListener !=null) {
    			mPulseOxiListener.onPulseOxiPacket(timeSampleCount, packet);
    		}
    	}
    }

    public void onPulseOxiPeak(int sampleCount, double hr, int rrSamples) {
        if(mPause) return;
        synchronized(this) {
            if(mPulseOxiListener !=null) {
                mPulseOxiListener.onPulseOxiPeak(sampleCount, hr, rrSamples);
            }
        }
    }

    public void onPulseOxiStatus(int statusID) {
    	mStatus = statusID;
    	if(mPause) return;
        if (statusID == PulseOximeterStatus.PO_STATUS_NONE)
            stop();
    	synchronized(this) {
    		if(mPulseOxiListener !=null) {
    			mPulseOxiListener.onPulseOxiStatus(statusID);
    		}
    	}
    }

    public void addPPGdata(double reading)
    {
        ppgData.add(reading);
    }

    public ArrayList<Double> getPpgData()
    {
        return ppgData;
    }
}
