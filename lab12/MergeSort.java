import edu.princeton.cs.algs4.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> singleQueues = new Queue<>();
        while (!items.isEmpty()){
            Queue<Item> singleQueue = new Queue<>();
            singleQueue.enqueue(items.dequeue());
            singleQueues.enqueue(singleQueue);
        }
        return singleQueues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> merge = new Queue<>();
        while (!(q1.isEmpty() && q2.isEmpty())){
            Item min = getMin(q1, q2);
            merge.enqueue(min);
        }
        return merge;
    }

    private static <Item extends Comparable> Queue<Queue<Item>> halve(Queue<Queue<Item>> queueOfQueues)
    {
        Queue<Queue<Item>> half = new Queue<>();
        while (queueOfQueues.size() >= 2){
            Queue<Item> q1 = queueOfQueues.dequeue();
            Queue<Item> q2 = queueOfQueues.dequeue();
            half.enqueue(mergeSortedQueues(q1, q2));
        }
        if (!queueOfQueues.isEmpty()){
            half.enqueue(queueOfQueues.dequeue());
        }
        return half;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> queueOfQueues = makeSingleItemQueues(items);
        while (queueOfQueues.size() > 1){
            queueOfQueues = halve(queueOfQueues);
        }
        Queue<Item> sortedItems;
        if (!queueOfQueues.isEmpty()) {
            sortedItems = queueOfQueues.dequeue();
            while(!items.isEmpty())
            {
                items.dequeue();
            }
            while(!sortedItems.isEmpty())
            {
                items.enqueue(sortedItems.dequeue());
            }
        }

        return items;
    }

    public static void main(String[] args)
    {
        Queue<Integer> q1 = new Queue<>();
        q1.enqueue(6);
        q1.enqueue(3);
        q1.enqueue(8);
        q1.enqueue(2);
        q1.enqueue(9);
        q1.enqueue(1);
        q1.enqueue(4);
        q1.enqueue(2);
        q1.enqueue(6);
        mergeSort(q1);
        assertEquals(9, q1.size());
        assertEquals(1, (int) q1.dequeue());
        assertEquals(2, (int) q1.dequeue());
        assertEquals(2, (int) q1.dequeue());
        assertEquals(3, (int) q1.dequeue());
        assertEquals(4, (int) q1.dequeue());
        assertEquals(6, (int) q1.dequeue());
        assertEquals(6, (int) q1.dequeue());
        assertEquals(8, (int) q1.dequeue());
        assertEquals(9, (int) q1.dequeue());
        assertTrue(q1.isEmpty());
    }
}
