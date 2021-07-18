import org.junit.Test;
import static org.junit.Assert.*;

public class TestTrieSet {
    @Test
    public void testTrieSet()
    {
        WordTrieSet s = new WordTrieSet();
        assertFalse(s.contains("abc"));
        s.put("abc");
        assertTrue(s.contains("abc"));
        assertFalse(s.contains("ab"));
        s.put("ab");
        assertTrue(s.contains("ab"));
        assertFalse(s.contains("bcd"));
        assertFalse(s.contains(""));
        //assertFalse(s.include(null));
    }
}
