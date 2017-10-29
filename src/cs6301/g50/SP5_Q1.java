/**
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 **/


/*
    Driver Program for SP5 Question-1. Run Time is displayed at the end after execution of this program.
 */


/**
 * Output : ( " With SHUFFLE of Array" ):
 * Number of Inputs         Partition 1 (RT With Shuffle)                Partition 2 (RT with Shuffle)
 * 1 Million                265 ms                                    230 ms
 * 10 Million               2045ms                                    1982 ms
 * 100 Million              20.675 seconds                            20.842 seconds
 */

/**
 * Output( " With DECREASING ORDER of ARRAY " ):
 * Number of Inputs         Partition 1 ( RT With Shuffle)
 * 1 Million                171 ms
 * 10 Million               1350 ms
 * 100 Million              14.043 seconds
 *
 * Partition 2 for decreasing order of array is inefficient.
 * For size 10,000 =  157 ms
 * Even for 100,000 needs a lot of stack memory.
 */

package cs6301.g50;
public class SP5_Q1 {
	
    public static void main(String[] args){
        int arraySize = 1000000;
        qSort q= new qSort();
        Shuffle s = new Shuffle();
        Timer t= new Timer();
        int[] a = new int[arraySize];
        int b=arraySize;
        for(int i=0;i<arraySize;i++){
            a[i]=b--;
        }


        /* To run QuickSort with
         1) Partition 1 (Lomuto'stack partition) : pass "false" in   q.quickSort(a,true);
         2) Partition 2 (Hoare'stack partition) : pass "true" in   q.quickSort(a,true);
        */
        // Shuffles the array before sorting.
        Shuffle.shuffle(a);  // Comment this if need to test decreasing order of number
        t.start();
        q.quickSort(a,true); // default "true" means partition 1
        t.end();
        //Printing time taken
        System.out.println(t.toString());
    }
}
