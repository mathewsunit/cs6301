package cs6301.g50;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by G50
 * Venkata Sarath Chandra Prasad Nelapati
 * Varun Simha Balaraju
 * Jithin Paul
 * Sunit Mathew
 */
public class BinarySearch<T extends Comparable<? super T>> {
    public static <T extends Comparable<? super T>> int binarySearch(T[] arr, T x) {
        int low = 0;
        int high = arr.length-1;
        while(high-low >= 0){
            int mid = low + (high - low)/2;

            // if the element is actually present in the array - returns the position of element
            if(arr[mid].compareTo(x) == 0){
                return mid;
            }
            // search in lower half
            else if (arr[mid].compareTo(x) > 0){
                high = mid-1;
            }
            // search in upper half
            else {
                low = mid+1;
            }
        }
        // if element is not found, high will point to the index of largest element in the array
        // that is less than given x.
        return high;
    }

    public static void main(String[] args) {
        BinarySearch<Integer> bs = new BinarySearch<>();
        List<Integer> nums = new ArrayList<>();

        Integer numToSearch = 5;

        Random rand = new Random();
        for(int i = 0 ; i < 30 ; i+=rand.nextInt(3)+1){
            nums.add(i);
        }
        System.out.println("Array : ");
        System.out.println(nums);

        Integer[] numsArray = nums.toArray(new Integer[nums.size()]);
        int index = binarySearch(numsArray, numToSearch);
        System.out.println("\nIndex of largest element less than or equal to "+numToSearch+": ( Returns -1 if lowest element in array " +
                "is greater than x )");
        System.out.println(index);
    }
}
