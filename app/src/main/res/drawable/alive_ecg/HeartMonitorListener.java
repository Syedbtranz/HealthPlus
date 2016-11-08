// AliveListener.java
// Alive Technologies
package drawable.alive_ecg;

public interface HeartMonitorListener {
	class HeartMonitorStatus {
		public static final int HM_STATUS_NONE=0;
	    public static final int HM_STATUS_CONNECTING=1;
	    public static final int HM_STATUS_CONNECTED=2;
	    public static final int HM_STATUS_RECONNECTING=3; // Connection lost/disconnected, attempting to reconnect.
	}
    void onAliveHeartBeat(int timeSampleCount, double hr, int rrSamples);
    void onAlivePacket(int timeSampleCount, final AliveHeartMonitorPacket packet);
    void onAliveStatus(int statusID);
}
