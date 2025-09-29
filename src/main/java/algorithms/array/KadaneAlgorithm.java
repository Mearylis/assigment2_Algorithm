package algorithms.array;

import algorithms.metrics.PerformanceTracker;

/**
 * Kadane's Algorithm implementation for maximum subarray problem
 * Finds contiguous subarray with maximum sum in O(n) time and O(1) space
 */
public class KadaneAlgorithm {
    private final PerformanceTracker tracker;

    public static class Result {
        public final int maxSum;
        public final int startIndex;
        public final int endIndex;
        public final int[] subarray;

        public Result(int maxSum, int startIndex, int endIndex, int[] originalArray) {
            this.maxSum = maxSum;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            if (startIndex >= 0 && endIndex >= startIndex) {
                this.subarray = new int[endIndex - startIndex + 1];
                System.arraycopy(originalArray, startIndex, subarray, 0, subarray.length);
            } else {
                this.subarray = new int[0];
            }
        }

        @Override
        public String toString() {
            return String.format("MaxSum: %d, Range: [%d, %d], Subarray: %s",
                    maxSum, startIndex, endIndex, java.util.Arrays.toString(subarray));
        }
    }

    public KadaneAlgorithm() {
        this.tracker = new PerformanceTracker("KadaneAlgorithm");
    }

    /**
     * Standard Kadane's algorithm with position tracking
     */
    public Result findMaximumSubarray(int[] array) {
        tracker.startTimer();
        tracker.resetCounters();

        if (array == null || array.length == 0) {
            tracker.stopTimer();
            return new Result(0, -1, -1, new int[0]);
        }

        int maxSoFar = array[0];
        int maxEndingHere = array[0];
        int start = 0, end = 0;
        int tempStart = 0;

        tracker.recordArrayAccess(2);

        for (int i = 1; i < array.length; i++) {
            tracker.recordArrayAccess(1);
            tracker.recordComparison();

            if (maxEndingHere + array[i] > array[i]) {
                maxEndingHere += array[i];
            } else {
                maxEndingHere = array[i];
                tempStart = i;
            }

            tracker.recordComparison();
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                start = tempStart;
                end = i;
            }
        }

        tracker.stopTimer();
        return new Result(maxSoFar, start, end, array);
    }

    /**
     * Handles case when array is circular (wraps around)
     */
    public Result findMaximumCircularSubarray(int[] array) {
        tracker.startTimer();
        tracker.resetCounters();

        if (array == null || array.length == 0) {
            tracker.stopTimer();
            return new Result(0, -1, -1, new int[0]);
        }

        // Case 1: Maximum subarray doesn't wrap (standard Kadane)
        Result kadaneResult = findMaximumSubarray(array);

        // Case 2: Maximum subarray wraps around (total - minimum subarray)
        int totalSum = 0;
        for (int num : array) {
            totalSum += num;
            tracker.recordArrayAccess(1);
        }

        // Find minimum subarray using inverted Kadane
        int minSubarraySum = findMinimumSubarray(array);
        int maxWrapped = totalSum - minSubarraySum;

        // Special case: all negative numbers
        if (maxWrapped == 0 && kadaneResult.maxSum < 0) {
            tracker.stopTimer();
            return kadaneResult;
        }

        tracker.recordComparison();
        if (maxWrapped > kadaneResult.maxSum) {
            // For circular case, we need to find the actual indices
            Result minSubarray = findMinimumSubarrayWithIndices(array);
            int start = (minSubarray.endIndex + 1) % array.length;
            int end = (minSubarray.startIndex - 1 + array.length) % array.length;
            tracker.stopTimer();
            return new Result(maxWrapped, start, end, array);
        } else {
            tracker.stopTimer();
            return kadaneResult;
        }
    }

    /**
     * Optimization: Kadane with early termination for positive arrays
     */
    public Result findMaximumSubarrayOptimized(int[] array) {
        tracker.startTimer();
        tracker.resetCounters();

        if (array == null || array.length == 0) {
            tracker.stopTimer();
            return new Result(0, -1, -1, new int[0]);
        }

        // Early check: if all positive, return entire array
        boolean allPositive = true;
        for (int num : array) {
            tracker.recordArrayAccess(1);
            tracker.recordComparison();
            if (num < 0) {
                allPositive = false;
                break;
            }
        }

        if (allPositive) {
            int total = 0;
            for (int num : array) {
                total += num;
                tracker.recordArrayAccess(1);
            }
            tracker.stopTimer();
            return new Result(total, 0, array.length - 1, array);
        }

        // Otherwise use standard algorithm
        return findMaximumSubarray(array);
    }

    private int findMinimumSubarray(int[] array) {
        int minEndingHere = array[0];
        int minSoFar = array[0];

        tracker.recordArrayAccess(2);

        for (int i = 1; i < array.length; i++) {
            tracker.recordArrayAccess(1);
            tracker.recordComparison();
            minEndingHere = Math.min(array[i], minEndingHere + array[i]);
            minSoFar = Math.min(minSoFar, minEndingHere);
        }

        return minSoFar;
    }

    private Result findMinimumSubarrayWithIndices(int[] array) {
        int minSoFar = array[0];
        int minEndingHere = array[0];
        int start = 0, end = 0;
        int tempStart = 0;

        for (int i = 1; i < array.length; i++) {
            if (minEndingHere + array[i] < array[i]) {
                minEndingHere = array[i];
                tempStart = i;
            } else {
                minEndingHere += array[i];
            }

            if (minEndingHere < minSoFar) {
                minSoFar = minEndingHere;
                start = tempStart;
                end = i;
            }
        }

        return new Result(minSoFar, start, end, array);
    }

    public PerformanceTracker getTracker() {
        return tracker;
    }
}