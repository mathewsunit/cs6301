package cs6301.g50;

import java.util.Iterator;
import java.util.Scanner;

/*
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 *
 */
/*
   Driver Program for SP 7 Question 1
 */
public class SP7_Q2 {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                tree.add(x);
//                tree.printLevelOrder(tree.root);
                tree.displayTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                tree.remove(-x);
//                tree.printLevelOrder(tree.root);
                tree.displayTree();
            } else {
                System.out.println();
                System.out.println("Verification : ");
                tree.printBalanceFactors();
                System.out.println("Contains : 10 ? " + tree.contains(10));
                System.out.println("Contains : 3 ? " + tree.contains(3));

                return;
            }
        }
        System.out.println("Done");
    }
}
