// AliveX.java
// Alive Technologies

package com.btranz.healthplus.alive_ecg.aliveclasses;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.btranz.healthplus.R;

public class AliveX extends Application {
	private static Context mContext;

    public void onCreate(){
        super.onCreate();
        
        AliveX.mContext = getApplicationContext();
		
        // Set initial default preferences
        PreferenceManager.setDefaultValues(getContext(), R.xml.heart_monitor_preferences, false);
        
        // Force display of overflow menu button, even on devices with a hardware menu button
        Util.forceOverflowMenuButton(this);
    }

    public static Context getContext() {
        return AliveX.mContext;
    }


}