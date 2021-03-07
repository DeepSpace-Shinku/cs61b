import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    static Palindrome palindrome = new Palindrome();

    // Your tests go here.
    @Test(timeout = 1000)
    public void testOffByN()
    {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('r', 'q'));
        assertFalse(offByOne.equalChars('a', 'e'));
        assertFalse(offByOne.equalChars('z', 'a'));
        assertFalse(offByOne.equalChars('a', 'a'));
    }

    @Test(timeout = 1000)
    public void testIsPalindrome()
    {
        assertFalse(palindrome.isPalindrome("persiflage", offByOne));
        assertTrue(palindrome.isPalindrome("ibonah", offByOne));
        assertTrue(palindrome.isPalindrome("on", offByOne));
        assertTrue(palindrome.isPalindrome("n", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));

    }
}
