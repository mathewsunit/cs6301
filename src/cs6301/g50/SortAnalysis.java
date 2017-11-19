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
import java.util.Scanner;


public class SortAnalysis {

    public static void main (String[] args){

        Timer timer= new Timer();

        Scanner sc = new Scanner(System.in);

        int size=1000000;
        int max_size=16000000;
        System.out.println("Merge Sort Run Time Analysis : ");
        System.out.println("Implementations :");
        System.out.println("1. Generics:");
        System.out.println("2. Normal Implementation :");
        System.out.println("Enter you choice :");
        int analysisChoice = sc.nextInt();
        switch(analysisChoice){

            // Case 1 option is to do Merge Sort with Generics implementation
            case 1:
                System.out.println("Generics Implementation selected ");
                System.out.println("                                 "); // Intentionally Blank

                for(int i=size;i<=max_size;i+=size){
                    Integer[] arr = new Integer[i];
                    Integer[] tmp = new Integer[i];
                    GenericsMergeSort<Integer> m1 = new GenericsMergeSort<>(arr,tmp);
                    for(int j=0;j<arr.length;j++){
                        arr[j]=-j;
                    }
                    timer.start();
                    GenericsMergeSort.mergeSort(arr,tmp);
                    timer.end();
                    System.out.println(i/size+" Million  "+ timer.elapsedTime + "msec ");

                }
                break;

            // Case 2 option does Merge Sort without Generics.
            case 2:
                System.out.println("Normal Implementation selected ");
                System.out.println("                                 "); // Intentionally Blank

                for(int i=size;i<=max_size;i+=size){
                    int[] arr = new int[i];
                    int[] tmp = new int[i];
                    MergeSort m2 = new MergeSort(arr,tmp);
                    for(int j=0;j<arr.length;j++){
                        arr[j]=-j;
                    }
                    timer.start();
                    MergeSort.mergeSort(arr,tmp);
                    timer.end();
                    System.out.println(i/size+" Million  "+ timer.elapsedTime + "msec ");

                }
                break;
            default:

                break;
        }


    }
}
