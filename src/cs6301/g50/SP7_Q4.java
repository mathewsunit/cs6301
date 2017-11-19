package cs6301.g50;

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
public class SP7_Q4 {
    public static void main( String [ ] args ){
        SplayTree<Integer> t = new SplayTree<>( );
        final int NUMS = 1000;
        for( int i = 0; i != NUMS; i++ ) {
            t.add( i );
            t.get(i-8);
            t.max();
            t.min();
            t.remove(i-3);
            t.contains(i-20);
        }
    }
}
