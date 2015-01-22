package com.fastdtw.dtw;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.Distances;
import com.fastdtw.util.EuclideanDistance;
import com.fastdtw.dtw.TestingUtil;

@State(Scope.Benchmark)
public class Benchmarks {

    private final TimeSeries tsI = TestingUtil.load("/trace0.csv");
    private final TimeSeries tsJ = TestingUtil.load("/trace1.csv");

    @Benchmark
    public void fastDtw() {
        com.fastdtw.dtw.FastDTW.compare(tsI, tsJ, 10, Distances.EUCLIDEAN_DISTANCE);
    }

}
