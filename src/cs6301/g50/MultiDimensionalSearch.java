package cs6301.g50;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class MultiDimensionalSearch {

    class Test{
        int test1;
    }

    class Item{
        long uid;
        long id;
        long[] desc;
        long supplier;
        int price;
    }

    class Supplier{
        long id;
        long reputation;
    }

    HashMap<Long,Item> idMap;
    HashMap<Long,Supplier> supplierMap;
    HashMap<Long,TreeSet<Long>> descMap;
    HashMap<Long,TreeSet<Long>> supIdMap;

    class PriceComparator implements Comparator<Long>{
        public int compare(Long t2, Long t1) {
            long first = idMap.get(t2).price;
            long second = idMap.get(t1).price;
            return first>second?1:first<second?-1:0;
        }
    }

    public MultiDimensionalSearch(){
        idMap = new HashMap<Long,Item>();
    }


}
