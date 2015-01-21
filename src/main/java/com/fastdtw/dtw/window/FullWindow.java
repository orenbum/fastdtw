/*
 * FullWindow.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.dtw.window;

import com.fastdtw.timeseries.TimeSeries;

public final class FullWindow extends SearchWindow {

    public FullWindow(TimeSeries tsI, TimeSeries tsJ) {
        super(tsI.size(), tsJ.size());

        for (int i = 0; i < tsI.size(); i++) {
            super.markVisited(i, minJ());
            super.markVisited(i, maxJ());
        }
    }

}
