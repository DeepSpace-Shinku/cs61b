public interface Deque<T> {
    public void addFirst(T item);

    public void addLast(T i);

    public boolean isEmpty();

    public T removeFirst();

    public T removeLast();

    public int size();

    public void printDeque();

    public T get(int index);
}
