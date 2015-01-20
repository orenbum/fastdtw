package com.fastdtw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesBase;
import com.fastdtw.timeseries.TimeSeriesPoint;

public class TestingUtil {
    
    public static TimeSeries load(String resourceName) {
        InputStream is = FastDtwTest.class.getResourceAsStream(resourceName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        List<Double> timeReadings = new ArrayList<Double>();
        List<TimeSeriesPoint> points = new ArrayList<TimeSeriesPoint>();
        try {
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0) {
                    double x = Double.parseDouble(line);
                    TimeSeriesPoint point = new TimeSeriesPoint(new double[] { x });
                    timeReadings.add(x);
                    points.add(point);
                }
            }
            return new TimeSeriesBase(timeReadings, points);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                // don't care
            }
        }
    }
}
