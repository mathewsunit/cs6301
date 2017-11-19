/**
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 * on 8/25/2017.
 **/

package cs6301.g50;
class InsertionSort<T extends Comparable<? super T>> {

    private T arr[];


    public InsertionSort(T[] arr){
        this.arr=arr;
    }

    public T[] getArr(){
        return arr;

    }


    public static<T extends Comparable<? super T>> void printArray(T[] input){
        for(int j=0;j<input.length;j++){
            System.out.println(input[j]);
        }

    }

    // method to do Insertion sort
    public static<T extends Comparable<? super T>> T[] nSquareSort(T[] input){
        int n = input.length;
        for (int i=1; i<n; ++i)
        {
            T key = input[i];
            int j = i-1;
            while (j>=0 && input[j].compareTo(key)==1)
            {
                input[j+1] = input[j];
                j = j-1;
            }
            input[j+1] = key;
        }
        return input;
    }

}