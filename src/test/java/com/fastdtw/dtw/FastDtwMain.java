package com.fastdtw.dtw;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.EuclideanDistance;

public class FastDtwMain {
    public static void main(String[] args) {
        System.out.println("running");
        final TimeSeries tsI = TestingUtil.load("/trace0.csv");
        final TimeSeries tsJ = TestingUtil.load("/trace1.csv");

        while (true)
            com.fastdtw.dtw.FastDTW.compare(tsI, tsJ, 10, new EuclideanDistance());
    }
}
