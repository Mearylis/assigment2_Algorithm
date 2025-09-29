package algorithms.metrics;

/**
 * Tracks performance metrics for algorithm analysis
 */
public class PerformanceTracker {
    private final String algorithmName;
    private long startTime;
    private long endTime;
    private long comparisons;
    private long swaps;
    private long arrayAccesses;
    private long memoryAllocations;

    public PerformanceTracker(String algorithmName) {
        this.algorithmName = algorithmName;
        resetCounters();
    }

    public void startTimer() {
        this.startTime = System.nanoTime();
    }

    public void stopTimer() {
        this.endTime = System.nanoTime();
    }

    public void resetCounters() {
        this.comparisons = 0;
        this.swaps = 0;
        this.arrayAccesses = 0;
        this.memoryAllocations = 0;
        this.startTime = 0;
        this.endTime = 0;
    }

    // Metric recording methods
    public void recordComparison() { this.comparisons++; }
    public void recordComparisons(int count) { this.comparisons += count; }
    public void recordSwap() { this.swaps++; }
    public void recordSwaps(int count) { this.swaps += count; }
    public void recordArrayAccess() { this.arrayAccesses++; }
    public void recordArrayAccess(int count) { this.arrayAccesses += count; }
    public void recordMemoryAllocation() { this.memoryAllocations++; }
    public void recordMemoryAllocations(int count) { this.memoryAllocations += count; }

    // Getters
    public long getExecutionTime() { return endTime - startTime; }
    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getMemoryAllocations() { return memoryAllocations; }
    public String getAlgorithmName() { return algorithmName; }

    public void printMetrics() {
        System.out.println("=== " + algorithmName + " Performance Metrics ===");
        System.out.printf("Execution Time: %,d ns%n", getExecutionTime());
        System.out.printf("Comparisons: %,d%n", comparisons);
        System.out.printf("Array Accesses: %,d%n", arrayAccesses);
        System.out.printf("Memory Allocations: %,d%n", memoryAllocations);
        System.out.println("=================================");
    }

    public String getMetricsCSV() {
        return String.format("%s,%d,%d,%d,%d",
                algorithmName, getExecutionTime(), comparisons, arrayAccesses, memoryAllocations);
    }
}