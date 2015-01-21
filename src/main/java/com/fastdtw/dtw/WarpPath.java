/*
 * WarpPath.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.dtw;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.fastdtw.matrix.ColMajorCell;

public final class WarpPath {

    private final List<Integer> tsIindexes;
    private final List<Integer> tsJindexes;

    private WarpPath(List<Integer> tsIindexes, List<Integer> tsJindexes) {
        this.tsIindexes = tsIindexes;
        this.tsJindexes = tsJindexes;
    }

    public WarpPath() {
        this(16);
    }

    public WarpPath(int initialCapacity) {
        this(new ArrayList<Integer>(initialCapacity), new ArrayList<Integer>(initialCapacity));
    }

    public int size() {
        return tsIindexes.size();
    }

    public int minI() {
        return tsIindexes.get(0).intValue();
    }

    public int minJ() {
        return tsJindexes.get(0).intValue();
    }

    public int maxI() {
        return tsIindexes.get(tsIindexes.size() - 1).intValue();
    }

    public int maxJ() {
        return tsJindexes.get(tsJindexes.size() - 1).intValue();
    }

    public void addFirst(int i, int j) {
        tsIindexes.add(0, i);
        tsJindexes.add(0, j);
    }

    public void addLast(int i, int j) {
        tsIindexes.add(i);
        tsJindexes.add(j);
    }

    public ColMajorCell get(int index) {
        if ((index > this.size()) || (index < 0))
            throw new NoSuchElementException();
        else
            return new ColMajorCell(tsIindexes.get(index).intValue(),
                    tsJindexes.get(index).intValue());
    }

    @Override
    public String toString() {
        StringBuffer outStr = new StringBuffer("[");
        for (int x = 0; x < tsIindexes.size(); x++) {
            outStr.append("(" + tsIindexes.get(x) + "," + tsJindexes.get(x) + ")");
            if (x < tsIindexes.size() - 1)
                outStr.append(",");
        } // end for loop

        return new String(outStr.append("]"));
    } // end toString()

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if ((obj instanceof WarpPath)) {
            final WarpPath p = (WarpPath) obj;
            if ((p.size() == this.size()) && (p.maxI() == this.maxI()) && (p.maxJ() == this.maxJ())) {
                // Compare each value in the warp path for equality
                for (int x = 0; x < this.size(); x++)
                    if (!(this.get(x).equals(p.get(x))))
                        return false;

                return true;
            } else
                return false;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return tsIindexes.hashCode() * tsJindexes.hashCode();
    }

}
