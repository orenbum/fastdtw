package com.fastdtw.dtw;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesBase;
import com.fastdtw.timeseries.TimeSeriesBase.Builder;

final class TestingUtil {

    static TimeSeries load(String resourceName) {
        InputStream is = FastDtwTest.class.getResourceAsStream(resourceName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        Builder builder = TimeSeriesBase.builder();
        try {
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0) {
                    double x = Double.parseDouble(line.trim());
                    builder = builder.add(count, x);
                    count+=1;
                }
            }
            return builder.build();
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
        final double PRECISION = 0.00000001;
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
