package byog.Core;

import org.junit.Test;
import static org.junit.Assert.*;
import static byog.Core.StringInputHandling.*;

public class TestInputHandling {

    @Test
    public void testSeedEndIndex()
    {
        String input;

        input = "N123456S";
        assertEquals(7, seedEndIndex(input));

        input = "n123456s";
        assertEquals(7, seedEndIndex(input));

        input = "N123456Ss";
        assertEquals(7, seedEndIndex(input));

        input = "N123456sS";
        assertEquals(7, seedEndIndex(input));

        input = "";
        assertEquals(-1, seedEndIndex(input));

        input = "N123456";
        assertEquals(-1, seedEndIndex(input));
    }

    @Test
    public void testGetSeed() {
        String input;

        input = "N123456S";
        assertEquals(123456, getSeed(input));

        input = "n1234s";
        assertEquals(1234, getSeed(input));

        input = "N123456Ss";
        assertEquals(123456, getSeed(input));

        input = "N123456sS";
        assertEquals(123456, getSeed(input));
    }

}
