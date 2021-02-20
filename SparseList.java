import java.util.*;

public class SparseList<E> implements List<E> {
    /**
     * The Map corresponding every key, acting as a index, to their value.
     */
    Map<Integer, E> elementData;
    /**
     * The default value of the list elements.
     */
    private E defaultValue;
    /**
     * The size of the list.
     */
    private int size;

    /**
     * This is the no argument constructor for the class SparseList.
     */
    public SparseList(){
        elementData = new HashMap<>();
        this.size = 0;
        defaultValue = null;
    }

    /**
     * This is the overloaded constructor for the class SparseList.
     * Initializes the default value of the list elements the given element.
     *
     * @param element is the default value of the list elements.
     */
    public SparseList(E element){
        this();
        defaultValue = element;
    }

    /**
     * This methods returns the size of the list.
     *
     * @return the size of the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * This methods checks if the list is empty.
     *
     * @return true if the list is empty; false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return elementData.isEmpty();
    }

    /**
     * This method adds an element to the list.
     *
     * @param e is the element to be added.
     * @return true if the element was added; false otherwise.
     */
    @Override
    public boolean add(E e) {
        elementData.put(size++, e);
        return true;
    }

    /**
     * This method clears the list of all its elements.
     */
    @Override
    public void clear() {
        elementData.clear();
    }

    /**
     * This method gets the value of a list element at a specific index.
     *
     * @param index is the index of the element on the list.
     * @return the element of the list at the given index.
     */
    @Override
    public E get(int index) {
        if(elementData.get(index) == null){
            return defaultValue;
        }
        else{
            return elementData.get(index);
        }
    }

    /**
     * This method sets the given element to the list at the given index.
     *
     * @param index is the index at which the element will be set.
     * @param element is the value being set at the given index.
     * @return the element set at the given index.
     */
    @Override
    public E set(int index, E element) {
        if(index < 0){
            throw new IndexOutOfBoundsException();
        }
        else{
            elementData.put(index, element);
            return elementData.get(index);
        }
    }

    /**
     * This method adds an element to the list at the given index.
     *
     * @param index is the index at which the element will be added.
     * @param element is the element being added at the given index.
     */
    @Override
    public void add(int index, E element) {
        if(index > size){
            size = index;
        }

        if(index < 0){
            throw new IndexOutOfBoundsException();
        }
        else if(!elementData.containsKey(index)){
            elementData.put(index, element);
            size++;
        }
        else{
            Map<Integer, E> newElementData = new HashMap<>();
            for(int key = 0; key < size; key++){
                if(key < index){
                    newElementData.put(key, elementData.get(key));
                }
                else if(key >= index){
                    newElementData.put(key+1, elementData.get(key));
                }
            }
            newElementData.put(index, element);
            elementData = newElementData;
            size++;
        }
    }

    /**
     * This method removes an element of the list at the given index.
     *
     * @param index is the index of the element to remove.
     * @return the element being removed from the list.
     */
    @Override
    public E remove(int index) {
        E element = elementData.get(index);
        elementData.remove(index);
        return element;
    }

    /**
     * This method produce a string representation of the list.
     *
     * @return the string representation og the list.
     */
    @Override
    public String toString() {
        String toReturn = "[";
        for(int key = 0; key < size; key++){
            toReturn += String.valueOf(this.get(key));
            if(key < size - 1){
                toReturn += ", ";
            }
        }
        toReturn += "]";
        return toReturn;
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
        throw new UnsupportedOperationException();
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
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
}
