/**
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
import java.util.*;

public class ListOps<T extends Comparable<? super T>> {

    public static <T extends Comparable<? super T>> void
    union(List<T> list1, List<T> list2, List<T> outList) {
        Iterator<T> iterator1 = list1.iterator();
        Iterator<T> iterator2 = list2.iterator();

        T item1 = iterator1.hasNext() ? iterator1.next() : null;
        T item2 = iterator2.hasNext() ? iterator2.next() : null;

        // to eliminate duplicates
        T lastAdded = null;

        while (item1 != null && item2 != null) {
            if (item1.compareTo(item2) < 0) {
                if (lastAdded == null || lastAdded.compareTo(item1) != 0) {
                    outList.add(item1);
                    lastAdded = item1;
                }
                item1 = iterator1.hasNext() ? iterator1.next() : null;
            } else if (item1.compareTo(item2) > 0) {
                if (lastAdded == null || lastAdded.compareTo(item2) != 0) {
                    outList.add(item2);
                    lastAdded = item2;
                }
                item2 = iterator2.hasNext() ? iterator2.next() : null;
            } else {
                if (lastAdded == null || lastAdded.compareTo(item1) != 0) {
                    outList.add(item1);
                    lastAdded = item1;
                }
                item1 = iterator1.hasNext() ? iterator1.next() : null;
                item2 = iterator2.hasNext() ? iterator2.next() : null;
            }
        }

        while (item1 != null) {
            if (lastAdded.compareTo(item1) != 0 || lastAdded == null) {
                outList.add(item1);
                lastAdded = item1;
            }
            item1 = iterator1.hasNext() ? iterator1.next() : null;
        }

        while (item2 != null) {
            if (lastAdded.compareTo(item2) != 0 || lastAdded == null) {
                outList.add(item2);
                lastAdded = item2;
            }
            item2 = iterator2.hasNext() ? iterator2.next() : null;
        }

    }


    public static <T extends Comparable<? super T>> void
    intersect(List<T> list1, List<T> list2, List<T> outList) {
        Iterator<T> iterator1 = list1.iterator();
        Iterator<T> iterator2 = list2.iterator();

        T item1 = iterator1.hasNext() ? iterator1.next() : null;
        T item2 = iterator2.hasNext() ? iterator2.next() : null;

        while (item1 != null && item2 != null) {
            // add to outList only if both list has a common element or else increment
            // one of the list accordingly
            if (item1.compareTo(item2) == 0) {
                outList.add(item1);
                item1 = iterator1.hasNext() ? iterator1.next() : null;
                item2 = iterator2.hasNext() ? iterator2.next() : null;
            } else if (item1.compareTo(item2) > 0) {
                item2 = iterator2.hasNext() ? iterator2.next() : null;
            } else
                item1 = iterator1.hasNext() ? iterator1.next() : null;
        }

    }

    public static <T extends Comparable<? super T>>
    void difference(List<T> l1, List<T> l2, List<T> outList) {
        Iterator<T> iterator1 = l1.iterator();
        Iterator<T> iterator2 = l2.iterator();

        T item1 = iterator1.hasNext() ? iterator1.next() : null;
        T item2 = iterator2.hasNext() ? iterator2.next() : null;

        // to eliminate duplicates
        T lastAdded = null;

        /*
        * Iterates till first list goes empty.
         */
        while (item1 != null) {
            // if second list is empty just copy all the elements in first list
            if (item2 == null) {
                if (lastAdded == null || lastAdded.compareTo(item1) != 0) {
                    outList.add(item1);
                    lastAdded = item1;
                }
                item1 = iterator1.hasNext() ? iterator1.next() : null;
                continue;
            } else if (item1.compareTo(item2) < 0) {
                if (lastAdded == null || lastAdded.compareTo(item1) != 0) {
                    outList.add(item1);
                    lastAdded = item1;
                }
                item1 = iterator1.hasNext() ? iterator1.next() : null;
            } else if (item1.compareTo(item2) > 0) {
                item2 = iterator2.hasNext() ? iterator2.next() : null;
            } else {
                item1 = iterator1.hasNext() ? iterator1.next() : null;
            }
        }
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ListOps<Integer> ops = new ListOps<>();
        Random rand = new Random();

        List<Integer> li1 = null, li2 = null;
        int testCases = 3;
        System.out.println("=============================================================================");
        while (testCases-- > 0) {
            System.out.println("Test Case : "+(3-testCases)+"\n");
            li1 = new ArrayList<>();
            li2 = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                li1.add(rand.nextInt(20));
                li2.add(rand.nextInt(20));
            }
            Collections.sort(li1);
            Collections.sort(li2);
            List<Integer> out = new ArrayList<>();
            intersect(li1, li2, out);
            System.out.println("List 1: " + li1);
            System.out.println("\nList 2: " + li2);
            System.out.println("\nIntersection: " + out);

            out = new ArrayList<>();
            union(li1, li2, out);
            System.out.println("\nUnion: " + out);

            out = new ArrayList<>();
            difference(li1, li2, out);
            System.out.println("\nDifference : " + out);
            System.out.println("=============================================================================");
        }
    }
}
