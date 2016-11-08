package com.btranz.healthplus.blood_pressure;

import java.util.ArrayList;

/**
 * Created by Andries on 29/08/2015.
 * This class contains data and methods related to continuous blood pressure measurement including
 * the one point calibration values and the actual blood pressure estimations.
 * The procedure used to calculate the blood pressure is based on information found in this document:
 * http://www.somnomedics.eu/fileadmin/SOMNOmedics/Dokumente/article_Gesche_et_al_-_Continuous_BP_Measurement_by_using_the_PTT_Comparison_to_cuff_based_method.pdf
 */
public class ContinuousBloodPressure {
    private static final double BDC = 0.5;
    private static final double P1 = 700.0;
    private static final double P2 = 766000.0;
    private static final double P3 = -1.0;
    private static final double P4 = 9;
    //properties
    private double currBP;
    private double BPcal;
    private ArrayList<Double> BPmeasurements;


    private ArrayList<Double> calibrationPTTs;
    private double BPptt;
    private double height;

    //methods
    public ContinuousBloodPressure(){
        BPcal = 0;
        currBP = 0;
        BPmeasurements = new ArrayList<>();
        calibrationPTTs = new ArrayList<>();
    }

    public void addCalibrationPTT(double PTT){
        calibrationPTTs.add(PTT);
    }

    public void clearCalibrationPTT() { calibrationPTTs.clear();}

    public void setBPcal(double BPcal) {
        this.BPcal = BPcal;
    }

    public boolean calibrate(int height)
    {
        //Calculate average calibration ptt
        if (calibrationPTTs.size() > 0 && BPcal != 0) {
            double pttCal = 0;
            for (double ptt : calibrationPTTs) {
                pttCal += ptt;
            }
            pttCal = pttCal / calibrationPTTs.size();
            //Calculate the Pulse wave velocity using this ptt
            this.height = height;
            double PWV = (BDC * height) / pttCal; // PWV in cm/ms

            //Calculate the first calibration parameter BPptt,cal
            BPptt = P1 * PWV * Math.exp(P3*PWV) + P2*Math.pow(PWV,P4);
            return true;
        }
        else
            return false;
    }

    public double calculateBP(double ptt)
    {
        double PWV = (BDC * height) / ptt; // Calculate the pulse wave velocity
        currBP = P1 * PWV * Math.exp(P3*PWV) + P2*Math.pow(PWV,P4) - (BPptt - BPcal); //Calculate the estimated blood pressure
        //BPmeasurements.add(currBP); //Add this blood pressure measurement to the list
        return currBP;
    }

    public double getCurrBP() {
        return currBP;
    }

    public ArrayList<Double> getBPmeasurements()
    {
        return BPmeasurements;
    }
    
}
