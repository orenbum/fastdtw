/*
 * FastDtwTest.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw;

import org.junit.Test;

import com.fastdtw.dtw.FastDTW;
import com.fastdtw.dtw.TimeWarpInfo;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.EuclideanDistance;

public class FastDtwTest {

    @Test
    public void testDistanceBetweenTrace0AndTrace1() {
        final TimeSeries tsI = TestingUtil.load("/trace0.csv");
        final TimeSeries tsJ = TestingUtil.load("/trace1.csv");

        final TimeWarpInfo info = FastDTW.getWarpInfoBetween(tsI, tsJ, 10, new EuclideanDistance());
        TestingUtil.assertDifferenceIsOk(info);
    }

}
