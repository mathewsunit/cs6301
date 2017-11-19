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

import java.util.Scanner;

public class SP6Q2 {

    /*
    * Driver function to implement Huffman coding
    *
    * */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text to Huffman encode: ");
        String input = scanner.nextLine();

        HuffmanCoding huffCode = new HuffmanCoding();
        HuffmanCoding.HuffResult result = huffCode.encode(input);

        System.out.println("Huffman encoded input: "+ result);
        System.out.println("Huffman decoded input: "+ huffCode.decodeMessage(result.toString(),result.getMapping()));
        System.out.println("Huffman looktable: "+ result.getLookupTable());
    }

}
