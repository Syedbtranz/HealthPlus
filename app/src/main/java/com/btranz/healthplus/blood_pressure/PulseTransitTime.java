package com.btranz.healthplus.blood_pressure;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Andries on 29/07/2015.
 *
 *
 */
public class PulseTransitTime {

    private static final int WINDOW_SIZE = 3; //Size of moving average filter used for smoothing

    //data
    private ArrayList<Double> hmPeakTimes;
    private Double currPTT;
    //private MovingAverage maf;


    //methods
    public PulseTransitTime() {
        currPTT = 0.0;
        hmPeakTimes = new ArrayList<>();
        //maf = new MovingAverage(WINDOW_SIZE);
    }

    public Double calculatePTT(Double poPeakTime, Double poRRTime)
    {
        //Iterate through the list of R wave occurrences
        for (Double hmPeakTime : hmPeakTimes) {
            Double tempPTT = poPeakTime - hmPeakTime; //Calculate the possible PTT value
            Log.d("PTT calculation", poPeakTime +  " - " + hmPeakTime + " = " + tempPTT);
            if ((tempPTT > 100) && (tempPTT < 1500)) //If the value is within the limits
            {
                //Remove the R wave occurrence from the list
                hmPeakTimes.clear();
                currPTT = tempPTT; //Confirm the PTT
                //maf.newNum(currPTT); //add the newest PTT value to the moving average filter
                //Log.d("PTT calculation", "PTT: " + currPTT);
                return currPTT; //Return the smoothed moving average value
            } else
                currPTT = 0.;

        }

        return currPTT; //If none of the possible PTT values fall within limits, return 0

    }


    public void addHMPeakTime(Double hmPeakTime)
    {
             hmPeakTimes.add(hmPeakTime);
    }



//    private class MovingAverage{
//
//        private final Queue<Double> window = new LinkedList<Double>();
//        private final int period;
//        private double sum;
//
//        public MovingAverage(int period) {
//            this.period = period;
//        }
//
//        public void newNum(double num) {
//            sum += num;
//            window.add(num);
//            if (window.size() > period) {
//                sum -= window.remove();
//            }
//        }
//
//        public double getAvg() {
//            if (window.isEmpty()) return 0; // technically the average is undefined
//            return sum / window.size();
//        }
//    }

}
