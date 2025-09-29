package algorithms.array;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KadaneAlgorithmTest {

    @Test
    void testFindMaximumSubarrayAllPositive() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {1, 2, 3, 4};
        KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
        assertEquals(10, result.maxSum);
        assertEquals(0, result.startIndex);
        assertEquals(3, result.endIndex);
    }

    @Test
    void testFindMaximumSubarrayMixed() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
        assertEquals(6, result.maxSum);
        assertEquals(3, result.startIndex);
        assertEquals(6, result.endIndex);
    }

    @Test
    void testFindMaximumSubarrayAllNegative() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {-5, -2, -8, -1};
        KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
        assertEquals(-1, result.maxSum);
        assertEquals(3, result.startIndex);
        assertEquals(3, result.endIndex);
    }

    @Test
    void testFindMaximumSubarrayEmpty() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {};
        KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
        assertEquals(0, result.maxSum);
        assertEquals(-1, result.startIndex);
        assertEquals(-1, result.endIndex);
    }

    @Test
    void testFindMaximumCircularSubarray() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {5, -3, 5};
        KadaneAlgorithm.Result result = kadane.findMaximumCircularSubarray(array);
        assertEquals(10, result.maxSum);
    }

    @Test
    void testFindMaximumCircularSubarrayNonCircular() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        KadaneAlgorithm.Result result = kadane.findMaximumCircularSubarray(array);
        assertEquals(6, result.maxSum);
    }

    @Test
    void testFindMaximumSubarrayOptimizedAllPositive() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {1, 2, 3, 4, 5};
        KadaneAlgorithm.Result result = kadane.findMaximumSubarrayOptimized(array);
        assertEquals(15, result.maxSum);
        assertEquals(0, result.startIndex);
        assertEquals(4, result.endIndex);
    }

    @Test
    void testFindMaximumSubarraySingleElement() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {7};
        KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
        assertEquals(7, result.maxSum);
        assertEquals(0, result.startIndex);
        assertEquals(0, result.endIndex);
    }

    @Test
    void testFindMaximumSubarrayAllZeros() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();
        int[] array = {0, 0, 0, 0};
        KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
        assertEquals(0, result.maxSum);
    }
}