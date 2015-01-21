/*
 * FastDtwTest.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.dtw;

import org.junit.Test;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.Distances;

public class FastDtwTest {

    @Test
    public void testDistanceBetweenTrace0AndTrace1() {
        final TimeSeries ts1 = TestingUtil.load("/trace0.csv");
        final TimeSeries ts2 = TestingUtil.load("/trace1.csv");

        final TimeWarpInfo info = FastDTW.compare(ts1, ts2, 10,
                Distances.EUCLIDEAN_DISTANCE);
        TestingUtil.assertDifferenceIsOk(info);
    }

}
