package cs6301.g50;

import java.util.*;

public class MultiDimensionalSearch {

    final static Long MIN_PLACE_HOLDER = Long.MIN_VALUE;
    final static Long MAX_PLACE_HOLDER = Long.MIN_VALUE + 1;
    NumberHeap numberHeap;
    //for desc mapping
    HashMap<Long, Set<Long>> dscItemMap;
    HashMap<Long, Set<Long>> itemDscMap;
    //for supplier mapping
    //supplier rating map
    HashMap<Long, Float> supRatingMap;
    TreeMap<Float, Set<Long>> ratingSupMap;
    //item+supplier=id hash; treeMap based on Price
    HashMap<Long, TreeMap<Long, Long>> storeMap;
    //Price Map
    HashMap<Long, Integer> idPriceMap;
    Comparator ratingComparator;
    HashComparator priceComparator;
    //item supplier map based on rep

    public MultiDimensionalSearch() {
        itemDscMap = new HashMap<>();
        dscItemMap = new HashMap<>();
        storeMap = new HashMap<>();
        supRatingMap = new HashMap<>();
        ratingSupMap = new TreeMap<>();
        idPriceMap = new HashMap<>();
        numberHeap = new NumberHeap();
        priceComparator = new HashComparator(idPriceMap);
        ratingComparator = new HashComparator(supRatingMap).reversed();
    }

    public boolean addNewDesc(Long itemId, Set<Long> description) {
        //check if item already present
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

    public boolean addNewItem(Long itemId, Long[] descArr) {
        //check if item already present
        Set<Long> description = new HashSet<Long>(Arrays.asList(descArr));
        Set<Long> desc = itemDscMap.get(itemId);
        if (null == desc) {
            desc = new HashSet<>();
            desc.addAll(description);
            itemDscMap.put(itemId, desc);
        } else {
            desc.addAll(description);
        }
        return addNewDesc(itemId, description);
    }

    public boolean addNewSupplier(Long supplierId, Float rep) {
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

    public int addSupplierInventory(Long supplierId, MDS.Pair[] pairs) {
        int rc = 0;

        Float r = supRatingMap.get(supplierId);
        if (null == r) {
            addNewSupplier(supplierId, 2.5F);
        }

        for (MDS.Pair pair : pairs) {
            Long itemId = pair.id;
            Integer price = pair.price;
            Set s = itemDscMap.get(itemId);
            if (null == s) addNewItem(itemId, null);

            TreeMap<Long, Long> tm = storeMap.get(itemId);
            if (null == tm) {
                Long iuid = numberHeap.get();
                idPriceMap.put(iuid, price);
                tm = new TreeMap(ratingComparator);
                tm.put(supplierId, iuid);
                storeMap.put(itemId, tm);
                rc++;
            } else {
                Long iuid = tm.get(supplierId);
                if (iuid == null) {
                    iuid = numberHeap.get();
                    idPriceMap.put(iuid, price);
                    tm.put(supplierId, iuid);
                    rc++;
                } else {
                    idPriceMap.put(iuid, price);
                }
            }
        }

        return rc;
    }

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

    public int removeItemDesc(Long itemId, Long[] arr) {

        int rc = 0;
        Set<Long> r = itemDscMap.get(itemId);

        for (Long val : arr) {
            Set<Long> s = dscItemMap.get(val);
            if (null != r && r.remove(val) && null != s && s.remove(itemId)) rc++;
        }

        return rc;
    }

    public Long remove(Long itemId) {

        TreeMap<Long, Long> tm = storeMap.get(itemId);

        if (null != tm) {
            for (Map.Entry<Long, Long> entry : tm.entrySet()) {
                idPriceMap.remove(entry.getValue());
                numberHeap.put(entry.getValue());
            }
            storeMap.remove(itemId);
        }

        return removeItemDesc(itemId);
    }

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

    public Long[] description(Long itemId) {
        Set<Long> l = itemDscMap.get(itemId);
        return l == null ? new Long[0] : l.toArray(new Long[l.size()]);
    }

    public Long[] purge(Float maxRep) {
        Collection<Set<Long>> collection = ratingSupMap.subMap(0.0F, maxRep).values();
        if (null == collection) return new Long[0];
        Set<Long> arr = new HashSet<>();

        for (Map.Entry<Long, TreeMap<Long, Long>> entry : storeMap.entrySet()) {
            Collection<Long> entryCollection = entry.getValue().keySet();
            if (collection.containsAll(entryCollection)) {
                arr.add(entry.getKey());
                remove(entry.getKey());
            }
        }
        return arr.toArray(new Long[arr.size()]);
    }

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
        Collections.sort(list, new CountComparator(hashMap).reversed());
        return list.toArray(new Long[list.size()]);
    }

    public Long[] findItem(Long n, Integer minPrice, Integer maxPrice, Float minReputation) {
        TreeMap<Long, Long> tree = new TreeMap(priceComparator);
        Set<Long> set = dscItemMap.get(n);
        if (set.size() == 0) return new Long[0];

        supRatingMap.put(MIN_PLACE_HOLDER, (minReputation - 0.01F));

        for (Long s : set) {
            TreeMap<Integer, Long> itemMap = new TreeMap();
            TreeMap<Long, Long> t = storeMap.get(s);
            for (Map.Entry<Long, Long> entry : t.headMap(MIN_PLACE_HOLDER, false).entrySet()) {
                itemMap.put(idPriceMap.get(entry.getValue()), entry.getValue());
            }
            SortedMap<Integer, Long> temp = itemMap.subMap(minPrice, maxPrice);
            if (temp.size() == 0) continue;
            tree.put(temp.get(temp.firstKey()), s);
        }

        supRatingMap.remove(MIN_PLACE_HOLDER);

        LinkedList<Long> fl = new LinkedList<>(tree.values());
        return fl.toArray(new Long[fl.size()]);
    }

    public Long[] findSupplier(Long itemId) {
        TreeMap<Long, Long> treeMap = new TreeMap<>(priceComparator);
        TreeMap<Long, Long> t = storeMap.get(itemId);

        for (Map.Entry<Long, Long> entry : t.entrySet()) {
            treeMap.put(entry.getValue(), entry.getKey());
        }

        LinkedList<Long> fl = new LinkedList<>(treeMap.values());
        return fl.toArray(new Long[fl.size()]);
    }

    public Long[] findSupplier(Long itemId, Float rep) {
        TreeMap<Long, Long> treeMap = new TreeMap<>(priceComparator);
        supRatingMap.put(MIN_PLACE_HOLDER, (rep - 0.01F));
        TreeMap<Long, Long> t = storeMap.get(itemId);

        for (Map.Entry<Long, Long> entry : t.headMap(MIN_PLACE_HOLDER, false).entrySet()) {
            treeMap.put(entry.getValue(), entry.getKey());
        }

        supRatingMap.remove(MIN_PLACE_HOLDER);

        LinkedList<Long> fl = new LinkedList<>(treeMap.values());
        return fl.toArray(new Long[fl.size()]);
    }

    public Long[] identical() {

        HashMap<Float, Counter> counterMap = new HashMap<>();

        for (Map.Entry<Long, TreeMap<Long, Long>> entry : storeMap.entrySet()) {
            TreeMap<Long, Long> supplierSet = entry.getValue();
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

    public int invoice(Long[] arr, float minReputation) {

        int fp = 0;

        supRatingMap.put(MIN_PLACE_HOLDER, (minReputation - 0.01F));

        for (Long a : arr) {
            TreeMap<Long, Long> t = storeMap.get(a);
            int min = Integer.MAX_VALUE;

            for (Map.Entry<Long, Long> entry : t.headMap(MIN_PLACE_HOLDER, false).entrySet()) {
                int price = idPriceMap.get(entry.getValue());
                if (price < min) {
                    min = price;
                }
            }

            fp += min == Integer.MAX_VALUE ? 0 : min;
        }

        supRatingMap.remove(MIN_PLACE_HOLDER);

        return fp;
    }

    class NumberHeap {
        PriorityQueue<Long> queue;
        Long max;

        NumberHeap() {
            queue = new PriorityQueue<>();
            max = 0L;
        }

        Long get() {
            if (queue.isEmpty()) {
                return max++;
            } else {
                return queue.poll();
            }
        }

        void put(Long e) {
            queue.add(e);
        }
    }

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

    class Counter {
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
    }

    class CountComparator implements Comparator<Long> {
        HashMap<Long, Counter> counterHash;

        public CountComparator(HashMap<Long, Counter> counterHash) {
            this.counterHash = counterHash;
        }

        @Override
        public int compare(Long t1, Long t2) {
            int l1 = counterHash.get(t1).getCount();
            int l2 = counterHash.get(t2).getCount();
            return l1 > l2 ? 1 : l1 < l2 ? -1 : t1.compareTo(t2);
        }
    }
}
