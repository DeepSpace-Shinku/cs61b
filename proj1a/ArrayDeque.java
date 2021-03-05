public class ArrayDeque<T> {

    private T[] items;
    private int left_size; // The amount of items that are stored in the left of zero index.
    private int right_size; // The amount of items that are stored in zero index or the right of it.

    public ArrayDeque()
    {
        items = (T []) new Object[8];
        left_size = 0;
        right_size = 0;
    }

    // Double the size of items.
    private void doubleSize()
    {
        T[] new_items = (T []) new Object[2 * items.length];
        int i = 0;
        for(; i < Math.min(left_size, size()); i += 1) {
            System.arraycopy(items, firstIndex() + i, new_items, i, 1);
        }
        for(int j = Math.min(right_size, size()); j > 0; j -= 1, i += 1) {
            System.arraycopy(items, lastIndex() - j + 1, new_items, i, 1);
        }
        left_size = 0;
        right_size = i;
        items = new_items;
    }

    private boolean shouldHalve()
    {
        if(size() * 4 <= items.length && items.length >= 16){
            return true;
        }
        return false;
    }

    private void halveSize()
    {
        T[] new_items = (T []) new Object[ items.length / 2];
        int i = 0;
        for(; i < Math.min(left_size, size()); i += 1) {
            System.arraycopy(items, firstIndex() + i, new_items, i, 1);
        }
        for(int j = Math.min(right_size, size()); j > 0; j -= 1, i += 1) {
            System.arraycopy(items, lastIndex() - j + 1, new_items, i, 1);
        }
        left_size = 0;
        right_size = i;
        items = new_items;
    }

    public void addFirst(T item)
    {
        if(size() == items.length){
            doubleSize();
        }
        left_size += 1;
        items[firstIndex()] = item;
    }

    public void addLast(T item)
    {
        if(size() == items.length){
            doubleSize();
        }
        right_size += 1;
        items[lastIndex()] = item;
    }

    public boolean isEmpty()
    {
        return left_size + right_size == 0;
    }

    public int size()
    {
        return left_size + right_size;
    }

    // return the index of the first element in the array
    private int firstIndex()
    {
        if(left_size > 0){
            return items.length - left_size;
        }
        else{
            return -left_size;
        }
    }

    // return the index of the first element in the array
    private int lastIndex()
    {
        if(right_size > 0){
            return right_size - 1;
        }
        else{
            return items.length + right_size - 1;
        }
    }

    public void printDeque()
    {
       for(int i = 0; i < Math.min(left_size, size()); i += 1) {
            System.out.print(items[firstIndex() + i] + " ");
        }
        for(int i = 0; i < Math.min(right_size, size()); i += 1) {
            System.out.print(items[lastIndex() - Math.min(right_size, size()) + i + 1] + " ");
        }

    }

    public T removeFirst()
    {
        if(size() == 0){
            return null;
        }
        if(shouldHalve()){
            halveSize();
        }
        T first = items[firstIndex()];
        items[firstIndex()] = null;
        left_size -= 1;
        return first;
    }

    public T removeLast()
    {
        if(size() == 0){
            return null;
        }
        if(shouldHalve()){
            halveSize();
        }
        T last = items[lastIndex()];
        items[lastIndex()] = null;
        right_size -= 1;
        return last;
    }

    public T get(int index)
    {
        if(index > size() - 1 || index < 0){
            return null;
        }
        return items[firstIndex() + index];
    }
}
