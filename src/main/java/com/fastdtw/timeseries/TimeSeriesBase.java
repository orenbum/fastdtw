/*
 * TimeSeries.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.timeseries;

import java.util.List;

public class TimeSeriesBase implements TimeSeries {

    private final List<Double> timeReadings; // ArrayList of Double
    private final List<TimeSeriesPoint> tsArray; // ArrayList of TimeSeriesPoint..
    private final int numDimensions;

    public TimeSeriesBase(List<Double> timeReadings, List<TimeSeriesPoint> tsArray) {
        this.timeReadings = timeReadings;
        this.tsArray = tsArray;
        this.numDimensions = tsArray.get(0).size(); 
    }

    @Override
    public int size() {
        return timeReadings.size();
    }

    @Override
    public int numOfDimensions() {
        return numDimensions;
    }

    @Override
    public double getTimeAtNthPoint(int n) {
        return ((Double) timeReadings.get(n)).doubleValue();
    }

    @Override
    public double getMeasurement(int pointIndex, int valueIndex) {
        return ((TimeSeriesPoint) tsArray.get(pointIndex)).get(valueIndex);
    }

    @Override
    public double[] getMeasurementVector(int pointIndex) {
        return ((TimeSeriesPoint) tsArray.get(pointIndex)).toArray();
    }

    @Override
    public String toString() {
        final StringBuffer outStr = new StringBuffer();
        // Write the data for each row.
        for (int r = 0; r < timeReadings.size(); r++) {
            // Time
            // outStr.append(timeReadings.get(r).toString());

            // The rest of the value on the row.
            final TimeSeriesPoint values = (TimeSeriesPoint) tsArray.get(r);
            for (int c = 0; c < values.size(); c++)
                outStr.append(values.get(c));

            if (r < timeReadings.size() - 1)
                outStr.append("\n");
        }

        return outStr.toString();
    }

}
