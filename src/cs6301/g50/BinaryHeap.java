// Ver 1.0:  Starter code for bounded size Binary Heap implementation

package cs6301.g50;
import java.util.Comparator;

public class BinaryHeap<T> {
    T[] pq;
    Comparator<T> c;
    /** Build a priority queue with a given array q, using q[0..n-1].
     *  It is not necessary that n == q.length.
     *  Extra space available can be used to add new elements.
     */
    public BinaryHeap(T[] q, Comparator<T> comp, int n) {
	pq = q;
	c = comp;
    }

    public void insert(T x) {
	add(x);
    }

    public T deleteMin() {
	return remove();
    }

    public T min() { 
	return peek();
    }

    public void add(T x) { /* TO DO. Throw exception if q is full. */
    }

    public T remove() { /* TO DO. Throw exception if q is empty. */
	return null;
    }

    public T peek() { /* TO DO. Throw exception if q is empty. */
	return null;
    }

    public void replace(T x) {
	/* TO DO.  Replaces root of binary heap by x if x has higher priority
	     (smaller) than root, and restore heap order.  Otherwise do nothing. 
	   This operation is used in finding largest k elements in a stream.
	 */
    }

    /** pq[i] may violate heap order with parent */
    void percolateUp(int i) { /* to be implemented */
    }

    /** pq[i] may violate heap order with children */
    void percolateDown(int i) { /* to be implemented */
    }

    /** Create a heap.  Precondition: none. */
    void buildHeap() {
    }

    /* sort array A[].
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public static<T> void heapSort(T[] A, Comparator<T> comp) { /* to be implemented */
    }
}
