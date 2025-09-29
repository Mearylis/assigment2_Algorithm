package algorithms.cli;

import algorithms.array.BoyerMooreMajorityVote;
import algorithms.array.KadaneAlgorithm;
import java.util.*;

public class BenchmarkRunner {
    private static final int[] SIZES = {100, 1000, 10000, 100000};

    public static void main(String[] args) {
        System.out.println("=== Linear Algorithms Benchmark ===\n");

        benchmarkBoyerMoore();
        benchmarkKadane();
        benchmarkComparison();
    }

    private static void benchmarkBoyerMoore() {
        System.out.println("Boyer-Moore Majority Vote:");
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();

        for (int size : SIZES) {
            int[] array = generateArrayWithMajority(size);
            long startTime = System.nanoTime();
            Integer result = bm.findMajority(array);
            long time = System.nanoTime() - startTime;

            System.out.printf("  Size: %,7d | Time: %,10d ns | Majority: %d%n",
                    size, time, result != null ? result : -1);

            bm.getTracker().printMetrics();
        }
    }

    private static void benchmarkKadane() {
        System.out.println("\nKadane's Algorithm:");
        KadaneAlgorithm kadane = new KadaneAlgorithm();

        for (int size : SIZES) {
            int[] array = generateMixedArray(size);
            long startTime = System.nanoTime();
            KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
            long time = System.nanoTime() - startTime;

            System.out.printf("  Size: %,7d | Time: %,10d ns | MaxSum: %,d%n",
                    size, time, result.maxSum);

            kadane.getTracker().printMetrics();
        }
    }

    private static void benchmarkComparison() {
        System.out.println("\n=== Algorithm Comparison ===");

        for (int size : SIZES) {
            int[] array = generateRandomArray(size);

            // Boyer-Moore
            BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
            long start1 = System.nanoTime();
            bm.findMajority(array);
            long time1 = System.nanoTime() - start1;

            // Kadane
            KadaneAlgorithm kadane = new KadaneAlgorithm();
            long start2 = System.nanoTime();
            kadane.findMaximumSubarray(array);
            long time2 = System.nanoTime() - start2;

            System.out.printf("Size: %,7d | Boyer-Moore: %,10d ns | Kadane: %,10d ns%n",
                    size, time1, time2);
        }
    }

    private static int[] generateArrayWithMajority(int size) {
        int[] array = new int[size];
        Random random = new Random();
        int majorityElement = random.nextInt(100);

        // Ensure majority element appears more than n/2 times
        int majorityCount = size / 2 + 1;
        for (int i = 0; i < majorityCount; i++) {
            array[i] = majorityElement;
        }

        // Fill rest with random elements
        for (int i = majorityCount; i < size; i++) {
            array[i] = random.nextInt(100);
        }

        // Shuffle array
        for (int i = 0; i < size; i++) {
            int j = random.nextInt(size);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        return array;
    }

    private static int[] generateMixedArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(200) - 100; // -100 to 100
        }
        return array;
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }
}