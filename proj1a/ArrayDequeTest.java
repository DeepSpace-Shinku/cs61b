import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayDequeTest {

    @Test
    public void CheckAdd()
    {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        ArrayDeque<Integer> b = new ArrayDeque<Integer>();
        a.addFirst(1);
        b.addLast(1);
        assertEquals(a.removeFirst(), b.removeLast());
        assertEquals(0, b.size());
        assertEquals(0, a.size());
        assertEquals(true, a.isEmpty());
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.removeLast();
        assertEquals(2, a.size());
        assertEquals(false, a.isEmpty());
        assertEquals(null, a.get(2));



    }
}
