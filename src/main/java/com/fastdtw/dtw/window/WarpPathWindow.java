/*
 * WarpPathWindow.java   Jul 14, 2004
 *
 * PROJECT DESCRIPTION
 */

package com.fastdtw.dtw.window;

import com.fastdtw.dtw.WarpPath;

/**
 * This class...
 *
 * @author Stan Salvador, stansalvador@hotmail.com
 * @version last changed: Jun 30, 2004
 * @see
 * @since Jun 30, 2004
 */

public final class WarpPathWindow extends SearchWindow {

    public WarpPathWindow(WarpPath path, int searchRadius) {
        super(path.get(path.size() - 1).getCol() + 1, path.get(path.size() - 1).getRow() + 1);

        for (int p = 0; p < path.size(); p++)
            super.markVisited(path.get(p).getCol(), path.get(p).getRow());

        super.expandWindow(searchRadius);
    }

}
