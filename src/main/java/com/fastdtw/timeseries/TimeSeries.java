package com.fastdtw.timeseries;

import java.util.List;

public interface TimeSeries {

    int size();

    int numOfDimensions();

    double getTimeAtNthPoint(int n);

    List<String> getLabels();

    double getMeasurement(int pointIndex, int valueIndex);

    double getMeasurement(int pointIndex, String valueLabel);

    double[] getMeasurementVector(int pointIndex);

}