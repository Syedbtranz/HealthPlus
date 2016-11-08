// AliveListener.java
// Alive Technologies
package com.btranz.healthplus.nonin_pulse_oxi;

public interface PulseOximeterListener {
	class PulseOximeterStatus {
		public static final int PO_STATUS_NONE = 0;
		public static final int PO_STATUS_CONNECTING = 1;
		public static final int PO_STATUS_CONNECTED = 2;
		public static final int PO_STATUS_RECONNECTING = 3; // Connection lost/disconnected, attempting to reconnect.
	}
    void onPulseOxiPacket(int timeSampleCount, final NoninPulseOxiPacket packet);
    void onPulseOxiStatus(int statusID);
	void onPulseOxiPeak(int sampleCount,  double hr, int rrSamples);
}
