/*
 * TimeSeries.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.timeseries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TimeSeries {

    private static final int[] ZERO_ARRAY = new int[0];
    private static final boolean DEFAULT_IS_TIME_1ST_COL = true;
    private static final char DEFAULT_DELIMITER = ',';
    private static final boolean DEFAULT_IS_LABELED = true;

    public List<String> labels; // labels for each column
    public List<Double> timeReadings; // ArrayList of Double
    public List<TimeSeriesPoint> tsArray; // ArrayList of TimeSeriesPoint..
                                          // no time

    public TimeSeries(List<String> labels, List<Double> timeReadings, List<TimeSeriesPoint> tsArray) {
        this.labels = labels;
        this.timeReadings = timeReadings;
        this.tsArray = tsArray;
    }

    public TimeSeries(int numOfDimensions) {
        this(createLabels(numOfDimensions), new ArrayList<Double>(numOfDimensions),
                new ArrayList<TimeSeriesPoint>(numOfDimensions));
    }

    private static List<String> createLabels(int numOfDimensions) {
        List<String> labels = new ArrayList<String>(numOfDimensions + 1);
        labels.add("Time");
        for (int x = 0; x < numOfDimensions; x++)
            labels.add("" + x);
        return labels;
    }

    public static TimeSeries create(String inputFile, boolean isFirstColTime) {
        return create(inputFile, ZERO_ARRAY, isFirstColTime);
    }

    public static TimeSeries create(String inputFile, char delimiter) {
        return create(inputFile, ZERO_ARRAY, DEFAULT_IS_TIME_1ST_COL, DEFAULT_IS_LABELED, delimiter);
    }

    public static TimeSeries create(String inputFile, boolean isFirstColTime, char delimiter) {
        return create(inputFile, ZERO_ARRAY, isFirstColTime, DEFAULT_IS_LABELED, delimiter);
    }

    public static TimeSeries create(String inputFile, boolean isFirstColTime, boolean isLabeled,
            char delimiter) {
        return create(inputFile, ZERO_ARRAY, isFirstColTime, isLabeled, delimiter);
    }

    public static TimeSeries create(String inputFile, int[] colToInclude, boolean isFirstColTime) {
        return create(inputFile, colToInclude, isFirstColTime, DEFAULT_IS_LABELED,
                DEFAULT_DELIMITER);
    }

    public static TimeSeries create(String inputFile, int[] colToInclude, boolean isFirstColTime,
            boolean isLabeled, char delimiter) {
        List<String> labels = new ArrayList<String>();
        ArrayList<Double> timeReadings = new ArrayList<Double>();
        ArrayList<TimeSeriesPoint> tsArray = new ArrayList<TimeSeriesPoint>();

        BufferedReader br = null;
        try {
            // Record the Label names (fropm the top row.of the input file).
            br = new BufferedReader(new FileReader(inputFile)); // open the
                                                                // input file
            String line = br.readLine(); // the top row that contains attribiute
                                         // names.
            StringTokenizer st = new StringTokenizer(line, String.valueOf(delimiter));

            if (isLabeled) {
                int currentCol = 0;
                while (st.hasMoreTokens()) {
                    final String currentToken = st.nextToken();
                    if ((colToInclude.length == 0) || (contains(colToInclude, currentCol)))
                        labels.add(currentToken);

                    currentCol++;
                } // end while loop

                // Make sure that the first column is labeled is for Time.
                if (labels.size() == 0)
                    throw new RuntimeException("ERROR:  The first row must contain label "
                            + "information, it is empty!");
                else if (!isFirstColTime)
                    labels.add(0, "Time");
                else if (isFirstColTime && !((String) labels.get(0)).equalsIgnoreCase("Time"))
                    throw new RuntimeException(
                            "ERROR:  The time column (1st col) in a time series must be labeled as 'Time', '"
                                    + labels.get(0) + "' was found instead");
            } else // time series file is not labeled
            {
                if ((colToInclude == null) || (colToInclude.length == 0)) {
                    labels.add("Time");
                    if (isFirstColTime)
                        st.nextToken();

                    int currentCol = 1; // TODO input fails gracefully
                    while (st.hasMoreTokens()) {
                        st.nextToken();
                        labels.add(new String("c" + currentCol++)); // TODO add
                                                                    // measurement
                                                                    // with no
                                                                    // time
                    }
                } else {
                    java.util.Arrays.sort(colToInclude);
                    labels.add("Time");
                    for (int c = 0; c < colToInclude.length; c++)
                        if (colToInclude[c] > 0)
                            labels.add(new String("c" + c)); // TODO change to
                                                             // letterNum
                } // end if

                // Close and re-open the file.
                br.close();
                // open the input file
                br = new BufferedReader(new FileReader(inputFile));
            } // end if

            // Read in all of the values in the data file.
            while ((line = br.readLine()) != null) // read lines until end of
                                                   // file
            {
                if (line.length() > 0) // ignore empty lines
                {
                    st = new StringTokenizer(line, ",");

                    // Make sure that the current line has the correct number of
                    // currentLineValues in it.
                    // if (st.countTokens() != (labels.size()+ignoredCol))
                    // throw new RuntimeException("ERROR:  Line " +
                    // (tsArray.size()+1) +
                    // "contains the wrong number of currentLineValues. " +
                    // "expected:  " + (labels.size()+ignoredCol) + ", " +
                    // "found: " + st.countTokens());

                    // Read all currentLineValues in the current line
                    final ArrayList<Double> currentLineValues = new ArrayList<Double>();
                    int currentCol = 0;
                    while (st.hasMoreTokens()) {
                        final String currentToken = st.nextToken();
                        if ((colToInclude.length == 0) || (contains(colToInclude, currentCol))) {
                            // Attempt to parse the next value to a Double
                            // value.
                            final Double nextValue;
                            try {
                                nextValue = Double.valueOf(currentToken);
                            } catch (NumberFormatException e) {
                                throw new RuntimeException("ERROR:  '" + currentToken
                                        + "' is not a valid number");
                            }

                            currentLineValues.add(nextValue);
                        }
                        currentCol++;
                    }

                    // Update the private data with the current Row that has
                    // been read.
                    if (isFirstColTime)
                        timeReadings.add(currentLineValues.get(0));
                    else
                        timeReadings.add(new Double(timeReadings.size()));
                    final int firstMeasurement;
                    if (isFirstColTime)
                        firstMeasurement = 1;
                    else
                        firstMeasurement = 0;
                    final TimeSeriesPoint readings = new TimeSeriesPoint(currentLineValues.subList(
                            firstMeasurement, currentLineValues.size()));
                    tsArray.add(readings);
                }
            }
            return new TimeSeries(labels, timeReadings, tsArray);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("ERROR:  The file '" + inputFile + "' was not found.");
        } catch (IOException e) {
            throw new RuntimeException("ERROR:  Problem reading the file '" + inputFile + "'.");
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    // don't care
                }
        }
    }

    public int size() {
        return timeReadings.size();
    }

    public int numOfDimensions() {
        return labels.size() - 1;
    }

    public double getTimeAtNthPoint(int n) {
        return ((Double) timeReadings.get(n)).doubleValue();
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> newLabels) {
        labels.clear();
        for (int x = 0; x < newLabels.size(); x++)
            labels.add(newLabels.get(x));
    }

    public double getMeasurement(int pointIndex, int valueIndex) {
        return ((TimeSeriesPoint) tsArray.get(pointIndex)).get(valueIndex);
    }

    public double getMeasurement(int pointIndex, String valueLabel) {
        final int valueIndex = labels.indexOf(valueLabel);
        if (valueIndex < 0)
            throw new RuntimeException("ERROR:  the label '" + valueLabel + "' was "
                    + "not one of:  " + labels);

        return ((TimeSeriesPoint) tsArray.get(pointIndex)).get(valueIndex - 1);
    }

    public double[] getMeasurementVector(int pointIndex) {
        return ((TimeSeriesPoint) tsArray.get(pointIndex)).toArray();
    }

    public void addLast(double time, TimeSeriesPoint values) {
        if (labels.size() != values.size() + 1) // labels include a label for
                                                // time
            throw new RuntimeException("ERROR:  The TimeSeriesPoint: " + values
                    + " contains the wrong number of values. " + "expected:  " + labels.size()
                    + ", " + "found: " + values.size());

        if ((this.size() > 0)
                && (time <= ((Double) timeReadings.get(timeReadings.size() - 1)).doubleValue()))
            throw new RuntimeException("ERROR:  The point being inserted at the "
                    + "end of the time series does not have " + "the correct time sequence. ");

        timeReadings.add(new Double(time));
        tsArray.add(values);
    }

    public String toString() {
        final StringBuffer outStr = new StringBuffer();
        /*
         * Write labels for (int x=0; x<labels.size(); x++) {
         * outStr.append(labels.get(x)); if (x < labels.size()-1)
         * outStr.append(","); else outStr.append("\n"); } // end for loop
         */
        // Write the data for each row.
        for (int r = 0; r < timeReadings.size(); r++) {
            // Time
            // outStr.append(timeReadings.get(r).toString());

            // The rest of the value on the row.
            final TimeSeriesPoint values = (TimeSeriesPoint) tsArray.get(r);
            for (int c = 0; c < values.size(); c++)
                outStr.append(values.get(c));

            if (r < timeReadings.size() - 1)
                outStr.append("\n");
        }

        return outStr.toString();
    }

    private static boolean contains(int arr[], int val) {
        for (int x = 0; x < arr.length; x++)
            if (arr[x] == val)
                return true;

        return false;
    }

}