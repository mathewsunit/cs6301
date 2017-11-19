/**
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 **/

package cs6301.g50;
public class qSort {

    void quickSort(int arr[],boolean partitionChoice){
        quickSort(arr,0,arr.length-1,partitionChoice);
    }

    
    
    void quickSort(int arr[],int p, int r,boolean partitionChoice){
        if(p<r&partitionChoice==true){
            int q = partition1(arr, p, r);
            quickSort(arr, p, q-1, true);
            quickSort(arr, q + 1, r,true);
        }
       
        if(p<r&partitionChoice==false){
           int q=partition2(arr,p,r);
           quickSort(arr,p,q,false);
           quickSort(arr,q+1,r,false);
        }
    }

    
    
    /*
       This method implements returns a random index given a range.
       @param int min - lower range value
       @param int r - upper range value.
       @return - a randomly selected integer.
     */
    static int randomIndex(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    
    
     /*
       This method implements the Lomuto's partition for QuickSort
       @param arr[]- int array to be partitioned
       @param int p - receives the start point
       @param int r - receives the end point.
       @return - the index for dividing the array in quickSort method.
     */
    int partition1(int arr[],int p, int r){
        int pivot = randomIndex(p, r);
        swap(arr, pivot, r);
        int x=arr[r];
        int i=p-1;
        for(int j=p;j<r;j++){
            if(arr[j]<=x){
                i=i+1;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,r);
        return i+1;
    }

    
    
    /*
       This method implements the Hoare's partition for QuickSort
       @param arr[]- int array to be partitioned
       @param int p - receives the start point
       @param int r - receives the end point.
       @return - the index for dividing the array in quickSort method.
     */
    int partition2(int arr[],int p, int r){
        int x=arr[p];
        int i=p-1;
        int j=r+1;
        while (true) {
            do {
                i++;
            } while (arr[i] < x);
            do {
                j--;
            } while (arr[j] > x);
            if (i < j) {
                swap(arr, i, j);
            }
            else{
                return j;
            }
        }
    }

    
    
    /*
       Swaps elements given the array and two index numbers.
       @param arr[]- int array to be partitioned
       @param int i - first index
       @param int r - second index.
     */
    private static  void swap(int [] arr, int i, int r) {
        int temp = arr[i];
        arr[i] = arr[r];
        arr[r] = temp;
    }
}
