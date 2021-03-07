public class ArrayDeque<T> implements Deque<T>
{

    private T[] items;
    private int size;
    private int firstIndex; //The index of the first item in ArrayDeque

    public ArrayDeque()
    {
        items = (T []) new Object[8];
        size = 0;
        firstIndex = 0;
    }

    private int firstIndex()
    {
        return firstIndex;
    }

    private int lastIndex()
    {
        if (firstIndex + size > items.length){
            return firstIndex + size - items.length - 1;
        }
        return firstIndex + size - 1;
    }

    // Double the size of items.
    private void doubleSize()
    {
        T[] new_items = (T []) new Object[2 * items.length];
        int ptr = firstIndex();
        for(int i = 0; i < size(); i += 1) {
            System.arraycopy(items, ptr, new_items, i, 1);
            ptr = indexAfter(ptr);
        }
        firstIndex = 0;
        items = new_items;
    }

    private boolean shouldHalve()
    {
        if(size() * 4 < items.length && items.length >= 16){
            return true;
        }
        return false;
    }

    private void halveSize()
    {
        /*
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
        items = new_items;*/
        T[] new_items = (T []) new Object[items.length / 2];
        int ptr = firstIndex();
        for(int i = 0; i < size(); i += 1) {
            System.arraycopy(items, ptr, new_items, i, 1);
            ptr = indexAfter(ptr);
        }
        firstIndex = 0;
        items = new_items;
    }

    // Return the index before the given index
    private int indexBefore(int index)
    {
        assert index >= 0;
        if(index == 0){
            return items.length - 1;
        }
        return index -= 1;
    }

    // Return the index after the given index
    private int indexAfter(int index)
    {
        assert index >= 0;
        if(index == items.length - 1){
            return 0;
        }
        return index + 1;
    }
    
    // Add item as the first element of the ArrayDeque
    private void addFirstElement(T item){
        assert isEmpty();
        firstIndex = 0;
        items[0] = item;
        size = 1;
    }

    @Override
    public void addFirst(T item)
    {
        if(isEmpty()){
            addFirstElement(item);
            return;
        }
        if(size() == items.length){
            doubleSize();
        }
        firstIndex = indexBefore(firstIndex());
        size += 1;
        items[firstIndex()] = item;
    }

    @Override
    public void addLast(T item)
    {
        if(size() == items.length){
            doubleSize();
        }
        if(isEmpty()){
            addFirstElement(item);
            return;
        }
        items[indexAfter(lastIndex())] = item;
        size += 1;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }


    @Override
    public void printDeque()
    {
        int ptr = firstIndex();
        for(int i = 0; i < size(); i += 1) {
            System.out.print(items[ptr] + " ");
            ptr = indexAfter(ptr);
        }
    }

    @Override
    public T removeFirst()
    {
        if(isEmpty()){
            return null;
        }
        T first = items[firstIndex()];
        items[firstIndex()] = null;
        firstIndex = indexAfter(firstIndex());
        size -= 1;
        if(shouldHalve()){
            halveSize();
        }
        return first;

    }

    @Override
    public T removeLast()
    {
        if(isEmpty()){
            return null;
        }
        T last = items[lastIndex()];
        items[lastIndex()] = null;
        size -= 1;
        if(shouldHalve()){
            halveSize();
        }
        return last;
    }

    @Override
    public T get(int index)
    {
        if(index > size() - 1 || index < 0){
            return null;
        }
        if(firstIndex() + index >= items.length) {
            return items[firstIndex() + index - items.length];
        }
        return items[firstIndex() + index];
    }
}
