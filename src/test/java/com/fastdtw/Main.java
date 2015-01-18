package com.fastdtw;

import com.fastdtw.dtw.TimeWarpInfo;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.EuclideanDistance;

public class Main {

    public static void main(String[] args) {
        final TimeSeries tsI = TimeSeries
                .create("src/test/resources/trace0.csv", false, false, ',');
        final TimeSeries tsJ = TimeSeries
                .create("src/test/resources/trace1.csv", false, false, ',');

        while (true) {
            TimeWarpInfo info = com.fastdtw.dtw.FastDTW.getWarpInfoBetween(tsI, tsJ, 10,
                    new EuclideanDistance());
            if (info.getDistance() < 0)
                System.out.println("boo");
        }

    }

}
