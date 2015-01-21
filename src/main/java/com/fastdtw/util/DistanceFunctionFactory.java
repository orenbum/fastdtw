/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.util;

public class DistanceFunctionFactory {
    public static final DistanceFunction EUCLIDEAN_DIST_FN = new EuclideanDistance();
    public static final DistanceFunction MANHATTAN_DIST_FN = new ManhattanDistance();
    public static final DistanceFunction BINARY_DIST_FN = new BinaryDistance();

    public static DistanceFunction getDistFnByName(String distFnName) {
        if (distFnName.equals("EuclideanDistance")) {
            return EUCLIDEAN_DIST_FN;
        } else if (distFnName.equals("ManhattanDistance")) {
            return MANHATTAN_DIST_FN;
        } else if (distFnName.equals("BinaryDistance")) {
            return BINARY_DIST_FN;
        } else {
            throw new IllegalArgumentException("There is no DistanceFunction for the name "
                    + distFnName);
        }
    }
}