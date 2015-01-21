/*
 * DtwTest.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.dtw;

import org.junit.Test;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.Distances;

public class DtwTest {

    @Test
    public void testDistanceBetweenTrace0AndTrace1() {
        final TimeSeries tsI = TestingUtil.load("/trace0.csv");
        final TimeSeries tsJ = TestingUtil.load("/trace1.csv");

        final TimeWarpInfo info = DTW.compare(tsI, tsJ, Distances.EUCLIDEAN_DISTANCE);
        TestingUtil.assertDifferenceIsOk(info);
    }

}
