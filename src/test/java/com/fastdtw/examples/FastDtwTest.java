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
import com.fastdtw.dtw.WarpPath;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.EuclideanDistance;

public class FastDtwTest {

    private static final double PRECISION = 0.00000001;

    @Test
    public void testDistanceBetweenTrace0AndTrace1() {
        final TimeSeries tsI = new TimeSeries("src/test/resources/trace0.csv", false, false, ',');
        final TimeSeries tsJ = new TimeSeries("src/test/resources/trace1.csv", false, false, ',');

        final TimeWarpInfo info = com.fastdtw.dtw.FastDTW.getWarpInfoBetween(tsI, tsJ, 10,
                new EuclideanDistance());
        assertEquals(9.139400704860002, info.getDistance(), PRECISION);
        // [(0,0),(0,1),(1,2),(2,3),...,(272,272),(273,272),(274,273),(274,274)]
        WarpPath p = info.getPath();
        assertEquals(0, p.get(0).getCol());
        assertEquals(0, p.get(0).getRow());
        assertEquals(0, p.get(1).getCol());
        assertEquals(1, p.get(1).getRow());

        assertEquals(1, p.get(2).getCol());
        assertEquals(2, p.get(2).getRow());

        assertEquals(274, p.get(p.size() - 2).getCol());
        assertEquals(273, p.get(p.size() - 2).getRow());
        assertEquals(274, p.get(p.size() - 1).getCol());
        assertEquals(274, p.get(p.size() - 1).getRow());
    }

}
