import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {

    /**
     * Array that will cause CountingSort.naiveCountingSort to fail, but
     * CountingSort.betterCountingSort can handle.
     **/
    private static String[] unsorted = {"a", "b", "aa", "bb", "aaa", "bbb"};

    /**
     * Array that both sorts should sort successfully.
     **/
    private static String[] sorted = {"a", "aa", "aaa", "b", "bb", "bbb"};


    @Test
    public void testNaiveWithNonNegative() {
        assertArrayEquals(RadixSort.sort(unsorted), sorted);
    }

}
