
import java.util.List;
import java.util.Random;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class SkipList<E> implements List<E> {
    private int size = 0;
    private int listHeight = 1;
    private Node<E> head;
    private Random random;
    private final double p = 0.5;
    private final int MAX_LEVEL = 20;

    /**
     *  Information sourced from the following sites:
     *
     *  https://eclass.yorku.ca/eclass/pluginfile.php/1924040/mod_resource/content/0/05%20Skip%20Lists.pdf
     *  http://hg.openjdk.java.net/jdk8u/jdk8u-dev/jdk/file/c5d02f908fb2/src/share/classes/java/util/AbstractCollection.java
     *  https://cglab.ca/~morin/teaching/5408/refs/p90b.pdf
     *  https://en.wikipedia.org/wiki/Skip_list
     *
     */

    /**
     * The private Node class, that is the foundation of the SkipList data-structure
     * implementation.
     *
     * @param <E> the generic data type.
     */
    private static class Node<E> {
        E element;
        Node<E> forward;
        Node<E> down;
        int distance;

        public Node(E element) {
            this.element = element;
        }
    }

    /**
     * The SkipList default constructor.
     */
    public SkipList() {
        this.head = new Node<>(null);
        random = new Random();
        head.distance = 1;
        head.forward = null;
        head.down = null;
    }

    /**
     * Generates a random integer to be used as a level.
     *
     * @return a random integer to be used as a level.
     */
    private int randomLevel() {
        int level = 1;
        while (random.nextDouble() < p && level < MAX_LEVEL)
            level += 1;
        return level;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list.
     * @return true.
     *
     */
    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    /**
     * This method adds an element to the list at the given index.
     *
     * @param index   is the index at which the element will be added.
     * @param element is the element being added at the given index.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 ||
     *                                   index > size)
     *
     *   Uses code from the slides pg 33. https://eclass.yorku.ca/eclass/pluginfile.php/1924040/mod_resource/content/0/05%20Skip%20Lists.pdf
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        int level = randomLevel();
        if (level > listHeight) {
            for (int i = level; i > listHeight; i--) {
                Node<E> node = new Node<>(null);
                node.down = head;
                node.forward = null;
                node.distance = size + 1;
                head = node;
            }
            listHeight = level;
        }

        int position = 0;
        int currentLevel = listHeight;
        Node<E> lastInserted = new Node<>(element);
        for (Node<E> x = head; x != null; x = x.down, currentLevel--) {
            while (x != null && position + x.distance <= index) {
                position = position + x.distance;
                x = x.forward;
            }
            if (currentLevel > level) {
                x.distance += 1;
            } else {
                Node<E> y = new Node<>(element);
                Node<E> z = x.forward;
                y.forward = z;
                x.forward = y;
                y.distance = position + x.distance - index;
                x.distance = index + 1 - position;
                if (lastInserted != null)
                    lastInserted.down = y;
                lastInserted = y;
            }
        }
        size++;
    }

    /**
     * This method removes an element of the list at the given index.
     *
     * @param index is the index of the element to remove.
     * @return the element being removed from the list.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > size)
     *
     *  uses code from https://opendatastructures.org/ods-java/4_3_SkiplistList_Efficient_.html for the array indexing.
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        E removed = get(index);
        Node[] arr = new Node[MAX_LEVEL];
        Node<E> root = head;
        int position = -1;
        for (int curr = listHeight; curr >= 1; curr--) {
            while (position + root.distance < index) {
                position += root.distance;
                root = root.forward;
            }
            arr[curr] = root;
            if (root.down != null)
                root = root.down;
        }
        root = root.forward;
        for (int i = 1; i <= listHeight; i++) {
            if (arr[i].forward == root) {
                arr[i].forward = root.forward;
                arr[i].distance = arr[i].distance + root.distance - 1;
            } else
                arr[i].distance--;
        }
        size--;
        return removed;
    }

    /**
     * This method gets the value of a list element at a specific index.
     *
     * @param index is the index of the element on the list.
     * @return the element of the list at the given index.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 ||
     *                                   index > size)
     */
    @Override
    public E get(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        int position = 0;
        for (Node<E> root = head; root != null; root = root.down) {

            while (position + root.distance <= index) {
                position = position + root.distance;
                root = root.forward;
            }
            if (position == index) {
                Node<E> x = null;
                for (Node<E> y = root; y != null; y = y.down)
                    x = y;
                x = x.forward;
                return x.element;
            }
        }
        return null;
    }

    /**
     * This methods returns the size of the list.
     *
     * @return the size of the list.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes all of the elements from this list. The list will be empty after this
     * call returns
     */
    @Override
    public void clear() {
        this.head = new Node<>(null);
        this.head.forward = null;
        this.head.down = null;
        this.head.distance = 1;
        this.size = 0;
    }

    /**
     * This method produce a string representation of the list.
     *
     * @return the string representation of the list.
     *
     *  Uses the stringbuilder from http://www.cs.unc.edu/~stotts/COMP410-s20/assn/skipList.html
     */
    @Override
    public String toString() {
        if (this.size == 0)
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (Node<E> node = head; node != null; node = node.down) {
            if (node.down == null) {
                for (Node<E> node2 = node; node2 != null; node2 = node2.forward) {
                    sb.append(" " + node2.element);
                }
            }
        }
        sb.append(" ]");
        sb.delete(sb.indexOf("null"), sb.indexOf("null") + 5);
        return sb.toString();
    }

    /**
     * This method is not supported.
     */
    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    /**
     * This method is not supported.
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public int indexOf(Object o) {
        return 0;
    }

    /**
     * This method is not supported.
     */
    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is not supported.
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}