package algorithms.array;


import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BoyerMooreMajorityVoteTest {

    @Test
    void testFindMajorityWithMajorityElement() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] array = {2, 2, 1, 2, 2, 3, 2};
        Integer result = bm.findMajority(array);
        assertEquals(2, result);
    }

    @Test
    void testFindMajorityWithoutMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] array = {1, 2, 3, 4, 5};
        Integer result = bm.findMajority(array);
        assertNull(result);
    }

    @Test
    void testFindMajorityEmptyArray() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] array = {};
        Integer result = bm.findMajority(array);
        assertNull(result);
    }

    @Test
    void testFindMajorityNullArray() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        Integer result = bm.findMajority(null);
        assertNull(result);
    }

    @Test
    void testFindMajoritySingleElement() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] array = {5};
        Integer result = bm.findMajority(array);
        assertEquals(5, result);
    }

    @Test
    void testFindMajorityElementsN3() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] array = {1, 1, 1, 2, 2, 2, 3, 3};
        List<Integer> result = bm.findMajorityElements(array);
        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
    }

    @Test
    void testFindMajorityElementsSingleMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] array = {1, 1, 1, 2, 3, 4, 5};
        List<Integer> result = bm.findMajorityElements(array);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }

    @Test
    void testFindMajorityElementsNoMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] array = {1, 2, 3, 4, 5, 6};
        List<Integer> result = bm.findMajorityElements(array);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindMajorityElementsAllSame() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] array = {5, 5, 5, 5};
        List<Integer> result = bm.findMajorityElements(array);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0));
    }
}