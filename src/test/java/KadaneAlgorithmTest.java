package algorithms.array;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KadaneAlgorithmTest {

    @Test
    void testFindMaximumSubarray() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();

        KadaneAlgorithm.Result result = kadane.findMaximumSubarray(
                new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});

        assertEquals(6, result.maxSum);
        assertEquals(3, result.startIndex);
        assertEquals(6, result.endIndex);
    }

    @Test
    void testCircularSubarray() {
        KadaneAlgorithm kadane = new KadaneAlgorithm();

        KadaneAlgorithm.Result result = kadane.findMaximumCircularSubarray(
                new int[]{5, -3, 5});

        assertEquals(10, result.maxSum);
    }
}