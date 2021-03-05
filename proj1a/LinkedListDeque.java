public class LinkedListDeque<T>
{
    private Node sentinel;
    private int size;

    public class Node
    {
        public Node prev;
        public Node next;
        T item;

        // Initialize the node with item i, prev p, next n
        public Node(T i, Node p, Node n)
        {
            item = i;
            next = n;
            prev = p;
        }
    }

    // Create an empty Linked List Deque.
    public LinkedListDeque()
    {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    // Add item i to the first position.
    public void addFirst(T i)
    {
        sentinel.next = new Node(i, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    // Add item i to the last position.
    public  void addLast(T i)
    {
        sentinel.prev = new Node(i, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty()
    {
        return sentinel.prev == sentinel && sentinel.next == sentinel;
    }

    public int size()
    {
        return size;
    }

    public void printDeque()
    {
        Node ptr = sentinel.next;
        while(ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }

    public T removeFirst()
    {
        if(this.isEmpty()) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return item;
    }

    public T removeLast()
    {
        if(this.isEmpty()) {
            return null;
        }
        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    // Get the item with the given index.
    public T get(int index)
    {
        Node ptr = sentinel.next;
        while(index != 0)
        {
            if(ptr == sentinel || index < 0) {
                return null;
            }
            ptr = ptr.next;
            index -= 1;
        }
        if(ptr == sentinel){
            return null;
        }
        else{
            return ptr.item;
        }
    }

    // Get the item with the given index using recursion.
    public T getRecursive(int index)
    {
        return getRecursive(sentinel.next, index);
    }

    // Helper method for the public getRecursive.
    private T getRecursive(Node n, int index)
    {
        if(n == sentinel || index < 0){
            return null;
        }
        if(index == 0){
            return n.item;
        }
        else{
            return getRecursive(n.next, index - 1);
        }
    }

}
