package com.fastdtw.examples;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.EuclideanDistance;

@State(Scope.Benchmark)
public class Benchmarks {

    private final TimeSeries tsI = TimeSeries.create("src/test/resources/trace0.csv", false, false,
            ',');
    private final TimeSeries tsJ = TimeSeries.create("src/test/resources/trace1.csv", false, false,
            ',');

    @Benchmark
    public void fastDtw() {
        com.fastdtw.dtw.FastDTW.getWarpInfoBetween(tsI, tsJ, 10, new EuclideanDistance());
    }

}
