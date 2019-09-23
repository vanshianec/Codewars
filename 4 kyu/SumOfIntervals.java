//Write a function called sumIntervals/sum_intervals() that accepts an array of intervals, and returns the sum of all the interval lengths. Overlapping intervals should only be counted once.
//
//Intervals
//Intervals are represented by a pair of integers in the form of an array. The first value of the interval will always be less than the second value. Interval example: [1, 5] is an interval from 1 to 5. The length of this interval is 4.
//
//Overlapping Intervals
//List containing overlapping intervals:
//
//[
//   [1,4],
//   [7, 10],
//   [3, 5]
//]
//The sum of the lengths of these intervals is 7. Since [1, 4] and [3, 5] overlap, we can treat the interval as [1, 5], which has a length of 4.
//
//Examples:
//// null argument
//Interval.sumIntervals(null);  // => 0
//
//// empty intervals
//Interval.sumIntervals(new int[][]{});  // => 0
//Interval.sumIntervals(new int[][]{2,2}, {5,5});  // => 0
//
//// disjoined intervals
//Interval.sumIntervals(new int[][]{
//  {1,2},{3,5}
//});  // => (2-1) + (5-3) = 3
//
//// overlapping intervals
//Interval.sumIntervals(new int[][]{
//  {1,4},{3,6},{2,8}
//});  // [1,8] => 7

import java.util.*;

public class SumOfIntervals {

    public static int sumIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        intervals = Arrays.stream(intervals).sorted((a, b) -> a[0] - b[0]).toArray(int[][]::new);
        Map<Integer, int[]> joinedIntervals = new HashMap<>();
        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i] == null) {
                continue;
            }
            int currentStart = intervals[i][0];
            int currentEnd = intervals[i][1];
            for (int j = i + 1; j < intervals.length; j++) {
                if (intervals[j] != null) {
                    int nextStart = intervals[j][0];
                    int nextEnd = intervals[j][1];
                    //since the intervals are sorted by start if the next interval's start
                    // is bigger than the current interval's end then that means that all next intervals
                    // can't connect to the current interval either so we break
                    if (nextStart > currentEnd) {
                        break;
                    } else {
                        //examples
                        //current 3 6
                        //next 4 20
                        //...
                        //current 4 20
                        //next 5 6
                        currentEnd = Math.max(currentEnd, nextEnd);
                        joinedIntervals.put(i, new int[]{currentStart, currentEnd});
                        //already joined so no need to check second time
                        intervals[j] = null;
                        intervals[i] = new int[]{currentStart, currentEnd};
                    }
                }
            }
        }

        for (int i = 0; i < intervals.length; i++) {
            if (intervals[i] != null) {
                joinedIntervals.put(i, new int[]{intervals[i][0], intervals[i][1]});
            }
        }

        return joinedIntervals.values().stream().mapToInt(a -> a[1] - a[0]).sum();
    }
}
