package com.fastdtw.timeseries;

public interface TimeSeries {

    int size();

    int numOfDimensions();

    double getTimeAtNthPoint(int n);

    double getMeasurement(int pointIndex, int valueIndex);

    double[] getMeasurementVector(int pointIndex);

}