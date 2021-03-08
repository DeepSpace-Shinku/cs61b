/**
 * @source StudentArrayDequeLauncher
 * */

import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void TestStudentArrayDeque() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();
        int testTimes = StdRandom.uniform(100);

        String traceback = addTest(stu, sol, testTimes);
        removeTest(stu, sol, testTimes - 1, traceback);
    }

    /**
     * Add item as the first item to both StudentArrayDeque and ArrayDequeSolution
     */
    private static void bothAddFirst(StudentArrayDeque<Integer> stu, ArrayDequeSolution<Integer> sol, int item)
    {
        stu.addFirst(item);
        sol.addFirst(item);
    }

    /**
     * Remove the first item of both StudentArrayDeque and ArrayDequeSolution
     */
    private static Integer[] bothRemoveFirst(StudentArrayDeque<Integer> stu, ArrayDequeSolution<Integer> sol)
    {
        Integer[] removedItems = new Integer[2];
        removedItems[0] = stu.removeFirst();
        removedItems[1] = sol.removeFirst();
        return removedItems;
    }

    /**
     * Add item as the last item to both StudentArrayDeque and ArrayDequeSolution
     */
    private static void bothAddLast(StudentArrayDeque<Integer> stu, ArrayDequeSolution<Integer> sol, Integer item)
    {
        stu.addLast(item);
        sol.addLast(item);
    }

    /**
     * Remove the last item of both StudentArrayDeque and ArrayDequeSolution
     * And return the values of the removed items
     */
    private static Integer[] bothRemoveLast(StudentArrayDeque<Integer> stu, ArrayDequeSolution<Integer> sol)
    {
        Integer[] removedItems = new Integer[2];
        removedItems[0] = stu.removeLast();
        removedItems[1] = sol.removeLast();
        return removedItems;
    }

    /**
     * Randomly decide to choose whether to choose
     * the first with equal probability for yes or no.
     */
    public static boolean isFirst()
    {
        double numberBetweenZeroAndOne = StdRandom.uniform();
        if (numberBetweenZeroAndOne < 0.5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Do the add test to the both Deque for testTimes times and
     * return a string which describes the trace back information.
     */
    private static String addTest(StudentArrayDeque<Integer> stu, ArrayDequeSolution<Integer> sol, int testTimes)
    {
        String traceback = "";
        for (int i = 0; i < testTimes; i += 1) {
            Integer item = StdRandom.uniform(1000);
            String funcName;
            if (isFirst()){
                bothAddFirst(stu, sol, item);
                funcName = "AddFirst";
            }else{
                bothAddLast(stu, sol, item);
                funcName = "AdLast";
            }
            traceback += stringGenerator(funcName, (int) item);
        }
        return traceback;
    }

    /**
     * Do the remove test to the both Deque for testTimes times and
     * add the traceback information to the given traceback string.
     */
    private static void removeTest(StudentArrayDeque<Integer> stu, ArrayDequeSolution<Integer> sol, int testTimes, String traceback)
    {
        for (int i = 0; i < testTimes; i += 1) {
            Integer[] removedItems;
            String funcName;
            if (isFirst()){
                removedItems = bothRemoveFirst(stu, sol);
                funcName = "removeFirst";
            }else{
                removedItems = bothRemoveLast(stu, sol);
                funcName = "removeLast";
            }
            traceback += stringGenerator(funcName);
            assertEquals(traceback,removedItems[0], removedItems[1]);
        }
    }

    /**
     * Return a string which describes the function call for the given parameter.
     */
    private static String stringGenerator(String functionName, int parameter)
    {
        return functionName + '(' + String.valueOf(parameter) + ")\n";
    }

    /**
     * Return a string which describes the function call without parameter.
     */
    private static String stringGenerator(String functionName)
    {
        return functionName + "()\n";
    }
}
