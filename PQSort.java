import java.util.PriorityQueue;
/**
 * Used the following resources:
 * https://algs4.cs.princeton.edu/24pq/Heap.java.html
 * https://users.cs.fiu.edu/~weiss/dsj3/code/Sort.java
 */
public class PQSort {

    static <E extends Comparable<? super E>> void heapSort(E[] a) {
        PriorityQueue<E> queue1 = new PriorityQueue<>();
        for (int i = 0; i < a.length; i++) {
            queue1.add(a[i]);
        }
        for (int i = 0; i < a.length; i++) {
            a[i] =  (E) queue1.remove();
        }
    }

    static <E extends Comparable<? super E>> void heapSort2011(E[] a) {
        PriorityQueue2011<E> queue2 = new PriorityQueue2011<>();
        for (int i = 0; i < a.length; i++) {
            queue2.add(a[i]);
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = (E) queue2.remove();
        }
    }
}
