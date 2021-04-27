import edu.princeton.cs.algs4.Queue;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestSort {
    /** To make sure APIs are private,
     *  the tests are commented.
    @Test
    public void testMakeSingleItemQueues()
    {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        Queue<Queue<String>> qStudents = MergeSort.makeSingleItemQueues(students);
        Queue<String> aliceQueue = new Queue<>();
        aliceQueue.enqueue("Alice");
        System.out.println(qStudents.dequeue());
        System.out.println(qStudents.dequeue());
        System.out.println(qStudents.dequeue());
    }

    @Test
    public void testMergeSortQueues()
    {
        Queue<String> q1 = new Queue<String>();
        Queue<String> q2 = new Queue<String>();
        q1.enqueue("Alice");
        q1.enqueue("Vanessa");
        q2.enqueue("Ethan");
        Queue<String> merged = MergeSort.mergeSortedQueues(q1, q2);
        assertEquals("Alice", merged.dequeue());
        assertEquals("Ethan", merged.dequeue());
        assertEquals("Vanessa", merged.dequeue());
    }

    @Test
    public void testMergeSort()
    {
        Queue<String> q1 = new Queue<String>();
        q1.enqueue("Alice");
        q1.enqueue("Vanessa");
        q1.enqueue("Ethan");
        Queue<String> q2 = MergeSort.mergeSort(q1);
        assertEquals("Alice", q2.dequeue());
        assertEquals("Ethan", q2.dequeue());
        assertEquals("Vanessa", q2.dequeue());
    }

    @Test
    public void testQuickSort()
    {
        return;
    }
    */
}
