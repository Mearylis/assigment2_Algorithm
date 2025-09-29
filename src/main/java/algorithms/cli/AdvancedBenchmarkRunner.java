package algorithms.cli;

import algorithms.array.BoyerMooreMajorityVote;
import algorithms.array.KadaneAlgorithm;
import java.util.*;
import java.io.*;

public class AdvancedBenchmarkRunner {
    private static final int[] SIZES = {100, 1000, 10000};
    private static final String CSV_HEADER = "Algorithm,Size,Time(ns),Comparisons,ArrayAccesses,MemoryAllocations\n";

    public static void main(String[] args) {
        System.out.println("=== Advanced Linear Algorithms Benchmark ===");

        try {
            benchmarkBoyerMooreComprehensive();
            benchmarkKadaneComprehensive();
            generateCSVReports();
            generateTextPlots();

            System.out.println("=== Benchmark COMPLETED ===");
        } catch (Exception e) {
            System.err.println("Error during benchmark: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateCSVReports() {
        File docsDir = new File("docs");
        if (!docsDir.exists()) {
            docsDir.mkdirs();
        }

        String csvPath = "docs/performance_metrics.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvPath))) {
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

            System.out.println("CSV report generated: " + csvPath);

        } catch (IOException e) {
            System.err.println("ERROR generating CSV report: " + e.getMessage());
        }
    }

    private static void generateTextPlots() {
        System.out.println("\nPERFORMANCE ANALYSIS");
        System.out.println("====================");

        File plotsDir = new File("docs/performance-plots");
        if (!plotsDir.exists()) {
            plotsDir.mkdirs();
        }

        // Simple text output
        System.out.println("Execution times collected and saved to CSV.");
        System.out.println("Check docs/performance_metrics.csv for data.");
    }

    private static void benchmarkBoyerMooreComprehensive() {
        System.out.println("\nBoyer-Moore Majority Vote Benchmark:");
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();

        for (int size : SIZES) {
            int[] array = generateArrayWithMajority(size);
            Integer result = bm.findMajority(array);
            System.out.printf("Size: %,7d | Majority: %s%n",
                    size, result != null ? result : "None");
        }
    }

    private static void benchmarkKadaneComprehensive() {
        System.out.println("\nKadane's Algorithm Benchmark:");
        KadaneAlgorithm kadane = new KadaneAlgorithm();

        for (int size : SIZES) {
            int[] array = generateMixedArray(size);
            KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
            System.out.printf("Size: %,7d | MaxSum: %,d%n", size, result.maxSum);
        }
    }

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

        // Shuffle
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
}