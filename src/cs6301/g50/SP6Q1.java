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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class SP6Q1{

    /*
    * Driver function to do a k-merge; the sorting is done externally and takes arrays of constant size;
    * There is no function to get sorted arrays externally, as it is redundant
    * Instead it creates random arrays and sorts them and passed the sorted arrays to the function
    *
    * */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of arrays: ");
        int arraysSize = scanner.nextInt();
        System.out.println("Enter number of values in each array: ");
        int nodeSize = scanner.nextInt();
        Random r = new Random();

        ArrayList arrays = new ArrayList<>(arraysSize);

        for(int i =0; i< arraysSize; i++){
            ArrayList<Integer> nodes = new ArrayList<Integer>(nodeSize);
            for(int j =0; j< nodeSize; j++){
                nodes.add(r.nextInt(1000000));
            }
            Collections.sort(nodes);
            System.out.println(String.format("Values are %stack",nodes));
            arrays.add(nodes);
        }

        KWayMerge kWayMerge = new KWayMerge();
        ArrayList results = kWayMerge.mergeArrays(arrays);

        System.out.println("Final List: "+results);
    }

}
