import java.util.*;

/**
 * Created by
 * Group 50
 *
 * Venkata Sarath Chandra Prasad Nelapati
 * Varun Simha Balaraju
 * Jithin Paul
 * Sunit Mathew
 **/
public class SelectTopKElements<T extends Comparable<? super T>> {

    // putting all elements in a max heap and removing top K elements.
    List<T> getKLargestElementsByMaxHeap(T[] arr, int k) {
        PriorityQueue<T> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (T each : arr) {
            heap.add(each);
        }
        List<T> list = new ArrayList<>();
        int size = heap.size();
        while (k-- > 0) {
            list.add(heap.poll());
        }
        return list;
    }

    //putting first k elements in a min heap and then for each next element, compare with head of heap and if found
    //bigger than head, remove head and insert the element into heap.
    List<T> getKLargestElementsByMinHeap(T[] arr, int k) {
        PriorityQueue<T> heap = new PriorityQueue<>();
        for (T num : arr) {
            if (heap.size() == k) {
                T top = heap.peek();
                if (top.compareTo(num) < 0) {
                    heap.poll();
                    heap.add(num);
                }
            } else heap.add(num);
        }
        List<T> nums = new ArrayList<>();
        while (!heap.isEmpty())
            nums.add(heap.poll());
        return nums;
    }

    //iterative version of select algorithm, marginally faster than recursive version
    // rearranges the array such that the top K elements are at the right end.
    public int selectKth(T[] arr, int k) {
        if (arr == null || arr.length <= k)
            return 0;

        int left = 0, right = arr.length - 1;

        // if left == right we got to kth position
        while (left < right) {
            int i = left, j = right;
            T pivot = arr[(i + j) / 2];

            while (i < j) {
                // move large values at the end
                if (arr[i].compareTo(pivot) >= 0) {
                    T tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                    j--;
                }
                // the value is smaller than the pivot
                else {
                    i++;
                }
            }
            if (arr[i].compareTo(pivot) > 0)
                i--;
            if (k <= i) {
                right = i;
            } else {
                left = i + 1;
            }
        }
        return arr.length - k;
    }


    public static void main(String[] args) {
        SelectTopKElements<Integer> select = new SelectTopKElements<>();

        Random random = new Random();
        int ARR_SIZE = 32000000;
        int k = 1000;
        Integer[] arr = new Integer[ARR_SIZE];
        for(int i=0;i<ARR_SIZE;i++){
            arr[i] = random.nextInt(10000);
        }
        long start = System.currentTimeMillis();
        List<Integer> list = select.getKLargestElementsByMaxHeap(arr, k);
        long end = System.currentTimeMillis();
        System.out.println("##########################################");
        System.out.println("Array size : "+ARR_SIZE);
        System.out.println("K Value : "+k);
        System.out.println("Time taken to fetch top "+k+" values by MaxHeap method: "+(end-start)+" milliseconds");
        System.out.println("##########################################");
        System.out.println();
        start = System.currentTimeMillis();
        list = select.getKLargestElementsByMinHeap(arr, k);
        end = System.currentTimeMillis();
        System.out.println("##########################################");
        System.out.println("Array size : "+ARR_SIZE);
        System.out.println("K Value : "+k);
        System.out.println("Time taken to fetch top "+k+" values by MinHeap method: "+(end-start)+" milliseconds");
        System.out.println("##########################################");
        System.out.println();
        start = System.currentTimeMillis();
        int index = select.selectKth(arr, k);
        end = System.currentTimeMillis();
        System.out.println("##########################################");
        System.out.println("Array size : "+ARR_SIZE);
        System.out.println("K Value : "+k);
        System.out.println("Time taken to fetch top "+k+" values by Select Algorithm: "+(end-start)+" milliseconds");
        System.out.println("##########################################");


    }
}
