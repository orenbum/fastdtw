/*
 * TimeSeries.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package com.fastdtw.timeseries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.fastdtw.util.Arrays;


public class TimeSeries
{
   private static final int[] ZERO_ARRAY = new int[0];
   private static final boolean DEFAULT_IS_TIME_1ST_COL = true;
   private static final char DEFAULT_DELIMITER = ',';
   private static final boolean DEFAULT_IS_LABELED = true;


   public ArrayList<String> labels;   // labels for each column
   public ArrayList<Double> timeReadings;        // ArrayList of Double
   public ArrayList<TimeSeriesPoint> tsArray;    // ArrayList of TimeSeriesPoint.. no time

                                                                                     // TODO don't use defaults delimiter/1stColTime... determine if not specified

   // CONSTRUCTORS                                                                   // TODO method to peek at determined delimiter, 1st col time
   TimeSeries()
   {
      labels = new ArrayList<String>();                                                      // TODO isLabeled constuctor options?
      timeReadings = new ArrayList<Double>();
      tsArray = new ArrayList<TimeSeriesPoint>();
   }


   public TimeSeries(int numOfDimensions)                                               
   {
      this();
      labels.add("Time");
      for (int x=0; x<numOfDimensions; x++)                
         labels.add(""+x);
   }

   // Copy Constructor
   public TimeSeries(TimeSeries origTS)
   {
      labels = new ArrayList<String>(origTS.labels);
      timeReadings = new ArrayList<Double>(origTS.timeReadings);
      tsArray = new ArrayList<TimeSeriesPoint>(origTS.tsArray);
   }


   public TimeSeries(String inputFile, boolean isFirstColTime)
   {
      this(inputFile, ZERO_ARRAY, isFirstColTime);
   }


   public TimeSeries(String inputFile, char delimiter)
   {
      this(inputFile, ZERO_ARRAY, DEFAULT_IS_TIME_1ST_COL, DEFAULT_IS_LABELED, delimiter);
   }


   public TimeSeries(String inputFile, boolean isFirstColTime, char delimiter)
   {
      this(inputFile, ZERO_ARRAY, isFirstColTime, DEFAULT_IS_LABELED, delimiter);
   }


   public TimeSeries(String inputFile, boolean isFirstColTime, boolean isLabeled, char delimiter)
   {
      this(inputFile, ZERO_ARRAY, isFirstColTime, isLabeled, delimiter);
   }


   public TimeSeries(String inputFile, int[] colToInclude, boolean isFirstColTime)
   {
      this(inputFile, colToInclude, isFirstColTime, DEFAULT_IS_LABELED, DEFAULT_DELIMITER);
   }


   public TimeSeries(String inputFile, int[] colToInclude, boolean isFirstColTime, boolean isLabeled, char delimiter)
   {
      this();

      BufferedReader br=null;
    try
      {
         // Record the Label names (fropm the top row.of the input file).
          br = new BufferedReader (new FileReader (inputFile));  // open the input file
         String line = br.readLine();  // the top row that contains attribiute names.
         StringTokenizer st = new StringTokenizer (line, String.valueOf(delimiter));


         if (isLabeled)
         {
            int currentCol = 0;
            while (st.hasMoreTokens())
            {
               final String currentToken = st.nextToken();
               if ( (colToInclude.length==0) || (Arrays.contains(colToInclude, currentCol)) )
                  labels.add(currentToken);

               currentCol++;
            }  // end while loop

            // Make sure that the first column is labeled is for Time.
            if (labels.size() == 0)
               throw new RuntimeException("ERROR:  The first row must contain label " +
                                       "information, it is empty!");
            else if (!isFirstColTime)
               labels.add(0, "Time");
            else if (isFirstColTime && !((String)labels.get(0)).equalsIgnoreCase("Time"))
               throw new RuntimeException("ERROR:  The time column (1st col) in a time series must be labeled as 'Time', '" +
                                       labels.get(0) + "' was found instead");
         }
         else    // time series file is not labeled
         {
            if ( (colToInclude==null) || (colToInclude.length==0))
            {
               labels.add("Time");
               if (isFirstColTime)
                  st.nextToken();

               int currentCol = 1;                                                 // TODO input fails gracefully
               while (st.hasMoreTokens())
               {
                  st.nextToken();
                  labels.add(new String("c" + currentCol++));                                // TODO add measurement with no time
               }
            }
            else
            {
               java.util.Arrays.sort(colToInclude);
               labels.add("Time");
               for (int c=0; c<colToInclude.length; c++)
                  if (colToInclude[c] > 0)
                     labels.add(new String("c" + c));                      // TODO change to letterNum
            }  // end if

            // Close and re-open the file.
            br.close();
            br = new BufferedReader (new FileReader (inputFile));  // open the input file
         }  // end if


         // Read in all of the values in the data file.
         while ((line = br.readLine()) != null)   // read lines until end of file
         {
            if (line.length() > 0)  // ignore empty lines
            {
               st = new StringTokenizer(line, ",");

               // Make sure that the current line has the correct number of
               //    currentLineValues in it.
    //           if (st.countTokens() != (labels.size()+ignoredCol))
    //              throw new RuntimeException("ERROR:  Line " + (tsArray.size()+1) +
    //                                      "contains the wrong number of currentLineValues. " +
    //                                      "expected:  " + (labels.size()+ignoredCol) + ", " +
    //                                      "found: " + st.countTokens());

               // Read all currentLineValues in the current line
               final ArrayList<Double> currentLineValues = new ArrayList<Double>();
               int currentCol = 0;
               while (st.hasMoreTokens())
               {
                  final String currentToken = st.nextToken();
                  if ( (colToInclude.length==0) || (Arrays.contains(colToInclude, currentCol)) )
                  {
                     // Attempt to parse the next value to a Double value.
                     final Double nextValue;
                     try
                     {
                        nextValue = Double.valueOf(currentToken);
                     }
                     catch (NumberFormatException e)
                     {
                        throw new RuntimeException("ERROR:  '" + currentToken + "' is not a valid number");
                     }

                     currentLineValues.add(nextValue);
                  }  // end if

                  currentCol++;
               }  // end while loop

               // Update the private data with the current Row that has been
               //    read.
               if (isFirstColTime)
                  timeReadings.add(currentLineValues.get(0));
               else
                  timeReadings.add(new Double(timeReadings.size()));
               final int firstMeasurement;
               if (isFirstColTime)
                  firstMeasurement = 1;
               else
                  firstMeasurement = 0;
               final TimeSeriesPoint readings = new TimeSeriesPoint(currentLineValues.subList(firstMeasurement,
                                                                                             currentLineValues.size()));
               tsArray.add(readings);
//               timeValueMap.put(timeReadings.get(timeReadings.size()-1), readings);
            }  // end if
         }  // end while loop
      }
      catch (FileNotFoundException e)
      {
         throw new RuntimeException("ERROR:  The file '" + inputFile + "' was not found.");
      }
      catch (IOException e)
      {
         throw new RuntimeException("ERROR:  Problem reading the file '" + inputFile + "'.");
      }  finally {
          if (br!=null)
            try {
                br.close();
            } catch (IOException e) {
                //don't care
            }
      }
   }  // end constructor



   // FUNCTIONS
   public void save(File outFile) throws IOException
   {
      final PrintWriter out = new PrintWriter(new FileOutputStream(outFile));
      out.write(this.toString());
      out.flush();
      out.close();
   }


   public void clear()
   {
      labels.clear();
      timeReadings.clear();
      tsArray.clear();
 //     timeValueMap.clear();
   }


   public int size()
   {
      return timeReadings.size();
   }


   public int numOfPts()
   {
      return this.size();
   }


   public int numOfDimensions()
   {
      return labels.size()-1;
   }


   public double getTimeAtNthPoint(int n)
   {
      return ((Double)timeReadings.get(n)).doubleValue();
   }


   public String getLabel(int index)
   {
      return (String)labels.get(index);
   }


   public String[] getLabelsArr()
   {
      final String[] labelArr = new String[labels.size()];
      for (int x=0; x<labels.size(); x++)
         labelArr[x] = (String)labels.get(x);
      return labelArr;
   }


   public ArrayList<String> getLabels()
   {
      return labels;
   }


   public void setLabels(String[] newLabels)
   {
      labels.clear();
      for (int x=0; x<newLabels.length; x++)
         labels.add(newLabels[x]);
   }


   public void setLabels(ArrayList<String> newLabels)
   {
      labels.clear();
      for (int x=0; x<newLabels.size(); x++)
         labels.add(newLabels.get(x));
   }


   public double getMeasurement(int pointIndex, int valueIndex)
   {
      return ((TimeSeriesPoint)tsArray.get(pointIndex)).get(valueIndex);
   }


   public double getMeasurement(int pointIndex, String valueLabel)
   {
      final int valueIndex = labels.indexOf(valueLabel);
      if (valueIndex < 0)
         throw new RuntimeException("ERROR:  the label '" + valueLabel + "' was " +
                                 "not one of:  " + labels);

      return ((TimeSeriesPoint)tsArray.get(pointIndex)).get(valueIndex-1);
   }


   public double[] getMeasurementVector(int pointIndex)
   {
      return ((TimeSeriesPoint)tsArray.get(pointIndex)).toArray();
   }


   public void setMeasurement(int pointIndex, int valueIndex, double newValue)
   {
      ((TimeSeriesPoint)tsArray.get(pointIndex)).set(valueIndex, newValue);
   }


   public void addFirst(double time, TimeSeriesPoint values)
   {
      if (labels.size() != values.size()+1)  // labels include a label for time
         throw new RuntimeException("ERROR:  The TimeSeriesPoint: " + values +
                                 " contains the wrong number of values. " +
                                 "expected:  " + labels.size() + ", " +
                                 "found: " + values.size());

      if (time >= (timeReadings.get(0)).doubleValue())
         throw new RuntimeException("ERROR:  The point being inserted into the " +
                                 "beginning of the time series does not have " +
                                 "the correct time sequence. ");

      timeReadings.add(0, new Double(time));
      tsArray.add(0, values);
   }  // end addFirst(..)


   public void addLast(double time, TimeSeriesPoint values)
   {
      if (labels.size() != values.size()+1)  // labels include a label for time
         throw new RuntimeException("ERROR:  The TimeSeriesPoint: " + values +
                                 " contains the wrong number of values. " +
                                 "expected:  " + labels.size() + ", " +
                                 "found: " + values.size());

      if ( (this.size()>0) && (time<=((Double)timeReadings.get(timeReadings.size()-1)).doubleValue()) )
         throw new RuntimeException("ERROR:  The point being inserted at the " +
                                 "end of the time series does not have " +
                                 "the correct time sequence. ");

      timeReadings.add(new Double(time));
      tsArray.add(values);
   }  // end addLast(..)


   public void removeFirst()
   {
      if (this.size() == 0)
         System.err.println("WARNING:  TimeSeriesPoint:removeFirst() called on an empty time series!");
      else
      {
         timeReadings.remove(0);
         tsArray.remove(0);
      }  // end if
   }  // end removeFirst()


   public void removeLast()
   {
      if (this.size() == 0)
         System.err.println("WARNING:  TimeSeriesPoint:removeLast() called on an empty time series!");
      else
      {
         tsArray.remove(timeReadings.size()-1);
         timeReadings.remove( timeReadings.size()-1 );
      }  // end if
   }  // end removeFirst()


   public void normalize()
   {
      // Calculate the mean of each FD.
      final double[] mean = new double[this.numOfDimensions()];
      for (int col=0; col<numOfDimensions(); col++)
      {
         double currentSum = 0.0;
         for (int row=0; row<this.size(); row++)
            currentSum += this.getMeasurement(row, col);

         mean[col] = currentSum / this.size();
      }  // end for loop

      // Calculate the standard deviation of each FD.
      final double[] stdDev = new double[numOfDimensions()];
      for (int col=0; col<numOfDimensions(); col++)
      {
         double variance = 0.0;
         for (int row=0; row<this.size(); row++)
            variance += Math.abs(getMeasurement(row, col)-mean[col]);

         stdDev[col] = variance / this.size();
      }  // end for loop


      // Normalize the values in the data using the mean and standard deviation
      //    for each FD.  =>  Xrc = (Xrc-Mc)/SDc
      for (int row=0; row<this.size(); row++)
      {
         for (int col=0; col<numOfDimensions(); col++)
         {
            // Normalize data point.
            if (stdDev[col] == 0.0)   // prevent divide by zero errors
               setMeasurement(row, col, 0.0);  // stdDev is zero means all pts identical
            else   // typical case
               setMeasurement(row, col, (getMeasurement(row, col)-mean[col]) / stdDev[col]);
         }  // end for loop
      }  // end for loop
   }  // end normalize();



   public String toString()
   {
      final StringBuffer outStr = new StringBuffer();
/*
      // Write labels
      for (int x=0; x<labels.size(); x++)
      {
         outStr.append(labels.get(x));
         if (x < labels.size()-1)
            outStr.append(",");
         else
            outStr.append("\n");
      }  // end for loop
*/
      // Write the data for each row.
      for (int r=0; r<timeReadings.size(); r++)
      {
         // Time
//         outStr.append(timeReadings.get(r).toString());

         // The rest of the value on the row.
         final TimeSeriesPoint values = (TimeSeriesPoint)tsArray.get(r);
         for (int c=0; c<values.size(); c++)
            outStr.append(values.get(c));

         if (r < timeReadings.size()-1)
            outStr.append("\n");
      }  // end for loop

      return outStr.toString();
   }  // end toString()


   protected void setMaxCapacity(int capacity)
   {
      this.timeReadings.ensureCapacity(capacity);
      this.tsArray.ensureCapacity(capacity);
   }



}  // end class TimeSeries
