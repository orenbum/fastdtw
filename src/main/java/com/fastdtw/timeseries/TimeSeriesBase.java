/*
 * TimeSeries.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.timeseries;

import java.util.ArrayList;
import java.util.List;

public class TimeSeriesBase implements TimeSeries {

    private List<TimeSeriesItem> items;
    private final int numDimensions;

    public TimeSeriesBase(List<TimeSeriesItem> items) {
        this.items = items;
        this.numDimensions = items.get(0).getPoint().size(); 
    }
    
    public final static Builder builder() {
        return new Builder();
    }
    
    public final static Builder add(double time, double... values) {
        return builder().add(time, values);
    }
    
    public final static Builder add(double time, TimeSeriesPoint point) {
        return builder().add(time, point);
    }
    
    public final static class Builder {
        
        private List<TimeSeriesItem> items = new ArrayList<TimeSeriesItem>() ;
        
        public Builder add(double time, double... values) {
            items.add(new TimeSeriesItem(time, new TimeSeriesPoint(values)));
            return this;
        }
        
        public Builder add(TimeSeriesItem item) {
            items.add(item);
            return this;
        }
        
        public Builder add(double time, TimeSeriesPoint point) {
            items.add(new TimeSeriesItem(time, point));
            return this;
        }
        
        public TimeSeries build() {
            return new TimeSeriesBase(items);
        }
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public int numOfDimensions() {
        return numDimensions;
    }

    @Override
    public double getTimeAtNthPoint(int n) {
        return items.get(n).getTime();
    }

    @Override
    public double getMeasurement(int pointIndex, int valueIndex) {
        return items.get(pointIndex).getPoint().get(valueIndex);
    }

    @Override
    public double[] getMeasurementVector(int pointIndex) {
        return items.get(pointIndex).getPoint().toArray();
    }

}
