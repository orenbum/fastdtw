/*
 * TimeSeries.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.timeseries;

import java.util.List;

public class TimeSeriesBase implements TimeSeries {

    private List<TimeSeriesItem> items;
    private final int numDimensions;

    public TimeSeriesBase(List<TimeSeriesItem> items) {
        this.items = items;
        this.numDimensions = items.get(0).getPoint().size(); 
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public int numOfDimensions() {
        return numDimensions;
    }

    @Override
    public double getTimeAtNthPoint(int n) {
        return items.get(n).getTime();
    }

    @Override
    public double getMeasurement(int pointIndex, int valueIndex) {
        return items.get(pointIndex).getPoint().get(valueIndex);
    }

    @Override
    public double[] getMeasurementVector(int pointIndex) {
        return items.get(pointIndex).getPoint().toArray();
    }

}
