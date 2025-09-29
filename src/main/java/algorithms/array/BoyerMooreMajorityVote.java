package algorithms.array;

import algorithms.metrics.PerformanceTracker;

/**
 * Boyer-Moore Majority Vote Algorithm implementation
 * Finds the majority element (appearing more than n/2 times) in O(n) time and O(1) space
 */
public class BoyerMooreMajorityVote {
    private final PerformanceTracker tracker;

    public BoyerMooreMajorityVote() {
        this.tracker = new PerformanceTracker("BoyerMooreMajorityVote");
    }

    /**
     * Finds majority element using Boyer-Moore algorithm
     * @param array input array
     * @return majority element if exists, null otherwise
     */
    public Integer findMajority(int[] array) {
        tracker.startTimer();
        tracker.resetCounters();

        // Edge cases
        if (array == null || array.length == 0) {
            tracker.stopTimer();
            return null;
        }

        if (array.length == 1) {
            tracker.recordArrayAccess(1);
            tracker.stopTimer();
            return array[0];
        }

        // Phase 1: Find candidate
        int candidate = findCandidate(array);

        // Phase 2: Verify candidate
        boolean isMajority = verifyCandidate(array, candidate);

        tracker.stopTimer();
        return isMajority ? candidate : null;
    }

    /**
     * Extended version to find all elements appearing more than n/3 times
     */
    public java.util.List<Integer> findMajorityElements(int[] array) {
        tracker.startTimer();
        tracker.resetCounters();

        java.util.List<Integer> result = new java.util.ArrayList<>();
        if (array == null || array.length == 0) {
            tracker.stopTimer();
            return result;
        }

        // Find two candidates for n/3 case
        int candidate1 = 0, candidate2 = 0;
        int count1 = 0, count2 = 0;

        for (int num : array) {
            tracker.recordArrayAccess(1);
            tracker.recordComparison();

            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // Verify candidates
        count1 = 0;
        count2 = 0;
        int n = array.length;

        for (int num : array) {
            tracker.recordArrayAccess(1);
            if (num == candidate1) count1++;
            if (num == candidate2) count2++;
        }

        if (count1 > n / 3) result.add(candidate1);
        if (count2 > n / 3 && candidate1 != candidate2) result.add(candidate2);

        tracker.stopTimer();
        return result;
    }

    private int findCandidate(int[] array) {
        int count = 0;
        Integer candidate = null;

        for (int num : array) {
            tracker.recordArrayAccess(1);
            tracker.recordComparison();

            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate != null ? candidate : 0;
    }

    private boolean verifyCandidate(int[] array, int candidate) {
        int count = 0;
        for (int num : array) {
            tracker.recordArrayAccess(1);
            tracker.recordComparison();
            if (num == candidate) {
                count++;
            }
        }
        return count > array.length / 2;
    }

    public PerformanceTracker getTracker() {
        return tracker;
    }
}