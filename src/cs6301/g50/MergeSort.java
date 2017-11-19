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
class MergeSort {

    private int arr[];
    private int tmp[];

    public MergeSort(int[] arr, int[] tmp){
        this.arr=arr; this.tmp=tmp;
    }

    public int[] getArr(){
        return arr;

    }

    // Public method and it is overloaded to call another private method MergeSort to do actual sorting.
    public static void mergeSort(int[] arr, int[] tmp) {
        int start = 0;
        int end = arr.length - 1;
        mergeSort(arr, tmp, start, end);

    }


    // Method which does actual sorting.

    private static void mergeSort(int[] arr, int[] tmp,int start, int end){

        if(arr==null||tmp==null){
            throw new IllegalArgumentException("Null Arguements were passed");
        }
        if(start>=end){
            return;
        }

        for(int i=start;i<=end;i++){
            tmp[i]=arr[i];
        }

        int midpoint=(start+end)/2;
        // Divide
        mergeSort(tmp,arr,start,midpoint);
        mergeSort(tmp,arr,midpoint+1,end);
        // Conquer
        merge(tmp,arr,start,midpoint,midpoint+1,end);
    }

    // Doing the merge operation
    private static void merge(int[] tmp, int[] arr,int start, int midpoint, int mid_Plusone, int end){

        int i= start;
        int j= mid_Plusone;
        int k= start;

        while(i<=midpoint&&j<=end){

            if(tmp[i]<(tmp[j])){
                arr[k]=tmp[i];
                i+=1;
            }
            else{
                arr[k]=tmp[j];
                j+=1;
            }
            k+=1;
        }
        while(i<=midpoint){
            arr[k]=tmp[i];
            i+=1;
            k+=1;
        }
        while(j<=end){
            arr[k]=tmp[j];
            j+=1;
            k+=1;
        }
    }

}