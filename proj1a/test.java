public class test
{
    public static void main(String[] args)
    {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.removeLast();
        a.printDeque();
        System.out.println("");
        //a.doubleSize();
        a.printDeque();
        System.out.println("");
        //a.halveSize();
        a.printDeque();


        System.out.println("");
        ArrayDeque<Integer> b = new ArrayDeque<Integer>();
        b.addLast(1);
        b.addLast(2);
        b.addLast(3);
        b.removeFirst();
        b.printDeque();
        System.out.println("");
        //b.doubleSize();
        b.printDeque();
        System.out.println("");
        //b.halveSize();
        b.printDeque();

        System.out.println("");
        ArrayDeque<Integer> c = new ArrayDeque<Integer>();
        c.addLast(1);
        c.addLast(2);
        c.addFirst(3);
        // c.removeFirst();
        c.printDeque();

        System.out.println("");
        //c.doubleSize();
        c.printDeque();
        System.out.println("");
        //c.halveSize();
        c.printDeque();

    }
}
