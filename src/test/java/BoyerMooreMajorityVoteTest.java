package algorithms.array;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BoyerMooreMajorityVoteTest {

    @Test
    void testFindMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();

        // Basic cases
        assertEquals(2, bm.findMajority(new int[]{2, 2, 1, 2}));
        assertEquals(1, bm.findMajority(new int[]{1}));
        assertNull(bm.findMajority(new int[]{1, 2}));
        assertNull(bm.findMajority(new int[]{1, 2, 3}));

        // Edge cases
        assertNull(bm.findMajority(new int[]{}));
        assertNull(bm.findMajority(null));
    }

    @Test
    void testFindMajorityElements() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();

        List<Integer> result = bm.findMajorityElements(new int[]{1, 1, 1, 2, 2, 2});
        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
    }
}