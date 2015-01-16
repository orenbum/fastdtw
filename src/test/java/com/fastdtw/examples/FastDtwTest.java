/*
 * FastDtwTest.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fastdtw.dtw.TimeWarpInfo;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.DistanceFunction;
import com.fastdtw.util.DistanceFunctionFactory;
import com.fastdtw.util.EuclideanDistance;

public class FastDtwTest {

    private static final double PRECISION = 0.00000001;

    @Test
    public void testDistanceBetweenTrace0AndTrace1() {
        final TimeSeries tsI = new TimeSeries("src/test/resources/trace0.csv", false, false, ',');
        final TimeSeries tsJ = new TimeSeries("src/test/resources/trace1.csv", false, false, ',');

        final TimeWarpInfo info = com.fastdtw.dtw.FastDTW.getWarpInfoBetween(tsI, tsJ, 10, new EuclideanDistance());
        assertEquals(9.139400704860002, info.getDistance(), PRECISION);
    }

}
