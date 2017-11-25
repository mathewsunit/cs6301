
package cs6301.g50;

import java.util.*;

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

public class MDS {

    final static Long MIN_PLACE_HOLDER = Long.MIN_VALUE;
    HashMap<Long, Set<Long>> dscItemMap;
    HashMap<Long, Set<Long>> itemDscMap;
    HashMap<Long, Float> supRatingMap;
    TreeMap<Float, Set<Long>> ratingSupMap;
    HashMap<Long, TreeMap<Long, Integer>> storeMap;
    Comparator ratingComparator;

    public MDS() {
        itemDscMap = new HashMap<>();
        dscItemMap = new HashMap<>();
        storeMap = new HashMap<>();
        supRatingMap = new HashMap<>();
        ratingSupMap = new TreeMap<>();
        ratingComparator = new HashComparator(supRatingMap).reversed();
    }

    /**
     * Helper function to remove item
     *
     * @param itemId
     * @return number of Long in description of item removed
     */
    public Long removeItemDesc(Long itemId) {

        Long rc = 0L;
        Set<Long> r = itemDscMap.get(itemId);

        if (null == r) return 0L;

        for (Long desc : r) {
            Set<Long> s = dscItemMap.get(desc);
            if (null != s && s.remove(itemId)) rc += desc;
        }

        itemDscMap.remove(itemId);

        return rc;
    }


    /**
     * Helper function to remove array of description from item given id
     * @param itemId
     * @param arr
     * @return number of Long in description of item actually removed
     */
    public int removeItemDesc(Long itemId, Long[] arr) {

        int rc = 0;
        Set<Long> r = itemDscMap.get(itemId);

        for (Long val : arr) {
            Set<Long> s = dscItemMap.get(val);
            if (null != r && r.remove(val) && null != s && s.remove(itemId)) rc++;
        }

        return rc;
    }

    /**
     * add a new item.  If an entry with the same id already exists,
       the new description is merged with the existing description of
       the item.  Returns true if the item is new, and false otherwise.
     * @param id
     * @param description
     * @return Returns true if the item is new, and false otherwise.
     */
    public boolean add(Long id, Long[] description) {

        Set<Long> descSet = new HashSet<Long>(Arrays.asList(description));
        Set<Long> desc = itemDscMap.get(id);

        if (null == desc) {
            desc = new HashSet<>();
            desc.addAll(descSet);
            itemDscMap.put(id, desc);
        } else {
            desc.addAll(descSet);
        }

        return addNewDesc(id, descSet);
    }

    /**
     * Helper function to add description to item
     * @param itemId
     * @param description
     * @return Returns true if the item is new, and false otherwise.
     */
    public boolean addNewDesc(Long itemId, Set<Long> description) {
        boolean rf = true;

        for (Long d : description) {
            Set s = dscItemMap.get(d);
            if (null == s) {
                s = new HashSet<>();
                s.add(itemId);
                dscItemMap.put(d, s);
            } else {
                rf = rf && s.add(itemId);
            }
        }

        return rf;
    }

    /**
     * add a new supplier (Long) and their reputation (float in
       [0.0-5.0], single decimal place). If the supplier exists, their
       reputation is replaced by the new value.  Return true if the
       supplier is new, and false otherwise.
     * @param supplierId
     * @param rep
     * @return Return true if the supplier is new, and false otherwise.
     */

    public boolean add(Long supplierId, Float rep) {
        Float r = supRatingMap.get(supplierId);
        boolean state = true;

        if (null == r) {
            supRatingMap.put(supplierId, rep);
        } else {
            r = rep;
            state = false;
        }

        Set<Long> tree = ratingSupMap.get(rep);

        if (null == tree) {
            tree = new TreeSet<>();
            tree.add(supplierId);
            ratingSupMap.put(rep, tree);
        } else {
            tree.add(supplierId);
        }

        return state;
    }

    /**
     * add products and their prices at which the supplier sells the
      product.  If there is an entry for the price of an id by the
      same supplier, then the price is replaced by the new price.
      Returns the number of new entries created.
     * @param supplierId
     * @param pairs
     * @return Returns the number of new entries created.
     */
    public int add(Long supplierId, Pair[] pairs) {
        int rc = 0;
        Float r = supRatingMap.get(supplierId);

        if (null == r) {
            add(supplierId, 2.5F);
        }

        for (Pair pair : pairs) {
            Long itemId = pair.id;
            Integer price = pair.price;
            Set s = itemDscMap.get(itemId);
            if (null == s) add(itemId, new Long[0]);
            TreeMap<Long, Integer> tm = storeMap.get(itemId);

            if (null == tm) {
                tm = new TreeMap(ratingComparator);
                tm.put(supplierId, price);
                storeMap.put(itemId, tm);
                rc++;
            } else {
                Integer newPrice = tm.get(supplierId);
                if (newPrice == null) rc++;
                tm.put(supplierId, price);
            }
        }

        return rc;
    }

    /**
     * return an array with the description of id.  Return null if there is no item with this id.
     * @param itemId
     * @return Return null if there is no item with this id.

    */
    public Long[] description(Long itemId) {
        Set<Long> l = itemDscMap.get(itemId);
        return l == null ? new Long[0] : l.toArray(new Long[l.size()]);
    }

    /**
     * given an array of Longs, return an array of items whose
     description contains one or more elements of the array, sorted
     by the number of elements of the array that are in the item's
     description (non-increasing order).
     * @param descArr
     * @return array of items whose description contains one or more elements of the array
     */
    public Long[] findItem(Long[] descArr) {
        HashMap<Long, Counter> hashMap = new HashMap<>();

        for (Long desc : descArr) {
            Set<Long> map = dscItemMap.get(desc);
            if (null == map) continue;
            for (Long id : map) {
                Counter c = hashMap.get(id);
                if (c == null) {
                    hashMap.put(id, new Counter());
                } else {
                    c.increment();
                }
            }
        }

        List<Long> list = new LinkedList<>(hashMap.keySet());
        Collections.sort(list, new HashComparator<>(hashMap).reversed());
        return list.toArray(new Long[list.size()]);
    }

    /**
     * given a Long n, return an array of items whose description
     contains n, which have one or more suppliers whose reputation
     meets or exceeds the given minimum reputation, that sell that
     item at a price that falls within the price range [minPrice,
     maxPrice] given.  Items should be sorted in order of their
     minimum price charged by a supplier for that item
     (non-decreasing order).
     * @param n
     * @param minPrice
     * @param maxPrice
     * @param minReputation
     * @return array of items
    */
    public Long[] findItem(Long n, Integer minPrice, Integer maxPrice, Float minReputation) {
        TreeMap<Integer, Long> tree = new TreeMap();
        Set<Long> set = dscItemMap.get(n);
        if (set.size() == 0) return new Long[0];

        supRatingMap.put(MIN_PLACE_HOLDER, (minReputation - 0.01F));

        for (Long s : set) {
            TreeSet<Integer> itemMap = new TreeSet<>();
            TreeMap<Long, Integer> t = storeMap.get(s);
            for (Map.Entry<Long, Integer> entry : t.headMap(MIN_PLACE_HOLDER, false).entrySet()) {
                itemMap.add(entry.getValue());
            }
            SortedSet<Integer> temp = itemMap.subSet(minPrice, maxPrice);
            if (temp.size() == 0) continue;
            tree.put(temp.first(), s);
        }

        supRatingMap.remove(MIN_PLACE_HOLDER);

        LinkedList<Long> fl = new LinkedList<>(tree.values());
        return fl.toArray(new Long[fl.size()]);
    }

    /**
     * given an id, return an array of suppliers who sell that item,
     ordered by the price at which they sell the item (non-decreasing order).
     * @param itemId
     * @return array of suppiers
     *
    */
    public Long[] findSupplier(Long itemId) {
        TreeMap<CustomKV<Integer, Long>, Long> treeMap = new TreeMap<>();
        TreeMap<Long, Integer> t = storeMap.get(itemId);

        for (Map.Entry<Long, Integer> entry : t.entrySet()) {
            treeMap.put(new CustomKV<>(entry.getValue(), entry.getKey()), entry.getKey());
        }

        LinkedList<Long> fl = new LinkedList<>(treeMap.values());
        return fl.toArray(new Long[fl.size()]);
    }

    /**
     * given an id and a minimum
     reputation, return an array of suppliers who sell that item,
     whose reputation meets or exceeds the given reputation.  The
     array should be ordered by the price at which they sell the item
     (non-decreasing order).
     * @param itemId
     * @param rep
     * @return arrays of suppliers
     */
    public Long[] findSupplier(Long itemId, Float rep) {
        TreeMap<CustomKV<Integer, Long>, Long> treeMap = new TreeMap<>();
        supRatingMap.put(MIN_PLACE_HOLDER, (rep - 0.01F));
        TreeMap<Long, Integer> t = storeMap.get(itemId);

        for (Map.Entry<Long, Integer> entry : t.headMap(MIN_PLACE_HOLDER, false).entrySet()) {
            treeMap.put(new CustomKV<>(entry.getValue(), entry.getKey()), entry.getKey());
        }

        supRatingMap.remove(MIN_PLACE_HOLDER);

        LinkedList<Long> fl = new LinkedList<>(treeMap.values());
        return fl.toArray(new Long[fl.size()]);
    }

    /**
     * find suppliers selling 5 or more products, who have the same
       identical profile as another supplier: same reputation, and,
       sell the same set of products, at identical prices.  This is a
       rare operation, so do not do additional work in the other
       operations so that this operation is fast.  Creative solutions
       that are elegant and efficient will be awarded excellence credit.
       Return array of suppliers satisfying above condition.  Make sure
       that each supplier appears only once in the returned array.
     * @return array of suppliers
     */
    public Long[] identical() {
        HashMap<Float, Counter> counterMap = new HashMap<>();

        for (Map.Entry<Long, TreeMap<Long, Integer>> entry : storeMap.entrySet()) {
            TreeMap<Long, Integer> supplierSet = entry.getValue();
            if (supplierSet.size() == 0) continue;
            Float firstRep = supRatingMap.get(supplierSet.firstKey());
            Set<Long> repSet = ratingSupMap.get(firstRep);
            if (repSet.containsAll(supplierSet.keySet())) {
                Counter c = counterMap.get(firstRep);
                if (null == c) {
                    counterMap.put(firstRep, new Counter());
                } else {
                    c.increment();
                }
            }
        }

        LinkedList<Long> fl = new LinkedList<>();
        for (Map.Entry<Float, Counter> entry : counterMap.entrySet()) {
            if (entry.getValue().getCount() > 4) fl.addAll(ratingSupMap.get(entry.getKey()));
        }
        return fl.toArray(new Long[fl.size()]);
    }

    /**
    /* given an array of ids, find the total price of those items, if
       those items were purchased at the lowest prices, but only from
       sellers meeting or exceeding the given minimum reputation.
       Each item can be purchased from a different seller.
     * @param arr
     * @param minReputation
     * @return int of total price of items
    */
    public int invoice(Long[] arr, float minReputation) {
        int fp = 0;
        supRatingMap.put(MIN_PLACE_HOLDER, (minReputation - 0.01F));

        for (Long a : arr) {
            TreeMap<Long, Integer> t = storeMap.get(a);
            int min = Integer.MAX_VALUE;

            for (Map.Entry<Long, Integer> entry : t.headMap(MIN_PLACE_HOLDER, false).entrySet()) {
                int price = entry.getValue();
                if (price < min) {
                    min = price;
                }
            }

            fp += min == Integer.MAX_VALUE ? 0 : min;
        }

        supRatingMap.remove(MIN_PLACE_HOLDER);

        return fp;
    }

    /**
    /* remove all items, all of whose suppliers have a reputation that
       is equal or lower than the given maximum reputation.  Returns
       an array with the items removed.
     * @param maxRep
     * @return array with items removed
     */
    public Long[] purge(Float maxRep) {
        Collection<Set<Long>> fC = ratingSupMap.subMap(0.0F, maxRep + 0.01F).values();

        if (null == fC) return new Long[0];
        Collection collection = new HashSet();
        for (Set s : fC) collection.addAll(s);

        Set<Long> arr = new HashSet<>();

        for (Map.Entry<Long, TreeMap<Long, Integer>> entry : storeMap.entrySet()) {
            if (collection.containsAll(entry.getValue().keySet())) arr.add(entry.getKey());
        }

        for (Long l : arr) remove(l);

        return arr.toArray(new Long[arr.size()]);
    }

    /**
    /* remove item from storage.  Returns the sum of the Longs that
       are in the description of the item deleted (or 0, if such an id
       did not exist).
     * @param itemId
     * @return sum of longs in description of items deleted
     */
    public Long remove(Long itemId) {

        TreeMap<Long, Integer> tm = storeMap.get(itemId);

        if (null != tm) storeMap.remove(itemId);

        return removeItemDesc(itemId);
    }

    /**
    /* remove from the given id's description those elements that are
       in the given array.  It is possible that some elements of the
       array are not part of the item's description.  Return the
       number of elements that were actually removed from the description.
     * @param id
     * @param arr
     * @return number of elements actually removed
     */
    public int remove(Long id, Long[] arr) {
        return removeItemDesc(id, arr);
    }

    /**
    /* remove the elements of the array from the description of all
       items.  Return the number of items that lost one or more terms
       from their descriptions.
     * @param arr
     * @return number of items that lost one or more terms from their descriptions
     */
    public int removeAll(Long[] arr) {

        Set<Long> fSet = new HashSet<>();

        for (Long val : arr) {
            Set<Long> s = dscItemMap.get(val);

            if (null != s) {
                fSet.addAll(s);
                for (Long v : s) {
                    Set<Long> vS = itemDscMap.get(v);
                    if (null != vS) vS.remove(val);
                }
            }

            dscItemMap.remove(val);
        }

        return fSet.size();
    }

    /**
     * Pair class used in inputs
     */
    public static class Pair {
        long id;
        int price;

        public Pair(long id, int price) {
            this.id = id;
            this.price = price;
        }
    }

    /**
     * hash Comparator that compares first the Values then compares the Keys if values are equal
     * Uses a HashTable passed at item of creation to retrieve values
     * @param <K>
     * @param <V>
     */
    class HashComparator<K extends Comparable, V extends Comparable> implements Comparator<K> {
        HashMap<K, V> builtInHash;

        public HashComparator(HashMap<K, V> builtInHash) {
            this.builtInHash = builtInHash;
        }

        public int compare(K t1, K t2) {
            V temp1 = builtInHash.get(t1);
            V temp2 = builtInHash.get(t2);
            int result = temp1.compareTo(temp2);

            return result == 0 ? t1.compareTo(t2) : temp1.compareTo(temp2);
        }
    }

    /**
     * Counter class to ease counting
     */
    class Counter implements Comparable<Counter>{
        int count;

        public Counter() {
            count = 1;
        }

        public int getCount() {
            return count;
        }

        public void increment() {
            count++;
        }

        public int compareTo(Counter counter) {
            return this.count > counter.count ? 1 : this.count < counter.count ? -1 : 0;
        }
    }

    /**
     * Key Value class to implement some Key Value comparison in case of Values being equal
     *
     * @param <K>
     * @param <V>
     */
    class CustomKV<K extends Comparable, V extends Comparable> implements Comparable<CustomKV> {
        K k;
        V v;

        CustomKV(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public int compareTo(CustomKV customKV) {
            int i = k.compareTo(customKV.k);
            return i == 0 ? v.compareTo(customKV.v) : i;
        }
    }
}
