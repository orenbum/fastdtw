/*
 * SineWave.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.timeseries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class...
 *
 * @author Stan Salvador, stansalvador@hotmail.com
 * @version last changed: Jun 30, 2004
 * @see
 * @since Jun 30, 2004
 */

public class SineWave implements TimeSeries {
    private static final Random rand = new Random();
    private final TimeSeries base;
    

    public SineWave(int length, double cycles, double noise) {
        List<Double> timeReadings = new ArrayList<Double>();
        List<TimeSeriesPoint> points = new ArrayList<TimeSeriesPoint>();
        for (int x = 0; x < length; x++) {
            final double nextPoint = Math.sin((double) x / length * 2.0 * Math.PI * cycles)
                    + rand.nextGaussian() * noise;
            timeReadings.add( (double) x);
            points.add(new TimeSeriesPoint(new double[] { nextPoint }));
        }
        base = new TimeSeriesBase(timeReadings, points);
    }

    @Override
    public int size() {
        return base.size();
    }

    @Override
    public int numOfDimensions() {
        return base.numOfDimensions();
    }

    @Override
    public double getTimeAtNthPoint(int n) {
        return base.getTimeAtNthPoint(n);
    }

    @Override
    public double getMeasurement(int pointIndex, int valueIndex) {
        return base.getMeasurement(pointIndex, valueIndex);
    }

    @Override
    public double[] getMeasurementVector(int pointIndex) {
        return base.getMeasurementVector(pointIndex);
    }
}
