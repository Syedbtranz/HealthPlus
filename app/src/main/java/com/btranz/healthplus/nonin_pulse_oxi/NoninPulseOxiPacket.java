package com.btranz.healthplus.nonin_pulse_oxi;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Andries on 22/05/2015.
 *
 * NoninPulseOxi Packet
 * This class is used to decode the serial data packet received from the Nonin pulse oximeter.
 * The output from the device has to be in Serial Data Format 2, described on page 15 of the Onyx
 * II Model 9560 Bluetooth Fingertip Oximeter OEM Specification and Technical Information.
 * This document can be found at http://www.nonin.com/documents/6470-000-05%20ENG.pdf
 */
public class NoninPulseOxiPacket {

    //properties
    //buffer
    private static final int BUFFER_SIZE = 512;

    //Frame States
    private final static int FRAME_STATE_FIRSTBYTE = 1;
    private final static int FRAME_STATE_STATUS = 2;
    private final static int FRAME_STATE_PLETH = 3;
    private final static int FRAME_STATE_MISC_DATA = 4;
    private final static int FRAME_STATE_CHECKSUM = 5;

    //Packet states
    private final static int PACKET_STATE_HR_RECORD_MSB = 1;
    private final static int PACKET_STATE_HR_RECORD_LSB = 2;
    private final static int PACKET_STATE_SPO_RECORD = 3;
    private final static int PACKET_STATE_TIMER_MSB = 4;
    private final static int PACKET_STATE_TIMER_LSB = 5;
    private final static int PACKET_STATE_SPO_DISPLAY = 6;
    private final static int PACKET_STATE_HR_DISPLAY_MSB = 7;
    private final static int PACKET_STATE_HR_DISPLAY_LSB = 8;


    private int status;
    private int[] mBuffer = new int[BUFFER_SIZE];

    //record data
    private int heartRateRecord;
    private int spoRecord;


    //display data
    private int spoDisplay;
    private int heartRateDisplay;
    private int[] plethData;



    private int mFrameCheckSum;
    private int mPacketIndex;
    private int mFrameState;
    private int mPacketState;
    private int mFrameIndex;
    private boolean outOfTrack;
    private boolean sensorAlarm;
    private boolean frameSync;
    private long timeOfArrival;
    private ArrayList<Long> plethTimeStamps;


    //methods

    //Constructor
    public NoninPulseOxiPacket()
    {
        init();
    }

    public void init() {
        plethTimeStamps = new ArrayList<>();
        plethData = new int[25];
        heartRateDisplay = 0;
        heartRateRecord = 0;
        status = 0;
        spoDisplay = 0;
        spoRecord = 0;
        mFrameIndex = 0;

        mPacketState = PACKET_STATE_HR_RECORD_MSB;
        mFrameState = FRAME_STATE_FIRSTBYTE;
    }

    public int[] getPlethData() {
        return plethData;
    }

    public int getHeartRateRecord() {
        return heartRateRecord;
    }

    public int getSpoRecord() {
        return spoRecord;
    }

    public int getSpoDisplay() {
        return spoDisplay;
    }

    public int getHeartRateDisplay() {
        return heartRateDisplay;
    }

    /*statusChecker
    *
    * Extract the Nonin Pulse Oximeter status information from the status byte.
    *
    */
    private void statusChecker()
    {
        outOfTrack = ((status & (byte)0x10) != 0); //True on an absence of consecutive good pulse signals.
        sensorAlarm = ((status & (byte)0x08) != 0); //True when device is providing unusable data.
        frameSync = ((status & (byte)0x01) != 0); //Should be True on first frame
    }

    /* add
    * This method receives an incoming byte from the Nonin Pulse Oximeter and moves the information
    * into appropriate fields. This method will return true when a complete packet (75 bytes) has been
    * received
    */
    public boolean add(byte dataByte)
    {
        if (dataByte < 0)
            mBuffer[mPacketIndex] = (int)dataByte & 0xFF;
        else
            mBuffer[mPacketIndex] = (int)dataByte;

        switch (mFrameState)
        {
            case FRAME_STATE_FIRSTBYTE:
                if (mBuffer[mPacketIndex] == 1) //Check that the first byte is valid
                {
                    mFrameCheckSum = 0; //reset checksum
                    mFrameState = FRAME_STATE_STATUS;
                }
                else
                    mPacketIndex = 0;

                break;


            case FRAME_STATE_STATUS:
                status = mBuffer[mPacketIndex];
                statusChecker();
                if (mFrameIndex == 0) //If this is the firstframe
                {
                    if (frameSync) //Framesync should be TRUE
                    {
                        mFrameState = FRAME_STATE_PLETH;
                    } else
                        mPacketIndex = 0;
                }
                else
                    mFrameState = FRAME_STATE_PLETH;
                break;

            case FRAME_STATE_PLETH:
                try
                {
                    plethData[mFrameIndex] = mBuffer[mPacketIndex];  //Pleth
                    plethTimeStamps.add(SystemClock.uptimeMillis());
                }
                catch (IndexOutOfBoundsException e)
                {
                    Log.d("PlethData", "Index Overrun");
                }
                mFrameState = FRAME_STATE_MISC_DATA;
                break;

            case FRAME_STATE_MISC_DATA:
                mFrameState = FRAME_STATE_CHECKSUM;
                switch (mPacketState)
                {
                    case PACKET_STATE_HR_RECORD_MSB :
                        if (mPacketIndex==3)
                        {
                            heartRateRecord = (mBuffer[mPacketIndex] & 0x300) << 7; //Read bit 0 and 1 of HR MSB, see documentation page 11
                            mPacketState = PACKET_STATE_HR_RECORD_LSB;
                        }
                        break;

                    case PACKET_STATE_HR_RECORD_LSB :
                        if (mPacketIndex==8)
                        {
                            heartRateRecord |= mBuffer[mPacketIndex]; //Read bits 0-6 of HR LSB, see documentation page 11
                            mPacketState = PACKET_STATE_SPO_RECORD;
                        }

                        break;

                    case PACKET_STATE_SPO_RECORD :
                        if (mPacketIndex==13)
                        {
                            spoRecord = mBuffer[mPacketIndex];
                            mPacketState = PACKET_STATE_TIMER_MSB;
                        }

                        break;

                    case PACKET_STATE_TIMER_MSB :
                        if (mPacketIndex==28)
                        {
                            mPacketState = PACKET_STATE_TIMER_LSB;
                        }
                        break;

                    case PACKET_STATE_TIMER_LSB :
                        if (mPacketIndex==33)
                        {
                            mPacketState = PACKET_STATE_SPO_DISPLAY;
                        }

                        break;

                    case PACKET_STATE_SPO_DISPLAY :
                        if (mPacketIndex==43)
                        {
                            spoDisplay = mBuffer[mPacketIndex];
                            mPacketState = PACKET_STATE_HR_DISPLAY_MSB;
                        }

                        break;

                    case PACKET_STATE_HR_DISPLAY_MSB :
                        if(mPacketIndex==98)
                        {
                            heartRateDisplay = ((mBuffer[mPacketIndex] & 0x03) << 7);
                            mPacketState = PACKET_STATE_HR_DISPLAY_LSB;
                        }
                        break;

                    case PACKET_STATE_HR_DISPLAY_LSB :
                        if (mPacketIndex==103)
                        {
                            heartRateDisplay |= (mBuffer[mPacketIndex] & 0x7F);
                        }
                        break;
                }

                break;

            case FRAME_STATE_CHECKSUM:
                if ((mFrameCheckSum%256) == mBuffer[mPacketIndex])
                {
                    mFrameIndex++;
                    mFrameState = FRAME_STATE_FIRSTBYTE;
                    if (mFrameIndex >= 25) //if the end of the packet has been reached
                    {
                        mFrameIndex = 0;
                        mPacketIndex = 0;
                        mPacketState = PACKET_STATE_HR_RECORD_MSB;
                        timeOfArrival = SystemClock.uptimeMillis();
                        return(true);
                    }
                }
                else
                {
                    mFrameIndex = 0;
                    mPacketIndex = 0;
                    mFrameState = FRAME_STATE_FIRSTBYTE;
                    mPacketState = PACKET_STATE_HR_RECORD_MSB;
                }
                break;
        }
        mFrameCheckSum += mBuffer[mPacketIndex];
        mPacketIndex++;
        return (false);
    }

    public long getTimeOfArrival() {
        return timeOfArrival;
    }

    public ArrayList<Long> getPlethTimeStamps() {
        return plethTimeStamps;
    }
}
