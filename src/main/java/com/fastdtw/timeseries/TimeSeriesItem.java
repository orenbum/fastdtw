package com.fastdtw.timeseries;

public final class TimeSeriesItem {
    
    private final double time;
    private final TimeSeriesPoint point;

    public TimeSeriesItem(double time, TimeSeriesPoint point) {
        this.time = time;
        this.point = point;
    }

    public double getTime() {
        return time;
    }

    public TimeSeriesPoint getPoint() {
        return point;
    }

}
