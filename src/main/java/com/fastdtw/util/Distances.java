/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.util;

public final class Distances {
    public static final DistanceFunction EUCLIDEAN_DISTANCE = new EuclideanDistance();
    public static final DistanceFunction MANHATTAN_DISTANCE = new ManhattanDistance();
    public static final DistanceFunction BINARY_DISTANCE = new BinaryDistance();

    public static DistanceFunction getDistFnByName(String distFnName) {
        if (distFnName.equals("EuclideanDistance")) {
            return EUCLIDEAN_DISTANCE;
        } else if (distFnName.equals("ManhattanDistance")) {
            return MANHATTAN_DISTANCE;
        } else if (distFnName.equals("BinaryDistance")) {
            return BINARY_DISTANCE;
        } else {
            throw new IllegalArgumentException("There is no DistanceFunction for the name "
                    + distFnName);
        }
    }
}