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

package cs6301.g50;
import java.util.Random;

public class SP7_Q4Driver {

    public static void main( String [ ] args )
    {
        Long bst = testTree(new double[]{0.25, 0.35, 0.25}, new BST());
        System.out.println(bst);
        Long spt = testTree(new double[]{0.25, 0.35, 0.25}, new SplayTree());
        System.out.println(spt);
    }

    public static long testTree(double[] probArray, BST t){
        final int NUMS = 15000000;
        Timer timer = new Timer();

        System.out.println( "Test has begun" );
        timer.start();

        for( int i = 1; i != NUMS; i++)
        {
            Double val = new Random().nextDouble();
            Integer tval = new Random().nextInt(i);

            if(val<probArray[0]){
                t.add( tval );
            }
            if(val<probArray[1]){
                t.contains( tval );
            }
            if(val<probArray[2]){
                t.remove( tval );
            }
        }

        timer.end();
        return timer.elapsedTime;
    }

}
