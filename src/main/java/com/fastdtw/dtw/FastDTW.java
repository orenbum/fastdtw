/*
 * FastDTW.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.dtw;

import com.fastdtw.dtw.window.ExpandedResWindow;
import com.fastdtw.dtw.window.SearchWindow;
import com.fastdtw.timeseries.PAA;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.util.DistanceFunction;

public final class FastDTW {

    public final static int DEFAULT_SEARCH_RADIUS = 1;

    public static TimeWarpInfo compare(TimeSeries tsI, TimeSeries tsJ, int searchRadius,
            DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, searchRadius, distFn);
    }

    public static TimeWarpInfo compare(TimeSeries tsI, TimeSeries tsJ, DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn);
    }

    private static TimeWarpInfo fastDTW(TimeSeries tsI, TimeSeries tsJ, int searchRadius,
            DistanceFunction distFn) {
        if (searchRadius < 0)
            searchRadius = 0;

        final int minTSsize = searchRadius + 2;

        if ((tsI.size() <= minTSsize) || (tsJ.size() <= minTSsize)) {
            // Perform full Dynamic Time Warping.
            return DTW.compare(tsI, tsJ, distFn);
        } else {
            final double resolutionFactor = 2.0;

            final PAA shrunkI = new PAA(tsI, (int) (tsI.size() / resolutionFactor));
            final PAA shrunkJ = new PAA(tsJ, (int) (tsJ.size() / resolutionFactor));

            // Determine the search window that constrains the area of the cost
            // matrix that will be evaluated based on
            // the warp path found at the previous resolution (smaller time
            // series).
            final SearchWindow window = new ExpandedResWindow(tsI, tsJ, shrunkI, shrunkJ, FastDTW
                    .compare(shrunkI, shrunkJ, searchRadius, distFn).getPath(), searchRadius);

            // Find the optimal warp path through this search window constraint.
            return DTW.compare(tsI, tsJ, window, distFn);
        }
    }

}
