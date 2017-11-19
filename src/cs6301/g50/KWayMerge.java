package cs6301.g50;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class KWayMerge<T extends Comparable<T>> {

    private class QueueNode implements Comparable<QueueNode>{
        int arrayIndex, index;
        T value;

        public QueueNode(int arrayIndex, int index, T value){
            this.arrayIndex = arrayIndex;
            this.index = index;
            this.value = value;
        }

        public int compareTo(QueueNode other){
            if(value.compareTo(other.value)==1) return 1;
            if(value.compareTo(other.value)==-1) return -1;
            return 0;
        }
    }

    public ArrayList<T> mergeArrays(ArrayList<ArrayList<T>> inputs){
        PriorityQueue<QueueNode> pQueue = new PriorityQueue<QueueNode>();

        int size = 0;
        for(int i = 0; i<inputs.size(); i++) {
            ArrayList<T> interest = inputs.get(i);
            int isize = interest.size();
            if (isize > 0) {
                size += isize;
                pQueue.add(new QueueNode(i, 0, interest.get(0)));
            }
        }

        ArrayList<T> result = new ArrayList<T>(size);
        int i = 0;
        while(!pQueue.isEmpty()){
            QueueNode q = pQueue.poll();
            result.add(q.value);
            int newIndex = q.index+1;
            ArrayList<T> interest = inputs.get(q.arrayIndex);
            if(newIndex< interest.size()){
                pQueue.add(new QueueNode(q.arrayIndex, newIndex, interest.get(newIndex)));
            }
        }

        return result;
    }
}
