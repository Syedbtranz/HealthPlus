// AliveHeartMonitor.java
// Alive Technologies
package drawable.nonin_pulse_oxi;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.btranz.healthplus.nonin_pulse_oxi.PulseOximeterListener.PulseOximeterStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class PulseOximeter {
	private static final String TAG = "PulseOximeter";

    // UUID for the Serial Port Profile (SPP)
    private static final UUID SERIALPORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private ConnectionThread mConnectionThread;
    private final BluetoothAdapter mBluetoothAdapter;
    private String mBtAddress;
    private PulseOximeterListener mListener;

    public PulseOximeter(PulseOximeterListener listener, String btAddress) {
       mListener =  listener;
       mBtAddress = btAddress;
       mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    
    private class ConnectionThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private boolean mmCancel=false;
        private boolean mmConnecting=false;
        
        public ConnectionThread() {
            // Use a temporary object that is later assigned to mmSocket, because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = mBluetoothAdapter.getRemoteDevice(mBtAddress);
     
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
            	//tmp = mmDevice.createRfcommSocketToServiceRecord(SERIALPORT_UUID);
                tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(SERIALPORT_UUID);
            } catch (IOException e) {
            	Log.e(TAG, "createInsecureRfcommSocketToServiceRecord() failed", e);
            }
            mmSocket = tmp;
        }
     
        public void run() {
            NoninPulseOxiPacket hmPacket = new NoninPulseOxiPacket();
            byte[] streamBuffer = new byte[256];
            InputStream inStream = null;
            int sampleCount=0;
            
            
            Log.v(TAG, "ConnectionThread started");
            
            while(mmCancel==false) {
	                
	            // Cancel discovery because it will slow down the connection
	        	mBluetoothAdapter.cancelDiscovery();
	        	mmConnecting=true;
	        	
	            try {
	                // Connect the device through the socket. This will block
	                // until it succeeds or throws an exception
	            	Log.i(TAG, "CONNECT START");
	                mmSocket.connect();
	                
	                Log.i(TAG, "CONNECT DONE");
	            } catch (IOException connectException) {
	            	Log.e(TAG, "CONNECT EXCEPTION", connectException);
	            }
	            
	            if(!mmCancel && mmSocket.isConnected()) {

			        InputStream tmpInStream = null;
			        inStream = null;
			        
		   	        // Get the BluetoothSocket input and output streams
		   	        try {
		   	        	tmpInStream = mmSocket.getInputStream();
		   	        } catch (IOException e) {
		   	            Log.e(TAG, "Bluetooth socket not created", e);
		   	        }
		   	        mmConnecting=false;

		    		if(!mmCancel && tmpInStream!=null) {

		    			mListener.onPulseOxiStatus(PulseOximeterStatus.PO_STATUS_CONNECTED);
		    			
		    			inStream = tmpInStream;
		    			try {
				            hmPacket.init();
				            while (!mmCancel) {
				                int nBytesRead;
				                nBytesRead = inStream.read(streamBuffer); // Blocks until data arrives
				                for (int n = 0; n < nBytesRead; n++) {
									if (hmPacket.add(streamBuffer[n])) {
										// We have a packet of data from the pulse oximeter
										mListener.onPulseOxiPacket(25, hmPacket);
				                    }
				                }
				            }
				        }catch (Exception e) {
				            // We get IOException when the socket is closed by the app, or if BT out of range or heart monitor turned off, 
				            // or java.lang.SecurityException if we don't have permissions to make BT connections
				        	// System.gc();
				            Log.e(TAG, e.getMessage());
				        }
		    		} 
	            }
	            
        		if(!mmCancel) {
        			mListener.onPulseOxiStatus(PulseOximeterStatus.PO_STATUS_RECONNECTING);
        			try {
        				// Wait for a few seconds and then try connecting again
        				int count=0;
        				while(!mmCancel && count++ < 50) { Thread.sleep(100); }
        			}catch(InterruptedException e) {}
        		}
        	}
            try {
            	mmSocket.close();
            } catch (Exception closeException) { }
            
            
       		Log.v(TAG, "ConnectionThread exited");
        }
     
        // Will cancel an in-progress connection, and close the socket
        public void cancel() {
            try {
            	mmCancel = true;
            	Log.i(TAG, "mmSocket.close() mmConneccting:" + mmConnecting);
                if(mmConnecting==false) mmSocket.close();
            } catch (IOException e) { }
        }
    }
    
    // Stop and exit the thread
    public synchronized void stopConnection() {
    	if (mConnectionThread != null) {mConnectionThread.cancel(); mConnectionThread = null;}
    	
    	mListener.onPulseOxiStatus(PulseOximeterStatus.PO_STATUS_NONE);
    }
    public synchronized void startConnection()
    {
    	if(mConnectionThread!=null) {mConnectionThread.cancel(); mConnectionThread = null;}
    	
        // Start the thread to manage the connection and perform transmissions
        mConnectionThread = new ConnectionThread();
        mConnectionThread.start();
        mListener.onPulseOxiStatus(PulseOximeterStatus.PO_STATUS_CONNECTING);
    }

}
