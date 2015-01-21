/*
 * TimeSeriesPoint.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.timeseries;

public class TimeSeriesPoint {
    private final double[] measurements;
    private final int hashCode;

    public TimeSeriesPoint(double[] values) {
        int hashCode = 0;
        measurements = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            hashCode += new Double(values[i]).hashCode();
            measurements[i] = values[i];
        }
        this.hashCode = hashCode;
    }

    public double get(int dimension) {
        return measurements[dimension];
    }

    public double[] toArray() {
        return measurements;
    }

    public int size() {
        return measurements.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o instanceof TimeSeriesPoint) {
            final double[] testValues = ((TimeSeriesPoint) o).toArray();
            if (testValues.length == measurements.length) {
                for (int x = 0; x < measurements.length; x++)
                    if (measurements[x] != testValues[x])
                        return false;

                return true;
            } else
                return false;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

}
