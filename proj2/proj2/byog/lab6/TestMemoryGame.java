package byog.lab6;

import  org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TestMemoryGame {
    @Test
    public void TestGenerateRandomString()
    {
        MemoryGame mg = new MemoryGame(20, 20, 1234);
        MemoryGameSolution mgs = new MemoryGameSolution(20, 20, 1234);
        assertEquals(mg.generateRandomString(20), mgs.generateRandomString(20));
    }
}
