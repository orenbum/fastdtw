package com.fastdtw;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fastdtw.dtw.TimeWarpInfo;
import com.fastdtw.dtw.WarpPath;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesBase;
import com.fastdtw.timeseries.TimeSeriesPoint;

public final class TestingUtil {
    
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
    
    static void assertDifferenceIsOk(final TimeWarpInfo info) {
        final double PRECISION=0.00000001;
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
