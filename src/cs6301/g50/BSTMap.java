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
import java.util.Iterator;

public class BSTMap<K extends Comparable<? super K>,V> implements Iterable<K> {

    /* class to store key-value pair*/
	class Pair implements Comparable<Pair>{
		K key;
		V value;
		
		Pair(K key, V value){this.key = key; this.value = value;}

		public int compareTo(Pair pair) {
			return key.compareTo(pair.key);
		}
	}

    RedBlackTree<Pair> rbt;

    /* Constructor of the class */
    BSTMap() {
    	rbt = new RedBlackTree<>();
    }


    /*Get the value associated with the key, if present. Else return null*/
    public V get(K key) {
    	Pair ret = rbt.get(new Pair(key,null));
    	if(ret!=null) return ret.value;
    	return null;
    }

    /* Add a new entry into the map */
    public void put(K key, V value) {
    	rbt.add(new Pair(key,value));
    }

    /* Iterate over the keys stored in the map, in order */
    public Iterator<K> iterator() {
	    return rbt.iterator();
    }


    public static void main( String [ ] args )
    {
        BSTMap<Integer, Integer> t = new BSTMap<>( );
        final int NUMS = 10000000;

        for( int i = 0; i != NUMS; i++ ){
            t.put( i , i+1);
            t.get( i-1);
        }
    }
}
