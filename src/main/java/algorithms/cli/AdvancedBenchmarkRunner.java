package algorithms.cli;

import algorithms.array.BoyerMooreMajorityVote;
import algorithms.array.KadaneAlgorithm;
import algorithms.metrics.PerformanceTracker;
import java.util.*;
import java.io.*;

public class AdvancedBenchmarkRunner {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final String CSV_HEADER = "Algorithm,Size,Time(ns),Comparisons,ArrayAccesses,MemoryAllocations\n";

    public static void main(String[] args) {
        System.out.println("=== Advanced Linear Algorithms Benchmark ===\n");

        benchmarkBoyerMooreComprehensive();
        benchmarkKadaneComprehensive();
        generateCSVReports();
        generatePerformancePlots();
    }

    private static void benchmarkBoyerMooreComprehensive() {
        System.out.println("=== Boyer-Moore Majority Vote Comprehensive Benchmark ===");
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();

        for (int size : SIZES) {
            // Test different input distributions
            int[][] testArrays = {
                    generateArrayWithMajority(size),
                    generateArrayWithoutMajority(size),
                    generateArrayWithN3Majority(size),
                    generateRandomArray(size)
            };

            String[] testTypes = {"With Majority", "Without Majority", "N/3 Majority", "Random"};

            for (int i = 0; i < testArrays.length; i++) {
                Integer result = bm.findMajority(testArrays[i]);
                System.out.printf("Size: %,7d | Type: %-15s | Majority: %s%n",
                        size, testTypes[i], result != null ? result : "None");
                bm.getTracker().printMetrics();
            }
        }
    }

    private static void benchmarkKadaneComprehensive() {
        System.out.println("\n=== Kadane's Algorithm Comprehensive Benchmark ===");
        KadaneAlgorithm kadane = new KadaneAlgorithm();

        for (int size : SIZES) {
            // Test different input distributions
            int[][] testArrays = {
                    generateAllPositiveArray(size),
                    generateAllNegativeArray(size),
                    generateMixedArray(size),
                    generateCircularFriendlyArray(size)
            };

            String[] testTypes = {"All Positive", "All Negative", "Mixed", "Circular"};

            for (int i = 0; i < testArrays.length; i++) {
                KadaneAlgorithm.Result result = kadane.findMaximumSubarray(testArrays[i]);
                System.out.printf("Size: %,7d | Type: %-15s | MaxSum: %,d%n",
                        size, testTypes[i], result.maxSum);
                kadane.getTracker().printMetrics();

                // Test circular version for mixed arrays
                if (i == 2 || i == 3) {
                    KadaneAlgorithm.Result circularResult = kadane.findMaximumCircularSubarray(testArrays[i]);
                    System.out.printf("Circular - Size: %,7d | Type: %-15s | MaxSum: %,d%n",
                            size, testTypes[i], circularResult.maxSum);
                }
            }
        }
    }

    private static void generateCSVReports() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("performance_metrics.csv"))) {
            writer.write(CSV_HEADER);

            // Boyer-Moore metrics
            BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
            for (int size : SIZES) {
                int[] array = generateArrayWithMajority(size);
                bm.findMajority(array);
                writer.write(bm.getTracker().getMetricsCSV() + "," + size + "\n");
            }

            // Kadane metrics
            KadaneAlgorithm kadane = new KadaneAlgorithm();
            for (int size : SIZES) {
                int[] array = generateMixedArray(size);
                kadane.findMaximumSubarray(array);
                writer.write(kadane.getTracker().getMetricsCSV() + "," + size + "\n");
            }

            System.out.println("CSV report generated: performance_metrics.csv");
        } catch (IOException e) {
            System.err.println("Error generating CSV report: " + e.getMessage());
        }
    }

    private static void generatePerformancePlots() {
        System.out.println("\n=== Generating Performance Analysis ===");

        Map<Integer, Long> bmTimes = new TreeMap<>();
        Map<Integer, Long> kadaneTimes = new TreeMap<>();

        // Collect timing data
        for (int size : SIZES) {
            // Boyer-Moore timing
            BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
            int[] bmArray = generateArrayWithMajority(size);
            long bmStart = System.nanoTime();
            bm.findMajority(bmArray);
            long bmTime = System.nanoTime() - bmStart;
            bmTimes.put(size, bmTime);

            // Kadane timing
            KadaneAlgorithm kadane = new KadaneAlgorithm();
            int[] kadaneArray = generateMixedArray(size);
            long kadaneStart = System.nanoTime();
            kadane.findMaximumSubarray(kadaneArray);
            long kadaneTime = System.nanoTime() - kadaneStart;
            kadaneTimes.put(size, kadaneTime);
        }

        // Print plot data
        System.out.println("\nBoyer-Moore Performance (Size vs Time):");
        bmTimes.forEach((size, time) ->
                System.out.printf("  %,7d | %,10d ns | O(n) verification: %b%n",
                        size, time, verifyLinearComplexity(bmTimes)));

        System.out.println("\nKadane Performance (Size vs Time):");
        kadaneTimes.forEach((size, time) ->
                System.out.printf("  %,7d | %,10d ns | O(n) verification: %b%n",
                        size, time, verifyLinearComplexity(kadaneTimes)));
    }

    private static boolean verifyLinearComplexity(Map<Integer, Long> times) {
        List<Integer> sizes = new ArrayList<>(times.keySet());
        if (sizes.size() < 2) return false;

        // Simple linearity check: time should roughly scale with size
        double prevRatio = 0;
        for (int i = 1; i < sizes.size(); i++) {
            double sizeRatio = (double) sizes.get(i) / sizes.get(i - 1);
            double timeRatio = (double) times.get(sizes.get(i)) / times.get(sizes.get(i - 1));

            if (timeRatio > sizeRatio * 2 || timeRatio < sizeRatio * 0.5) {
                return false;
            }
        }
        return true;
    }

    // Additional array generators for comprehensive testing
    private static int[] generateArrayWithoutMajority(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size / 2 + 2); // Ensure no majority
        }
        return array;
    }

    private static int[] generateArrayWithN3Majority(int size) {
        int[] array = new int[size];
        Random random = new Random();
        int majority1 = random.nextInt(100);
        int majority2 = random.nextInt(100);

        int count1 = size / 3 + 1;
        int count2 = size / 3 + 1;

        for (int i = 0; i < count1; i++) array[i] = majority1;
        for (int i = count1; i < count1 + count2; i++) array[i] = majority2;
        for (int i = count1 + count2; i < size; i++) array[i] = random.nextInt(100);

        // Shuffle
        for (int i = 0; i < size; i++) {
            int j = random.nextInt(size);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        return array;
    }

    private static int[] generateAllPositiveArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100) + 1;
        }
        return array;
    }

    private static int[] generateAllNegativeArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = -random.nextInt(100) - 1;
        }
        return array;
    }

    private static int[] generateCircularFriendlyArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        // Create array where circular subarray might be better
        for (int i = 0; i < size; i++) {
            if (i < size / 4 || i > 3 * size / 4) {
                array[i] = random.nextInt(50) + 1; // Positive ends
            } else {
                array[i] = -random.nextInt(10); // Slightly negative middle
            }
        }
        return array;
    }

    // Keep existing generators from original BenchmarkRunner
    private static int[] generateArrayWithMajority(int size) {
        int[] array = new int[size];
        Random random = new Random();
        int majorityElement = random.nextInt(100);

        int majorityCount = size / 2 + 1;
        for (int i = 0; i < majorityCount; i++) {
            array[i] = majorityElement;
        }

        for (int i = majorityCount; i < size; i++) {
            array[i] = random.nextInt(100);
        }

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
            array[i] = random.nextInt(200) - 100;
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