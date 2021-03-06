import java.util.AbstractQueue;
        import java.util.Arrays;
        import java.util.Iterator;
        import java.util.NoSuchElementException;
/**
 * Used the following resources:
 * https://algs4.cs.princeton.edu/24pq/
 * https://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html
 * https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html#element--
 * https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html#comparator--
 * https://stackoverflow.com/questions/1951091/binary-tree-height
 * https://www.hackerearth.com/practice/notes/heaps-and-priority-queues/
 * https://stackoverflow.com/questions/36385868/java-how-to-print-heap-stored-as-array-level-by-level
 */
public class PriorityQueue2011<E> extends AbstractQueue<E> {

    /**
     * Default capacity of the queue.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The size of the queue.
     */
    private int size;

    /**
     * Array representation of the binary heap
     */
    private E[] elementData;

    /**
     * Create an empty queue of default capacity.
     */
    public PriorityQueue2011() {
        this.elementData = (E[]) new Comparable[DEFAULT_CAPACITY];
        elementData[0] = null;
        size = 0;
    }

    // Not Required
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    /**
     * Inserts the specified element into this priority queue.
     *
     * @param e - the element to add
     * @return true if the element was added to this queue, else false
     */
    @Override
    public boolean offer(E e) {
        if (add(e)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Inserts the specified element into this priority queue.
     *
     * @param a - the element to add
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean add(E a) {
        try {
            this.elementData[++size] = a;
        } catch (IndexOutOfBoundsException exception) {
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity * 2;
            elementData = Arrays.copyOf(elementData, newCapacity);
            elementData[size] = a;
        }
        swim(size);
        return true;
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue
     * is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public E poll() {
        if (size == 0) {
            return null;
        } else {
            E element = elementData[1];
            swap(1, size);
            elementData[size] = null;
            size--;
            sink(1);
            return element;
        }
    }

    /**
     * Retrieves and removes the head of this queue. This method differs from poll
     * only in that it throws an exception if this queue is empty.
     *
     * @throws NoSuchElementException - if this queue is empty
     *
     * @return the head of this queue
     */
    public E remove() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if
     * this queue is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public E peek() {
        if (size == 0) {
            return null;
        } else {
            return elementData[1]; // issue here
        }
    }

    /**
     * Retrieves, but does not remove, the head of this queue. This method differs
     * from peek only in that it throws an exception if this queue is empty.
     *
     * @throws NoSuchElementException - if this queue is empty
     *
     * @return the head of this queue
     */
    @Override
    public E element() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return peek();
        }
    }

    /**
     * Returns the number of elements in this collection. If this collection
     * contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return the number of elements in this collection
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a string representation of this collection. The string representation
     * consists of a list of the collection's elements in the order they are
     * returned by its iterator, enclosed in square brackets ("[]"). Adjacent
     * elements are separated by the characters ", " (comma and space). Elements are
     * converted to strings as by String.valueOf(Object).
     *
     * @return string representation of this collection
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");

        for (int i = 0; i < this.elementData.length - 1; i++) {
            sb.append(elementData[i] + ", ");
            if (elementData[i + 1] == null) {
                break;
            }
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * Returns a String with the tree representation of this collection.
     *
     * @return a String with the tree representation of the heap
     */
    public String toTree() {
        if (size == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int height = (int) (Math.log(size) / Math.log(2));
        for (int h = height; h >= 0; h--) {
            int level = (int) Math.pow(2, h);
            StringBuilder sb2 = new StringBuilder();
            for (int i = level; i < (int) Math.pow(2, h + 1); i++) {
                if (h != height) {
                    sb2.append(" ".repeat((int) Math.pow(2, height - h)));
                }
                int count = height - h;
                if (count >= 2) {
                    count -= 2;
                    while (count >= 0) {
                        sb2.append(" ".repeat((int) Math.pow(2, count)));
                        count--;
                    }
                }

                if (i <= size) {
                    sb2.append(elementData[i].toString());
                } else {
                    sb2.append("  ");
                }
                sb2.append(" ".repeat((int) Math.pow(2, height - h)));
                count = height - h;
                if (count >= 2) {
                    count -= 2;
                    while (count >= 0) {
                        sb2.append(" ".repeat((int) Math.pow(2, count)));
                        count--;
                    }
                }

            }
            sb.insert(0, sb2.toString() + "\n");
        }
        return sb.toString();
    }

    // Helper methods

    /**
     * Compares the element located at the first index to the element located at the second index.
     *
     * @param i - the index the first element
     * @param j - the index the second element
     *
     * @return true if the element at index i is greater than the element at index j, otherwise false
     */
    private boolean greater(int i, int j) {
        return ((Comparable) elementData[i]).compareTo(elementData[j]) >= 0;
    }

    /**
     * Swaps the element located at the first index with the element located at the second index.
     *
     * @param i - the index of the first element
     * @param j - the index of the second element
     */
    private void swap(int i, int j) {
        E temp = elementData[i];
        elementData[i] = elementData[j];
        elementData[j] = temp;
    }

    /**
     * Sorts the priorty queue after adding an element.
     * Moves up the heap.
     *
     * @param k the starting index -last index of the queue
     */
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    /**
     * Sorts the priorty queue after removing an element.
     * Moves down the heap.
     *
     * @param k the starting index - the index of the root(first index)
     */
    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

}
